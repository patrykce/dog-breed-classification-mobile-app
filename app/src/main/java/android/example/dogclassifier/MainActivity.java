package android.example.dogclassifier;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.example.dogclassifier.preference.Preference;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    private SharedPreferences sharedPreferences;
    public static final String EXTRA_MESSAGE = "com.example.android.dogclassifier.extra.MESSAGE";
    public static final String EXTRA_PREFERENCE_COUNT = "com.example.android.dogclassifier.extra.PREFERENCE_COUNT";
    public static final String EXTRA_PREFERENCE_PERCENTAGE = "com.example.android.dogclassifier.extra.PREFERENCE_PERCENTAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

    }

    public void getFromCamera(View view) {
        selectImage();
    }

    private void selectImage() {
        boolean percentage = sharedPreferences.getBoolean("percentage", false);
        String listPreference = sharedPreferences.getString("listpref", "1");
        int count = Integer.parseInt(listPreference);
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose source of your picture");

        builder.setItems(options, (dialog, item) -> {
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra(EXTRA_PREFERENCE_COUNT, count);
            intent.putExtra(EXTRA_PREFERENCE_PERCENTAGE, percentage);

            if (options[item].equals("Take Photo")) {
                intent.putExtra(EXTRA_MESSAGE, PictureSelectingMode.CAMERA);
                startActivity(intent);

            } else if (options[item].equals("Choose from Gallery")) {
                intent.putExtra(EXTRA_MESSAGE, PictureSelectingMode.GALLERY);
                startActivity(intent);

            } else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    public void displaySharedPreferences(View view) {
        Intent i = new Intent(this, Preference.class);
        startActivity(i);
    }

}