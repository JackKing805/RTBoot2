package com.rtboot.boot.http.enums;

/**
 * 请求方法
 */
public enum RequestVersion {
    HTTP1_1("HTTP/1.1"),
    HTTP2_0("HTTP/2.0");

    private final String version;
    RequestVersion(String version){
        this.version = version;
    }

    public String getVersion(){
        return version;
    }

    public boolean isEquals(String version){
        return this.version.equalsIgnoreCase(version);
    }

    @Override
    public String toString() {
        return "RequestVersion{" +
                "version='" + version + '\'' +
                '}';
    }
}
