package com.ubg.simulator.http.server.beans;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.List;

/***********
 * @author bruce
 * HTTPServer配置定义
 * */
@Component
@Data
@ConfigurationProperties(prefix = "ubg.httpsimulator")
public class HTTPServer {
    private int port;
    private List<HTTPServerDefine> serverList;
}
