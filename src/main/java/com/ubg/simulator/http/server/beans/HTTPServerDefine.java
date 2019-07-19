package com.ubg.simulator.http.server.beans;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author bruce
 * HTTPServer基本属性定义
 */
@Data
@Component
public class HTTPServerDefine {
    /**
     * 请求URL定义
     */
    private String url;
    /**
     * 请求方法定义
     */
    private String method;
    /**
     * 响应报文定义，声明文件名在resources/response目录下
     */
    private String response;
    /**
     * true是每次都从文件读取，false第一次读文件，以后从内存读取，默认false
     */
    private boolean reload = false;
    /**
     * 是否读请求报文头true是，false否，默认false
     */
    private boolean isReadHeader = false;
    /**
     * 是否读请求参数true是，false否，默认false
     */
    private boolean isReadParameter = false;
    /**
     * 是否读请求流true是，false否，默认false
     */
    private boolean isReadStream = false;

    /**
     * 报文通讯的字符集
     */
    private String encoding = "UTF-8";

    /**
     * 响应报文类型，json,html,xml
     * 主要定义错误错误输出的格式，正确响应取决于自定义报文
     * */
    private String returnType="json";
    /**
     * 延迟响应时间,单位:ms
     */
    private long delay = 0;
}
