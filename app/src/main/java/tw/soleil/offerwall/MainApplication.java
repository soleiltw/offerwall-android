package tw.soleil.offerwall;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import tw.soleil.offerwall.object.parse.Action;
import tw.soleil.offerwall.object.parse.Offer;
import tw.soleil.offerwall.object.parse.Wall;

/**
 * Created by edward_chiang on 15/1/1.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Parse
        Parse.initialize(this, ParseSettings.APP_ID, ParseSettings.CLIENT_KEY);
        ParseObject.registerSubclass(Action.class);
        ParseObject.registerSubclass(Wall.class);
        ParseObject.registerSubclass(Offer.class);

        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
