package nz.assurity.automation.util;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import nz.assurity.automation.common.LoggerUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;

public class RestUtil {
    public static String API_HOST;
    public static String BASE_PATH;
    public static int PORT = 0;

    public RestUtil() {
    }

    public static Response send(Map<String, String> headers, String bodyString, String uri, String requestMethod, Map<String, String> queryParameters) {
        RestAssured.baseURI = API_HOST;
        RestAssured.basePath = BASE_PATH;
        if (PORT != 0) {
            RestAssured.port = PORT;
        }

        LoggerUtil.logINFO("\n\nHEADERS\n" + headers + "\n*********\n\n");
        LoggerUtil.logINFO("\n\nREQUEST_URL\n" + RestAssured.baseURI + RestAssured.basePath + "/" + uri + "\n*********\n\n");
        RequestSpecification requestSpecification = getRequestSpec(headers, bodyString);
        LoggerUtil.logINFO("\n\nREQUEST_BODY\n" + bodyString + "\n*********\n\n");
        RestAssured.useRelaxedHTTPSValidation();
        requestSpecification = RestAssured.given().spec(requestSpecification);
        String theUri = setQueryParameters(uri, queryParameters);
        Response response = execute(requestMethod, requestSpecification, theUri);
        LoggerUtil.logINFO("\n\nRESPONSE\n" + response.getBody().asString() + "\n*********\n\n");
        LoggerUtil.logINFO("\n\nRESPONSE_STATUS_CODE\n" + response.getStatusCode() + "\n*********\n\n");
        return response;
    }

    public static Response send(Map<String, String> headers, String bodyString, String uri, String requestMethod) {
        return send(headers, bodyString, uri, requestMethod, (Map)null);
    }

    public static Response execute(String reqMethod, RequestSpecification requestSpec, String uri) {
        RequestSpecification requestSpecification = RestAssured.given(requestSpec).config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)));
        Response response = null;
        if ("GET".equalsIgnoreCase(reqMethod)) {
            response = (Response)requestSpecification.expect().when().get(uri, new Object[0]);
        }

        if ("POST".equalsIgnoreCase(reqMethod)) {
            response = (Response)requestSpecification.expect().when().post(uri, new Object[0]);
        }

        if ("PUT".equalsIgnoreCase(reqMethod)) {
            response = (Response)requestSpecification.expect().when().put(uri, new Object[0]);
        }

        if ("DELETE".equalsIgnoreCase(reqMethod)) {
            response = (Response)requestSpecification.expect().when().delete(uri, new Object[0]);
        }

        if ("PATCH".equalsIgnoreCase(reqMethod)) {
            response = (Response)requestSpecification.expect().when().patch(uri, new Object[0]);
        }

        return response != null ? response : null;
    }

    public static RequestSpecification getRequestSpec(Map<String, String> headers, String body) {
        RequestSpecBuilder reqSpecBuilder = new RequestSpecBuilder();
        if (headers != null) {
            reqSpecBuilder.addHeaders(headers);
        }

        if (body != null && body.length() > 0) {
            reqSpecBuilder.setBody(body);
        }

        return reqSpecBuilder.build();
    }

    public static String send(Map<String, String> headers, String body, String baseUri, String basePath, String uri, String requestMethod) {
        if (PORT != 0) {
            RestAssured.port = PORT;
        }

        RestAssured.baseURI = baseUri;
        RestAssured.basePath = basePath;
        LoggerUtil.logINFO("\n\nHEADERS\n" + headers + "\n*********\n\n");
        LoggerUtil.logINFO("\n\nREQUEST_URL\n" + RestAssured.baseURI + RestAssured.basePath + "/" + uri + "\n*********\n\n");
        RequestSpecification requestSpecification = getRequestSpec(headers, body);
        requestSpecification = RestAssured.given().spec(requestSpecification);
        LoggerUtil.logINFO("\n\nREQUEST_BODY\n" + body + "\n*********\n\n");
        String response = executeAndGetResponseAsString(requestMethod, requestSpecification, uri);
        LoggerUtil.logINFO("\n\nRESPONSE_BODY\n" + response + "\n*********\n\n");
        return response;
    }

    public static String executeAndGetResponseAsString(String reqMethod, RequestSpecification requestSpecification, String uri) {
        requestSpecification = RestAssured.given(requestSpecification).config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)));
        Response response = null;
        if (reqMethod.equalsIgnoreCase("GET")) {
            response = (Response)requestSpecification.expect().when().get("/" + uri, new Object[0]);
        }

        if (reqMethod.equalsIgnoreCase("POST")) {
            response = (Response)requestSpecification.expect().when().post("/" + uri, new Object[0]);
        }

        if (reqMethod.equalsIgnoreCase("PUT")) {
            response = (Response)requestSpecification.expect().when().put("/" + uri, new Object[0]);
        }

        if (reqMethod.equalsIgnoreCase("DELETE")) {
            response = (Response)requestSpecification.expect().when().delete("/" + uri, new Object[0]);
        }

        return response.asString();
    }

    public static String setQueryParameters(String url, Map<String, String> queryParameters) {
        if (queryParameters != null && !queryParameters.isEmpty()) {
            String newUrl = url.concat("?");

            String key;
            String value;
            for(Iterator var3 = queryParameters.entrySet().iterator(); var3.hasNext(); newUrl = newUrl.concat(key).concat("=").concat(value).concat("&")) {
                Map.Entry<String, String> entry = (Map.Entry)var3.next();
                key = (String)entry.getKey();
                value = (String)entry.getValue();
            }

            return newUrl.substring(0, newUrl.length() - 1);
        } else {
            return url;
        }
    }

    public static int getResponseCode(Response response) {
        return response.getStatusCode();
    }

    public static boolean getResponseStatus(String response) {
        boolean isSuccess = false;

        try {
            String status = getValue(response, "success");
            if (!status.isEmpty()) {
                isSuccess = Boolean.parseBoolean(status);
            } else {
                isSuccess = getValue(response, "status").equals("success");
            }

            return isSuccess;
        } catch (Exception var6) {
            var6.printStackTrace();
            return isSuccess;
        } finally {
            ;
        }
    }

    public static boolean getResponseStatus(Response response) {
        boolean isSuccess = false;

        try {
            String status = getValue(response, "success");
            if (!status.isEmpty()) {
                isSuccess = Boolean.parseBoolean(status);
            } else {
                isSuccess = getValue(response, "status").equals("success");
            }

            return isSuccess;
        } catch (Exception var6) {
            var6.printStackTrace();
            return isSuccess;
        } finally {
            ;
        }
    }

    public static String getValue(String response, String key) {
        String value = "";

        try {
            if (response.charAt(0) != '{' && response.charAt(response.length() - 1) != '}') {
                response = response.replace(response.substring(0, 1), "");
                response = response.replace(response.substring(response.length() - 1, response.length()), "");
            }

            JSONObject responseBody;
            try {
                responseBody = new JSONObject(response);
                value = responseBody.getString(key);
            } catch (Exception var17) {
            }

            try {
                responseBody = new JSONObject(response);
                value = String.valueOf(responseBody.getBoolean(key));
            } catch (Exception var16) {
            }

            try {
                responseBody = new JSONObject(response);
                value = String.valueOf(responseBody.getInt(key));
            } catch (Exception var15) {
            }

            try {
                responseBody = new JSONObject(response);
                value = String.valueOf(responseBody.getDouble(key));
            } catch (Exception var14) {
            }

            try {
                responseBody = new JSONObject(response);
                value = String.valueOf(responseBody.getJSONObject(key));
            } catch (Exception var13) {
            }

            try {
                responseBody = new JSONObject(response);
                value = String.valueOf(responseBody.getJSONArray(key));
            } catch (Exception var12) {
            }

            return value;
        } finally {
            ;
        }
    }

    public static String getValueRecursively(String response, String key) {
        String[] keys = key.split("->");

        for(int i = 0; i < keys.length; ++i) {
            response = getValue(response, keys[i]);
        }

        LoggerUtil.logINFO(response);
        return response;
    }

    public static String getValue(Response response, String key) {
        return getValue(response.asString(), key);
    }

    public static String getValueInArray(String response, String attribute, int dataArrayIndex, String key) {
        String toReturn = "";

        try {
            JSONObject responseBody = new JSONObject(response);
            JSONArray dataArray = (JSONArray)responseBody.get(attribute);
            if (dataArrayIndex < 0) {
                dataArrayIndex = dataArray.length() - Math.abs(dataArrayIndex);
            }

            JSONObject dataObject = (JSONObject)dataArray.get(dataArrayIndex);
            toReturn = dataObject.getString(key);
            return toReturn;
        } catch (Exception var11) {
            var11.getMessage();
            return toReturn;
        } finally {
            ;
        }
    }

    public static String getValueInArray(Response response, String attribute, int dataArrayIndex, String key) {
        String toReturn = "";

        try {
            JSONObject responseBody = new JSONObject(response);
            JSONArray dataArray = (JSONArray)responseBody.get(attribute);
            if (dataArrayIndex < 0) {
                dataArrayIndex = dataArray.length() - Math.abs(dataArrayIndex);
            }

            JSONObject dataObject = (JSONObject)dataArray.get(dataArrayIndex);
            toReturn = dataObject.getString(key);
            return toReturn;
        } catch (Exception var11) {
            var11.getMessage();
            return toReturn;
        } finally {
            ;
        }
    }

    public static String getSalesForceAccessToken(String uri, String clientID, String clientSecret, String username, String pw) {
        String body = "grant_type=password&client_id=CLIENTID&client_secret=CLIENTSECRET&username=USER_NAME&password=PASS_WORD".replace("CLIENTID", clientID).replace("CLIENTSECRET", clientSecret).replace("USER_NAME", username).replace("PASS_WORD", pw);
        Response response = send(Headers.getHeaderUrlEncoded(), body, uri, RequestMethods.POST.toString());
        return getValue(response, "access_token");
    }

    public static boolean isGivenAttributeInResponse(String response, String attribute) {
        boolean isAttributeExists = false;

        try {
            JSONObject responseBody = new JSONObject(response);
            if (responseBody.has(attribute)) {
                isAttributeExists = true;
            }

            return isAttributeExists;
        } catch (Exception var7) {
            var7.printStackTrace();
            return isAttributeExists;
        } finally {
            ;
        }
    }

    public static boolean isGivenAttributeInResponse(Response response, String attribute) {
        boolean isAttributeExists = false;

        try {
            JSONObject responseBody = new JSONObject(response);
            if (responseBody.has(attribute)) {
                isAttributeExists = true;
            }

            return isAttributeExists;
        } catch (Exception var7) {
            var7.printStackTrace();
            return isAttributeExists;
        } finally {
            ;
        }
    }

}
