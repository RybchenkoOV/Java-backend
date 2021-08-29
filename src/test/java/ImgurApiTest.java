import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


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

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .auth()
                .oauth2(getToken())
                .baseUri(getBaseUri())
                .log()
                .all()
                .when()
                .formParam("image_id", getImageHash())
                .formParam("comment", "Some test data!!!")
                .post("3/comment");

    }
}

