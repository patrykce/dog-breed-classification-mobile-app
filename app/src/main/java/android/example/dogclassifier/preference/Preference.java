package android.example.dogclassifier.preference;

import android.example.dogclassifier.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Preference extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
    }
}
