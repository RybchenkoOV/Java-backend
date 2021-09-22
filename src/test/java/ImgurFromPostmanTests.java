import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ImgurFromPostmanTests extends BaseApiTestImgur {

    public ImgurFromPostmanTests() throws IOException {
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
    @DisplayName("Test Get info AccountBase")
    void testGetAccountBase() {
        given()
                .get(EndPointsForImgur.accountBase, getUserName())
                .then()
                .assertThat()
                .body("data.url", is("lyubanya2003"));
    }

    @Test
    @DisplayName("Test Get info AccountImage")
    void testGetAccountImage() {
        given()
                .get(EndPointsForImgur.accountImage, getUserName(), getImageId())
                .then()
                .assertThat()
                .body("data.link", is("https://i.imgur.com/N2id9C6.jpg"));
    }

    @Test
    @DisplayName("Test Create Comment")
    void testCreateComment() {

        createComment();

    }

    private String createComment() {
        EndPointsForImgur.setFormsParams();
        return given()
                .config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs("multipart/form-data", ContentType.TEXT)))
                .contentType("multipart/form-data; boundary=--MyBoundary")
                .queryParams(EndPointsForImgur.formsParams)
                .post(EndPointsForImgur.createComment)
                .then()
                .body("data.id", is(notNullValue()))
                .extract()
                .response()
                .jsonPath()
                .getString("data.id");
    }

    @Test
    @DisplayName("Test Delete Comment")
    void testDeleteComment() {
        String currentCommentId = createComment();
        given()
                .delete(EndPointsForImgur.commentDelete, currentCommentId);
    }
}
