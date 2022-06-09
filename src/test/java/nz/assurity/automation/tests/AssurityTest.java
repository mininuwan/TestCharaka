package nz.assurity.automation.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import nz.assurity.automation.common.Constants;
import nz.assurity.automation.common.Headers;
import nz.assurity.automation.common.URLs;
import nz.assurity.automation.functions.AccessToken;
import nz.assurity.automation.models.PromotionsModel;
import nz.assurity.automation.util.RequestMethods;
import nz.assurity.automation.util.RestUtil;
import nz.assurity.automation.util.TestBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class AssurityTest extends TestBase {
    @BeforeClass
    public void init()
    {

    }

    @BeforeMethod
    public void generateAccessToken(){
        //AccessToken.generateAccessToken();
    }

    @Test(alwaysRun = true, priority = 1, description = "Category Carbon Credits")
    @Description("Assurity Test - Category Carbon Credits")
    @Epic("Assurity Test Epic")
    @Feature("Assurity Test Feature")
    public void testGenerateAssurityResponse(){
        SoftAssert softAssert = new SoftAssert();
        Response response = RestAssured.get(Constants.API_HOST + URLs.ASSURITY_GIVEN_URL);
        System.out.println(response.getStatusCode());

        String CategoryId = response.getBody().jsonPath().get("CategoryId").toString();
        String CategoryName = response.getBody().jsonPath().get("Name").toString();
        String CanRelist = response.getBody().jsonPath().get("CanRelist").toString();
        String PromotionDesc = response.getBody().jsonPath().getList("Promotions", PromotionsModel.class).get(1).getDescription();

        softAssert.assertEquals(CategoryName,"Carbon credits");
        softAssert.assertEquals(CanRelist, "true");

        List<PromotionsModel> promotionsModelList = response.getBody().jsonPath().getList("Promotions", PromotionsModel.class);

        if(promotionsModelList.size() > 0) {
            for (PromotionsModel promotionsModel : promotionsModelList) {
                String PromotionName = promotionsModel.getName();
                if (PromotionName == "Gallery") {
                    String PromotionDescCur = promotionsModel.getDescription();
                    softAssert.assertEquals(PromotionDescCur, "Good position in category");
                }
            }
        }

        softAssert.assertAll();
    }


    @Test(alwaysRun = true, priority = 2, description = "Category Carbon Credits")
    @Description("Assurity Test - Category Carbon Credits")
    @Epic("Assurity Test Epic")
    @Feature("Assurity Test Feature")
    public void testGenerateAssurityResponseTryTwo(){
        SoftAssert softAssert = new SoftAssert();
        Response response;
        RestUtil.API_HOST = Constants.API_HOST;
        RestUtil.BASE_PATH = "";

        response = RestUtil.send(
                Headers.getEmptyHeader(),
                "",
                URLs.ASSURITY_GIVEN_URL,
                RequestMethods.GET.toString()
        );

        softAssert.assertEquals(RestUtil.getResponseCode(response),200,"Incorrect Status Code");

        String CategoryName = response.getBody().jsonPath().get("Name").toString();
        String CanRelist = response.getBody().jsonPath().get("CanRelist").toString();
        softAssert.assertEquals(CategoryName,"Carbon credits");
        softAssert.assertEquals(CanRelist, "true");

        List<PromotionsModel> promotionsModelList = response.getBody().jsonPath().getList("Promotions", PromotionsModel.class);
        if(promotionsModelList.size() > 0) {
            for (PromotionsModel promotionsModel : promotionsModelList) {
                String PromotionName = promotionsModel.getName();
                if (PromotionName == "Gallery") {
                    String PromotionDescCur = promotionsModel.getDescription();
                    softAssert.assertEquals(PromotionDescCur, "Good position in category");
                }
            }
        }

        softAssert.assertAll();
    }
}
