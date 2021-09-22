import java.util.HashMap;
import java.util.Map;

public final class EndPointsForImgur {
    public static final String imageBase = "/3/image/{imageHash}";
    public static final String imageUpload = "/3/upload";
    public static final String imageDelete = "/3/image/{imageHash}";
    public static final String accountBase = "3/account/{username}";
    public static final String accountImage = "/3/account/{username}/image/{imageId}";
    public static final String createComment = "/3/comment";
    public static final String commentDelete = "/3/comment/{commentId}";
    public static Map<String, String> formsParams = new HashMap<>();

     static void setFormsParams() {
        formsParams.put("image_id", "jVUQoga");
        formsParams.put("comment", "Hello");
    }
}