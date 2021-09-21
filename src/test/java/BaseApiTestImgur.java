import java.io.IOException;

abstract class BaseApiTestImgur {

    private final String token;
    private final String baseUrl;
    private final String userName;
    private final String imageHash;
    private final String imageId;
    private final String commentId;
    private final PropertyScanner scanner;

    public BaseApiTestImgur() throws IOException {
        scanner = new PropertyScanner();
        baseUrl = scanner.getProperty("imgur.api.url");
        token = scanner.getProperty("imgur.auth.token");
        userName = scanner.getProperty("imgur.username");
        imageHash = scanner.getProperty("imgur.imageHash");
        imageId = scanner.getProperty("imgur.imageId");
        commentId = scanner.getProperty("imgur.commentId ");
    }

    public String getToken() {
        return token;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getUserName() {
        return userName;
    }

    public PropertyScanner getScanner() {
        return scanner;
    }

    public String getImageHash() {
        return imageHash;
    }

    public String getImageId() {
        return imageId;
    }

    public String getCommentId() {
        return commentId;
    }
}
