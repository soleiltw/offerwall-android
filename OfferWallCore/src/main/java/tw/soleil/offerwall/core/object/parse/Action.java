package tw.soleil.core.parse;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by edward_chiang on 15/1/1.
 */
@ParseClassName("Action")
public class Action extends ParseObject {

    private String type;
    private String value;
    private ParseUser userPointer;

    public String getType() {
        return getString("type");
    }

    public void setType(String type) {
        put("type", type);
    }

    public String getValue() {
        return getString("value");
    }

    public void setValue(String value) {
        put("value", value);
    }

    public ParseUser getUserPointer() {
        return getParseUser("userPointer");
    }

    public void setUserPointer(ParseUser userPointer) {
        put("userPointer", userPointer);
    }
}
