package tw.soleil.offerwall.core.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import com.parse.ParseUser;

import tw.soleil.offerwall.core.object.parse.Action;

/**
 * Created by edward_chiang on 14/12/31.
 */
public class InstallerReceiver extends BroadcastReceiver {

    private static final String SKIP_PACKAGE = "tw.soleil.offerwall";

    public InstallerReceiver() {
        Log.d("OfferWall", "Constructor called.");
    }

    /**
     * This method is called when the BroadcastReceiver is receiving an Intent
     * broadcast.  During this time you can use the other methods on
     * BroadcastReceiver to view/modify the current result values.  This method
     * is always called within the main thread of its process, unless you
     * explicitly asked for it to be scheduled on a different thread using
     * {@link android.content.Context#registerReceiver(android.content.BroadcastReceiver,
     * IntentFilter, String, android.os.Handler)}. When it runs on the main
     * thread you should
     * never perform long-running operations in it (there is a timeout of
     * 10 seconds that the system allows before considering the receiver to
     * be blocked and a candidate to be killed). You cannot launch a popup dialog
     * in your implementation of onReceive().
     * <p/>
     * <p><b>If this BroadcastReceiver was launched through a &lt;receiver&gt; tag,
     * then the object is no longer alive after returning from this
     * function.</b>  This means you should not perform any operations that
     * return a result to you asynchronously -- in particular, for interacting
     * with services, you should use
     * {@link android.content.Context#startService(android.content.Intent)} instead of
     * {@link android.content.Context#bindService(android.content.Intent, ServiceConnection, int)}.  If you wish
     * to interact with a service that is already running, you can use
     * {@link #peekService}.
     * <p/>
     * <p>The Intent filters used in {@link android.content.Context#registerReceiver}
     * and in application manifests are <em>not</em> guaranteed to be exclusive. They
     * are hints to the operating system about how to find suitable recipients. It is
     * possible for senders to force delivery to specific recipients, bypassing filter
     * resolution.  For this reason, {@link #onReceive(android.content.Context, android.content.Intent) onReceive()}
     * implementations should respond only to known actions, ignoring any unexpected
     * Intents that they may receive.
     *
     * @param context The Context in which the receiver is running.
     * @param intent  The Intent being received.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Uri data = intent.getData();
        String pkgName = data.getEncodedSchemeSpecificPart();

        if ((Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction()) ||
                Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())
                )
                && !pkgName.equalsIgnoreCase(SKIP_PACKAGE)) {
            Log.i("OfferWall", "intent.getAction(). " + pkgName);
            Action action = new Action();
            action.setType(intent.getAction());
            action.setValue(pkgName);
            if (ParseUser.getCurrentUser().isAuthenticated() && ParseUser.getCurrentUser().getObjectId() !=null &&
                    !"null".equalsIgnoreCase(ParseUser.getCurrentUser().getObjectId())) {
                action.setUserPointer(ParseUser.getCurrentUser());
            }

            action.saveEventually();
        }
    }
}
