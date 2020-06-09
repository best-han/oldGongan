package com.windaka.suizhi.mpi.message;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SendMessage {

    /**
     * 短信发送功能
     * @return
     * @throws IOException
     */
    public static void send(List<String> phones,String content) throws IOException{
        if(phones==null || phones.size()==0){
            log.error("SendMessage******无任何电话号码");
            return;
        }
        log.info("SendMessage*******短信内容："+content);
        //content="hello逄华";
        log.info("SendMessage*******短信号码s："+phones);
        String auth=get_auth();
        for(String phone:phones){
            log.info("SendMessage*******短信号码："+phone);
            get_result(auth,phone,content);
        }

    }
    public static void main(String[] args) {
        String phone_number = "13668840365";
        String word ="沙雕逄华";
     //   get_result(get_auth(),phone_number,word);
    }

    public static String get_auth() throws IOException {
        String auth = "";
        HttpPost httpport = new HttpPost("http://10.48.147.30/oam/oauth/token");
        List params = new ArrayList();
        params.add(new BasicNameValuePair("client_id", URLEncoder.encode("7157", "UTF-8")));
        params.add(new BasicNameValuePair("client_secret", URLEncoder.encode("7336bdb67a1a5d78b0aaad0d5cdbd369", "UTF-8")));
        params.add(new BasicNameValuePair("grant_type", URLEncoder.encode("client_credentials", "UTF-8")));
        httpport.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

        HttpResponse response = new DefaultHttpClient().execute(httpport);

        if (response.getStatusLine().getStatusCode() == 200) {
            JSONObject jsonobj = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
            System.out.println(jsonobj);
            String token = jsonobj.getString("access_token");
            auth = token;
        }

        return auth;
    }

    public static void get_result(String auth,String phone_number, String word) throws IOException {
        HttpPost httpport = new HttpPost("http://10.48.147.30/oam/api/sendMessage/v1.0");
        List params = new ArrayList();
        params.add(new BasicNameValuePair("phoneNumber", URLEncoder.encode(phone_number, "UTF-8")));
        params.add(new BasicNameValuePair("content", URLEncoder.encode(word, "UTF-8")));
        params.add(new BasicNameValuePair("access_token", URLEncoder.encode(auth, "UTF-8")));
        httpport.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        HttpResponse response = new DefaultHttpClient().execute(httpport);
        log.info("SendMessage**********短信:"+response.getEntity().toString());
        if (response.getEntity().toString().equals("true")) {
            log.info("SendMessage**********短信发送成功");
        }else{
            log.info("SendMessage**********短信发送有问题！！");
        }
    }

    public static  ArrayList<String> toArrayByFileReader(String path) {
        // 使用ArrayList来存储每行读取到的字符串
        ArrayList<String> arrayList = new ArrayList<String>();
        try {
            FileReader fr = new FileReader(path);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                arrayList.add(str);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对ArrayList中存储的字符串进行处理
        //int length = arrayList.size();
        //int[] array = new int[length];
        //for (int i = 0; i < length; i++) {
        //    String s = arrayList.get(i);
        //    array[i] = Integer.parseInt(s);
        //}
        // 返回数组
        return arrayList;
    }
}