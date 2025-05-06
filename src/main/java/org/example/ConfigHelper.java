package org.example;

import java.io.InputStream;
import java.util.Properties;

public class ConfigHelper {

    public static String getProperty(String key) {
        Properties properties = new Properties();
        try (InputStream input = ConfigHelper.class.getClassLoader().getResourceAsStream("conf.properties")) {
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
