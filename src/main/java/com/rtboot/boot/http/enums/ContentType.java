package com.rtboot.boot.http.enums;

public enum ContentType {
    TEXT_HTML("text/html"),
    TEXT_PLAIN("text/plain"),
    TEXT_XML("text/xml"),
    IMAGE_GIF("image/gif"),
    IMAGE_JPEG("image/jpg"),
    IMAGE_PNG("image/PNG"),


    APPLICATION_XHTML_XML("application/xhtml+xml"),
    APPLICATION_XML("application/xml"),
    APPLICATION_ATOM_XML("application/atom+xml"),
    APPLICATION_JSON("application/json"),
    APPLICATION_PDF("application/pdf"),
    APPLICATION_MSWORD("application/msword"),
    APPLICATION_OCTET_STREAM("application/octet-stream"),
    APPLICATION_X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded"),


    //文件上传
    MULTIPART_FORM_DATA("multipart/form-data"),

    //实时文件传输
    RT_DATA("rt/data");

    private final String contentType;

    ContentType(String contentType){
        this.contentType =contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public boolean isEquals(String contentType){
        return this.contentType.equalsIgnoreCase(contentType);
    }
}
