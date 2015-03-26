package tw.soleil.core.parse;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by edward_chiang on 15/1/1.
 */
@ParseClassName("Wall")
public class Wall extends ParseObject {

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPackageName() {
        return getString("packageName");
    }

    public void setPackageName(String packageName) {
        put("packageName", packageName);
    }

    public String getAppName() {
        return getString("appName");
    }

    public void setAppName(String appName) {
        put("appName", appName);
    }

    public String getImageURL() {
        return getString("imageURL");
    }

    public void setImageURL(String imageURL) {
        put("imageURL", imageURL);
    }

    public String getActionURL() {
        return getString("actionURL");
    }

    public void setActionURL(String actionURL) {
        put("actionURL", actionURL);
    }

    public ParseFile getImageFile() {
        return getParseFile("imageFile");
    }

    public void setImageFile(ParseFile imageFile) {
        put("imageFile", imageFile);
    }

    public ParseObject getOfferPointer() {
        return getParseObject("offerPointer");
    }

    public void setOfferPointer(Offer offerPointer) {
        put("offerPointer", offerPointer);
    }
}
