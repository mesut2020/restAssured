package basqar;

import basqar.model.Country;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CountryTest {

    Cookies cookies;
    private String randomGenName;
    private String randomGenCode;
    String id;

    @BeforeClass
    public void login()
    {
        baseURI ="https://demo.mersys.io";

        randomGenName = RandomStringUtils.randomAlphabetic(8);
        randomGenCode = RandomStringUtils.randomAlphabetic(4);

        // {"username": "richfield.edu", "password": "Richfield2020!", "rememberMe": true}

        Map<String, String> credentials= new HashMap<>();
        credentials.put("username", "richfield.edu");
        credentials.put("password", "Richfield2020!");
        credentials.put("rememberMe", "true");

        cookies= given()
                .body(credentials) // json ı direk yazmak yerine map olarak da verebiliriz.
                .contentType(ContentType.JSON) // verilen bilgiyi JSON olarak gönder
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract().response().getDetailedCookies();

        System.out.println(cookies);
    }


    @Test
    public void createCountry()
    {
        Country country=new Country();
        country.setName(randomGenName);
        country.setCode(randomGenCode);

        id=  given()
                  .cookies(cookies)  // aldığımız yetki bilgilerini barındıran bilgileri tekrar göndererek yetkili işlem yaptığımızı belirttik.
                  .body(country) // JSON formatında vermek yerine NESNE olarak daha kolay formatta verdim.
                  .contentType(ContentType.JSON) // verilen bilgiyi JSON olarak gönder
                  .when()
                  .post("/school-service/api/countries")
                  .then()
                  .log().body()           // olusturulan country bilgisini konsola yazar
                  .statusCode(201)
                  .body("name", equalTo(randomGenName))
                  .extract().jsonPath().getString("id")
                  //.extract().path("id"); // 2.yöntem
          ;
        System.out.println(id);

        Response response = given().cookies(cookies)
                .contentType(ContentType.JSON)
                .when()
                .get("/school-service/api/countries")
                .then()
                .statusCode(200)
                .body("name", Matchers.hasItem(randomGenName))
                .extract().response();
        response.prettyPrint();
        List<String> countryNames = response.jsonPath().getList("name");
        Assert.assertTrue(countryNames.contains(randomGenName),"No Record");

    }

    @Test(dependsOnMethods = "createCountry")
    public void createCountryNegative()
    {
        Country country=new Country();
        country.setName(randomGenName);
        country.setCode(randomGenCode);

        given()
                .cookies(cookies)  // aldığımız yetki bilgilerini barındıran bilgileri tekrar göndererek yetkili işlem yaptığımızı belirttik.
                .body(country) // JSON formatında vermek yerine NESNE olarak daha kolay formatta verdim.
                .contentType(ContentType.JSON) // verilen bilgiyi JSON olarak gönder
                .when()
                .post("/school-service/api/countries")
                .then()
                .log().body()
                .statusCode(400)
                .body("message", equalTo("The Country with Name \""+randomGenName+"\" already exists."))
        ;
    }

    @Test(dependsOnMethods ="createCountry")
    public void updateCountry()
    {
        Country country=new Country();
        country.setId(id);
        country.setName(RandomStringUtils.randomAlphabetic(8));
        country.setCode(RandomStringUtils.randomAlphabetic(4));

        given()
                .cookies(cookies)
                .body(country)
                .contentType(ContentType.JSON)
                .when()
                .put("/school-service/api/countries")
                .then()
                .statusCode(200)
                .body("name", equalTo(country.getName()))
                .body("code", equalTo(country.getCode()))
                ;

        //    {
//        "id": "5fd7aab8146e3837d4905510",
//            "name": "Zimbabwe 851",
//            "shortName": null,
//            "translateName": [],
//        "code": "GU 635"
//    }
    }

    @Test(dependsOnMethods = "updateCountry")
    public void deleteById()
    {
        given()
                .cookies(cookies)
                .pathParam("countryId", id)
                .when()
                .delete("/school-service/api/countries/{countryId}")
                .then()
                .statusCode(200)
                .body(equalTo(""))
                ;
    }

    @Test(dependsOnMethods = "deleteById")
    public void negativeDeleteById()
    {

            given()
                    .cookies(cookies)
                    .pathParam("countryId", id)
                    .when()
                    .delete("/{countryId}")
                    .then()
                    .log().body()
                    .statusCode(405)
            ;



        //    {
//        "type": "https://support.mersys.io/cloud/problem/problem-with-message",
//            "status": 404,
//            "path": "/api/countries/5fd7aab8146e3837d4905510",
//            "code": null,
//            "message": "Country not found",
//            "lang": null,
//            "uri": null
//    }
    }

}












