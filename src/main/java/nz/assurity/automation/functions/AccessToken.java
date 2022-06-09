package nz.assurity.automation.functions;

import io.restassured.response.Response;
import nz.assurity.automation.common.Constants;
import nz.assurity.automation.common.Headers;
import nz.assurity.automation.common.LoggerUtil;
import nz.assurity.automation.common.URLs;
import nz.assurity.automation.util.RequestMethods;
import nz.assurity.automation.util.RestUtil;

public class AccessToken {
    public static void generateAccessToken(){
        Response response;
        RestUtil.API_HOST = Constants.API_HOST;
        RestUtil.BASE_PATH = "";

        response = RestUtil.send(
                Headers.getGenerateTokenHeader(),
                "grant_type=client_credentials&client_id=xxxxx&client_secret=xxxxxxxxx",
                URLs.URL_GENERATE_TOKEN,
                RequestMethods.POST.toString());

        Constants.ACCESS_TOKEN = response.getBody().jsonPath().get("access_token").toString();
        LoggerUtil.logINFO("ACCESS_TOKEN " + Constants.ACCESS_TOKEN);
    }
}
