package com.windaka.suizhi.mpi.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.windaka.suizhi.mpi.websocket.WebSocketCarMes;
import com.windaka.suizhi.mpi.websocket.WebSocketMes;
import com.windaka.suizhi.mpi.websocket.WebSocketPersonMes;

@Controller
@RequestMapping("/show")
public class ShowController {
	
	@Resource
	WebSocketMes webSocketMes;
	
	@Resource
	WebSocketPersonMes webSocketPersonMes;
	
	@Resource
	WebSocketCarMes webSocketCarMes;
	
	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	//车辆占道
	@RequestMapping("/a")
	@ResponseBody
	public String a(){
		Map<String,String> map =new HashMap<String, String>();
		map.put("attr", "0");
		map.put("event", "0");
		map.put("eventName", "滞留");
		map.put("timeStamp", System.currentTimeMillis()+"");
		map.put("id", "1189005671392886784");
		map.put("location", "摄像头1");
		map.put("time", format.format(new Date()));
		map.put("img", "http://10.10.6.53:8980/windaka/userfiles/fileupload/1191145918529126400/2019-12/2019-12-11/1576033137195.jpg");
		webSocketMes.sendMessages(JSON.toJSONString(map));
		return "yes";
	}
	
	//流浪动物
	@RequestMapping("/b")
	@ResponseBody
	public String b(){
		Map<String,String> map =new HashMap<String, String>();
		map.put("attr", "4");
		map.put("event", "4");
		map.put("timeStamp", System.currentTimeMillis()+"");
		map.put("eventName", "流浪动物");
		map.put("id", "1189005671392886784");
		map.put("location", "摄像头1");
		
		map.put("time", format.format(new Date()));
		map.put("img", "http://10.10.6.53:8980/windaka/userfiles/fileupload/1191145918529126400/2019-12/2019-12-11/1576053161521.jpg");
		webSocketMes.sendMessages(JSON.toJSONString(map));
		return "yes";
	}
	
	//行人出入
	@RequestMapping("/c")
	@ResponseBody
	public String c(){
		Map<String,String> map =new HashMap<String, String>();
		//{"id":11899,"img":"tttt","name":"展涛","time":"2019-12-11 08:45:21","xqName":"邃智switch智慧社区"}
		map.put("name", "卡卡西");
		map.put("xqName", "凤凰城");
		map.put("timeStamp", System.currentTimeMillis()+"");
		map.put("time", format.format(new Date()));
		map.put("img", "http://10.10.6.53:8980/windaka/userfiles/fileupload/1191145918529126400/2019-12/2019-12-11/1576053161521.jpg");
		webSocketPersonMes.sendMessages(JSON.toJSONString(map));
		return "yes";
	}
	
	//车辆出入
	@RequestMapping("/d")
	@ResponseBody
	public String d(){
		Map<String,String> map =new HashMap<String, String>();
		map.put("carNum", "鲁B88888");
		map.put("timeStamp", System.currentTimeMillis()+"");
		map.put("xqName", "凤凰城");
		map.put("time", format.format(new Date()));
		map.put("img", "http://10.10.6.53:8980/windaka/userfiles/fileupload/1191145918529126400/2019-12/2019-12-11/1576053161521.jpg");
		webSocketCarMes.sendMessages(JSON.toJSONString(map));
		return "yes";
	}

}
