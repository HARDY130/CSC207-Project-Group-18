package util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class ApiConfig {
    private static Properties properties;
    private static final String CONFIG_FILE = "src/res/config.properties";

    static {
        properties = new Properties();
        try {
            // Load using direct file path instead of classpath
            properties.load(Files.newInputStream(Paths.get(CONFIG_FILE)));
        } catch (IOException e) {
            throw new RuntimeException("Error loading configuration", e);
        }
    }

    public static String getEdamamAppId() {
        return properties.getProperty("edamam.app.id");
    }

    public static String getEdamamAppKey() {
        return properties.getProperty("edamam.app.key");
    }
}