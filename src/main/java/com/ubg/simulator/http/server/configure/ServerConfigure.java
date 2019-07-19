package com.ubg.simulator.http.server.configure;

import com.ubg.simulator.http.server.beans.HTTPServer;
import com.ubg.simulator.http.server.beans.HTTPServerDefine;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*************
 * @author
 * 配置文件描述信息
 * */
@Data
@Component
public class ServerConfigure {
    @Autowired
    HTTPServer server;

    public String showServerConfigure() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<p>模拟器监听端口：" + server.getPort() + "</p>");
        stringBuilder.append("<table border='1'><thead><td>服务地址</td>");
        stringBuilder.append("<td>请求方法</td>");
        stringBuilder.append("<td>响应报文文件</td>");
        stringBuilder.append("<td>延迟时间</td>");
        stringBuilder.append("<td>是否自动刷新</td></thead>");
        stringBuilder.append("<tbody>");
        for (HTTPServerDefine server : server.getServerList()) {
            stringBuilder.append("<tr>");
            stringBuilder.append("<td>" + server.getUrl() + "</td>");
            stringBuilder.append("<td>" + server.getMethod() + "</td>");
            stringBuilder.append("<td>" + server.getResponse() + "</td>");
            stringBuilder.append("<td>" + server.getDelay() + "</td>");
            stringBuilder.append("<td>" + server.isReload() + "</td>");
            stringBuilder.append("</tr>");
        }
        stringBuilder.append("</tbody></table>");
        return stringBuilder.toString();
    }
}
