import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class ImgurApiTest extends BaseApiTest{

    public ImgurApiTest() throws IOException {
    }

    @Test
    @DisplayName("Get Account Info:")
    public void testGetAccountBase() throws IOException {
        ProperyScanner scanner;


        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .auth()
            .oauth2(getToken())
            .baseUri(getBaseUri())
            .expect()
            .log()
            .all()
            .statusCode(200)
            .when()
            .get("3/account/{username}", getUserName());

    }

    @Test
    @DisplayName("Get Album info:")
    public void testGetAlbum() throws IOException {

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .auth()
                .oauth2(getToken())
                .baseUri(getBaseUri())
                .expect()
                .body("data",notNullValue())
                .body("data.id",is(getAlbumHash()))
                .body("data.images[0].id",is(getImageHash()))
                .log()
                .all()
                .statusCode(200)
                .when()
                .get("3/account/{username}/album/{albumHash}", getUserName(), getAlbumHash());
    }

    @Test
    @DisplayName("Get Account images:")
    public void testGetListOfAccoutImages() throws IOException {

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .auth()
                .oauth2(getToken())
                .baseUri(getBaseUri())
                .expect()
                .body("data",notNullValue())
                .body("data[0].id",is(getImageHash()))
                .log()
                .all()
                .statusCode(200)
                .when()
                .get("3/account/me/images");

    }

    @Test
    @DisplayName("Create Comment:")
    public void testCreateAComment() throws IOException {

        String requestBody = "{\n" +
                "  \"image_id\": \""+getImageHash()+"\",\n" +
                "  \"comment\": \"Some test data!!!\"\n}";

        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .auth()
                .oauth2(getToken())
                .baseUri(getBaseUri())
                .body(requestBody)
                .log()
                .all()
                .when()
                .post("3/comment")
                .then().extract().response();

        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    @DisplayName("Get All Comments:")
    public void testGetListOfComments() throws IOException {

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .auth()
                .oauth2(getToken())
                .baseUri(getBaseUri())
                .expect()
                .body("data",notNullValue())
                .body("data[0].id" ,equalTo(Integer.parseInt(getCommentID())))
                .body("data[0].author" ,equalTo("RibusOV"))
                .log()
                .all()
                .statusCode(200)
                .when()
                .get("3/account/{username}/comments", getUserName());
    }

    @Test
    @DisplayName("Get Comment by ID:")
    public void testGetCommentByID() throws IOException {

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .auth()
                .oauth2(getToken())
                .baseUri(getBaseUri())
                .expect()
                .body("data",notNullValue())
                .body("data[0].id" ,equalTo(getCommentID()))
                .body("data[0].author" ,equalTo("RibusOV"))
                .log()
                .all()
                .statusCode(200)
                .when()
                .get("3/account/{commentID}", getCommentID());
    }



}

