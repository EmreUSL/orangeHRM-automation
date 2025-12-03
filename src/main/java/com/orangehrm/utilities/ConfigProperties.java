package com.orangehrm.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigProperties {

    private static final Properties properties = new Properties();

    static {
        try {
            FileInputStream file = new FileInputStream("src/main/resources/config.properties");
            properties.load(file);

        } catch (IOException e) {
            System.err.println("Error loading config.properties");
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

}
