import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ImgurImageTests extends BaseApiTestImgur {

    public ImgurImageTests() throws IOException {
    }

    @BeforeEach
    void beforeTest() {

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(getBaseUrl())
                .setAuth(oauth2(getToken()))
                .setContentType(ContentType.JSON)
                .addFilter(new ResponseLoggingFilter())
                .build();
        responseSpecification = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .build();
    }

    @Test
    @DisplayName("Test Get info image")
    void testGetImageBase() {
        given()
                .get(EndPointsForImgur.imageBase, getImageHash())
                .then()
                .assertThat()
                .body("data.id", is("xqRgN9O"));
    }

    @Test
    @DisplayName("Test Upload image")
    void testUploadImage() {

        uploadImage();

    }

    private String uploadImage() {
        return given()
                .header(new Header("content-type", "multipart/form-data"))
                .multiPart("image", new File("./src/main/resources/image.jpg"))
                .post(EndPointsForImgur.imageUpload)
                .then()
                .body("data.id", is(notNullValue()))
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Test
    @DisplayName("Test Delete image")
    void testDeleteImage() {
        String currentDeleteHash = uploadImage();
        given()
                .delete(EndPointsForImgur.imageDelete, currentDeleteHash);
    }
}
