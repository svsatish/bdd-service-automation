package com.example.api.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static final Properties properties = new Properties();
    private static final String ENV = System.getProperty("env", "dev"); // default to dev

    static {
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/config/" + ENV + ".properties");
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config for environment: " + ENV, e);
        }
    }

    public static String getEnv(){
        return ENV;
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
