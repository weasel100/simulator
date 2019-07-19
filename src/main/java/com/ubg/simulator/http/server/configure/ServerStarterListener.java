package com.ubg.simulator.http.server.configure;

import com.ubg.simulator.http.server.beans.HTTPServer;
import com.ubg.simulator.http.server.beans.HTTPServerDefine;
import com.ubg.simulator.http.server.service.HomepageServletImpl;
import com.ubg.simulator.http.server.service.ServletImpl;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/***********
 * @author bruce
 * 监听启动，加载web服务
 * */
@Slf4j
@Component
public class ServerStarterListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    ServerConfigure serverConfigure;
    @Autowired
    HTTPServer servers;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        int port = servers.getPort();
        log.info("stert HTTP Simulator on port:" + port);
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.setConnectors(new Connector[]{connector});
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.addServlet(new ServletHolder(new HomepageServletImpl(serverConfigure)), "/");
        for (HTTPServerDefine httpserver : servers.getServerList()) {
            context.addServlet(new ServletHolder(new ServletImpl(httpserver)), httpserver.getUrl());
            log.info("Initializing simulator webservice:["+httpserver.getMethod()+"] " + httpserver.getUrl()+" Done");
        }
        server.setHandler(context);
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
