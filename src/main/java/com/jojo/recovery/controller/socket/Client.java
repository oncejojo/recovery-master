package com.jojo.recovery.controller.socket;

import com.alibaba.fastjson.JSONObject;
import com.jojo.recovery.common.domain.AjaxResult;
import com.jojo.recovery.common.enums.JsonResultEnum;
import com.jojo.recovery.model.BinInfo;
import com.jojo.recovery.model.Order;
import com.jojo.recovery.model.User;
import com.jojo.recovery.service.OrderService;
import com.jojo.recovery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.Socket;

/**
 * @Author JoJo
 * @Date 2022/5/25 10:12
 * @Description
 * @Version 1.0
 */
public class Client {
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
    public static void main(String[] args) {
        String msg = "{\"o\":1204,\"d\":{\"bin_info\":{\"no\":1,\"scan\":\"4\",\"weight\":-1.974,\"unit\":\"kg\"}}}";

        String msgStr = msg.trim().substring(26, msg.length()-2);
        System.out.println(msgStr);
        BinInfo weight = JSONObject.parseObject(msgStr, BinInfo.class);
        System.out.println("get weight:" + weight.getWeight());

//        Client client=new Client();
//        client.startAction();
    }


    void readSocketInfo(BufferedReader reader){
        new Thread(new MyRuns(reader)).start();
    }

    class MyRuns implements Runnable{

        BufferedReader reader;

        public MyRuns(BufferedReader reader) {
            super();
            this.reader = reader;
        }

        @Override
        public void run() {
            try {
                String lineString="";
                while( (lineString = reader.readLine())!=null ){
                    System.out.println(lineString);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void startAction(){
        Socket socket=null;
        BufferedReader reader=null;
        BufferedWriter writer=null;
        BufferedReader reader2=null;
        try {
            socket=new Socket("127.0.0.1", 8083);
            reader = new BufferedReader(new InputStreamReader(System.in));
            reader2=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            readSocketInfo(reader2);
            String lineString="";
            while(!(lineString=reader.readLine()).equals("exit")){
                writer.write(lineString+"\n");
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader!=null) {
                    reader.close();
                }
                if (writer!=null) {
                    writer.close();
                }
                if (socket!=null) {
                    socket.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

}
