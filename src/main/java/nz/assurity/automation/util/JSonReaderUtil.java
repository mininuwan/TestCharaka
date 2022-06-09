package nz.assurity.automation.util;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

public class JSonReaderUtil {
    public static String readJSONFile(String jsonFile) throws IOException {
        File file = new File(System.getProperty("user.dir")+"/src/main/resources/testdata/"+jsonFile+"");
        return (FileUtils.readFileToString(file, "utf-8"));
    }
}
