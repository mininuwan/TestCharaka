package nz.assurity.automation.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileReader {
    private Properties getData(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        //File file = new File(classLoader.getResource("src/main/resources/" + fileName + ".properties").getFile());
        File file = new File("src/main/resources/" + fileName + ".properties");
        FileInputStream fileInput = null;
        try {
            fileInput = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Properties prop = new Properties();

        try {
            prop.load(fileInput);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    public String getProperty(String fileName, String key) {
        return getData(fileName).getProperty(key);

    }
}
