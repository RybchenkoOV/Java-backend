import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ImgurFromPostmanTests extends BaseApiTestImgur {

    public ImgurFromPostmanTests() throws IOException {
    }

    private static Map<String, String> formsParams = new HashMap<>();
    private String currentCommentId;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = getBaseUrl();
    }

    @Test
    @DisplayName("Test Get info AccountBase")
    void testGetAccountBase() {
        given()
                .auth()
                .oauth2(getToken())
                .expect()
                .log()
                .all()
                .when()
                .get("3/account/{username}", getUserName())
                .then()
                .statusCode(200)
                .assertThat()
                .body("data.url", is("lyubanya2003"));
    }

    @Test
    @DisplayName("Test Get info AccountImage")
    void testGetAccountImage() {
        given()
                .auth()
                .oauth2(getToken())
                .expect()
                .log()
                .all()
                .when()
                .get("/3/account/{username}/image/{imageId}", getUserName(), getImageId())
                .then()
                .statusCode(200)
                .assertThat()
                .body("data.link", is("https://i.imgur.com/N2id9C6.jpg"));
    }

    static void setFormsParams() {
        formsParams.put("image_id", "jVUQoga");
        formsParams.put("comment", "Hello");
    }

    @Test
    @DisplayName("Test Create Comment")
    void testCreateComment() {

        createComment();

    }

    private String createComment(){
        setFormsParams();
       return given()
                .auth()
                .oauth2(getToken())
                .config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().encodeContentTypeAs("multipart/form-data", ContentType.TEXT)))
                .contentType("multipart/form-data; boundary=--MyBoundary")
                .queryParams(formsParams)
                .expect()
                .body("data.id", is(notNullValue()))
                .log()
                .all()
                .when()
                .post("/3/comment")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getString("data.id");
    }

    @Test
    @DisplayName("Test Delete Comment")
    void testDeleteComment(){
        currentCommentId = createComment();
        given()
                .auth()
                .oauth2(getToken())
                .when()
                .delete("/3/comment/{commentId}",currentCommentId)
                .prettyPeek()
                .then()
                .statusCode(200)
                .contentType("application/json");
    }
}
