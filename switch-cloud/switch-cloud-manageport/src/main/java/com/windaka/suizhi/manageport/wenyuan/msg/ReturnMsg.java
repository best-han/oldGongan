package com.windaka.suizhi.manageport.wenyuan.msg;
/**
 * 
 * @project: JeeSite Web
 * @Description: 定义返回结果
 * @author: kakaxi
 * @date: 2019年11月4日 下午1:41:59
 */
public class ReturnMsg {
	private String result; 
	private String message;
	private Object data;
	private String code;
	public static final String SUCCESS_DESC="上传成功!";
	public static final String ERROR_DESC="上传失败!";
	public static final String LOGIN_DESC="登录凭证失效!";
	public static final String SUCCESS="true";
	public static final String LOGIN="login";
	public static final String FALSE="false";
	
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public ReturnMsg(String result, String message, Object data, String code) {
		super();
		this.result = result;
		this.message = message;
		this.data = data;
		this.code = code;
	}
	
	public ReturnMsg(String result, String message, Object data) {
		super();
		this.result = result;
		this.message = message;
		this.data = data;
	}

}
