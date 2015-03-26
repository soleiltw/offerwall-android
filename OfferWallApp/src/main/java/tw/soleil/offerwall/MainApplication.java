package tw.soleil.offerwall;

import android.app.Application;

import tw.soleil.offerwall.core.OfferWallCore;

/**
 * Created by edward_chiang on 15/1/1.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        OfferWallCore.initialize(this);
    }
}
