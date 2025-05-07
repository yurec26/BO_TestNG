package org.example;

import java.io.InputStream;
import java.util.Properties;

public class ConfigHelper {

    public static String getProperty(String key, String filename) {
        Properties properties = new Properties();
        try (InputStream input = ConfigHelper.class.getClassLoader().getResourceAsStream(filename)) {
            if (input == null) {
                System.out.println("Sorry, unable to find conf.properties");
                return null;
            }
            properties.load(input);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return properties.getProperty(key);
    }
}
