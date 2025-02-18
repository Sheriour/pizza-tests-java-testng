package system;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {

    private static PropertyManager instance;

    public static PropertyManager GetInstance(){
        if (instance == null) instance = new  PropertyManager();
        return instance;
    }

    private Properties properties;

    private PropertyManager(){
        properties = new Properties();
        loadPropertyFiles();
    }

    private void loadPropertyFiles(){
        String path = System.getProperty("user.dir") + "/src/test/resources/pizzatest.properties";
        try {
            InputStream input = new FileInputStream(path);
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String propertyName){
        return properties.getProperty(propertyName);
    }
}
