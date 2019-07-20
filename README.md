## 报文模拟器

# 1.http服务模拟器
    接口定义在配置文件：application.yml
    ubg:
      httpsimulator:
        port: 8080                    定义WEB服务端口，它会覆盖springboot的默认端口
        serverList:                   定义接口组，子接口为“-”开头，后面跟JSON定义，注意空格
          - {url: "/test",            url  - 必输
             method: "post",          限定请求方法 - 必输,"*"所有方法都支持
             response: "post.json",   约定响应报文文件，- 必输 以resources/response为根目录，可以在此目录下定义子目录
             isReadHeader: true,      是否读请求报文头  -默认为false
             isReadParameter: true,   是否读请求参数列表    -默认为false
             isReadStream: true,      是否读请求流 -默认为false
             encoding: "UTF-8",       通讯字符集  -默认为UTF-8
             reload: true,            是否每次请求都刷新响应报文  -默认为false
             returnType: "json",      响应报文格式：json,html,xml,-默认为json,仅约定错误响应报文格式
             delay: 1000}             通讯延迟响应时间，单位：毫秒（ms）-默认为0
          - {url: "/bbb",method: "get", response: "get.json",returnType: "html",reload: false}