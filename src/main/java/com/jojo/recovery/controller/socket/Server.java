package com.jojo.recovery.controller.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jojo.recovery.common.domain.AjaxResult;
import com.jojo.recovery.common.enums.JsonResultEnum;
import com.jojo.recovery.model.*;
import com.jojo.recovery.service.BinFullService;
import com.jojo.recovery.service.BoxService;
import com.jojo.recovery.service.UserService;
import lombok.SneakyThrows;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;


import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


/**
 * @author JoJo
 * @Date 2022/7/21 9:37
 **/
@RestController
@RequestMapping("/api/server")
@Slf4j
public class Server {

    /**
     * 客户端集合
     */
    private static List<Socket> clientList = new ArrayList<>();


    public static ConcurrentMap<String, Socket> existSocketClientMap = new ConcurrentHashMap<>();

    /**
     * 客户端心跳时间集合
     */
    private static Map<Socket, Date> heartbeatMap = new HashMap<>(16);

    /**
     * 心跳超时时间
     */
    private static final long TIMEOUT = 180 * 1000;

    /**
     * 服务端端口
     */
    private static final int PORT = 8083;

    /**
     * 以下为与客户端约定的指令，分别是：心跳、心跳回执、退出和退出回执
     */
    private static final String HEARTBEAT = "heartbeat";
    private static final String HEARTBEAT_RECEIPT = "heartbeat_receipt";
    private static final String EXIT = "exit";
    private static final String EXIT_RECEIPT = "exit_receipt";

    public static void main(String[] args) {
        new Server().start();
    }

    @Resource
    UserService userService;
    @Resource
    BoxService boxService;
    @Resource
    BinFullService binFullService;

    /**
     * Socket服务端
     */
    @GetMapping("/start")
    public AjaxResult socketServer() {
        new Server().start();
        return AjaxResult.success();
    }

    /**
     * @return com.jojo.recovery.common.domain.AjaxResult
     * @desc 设备登录
     * @Date 2022/7/21 10:06
     * @Param [paramVo]
     **/
    @PostMapping("/check")
    public AjaxResult sendMsg(ClientParamVo paramVo) throws IOException {
        if (!existSocketClientMap.containsKey(paramVo.getMessage())) {
            return AjaxResult.error("设备未连接");
        }
        User user = userService.getRecord(paramVo.getUserId());
        if (user == null) {
            return AjaxResult.error(JsonResultEnum.USER_NULL);
        }
        Socket client = existSocketClientMap.get(paramVo.getMessage());
        sendMsg(client, "{\"o\":1302,\"d\":{\"user\":\"" + paramVo.getUserId() + "\"}}");
        return AjaxResult.success();
    }

    /**
     * @return com.jojo.recovery.common.domain.AjaxResult
     * @desc 打开箱体
     * @Date 2022/7/21 10:07
     * @Param [binInfo]
     **/
    @PostMapping("/openBin")
    public AjaxResult openBin(BinInfo binInfo) throws IOException {
        if (!existSocketClientMap.containsKey(binInfo.getMessage())) {
            return AjaxResult.error("设备未连接");
        }
        User user = userService.getRecord(binInfo.getUserId());
        if (user == null) {
            return AjaxResult.error(JsonResultEnum.USER_NULL);
        }
        Box box = boxService.getRecordByCode(binInfo.getMessage());
        if (box == null) {
            return AjaxResult.error("箱体不兼容");
        }
        BinFull target = new BinFull();
        target.setBoxId(box.getId());
        target.setNo(binInfo.getNo());
        BinFull binFull = binFullService.getInfoByCodeNo(target);
        if (binFull.getFullValue() == 1) {
            return AjaxResult.error("该回收箱已满");
        }
        Socket client = existSocketClientMap.get(binInfo.getMessage());
        sendMsg(client, "{\"o\":8001,\"d\":{\"bin_no\":" + binInfo.getNo() + ",\"code\":1}}");

        return AjaxResult.success();
    }

    @PostMapping("/code")
    public AjaxResult createCode(Integer id) throws IOException {
        Box box = boxService.getRecord(id);
        if (box == null) {
            return AjaxResult.error("箱体不存在");
        }
        if (!existSocketClientMap.containsKey(box.getCode())) {
            return AjaxResult.error("设备未连接");
        }
        String code = "https://jblhsx.ahwrxx.com/image/message=" + box.getCode();
        Socket client = existSocketClientMap.get(box.getCode());
        sendMsg(client, "{\"o\":3001,\"d\":{\"mini\":\"" + code + "\"}}");
        return AjaxResult.success();
    }


    /**
     * 功能描述:
     * <服务端启动>
     *
     * @return void
     * @author zhoulipu
     * @date 2019/8/8 15:52
     */
    public void start() {
        try {
            // 服务端开启
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("服务端开启，等待客户端连接中...");
            // 循环监听客户端连接
            while (true) {
                // 等待客户端进行连接
                Socket client = server.accept();
                // 将客户端添加到集合
                clientList.add(client);
                System.out.println("有建立连接了，客户端地址：" + client.getRemoteSocketAddress().toString().replace("/", "") + "，当前连接数量：" + clientList.size());
                // 添加首次连接时间作为心跳
                heartbeatMap.put(client, new Date());
                // 开启新线程处理消息
                new MessageListener(client).start();
                // 开启新线程监测心跳
                new HeartbeatListener(client).start();
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    /**
     * 消息处理
     */
    class MessageListener extends Thread {

        private Socket client;


        public MessageListener(Socket socket) {
            this.client = socket;

        }

        @Override
        public void run() {
            try {
                // 客户端连接后立即下发消息
                sendMsg(client, "Connection Success");
                String message;
                String msg;
                // 当心跳存在时，循环处理消息 heartbeatMap.get(client) != null
                while (heartbeatMap.get(client) != null) {
                    // 读取客户端消息
                    Thread.sleep(100);
                    message = receiveMsg(client).trim();
                    msg = message.replaceAll(" +", "");

                    if (msg == null) {
                        // 表示连接已断开，等待心跳监听线程来处理
//                        sendMsg(client, "{\"o\":1101}");
                        Thread.sleep(100);
                        continue;
                    } else if (!msg.contains("{")) {
                        sendMsg(client, "Incompatible Data");
                        client.close();
                    } else if (msg.contains("\"o\":1101")) {
                        // 记录客户端的心跳时间
                        heartbeatMap.put(client, new Date());
                        // 发送回执消息
                        sendMsg(client, "{\"o\":1102}");
                    } else if (EXIT.equals(msg)) {
                        // 客户端主动下线，删除连接和心跳
                        clientList.remove(client);
                        heartbeatMap.remove(client);
                        // 发送回执消息
                        sendMsg(client, EXIT_RECEIPT);
                        // 关闭连接
                        try {
                            client.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("有断开连接了，客户端地址：" + client.getRemoteSocketAddress().toString().replace("/", "") + "，当前连接数量：" + clientList.size());
                    } else {

                        heartbeatMap.put(client, new Date());
                        if (!msg.equals("") && !msg.contains("\"o\":1601")) {
                            log.info("[" + client.getPort() + "]:" + msg);
                        }
                        // 满溢
                        if (msg.contains("\"o\":1601")) {
                            Set<String> keySet = new LinkedHashSet<String>();
                            for (String k : existSocketClientMap.keySet()) {
                                if (existSocketClientMap.get(k).equals(client)) {
                                    keySet.add(k);
                                }
                            }
                            if (keySet.size() != 0) {

                                String code = (String) keySet.toArray()[0];
                                String s2 = msg.split("\\[")[1];
                                String s3 = s2.split("\\]")[0].trim().replaceAll(" +", "");

                                String s4 = "[" + s3 + "]";
//                                System.out.println("full:" + s4);
                                RestTemplate template = new RestTemplate();
                                String url = "https://jbl.ahwrxx.com/api/box/full";
                                // 封装参数，千万不要替换为Map与HashMap，否则参数无法传递
                                MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
                                paramMap.add("code", code);
                                paramMap.add("message", s4);
                                // 1、使用postForObject请求接口
                                AjaxResult result = template.postForObject(url, paramMap, AjaxResult.class);
                            }
                        }
                        // 设备连接
                        if (msg.contains("\"o\":1001")) {
                            String msgStr = msg.substring(14, msg.length() - 1);
//                            log.info("[" + client.getPort() + "]:" + msgStr);
                            Object object = JSON.parse(msgStr);
                            String s = object.toString();
                            Device device = JSONObject.parseObject(s, Device.class);
//
                            RestTemplate template = new RestTemplate();
                            String url = "https://jbl.ahwrxx.com/api/box/check";
                            // 封装参数，千万不要替换为Map与HashMap，否则参数无法传递
                            MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();

                            paramMap.add("code", device.getDevCode());

                            // 1、使用postForObject请求接口
                            AjaxResult result = template.postForObject(url, paramMap, AjaxResult.class);
                            System.out.println(result);

                            if (Objects.equals(result.get("code"), 200)) {
                                sendMsg(client, "{\"o\":1001, \"s\":0, \"d\":{\"dev_code\":\"" + device.getDevCode() + "\"}}\n");
                                existSocketClientMap.put(device.getDevCode(), client);
                            } else {
                                sendMsg(client, "Incompatible Device");
                                client.close();
                            }

                            System.out.println("[" + client.getPort() + "]:" + device.getDevCode());
                        } else {
                            // 下单
                            if (msg.contains("\"o\":1204")) {
                                Set<String> devCode = new LinkedHashSet<String>();
                                for (String k : existSocketClientMap.keySet()) {
                                    if (existSocketClientMap.get(k).equals(client)) {
                                        devCode.add(k);
                                    }
                                }
                                if (devCode.size() != 0) {
                                    msg = msg.trim();
                                    String msgStr = msg.substring(26, msg.length() - 2);
                                    Object object = JSON.parse(msgStr);
                                    String s = object.toString();
                                    BinInfo binInfo = JSONObject.parseObject(s, BinInfo.class);
                                    System.out.println(binInfo);
                                    BigDecimal weight = binInfo.getWeight();

                                    if (weight.compareTo(new BigDecimal(0.00)) == 0) {
                                        log.info("0kg");
                                    } else {
                                        if (weight.compareTo(new BigDecimal(0.00)) == -1) {
                                            weight = weight.multiply(new BigDecimal(-1));
                                        }

                                        RestTemplate template = new RestTemplate();
                                        String url = "https://jbl.ahwrxx.com/api/order/insert";
                                        // 封装参数，千万不要替换为Map与HashMap，否则参数无法传递
                                        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
                                        Integer userId = Integer.parseInt(binInfo.getScan());
                                        Integer bucketId = binInfo.getNo();
                                        String remark = (String) devCode.toArray()[0];
                                        paramMap.add("userId", userId);
                                        paramMap.add("weight", weight);
                                        paramMap.add("remark", remark);
                                        paramMap.add("bucketId", bucketId);
                                        // 1、使用postForObject请求接口
                                        template.postForObject(url, paramMap, AjaxResult.class);

                                    }

                                }

                            }
                        }
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.getStackTrace();
            }
        }
    }

    /**
     * 心跳监测
     */
    class HeartbeatListener extends Thread {

        private Socket client;

        private HeartbeatListener(Socket socket) {
            this.client = socket;
        }

        @SneakyThrows
        @Override
        public void run() {
            Date time, now;
            // 当心跳存在时，循环处理消息
            while ((time = heartbeatMap.get(client)) != null) {
                Thread.sleep(100);
                now = new Date();
                // 比对当前时间和最新心跳时间
                if (now.getTime() - time.getTime() > TIMEOUT) {
                    // 客户端心跳超时（这里当作断开连接处理），删除连接和心跳
                    heartbeatMap.remove(client);
                    clientList.remove(client);
                    for (String k : existSocketClientMap.keySet()) {
                        System.out.println(k);
                        if (existSocketClientMap.get(k).equals(client)) {
                            System.out.println("remark"+k);
                            existSocketClientMap.remove(k);
                        }
                    }

                    // 关闭连接
                    try {
                        client.close();
                        Thread.yield();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("有心跳超时了，客户端地址：" + client.getRemoteSocketAddress().toString().replace("/", "") + "，当前连接数量：" + clientList.size());
                }
            }
        }
    }

    /**
     * 功能描述:
     * <发送消息>
     */
    private void sendMsg(Socket socket, String msg) throws IOException {
        OutputStream out = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(out);
        // 使用pw.write(msg); msg末尾必须加"\n"转义， println自动添加转义
        writer.println(msg);
        writer.flush();
    }

    /**
     * 功能描述:
     * <接受消息>
     */
    private String receiveMsg(Socket socket) throws IOException {
        InputStream in = socket.getInputStream();
        byte b[] = new byte[1024];
        in.read(b);
        String msg = new String(b);
        return msg;
    }

}
