package system;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

@Slf4j
public class PropertyManager {

    private static PropertyManager instance;

    private static final String PROPERTIES_FILE = "pizzatest.properties";
    private static final String LOCAL_PROPERTIES_FILE = "pizzatest.local.properties";

    public static PropertyManager GetInstance(){
        if (instance == null) instance = new  PropertyManager();
        return instance;
    }

    private final Properties properties;

    private PropertyManager(){
        properties = new Properties();

        loadPropertyFile(PROPERTIES_FILE);
        loadPropertyFile(LOCAL_PROPERTIES_FILE);
    }

    private void loadPropertyFile(String filename){
        String path = System.getProperty("user.dir") + "/src/test/resources/" + filename;
        try {
            InputStream input = new FileInputStream(path);
            properties.load(input);
        } catch (IOException e) {
            if (Objects.equals(filename, PROPERTIES_FILE)) {
                //If the file was the main properties file and not the local override, log an error
                log.error("Failed to load default properties file.");
                log.error(e.getMessage());
            }
        }
    }

    public String getPropertyString(String propertyName){
        return properties.getProperty(propertyName);
    }

    public int getPropertyInt(String propertyName){
        return Integer.parseInt(getPropertyString(propertyName));
    }

    public boolean getPropertyBool(String propertyName){
        return Boolean.parseBoolean(getPropertyString(propertyName));
    }
}
