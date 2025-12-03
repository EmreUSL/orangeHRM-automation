package com.orangehrm.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigProperties {

    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(ConfigProperties.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            System.err.println("Error loading config.properties");
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
