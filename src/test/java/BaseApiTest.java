import java.io.IOException;

abstract class BaseApiTest {
    private final String token;
    private final String baseUri;
    private final String userName;
    private final String albumHash;
    private final String imageHash;
    private final String commentID;
    private ProperyScanner scanner;



    public BaseApiTest() throws IOException {
        scanner = new ProperyScanner();
        token = scanner.getProperty("imgur.auth.token");
        baseUri = scanner.getProperty("imgur.api.uri");
        userName = scanner.getProperty("imgur.username");
        albumHash = scanner.getProperty("imgur.albumHash");
        imageHash = scanner.getProperty("imgur.imageHash");
        commentID = scanner.getProperty("imgur.commentID");
    }
    public String getCommentID() { return commentID; }

    public String getToken() {
        return token;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public String getUserName() {
        return userName;
    }

    public ProperyScanner getScanner() {
        return scanner;
    }

    public String getAlbumHash() {
        return albumHash;
    }

    public String getImageHash() {
        return imageHash;
    }
}

