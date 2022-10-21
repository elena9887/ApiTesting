import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import  io.restassured.response.Response;
import io.restassured.http.Headers;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;


import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static io.restassured.path.json.JsonPath.from;

public class RestAssuredClass  extends BaseTest{



    @Test
    public void LoginRequestTest(){

                given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"cityslicka\"\n" +
                        "}")
                .post("login")
                .then()
                .statusCode(200)
                .log()
                .all()
                .body("token", notNullValue());

    }
    @Test
    public void deleteUserTest(){
        given()
                .delete("users/2")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }



       @Test
    public void patchUserTest(){
        String nameUpdated = given()
                .when()
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
                .patch("users/2")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .jsonPath().getString("name");

        assertThat(nameUpdated, equalTo("morpheus"));

    }

    @Test
    public void putUserTest(){
        String jobUpdated = given()
                .when()
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
                .put("users/2")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .jsonPath().getString("job");

        assertThat(jobUpdated, equalTo("zion resident"));

}


    @Test
        public void createUserTest(){
        CreateRequest user = new CreateRequest();
        user.setName("morpheus");
        user.setJob("leader");

        CreateResponse response = given()
                .when()
                .body(user)
                .post("users")
                .then()
                .extract()
                .body()
                .as(CreateResponse.class);

        assertThat(response.getJob(), equalTo("leader"));}


        @Test
        public void registerUserTest(){
            RegisterRequest regis  = new RegisterRequest();
            regis.setEmail("eve.holt@reqres.in");
            regis.setPassword("pistol");

            RegisterResponse response = given()
                    .when()
                    .body(regis)
                    .post("register")
                    .then()
                    .extract()
                    .body()
                    .as(RegisterResponse.class);

            assertThat(response.getToken(), equalTo("pistol"));




        }}