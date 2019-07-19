package com.ubg.simulator.http.server.service;

import com.alibaba.fastjson.JSON;
import com.ubg.simulator.http.server.beans.HTTPRequestBean;
import com.ubg.simulator.http.server.beans.HTTPServerDefine;
import com.ubg.simulator.utils.UIOUtils;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/*****************
 * @author
 * 模拟器的核心实现方法
 * */
@Slf4j
public class ServletImpl implements Servlet {
    private ServletConfig servletConfig;
    private HTTPServerDefine httpserver;
    private String responseContext;

    public ServletImpl(HTTPServerDefine httpserver) {
        this.httpserver = httpserver;
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.servletConfig = servletConfig;
    }

    @Override
    public ServletConfig getServletConfig() {
        return this.servletConfig;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HTTPRequestBean requestBean = new HTTPRequestBean();
        //验证方法是否符合定义，不符合，以500错误响应
        requestBean.setUrl(request.getRequestURI());
        requestBean.setMethod(request.getMethod());
        requestBean.setRemoteHost(request.getRemoteHost());
        requestBean.setRemoteIP(request.getRemoteAddr());
        //设置XML的响应格式
        if (httpserver.getReturnType().toUpperCase().equals("xml")) {
            response.setContentType("application/xml;charset=utf-8");
        }//设置JSON的响应格式
        else if (httpserver.getReturnType().toUpperCase().equals("json")) {
            response.setContentType("application/json;charset=utf-8");
        } else {
            //设置其它的响应格式都以HTML形式
            response.setContentType("text/html;charset=utf-8");
        }
        if (requestBean.getMethod().toUpperCase().equals(httpserver.getMethod().toUpperCase())) {
            //如果设定读报文头
            if (httpserver.isReadHeader()) {
                HashMap<String, String> headerMap = new HashMap<>();
                Enumeration<String> headers = request.getHeaderNames();
                while (headers.hasMoreElements()) {
                    String key = headers.nextElement();
                    headerMap.put(key, request.getHeader(key));
                    requestBean.setHeasers(headerMap);
                }
            }
            //如果设定读报文参数
            if (httpserver.isReadParameter()) {
                Map<String, String[]> parameterMap = request.getParameterMap();
                if (requestBean.getUrl().indexOf("?") > 0) {
                    String paramters[] = requestBean.getUrl().substring(requestBean.getUrl().indexOf("?")).split("&");
                    for (String str : paramters) {
                        String parameter[] = str.split("=");
                        String[] values = new String[1];
                        values[0] = parameter[1];
                        parameterMap.put(parameter[0], values);
                    }
                }
                requestBean.setParameter(parameterMap);
            }
            //如果设定读请求报文流
            if (httpserver.isReadStream()) {
                try {
                    requestBean.setInputStream(UIOUtils.readInputStream(request.getInputStream(), httpserver.getEncoding()));
                } catch (Exception e) {
                    log.error("读取异常", e);
                }
            }
            log.info(JSON.toJSONString(requestBean, true));
            //如果有延迟响应的设定，给客户端延迟响应
            if (httpserver.getDelay() > 0) {
                try {
                    Thread.sleep(httpserver.getDelay());
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
            }
            response.setStatus(HttpServletResponse.SC_OK);
            //写响应报文
            response.getWriter().println(readResponse());
        } else {
            //错误报文响应
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(returnErrorMessage(request.getMethod()));
        }
    }

    /**
     * 读响应报文
     * 只有第一次读，以及要求每次刷新才要重新加载
     */
    private String readResponse() {
        //如果是每次刷新，则重新加载文件
        if (httpserver.isReload()) {
            responseContext = UIOUtils.readFile(httpserver.getResponse(), httpserver.getEncoding());
        }
        //如果为空，需要读取文件
        if (responseContext == null) {
            responseContext = UIOUtils.readFile(httpserver.getResponse(), httpserver.getEncoding());
        }
        //仍然为空，说明配置文件不存在，请检查配置参数
        if (responseContext == null) {
            responseContext = returnNullFileErrorMessage(httpserver.getResponse());
        }
        return responseContext;
    }

    /********
     * 配置错误的响应报文的响应
     * */
    private String returnNullFileErrorMessage(String filePath) {
        String returnMessage = "";
        if (httpserver.getReturnType().toUpperCase().equals("xml")) {
            returnMessage = "<error>响应文件不存在，请检查配置：" + filePath + "</error>";
        } else if (httpserver.getReturnType().toUpperCase().equals("json")) {

            returnMessage = "{\"error\",\"响应文件不存在，请检查配置：" + filePath + "\"}";
        } else {
            returnMessage = "响应文件不存在，请检查配置：" + filePath;
        }
        return returnMessage;
    }

    /********
     * 错误方法的响应
     * */
    private String returnErrorMessage(String errorMethod) {
        String returnMessage = "";
        if (httpserver.getReturnType().toUpperCase().equals("xml")) {
            returnMessage = "<error>请求方法错误，不支持方法：" + errorMethod + "</error>";
        } else if (httpserver.getReturnType().toUpperCase().equals("json")) {

            returnMessage = "{\"error\",\"请求方法错误，不支持方法：" + errorMethod + "\"}";
        } else {
            returnMessage = "请求方法错误，不支持方法：" + errorMethod;
        }
        return returnMessage;
    }

    @Override
    public String getServletInfo() {
        return servletConfig.getServletContext().getServerInfo();
    }

    @Override
    public void destroy() {

    }
}
