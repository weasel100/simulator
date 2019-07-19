package com.ubg.simulator.http.server.service;

import com.alibaba.fastjson.JSON;
import com.ubg.simulator.http.server.configure.ServerConfigure;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*****************
 * @author
 * 设定首页页面，显示接口配置信息
 * */
@Slf4j
public class HomepageServletImpl implements Servlet {
    private ServletConfig servletConfig;
    private ServerConfigure serverConfigure;

    public HomepageServletImpl(ServerConfigure serverConfigure) {
        this.serverConfigure = serverConfigure;
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
        log.info("request:" + JSON.toJSONString(request.getParameterMap()));
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(serverConfigure.showServerConfigure());
    }

    @Override
    public String getServletInfo() {
        return servletConfig.getServletContext().getServerInfo();
    }

    @Override
    public void destroy() {

    }
}
