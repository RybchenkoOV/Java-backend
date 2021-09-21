import io.restassured.RestAssured;
import io.restassured.http.Header;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ImgurImageTests extends BaseApiTestImgur {

    private String currentDeleteHash;

    public ImgurImageTests() throws IOException {
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = getBaseUrl();
    }

    @Test
    @DisplayName("Test Get info image")
    void testGetImageBase() {
        given()
                .auth()
                .oauth2(getToken())
                .expect()
                .log()
                .all()
                .when()
                .get("/3/image/{imageHash}", getImageHash())
                .then()
                .statusCode(200)
                .assertThat()
                .body("data.id", is("xqRgN9O"));
    }

    @Test
    @DisplayName("Test Upload image")
    void testUploadImage() {

        uploadImage();

    }

    private String uploadImage(){
        return given()
                .auth()
                .oauth2(getToken())
                .when()
                .header(new Header("content-type", "multipart/form-data"))
                .multiPart("image", new File("./src/main/resources/image.jpg"))
                .expect()
                .body("data.id", is(notNullValue()))
                .body("data.deletehash", is(notNullValue()))
                .log()
                .all()
                .when()
                .post("3/upload")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }
    @Test
    @DisplayName("Test Delete image")
    void testDeleteImage() {
        currentDeleteHash = uploadImage();
        given()
                .auth()
                .oauth2(getToken())
                .when()
                .delete("/3/image/{imageHash}",currentDeleteHash)
                .prettyPeek()
                .then()
                .statusCode(200)
                .contentType("application/json");
    }
}
