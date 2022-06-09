package nz.assurity.automation.dao;

import nz.assurity.automation.util.JSonReaderUtil;

public class TokenData {
    public static String getGenerateTokenBody() throws Exception{
        return JSonReaderUtil.readJSONFile("/token/GenerateToken.json");
    }
}
