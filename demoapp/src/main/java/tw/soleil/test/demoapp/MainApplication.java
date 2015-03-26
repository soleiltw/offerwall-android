package tw.soleil.test.demoapp;

import android.app.Application;
import tw.soleil.offerwall.core.OfferWallCore;

/**
 * Created by weitang114 on 15/3/26.
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        OfferWallCore.initialize(this);
    }
}
