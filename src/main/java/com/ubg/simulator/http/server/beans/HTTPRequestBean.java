package com.ubg.simulator.http.server.beans;

import lombok.Data;

import java.util.Map;

/**
 * @author bruce
 * 用于结构化显示请求报表
 */
@Data
public class HTTPRequestBean {
    private String url;
    private String method;
    private String remoteHost;
    private String remoteIP;
    private Map<String, String> heasers;
    private Map<String, String[]> parameter;
    private String inputStream;
}
