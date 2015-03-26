package tw.soleil.offerwall.fragment;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.util.Log;
import android.view.View;

import tw.soleil.offerwall.OfferWall;
import tw.soleil.offerwall.R;

/**
 * Created by edward_chiang on 15/1/2.
 */
public class SettingsFragment extends PreferenceFragment {

    private SharedPreferences preferences;

    private static String PREFERENCES_KEY_APP_VERSION = "PREFERENCES_KEY_APP_VERSION";

    private SwitchPreference autoUpdatePreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        try {
            String appVersion = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(PREFERENCES_KEY_APP_VERSION, appVersion);
            editor.commit();

        } catch (PackageManager.NameNotFoundException e) {
            Log.e(OfferWall.TAG, e.getLocalizedMessage());
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Version
        Preference versionPreference = findPreference("setting_version_key");
        versionPreference.setSummary(this.preferences.getString(PREFERENCES_KEY_APP_VERSION, ""));
    }
}
