package nz.assurity.automation.common;
import java.util.HashMap;

public class Headers {
    public static HashMap<String, String> getHeaderWithToken() {
        HashMap<String, String> headers = new HashMap();
        headers.put("Authorization", "Bearer " + Constants.ACCESS_TOKEN);
        return headers;
    }

    public static HashMap<String, String> getHeaderWithTokenWithContentType() {
        HashMap<String, String> headers = new HashMap();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + Constants.ACCESS_TOKEN);
        return headers;
    }

    public static HashMap<String, String> getEmptyHeader() {
        HashMap<String, String> headers = new HashMap();
        return headers;
    }

    public static HashMap<String, String> getGenerateTokenHeader() {
        HashMap<String, String> headers = new HashMap();
        headers.put("Authorization", "Basic NHZmM2M4MG");
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        return headers;
    }
}
