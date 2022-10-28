package com.jojo.recovery.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


/**
 * @author JoJo
 * @Date 2022/4/13 15:32
 **/
public class ImageFileUtil {

    public static String sendGetFile(String url, String filePath) {
        String sResponse = "";
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost uploadFile = new HttpPost(url);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            File f = new File(filePath);
            System.out.println("isFile:" + f.isFile() + "---filePath:" + f.getPath());
            // 把文件加到HTTP的post请求中
            builder.addBinaryBody(
                    "imageFile", new FileInputStream(f), ContentType.APPLICATION_OCTET_STREAM, f.getName());
            HttpEntity multipart = builder.build();
            uploadFile.setEntity(multipart);
            CloseableHttpResponse response = httpClient.execute(uploadFile);
            HttpEntity responseEntity = response.getEntity();
            sResponse = EntityUtils.toString(responseEntity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(JSONObject.parseObject(sResponse));
        return sResponse;
    }

    public static void main(String[] args) throws Exception {
        sendGetFile(
                "https://comentropy.com/cropweb/datamodel/garbagedetection",
                "D:\\jojo\\demo\\src\\main\\resources\\static\\admin\\images\\six.png");
    }


    public static File urlToFile(String path) {
        // 对本地文件命名，path是http的完整路径，主要得到资源的名字
        String newUrl = path;
        newUrl = newUrl.split("[?]")[0];
        String[] bb = newUrl.split("/");
        // 得到最后一个分隔符后的名字
        String fileName = bb[bb.length - 1];

        // 保存到本地的路径
        String filePath = "/root/imageFile/";
        String fix = UUID.randomUUID().toString();
        File file = null;

        URL urlfile;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            // 判断文件的父级目录是否存在，不存在则创建
            file = new File(filePath + fix + ".jpg");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try {
                // 创建文件
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 下载
            urlfile = new URL(newUrl);
            inputStream = urlfile.openStream();
            outputStream = new FileOutputStream(file);

            int bytesRead = -1;
            byte[] buffer = new byte[1024 * 1000];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != outputStream) {
                    outputStream.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static File base64ToFile(String base64) throws Exception {
        if (base64.contains("data:image")) {
            base64 = base64.substring(base64.indexOf(",") + 1);
        }
        base64 = base64.toString().replace("\r\n", "");
        // 创建文件目录
        String prefix = ".png";
        File directory = new File("/root/photo/");
        File file = File.createTempFile("artwork", prefix, directory);
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(base64);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("imageFileName:" + file);
        return file;
    }


    public static String uploadFile(MultipartFile file) {
        String url = "";
        System.out.print("上传文件===" + "\n");
        //判断文件是否为空
        if (file.isEmpty()) {
            return "上传文件不可为空";
        }


        // 获取文件名
        String fileName = file.getOriginalFilename();
//        System.out.print("上传的文件名为: "+fileName+"\n");

        fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + fileName;
        System.out.print("（加个时间戳，尽量避免文件名称重复）保存的文件名为: " + fileName + "\n");


        //加个时间戳，尽量避免文件名称重复
//    String path = "D:/fileUpload/" +fileName;
        String path = "/root/source/" + fileName;
        //String path = "E:/fileUpload/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + fileName;
        //文件绝对路径
        System.out.print("保存文件绝对路径" + path + "\n");

        //创建文件路径
        File dest = new File(path);

        //判断文件是否已经存在
        if (dest.exists()) {
            return "文件已经存在";
        }

        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }

        try {
            //上传文件
            file.transferTo(dest); //保存文件
            System.out.print("保存文件路径" + path + "\n");
//      url = "https://admin1.ahftt.cn/image/"+fileName;
            url = "https://jblhsx.ahwrxx.com/image/" + fileName;
//      int jieguo= shiPinService.insertUrl(fileName,path,url);
//      System.out.print("插入结果"+jieguo+"\n");
            System.out.print("保存的完整url====" + url + "\n");

        } catch (IOException e) {
            return "上传失败";
        }

        return url;
    }

}
