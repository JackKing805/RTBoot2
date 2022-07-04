package com.rtboot.boot.rtboot.bean;

public class AppConfig {
    private String propertiesName;

    public AppConfig(String propertiesName) {
        this.propertiesName = propertiesName;
    }

    public String getPropertiesName() {
        return propertiesName;
    }
}
