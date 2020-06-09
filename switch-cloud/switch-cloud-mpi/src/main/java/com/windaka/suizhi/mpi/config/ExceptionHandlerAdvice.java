package com.windaka.suizhi.mpi.config;

/*import com.aisino.yshs.api.common.ReturnConstants;
import com.aisino.yshs.common.exception.OssRenderException;*/
import com.alibaba.fastjson.JSONObject;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

	/**
	 * feignClient调用异常，将服务的异常和http状态码解析
	 * @param exception
	 * @return
	 */
	@ExceptionHandler({ FeignException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> feignException(FeignException exception) {
		int httpStatus = exception.status();
		if (httpStatus >= HttpStatus.INTERNAL_SERVER_ERROR.value()) {
			//log.error("feignClient调用异常", exception);
		}

		Map<String, Object> data = new HashMap<>();

		String msg = exception.getMessage();

		if (!StringUtils.isEmpty(msg)) {
			int index = msg.indexOf("\n");
			if (index > 0) {
				String string = msg.substring(index);
				if (!StringUtils.isEmpty(string)) {
					JSONObject json = JSONObject.parseObject(string.trim());
					data.putAll(json.getInnerMap());
				}
			}
		}
		if (data.isEmpty()) {
			data.put("msg", msg);
		}
		data.put("code", httpStatus + "");
		return data;
	}


	/*@ExceptionHandler({ClientException.class, Throwable.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> serverException(Throwable throwable) {
		log.error("服务端异常", throwable);
		Map<String, Object> data = new HashMap<>();
		data.put("status",ReturnConstants.STATUS_FAILED);
		data.put("code", ReturnConstants.CODE_FAILED);
		data.put("msg", "服务异常，请联系管理员");

		return data;
	}*/

/*	@ExceptionHandler({OssRenderException.class})
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Object> ossRenderException(OssRenderException ore) {

		Map<String, Object> data = new HashMap<>();
		if (ReturnConstants.STATUS_TOKEN_FAILED.equals(ore.getMsg())) {
			data.put("status", ReturnConstants.STATUS_TOKEN_FAILED);
			data.put("code", ReturnConstants.CODE_FAILED);
			data.put("msg", ReturnConstants.MSG_TOKEN_FAILED);
		}else{
			data.put("status", ReturnConstants.STATUS_FAILED);
			data.put("code", ore.getCode());
			data.put("msg", ore.getMsg());
		}

		return data;
	}*/


}
