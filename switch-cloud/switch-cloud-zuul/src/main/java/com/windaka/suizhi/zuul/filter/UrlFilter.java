package com.windaka.suizhi.zuul.filter;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 请求参数过滤器
 *  * 过滤非法参数
 * @author: Corey
 * @Date: 2018/12/15 11:18
 * @Version 1.0
 */
@Slf4j
@Component
public class UrlFilter extends ZuulFilter {
    private static String[] validateStr = {
            "javascript", "expression", "alert(",     "delete ",    "insert ",    "update ",
            "drop ",       "truncate ",   "select ",    "chr(",        "exec(","exec ",       "count(",
            " and ",      "src=",       "master ",    "char ",       "declare ",    " or ",
            "ping ",       "telnet ",     ".html",     ".htm",       ".jsp",       ".asp",
            ".aspx",      ".js",        "false",     "true",       "http:",      "https:",
            "cookie",     "href=",      "Content-Type",
            "<script>",   "</script>",  "<iframe>",  "</iframe>",  "<img>",      "</img>",
            "<script",    "</script",   "<iframe",   "</iframe",   "<img",       "</img",
            "script>",    "/script>",   "iframe>",    "/iframe>",   "img>",      "/img>",  "tesgfn(",
            "prompt","document.","window.","confirm","<svg"};

    /**
     * 校验参数是否包含非法关键字
     * @param str
     * @return
     */
    private static boolean checkValidate(String str, HttpServletRequest request)
    {
        if(StringUtils.isBlank(str)) return true;
        for (int i = 0; i < validateStr.length; i++) {
            if(!str.toLowerCase().equals("yap ping luen（叶炳銮）") && !str.toUpperCase().equals("YAP PING LUEN") && !str.toUpperCase().equals("Fang Ping Tan")){
                if (str.toLowerCase().indexOf(validateStr[i]) < 0){
                    continue;
                }else{
                    //log.info("来自["+ ZuulTools.getIpAddress(request) + "]的请求包含关键字：" + validateStr[i]);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        HttpServletResponse response = requestContext.getResponse();
        String reqPath = request.getRequestURI();
        String reqDo = reqPath.substring(reqPath.lastIndexOf("/"));
        String unhealthyString="";
        boolean flag = true;
        String key = "";
        Map<String, String[]> pMAP = request.getParameterMap();
        if (pMAP != null){
            Iterator it = pMAP.keySet().iterator();
            String[] values = null;
            while (it.hasNext()){
                key = (String)it.next();
                if((!key.equals("_search")) && (!"password".equals(key))){
                    values = (String[])request.getParameterMap().get(key);
                    for(int i = 0; values!=null && i < values.length; i++) {
                        if (checkValidate(values[i], request)){
                            continue;
                        }else{
                            flag = false;
                            unhealthyString=values[i];
                            break;
                        }
                    }
                }
            }
        }
        if (!flag){
            log.info("过滤器检测到非法字符提交!链接："+reqPath+"参数："+key+"值："+unhealthyString);
            System.out.println("参数["+key+"]的数据["+unhealthyString+"]包含系统关键字！");
            Map<String, Object> retData = new HashMap<>();
            retData.put("status",ReturnConstants.STATUS_FAILED);
            retData.put("msg", "您提交的数据可能包含系统关键字，也可能包含半角字符，请重新输入！");
            retData.put("code", ReturnConstants.CODE_FAILED);
            requestContext.setResponseStatusCode(HttpStatus.OK.value());
            requestContext.setResponseBody(JSONObject.toJSONString(retData));
            requestContext.setSendZuulResponse(false);

        }

        return null;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }
}
