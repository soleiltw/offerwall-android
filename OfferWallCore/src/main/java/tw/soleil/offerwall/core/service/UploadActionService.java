package tw.soleil.offerwall.core.service;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import tw.soleil.offerwall.core.object.parse.Action;

/**
 * Created by weitang114 on 15/3/26.
 */
public class UploadActionService extends Service {

    public static final String TAG = "UploadActionService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        handleCommand(intent);

        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    private void handleCommand(Intent intent) {
        Uri data = intent.getData();
        String pkgName = data.getEncodedSchemeSpecificPart();

        Log.i("OfferWall", "intent.getAction(). " + pkgName);
        Action action = new Action();
        action.setType(intent.getAction());
        action.setValue(pkgName);
        if (ParseUser.getCurrentUser().isAuthenticated() && ParseUser.getCurrentUser().getObjectId() !=null &&
                !"null".equalsIgnoreCase(ParseUser.getCurrentUser().getObjectId())) {
            action.setUserPointer(ParseUser.getCurrentUser());
        }

        try {
            // synchronous uploading right after installation, succeeding in such as 97% confidence
            action.save();
            Log.i(TAG, "upload done");
            stopSelf();
        } catch (ParseException e) {
            e.printStackTrace();

            // synchronous upload failed, so we rely on Parse's help...
            // any other good implementation?
            action.saveEventually(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    stopSelf();
                }
            });
        }
    }
}
