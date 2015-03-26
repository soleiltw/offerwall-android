package tw.soleil.offerwall.core;

import android.content.Context;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;

import tw.soleil.offerwall.core.object.parse.Action;
import tw.soleil.offerwall.core.object.parse.Offer;
import tw.soleil.offerwall.core.object.parse.Wall;


/**
 * A Class for test the jar has install successfully.
 * Created by edward_chiang on 15/1/22.
 */
public class OfferWallCore {

    public static final String TAG = "Core";

    public void testLog() {
        Log.d(OfferWallCore.TAG, "Test");
    }

    public String echoString() {
        return "Echo";
    }

    public String echoResString(Context context) {
        return context.getResources().getString(R.string.app_name);
    }

    /**
     * Initialize all setup
     * @param context
     */
    public static void initialize(Context context) {
        // Parse

        ParseSettings parseSettings = new ParseSettings();

        Parse.initialize(context, parseSettings.getAppId(), parseSettings.getClientKey());
        ParseObject.registerSubclass(Action.class);
        ParseObject.registerSubclass(Wall.class);
        ParseObject.registerSubclass(Offer.class);
        ParseUser.enableAutomaticUser();

        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    private static class ParseSettings {

        public String getAppId() {
            return "h3HF2RHO2B8BTXdtDfXWOD0cXWhuvAZX9QlDrUXW";
        }

        public String getClientKey() {
            return "6C2yChztrfJZDJlWcWOVxdIZAli2HlLZLlmND0df";
        }
    }
}
