package com.rtboot.core.bean;

public class AppConfig {
    private String propertiesName;

    public AppConfig(String propertiesName) {
        this.propertiesName = propertiesName;
    }

    public String getPropertiesName() {
        return propertiesName;
    }
}
