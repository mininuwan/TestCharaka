package nz.assurity.automation.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import nz.assurity.automation.models.PromotionsModel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class AssurityTest {
    @BeforeClass
    public void init()
    {

    }

    @Test(alwaysRun = true, priority = 1, description = "Assurity Test")
    public void testGenerateAssurityResponse(){
        SoftAssert softAssert = new SoftAssert();
        Response response = RestAssured.get("https://api.tmsandbox.co.nz/v1/Categories/6327/Details.json?catalogue=false");
        System.out.println(response.getStatusCode());

        String CategoryId = response.getBody().jsonPath().get("CategoryId").toString();
        String CategoryName = response.getBody().jsonPath().get("Name").toString();
        String CanRelist = response.getBody().jsonPath().get("CanRelist").toString();
        String PromotionDesc = response.getBody().jsonPath().getList("Promotions", PromotionsModel.class).get(1).Description;

        System.out.println(CategoryId);
        System.out.println(CategoryName);
        System.out.println(CanRelist);
        System.out.println(PromotionDesc);

        softAssert.assertEquals(CategoryName,"Carbon credits");
        softAssert.assertEquals(CanRelist, "true");

        List<PromotionsModel> promotionsModelList = response.getBody().jsonPath().getList("Promotions", PromotionsModel.class);
        for(PromotionsModel promotionsModel : promotionsModelList)
        {
            String PromotionName = promotionsModel.Name;
            if(PromotionName == "Gallery")
            {
                String PromotionDescCur = promotionsModel.Description;
                softAssert.assertEquals(PromotionDescCur, "Good position in category");
            }
        }

        softAssert.assertAll();
    }
}
