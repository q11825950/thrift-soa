package com.github.mql.rpc.configBean;

public class Service {

    /*服务ip*/
    private String host;

    /*服务端口*/
    private String port;

    /*rpc类型*/
    private String serverProtocol;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getServerProtocol() {
        return serverProtocol;
    }

    public void setServerProtocol(String serverProtocol) {
        this.serverProtocol = serverProtocol;
    }
}
