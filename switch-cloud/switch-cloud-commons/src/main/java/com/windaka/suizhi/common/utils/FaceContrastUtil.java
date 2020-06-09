package com.windaka.suizhi.common.utils;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class FaceContrastUtil {

//    static String ip = "10.10.5.60";// socket ip
    static String ip = "56.35.130.3";
    static int port = 3333;// socket port


    public static Map<String,Object> addFace(int faceTypePersonId, String base64Img, String from) {
        Map<String,Object> map=new HashMap<>();
        String id = null;
        String result;
        String faceid = null;
        Socket socket = null;
        // 获取输出流，向服务器端发送信息
        OutputStream os = null;// 字节输出流
        PrintWriter pw = null;// 将输出流包装为打印流
        //String imageData = Base64Utils.ImageToBase64ByLocal(image);
        InputStream is = null;
        BufferedReader in = null;// 输出流
        try {

            socket = new Socket(ip, port);
            // 获取输出流，向服务器端发送信息
            os = socket.getOutputStream();// 字节输出流
            pw = new PrintWriter(os);// 将输出流包装为打印流
            //String imageData = Base64Utils.ImageToBase64ByLocal(image);
            is = socket.getInputStream();
            in = new BufferedReader(new InputStreamReader(is));// 输出流

            pw.write(from);// 操作名称:0:添加人脸；1：删除人脸；(0和1是前端手动维护人脸库操作的)   2：普通人员信息录入（2为管理端上传的人的基本信息操作的）
            pw.write(String.format("%1$-16s", faceTypePersonId));
            pw.write(String.format("%1$-16s", base64Img.length()));
            pw.write(base64Img);
            pw.flush();
            System.out.print("向识别端发送操作类型:" + "0" + ",人脸id:" + faceTypePersonId + ",图片长度及图片"+base64Img.length()+":"+base64Img);
            int i=1;
            while ((result = in.readLine()) != null) {//返回值：匹配成功：faceTypePersonId;  图片质量不行，需换图片：noface;  没有对应的id:noId（2普通人员信息录入才会有此返回值）
                System.out.print("收到识别端的信息：" + result);
                if(i==1){
                    if("noface".equals(result)){
                        map.put("result",result);
                    }else if("noId".equals(result)){
                        map.put("result",result);
                    }else{
                        id=result;
                    }
                    i++;
                }else{
                    map.put("faceFeature",result);
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("特征失败.....");
            e.printStackTrace();

        }finally {
            try{
                is.close();
                in.close();
                socket.close();
            }catch(IOException ioe){
                ioe.printStackTrace();
            }
        }
        map.put("id",id);
        return map;
    }

    public static String delFace(int faceTypePersonId) {
        String id = null;
        String info;
      //  ArrayList faceids = new ArrayList();
        Socket socket = null;
        // 获取输出流，向服务器端发送信息
        OutputStream os = null;// 字节输出流
        PrintWriter pw = null;// 将输出流包装为打印流
        InputStream is = null;
        BufferedReader in =null;// 输出流
        try {

            socket = new Socket(ip, port);
            // 获取输出流，向服务器端发送信息
            os = socket.getOutputStream();// 字节输出流
            pw = new PrintWriter(os);// 将输出流包装为打印流
            is = socket.getInputStream();
            in = new BufferedReader(new InputStreamReader(is));// 输出流

            pw.write("1");// 操作名称
         //   pw.write(String.valueOf(faceids.size()));// id个数
            pw.write(String.format("%1$-16s", faceTypePersonId));
            pw.flush();
            System.out.print("向识别端发送操作类型:" + "1" );
            while ((info = in.readLine()) != null) {
                System.out.print("收到识别端的信息：" + info);
                System.out.println("数据删除Success!");
            }
            id = String.valueOf(faceTypePersonId);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            try{
                is.close();
                in.close();
                socket.close();
            }catch(IOException ioe){
                ioe.printStackTrace();
            }
        }
        return id;
    }


}
