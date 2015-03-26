package tw.soleil.offerwall.core.object.parse;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by edward_chiang on 15/1/1.
 */
@ParseClassName("Offer")
public class Offer extends ParseObject {

    private String title;

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        put("title", title);
    }
}
