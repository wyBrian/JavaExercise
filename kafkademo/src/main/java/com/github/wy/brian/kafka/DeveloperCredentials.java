package com.github.wy.brian.kafka;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DeveloperCredentials {

    private static Properties prop;

    static {
        InputStream inputStream = ClassLoader.class.getResourceAsStream("/developer.properties");
        prop = new Properties();
        try {
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Properties getProperties() {
        return prop;
    }
}
