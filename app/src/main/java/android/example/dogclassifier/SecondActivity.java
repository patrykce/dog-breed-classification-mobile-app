package android.example.dogclassifier;

import android.content.Intent;
import android.example.dogclassifier.entities.BreedPredictions;
import android.example.dogclassifier.net.HTTPReqTask;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;


public class SecondActivity extends AppCompatActivity {


    private boolean percentage;
    private int count;
    private String connectionURL;
    private ImageView dogView;
    private PictureSelectingMode mode;
    private BreedPredictions breedPredictions;

    private static final String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        this.mode = (PictureSelectingMode) intent.getSerializableExtra(MainActivity.EXTRA_MESSAGE);
        this.count = (int) intent.getSerializableExtra(MainActivity.EXTRA_PREFERENCE_COUNT);
        this.percentage = (boolean) intent.getSerializableExtra(MainActivity.EXTRA_PREFERENCE_PERCENTAGE);
        Button button = findViewById(R.id.makePhotoBtn);
        if (this.mode == PictureSelectingMode.CAMERA) {
            button.setText(R.string.take_picture);
        } else {
            button.setText(R.string.select_picture);
        }
        try {
            String[] props = PropertyReader.getConnectionProperty(getApplicationContext());
//                                .url("http://10.0.2.2:5000/")
            this.connectionURL = String.format("http://%s:%s/", props[0], props[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.dogView = findViewById(R.id.dog_view);

        getImage(this.dogView);
    }

    public void getImage(View view) {
        Intent takePicture;
        if (this.mode == PictureSelectingMode.CAMERA) {
            takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePicture, 0);
        } else {
            takePicture = new Intent(Intent.ACTION_PICK);
            takePicture.setType("image/*");
            startActivityForResult(takePicture, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                        this.dogView.setImageBitmap(imageBitmap);
                        String encodedImage = convertBitmapToStringBase64(imageBitmap);
                        try {
                            this.breedPredictions = new HTTPReqTask(encodedImage, this.connectionURL).execute().get();
                        } catch (ExecutionException | InterruptedException e) {
                            Log.e(TAG, String.format("%s %s", e, Arrays.toString(e.getStackTrace())));
                        }
                        Log.i(TAG, "Result of predictions from backend server: " + this.breedPredictions);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        try {
                            final Uri imageUri = data.getData();
                            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                            final Bitmap imageBitmap = BitmapFactory.decodeStream(imageStream);
                            this.dogView.setImageBitmap(imageBitmap);
                            String encodedImage = convertBitmapToStringBase64(imageBitmap);
                            this.breedPredictions = new HTTPReqTask(encodedImage, this.connectionURL).execute().get();
                            Log.i(TAG, "Result of predictions from backend server: " + this.breedPredictions);
                        } catch (FileNotFoundException | InterruptedException | ExecutionException e) {
                            Log.e(TAG, String.format("%s %s", e, Arrays.toString(e.getStackTrace())));
                        }
                    }
                    break;
            }
        }
        setupPredictionInfo();
    }

    private void setupPredictionInfo() {
        TextView dog_1 = findViewById(R.id.first_dog_result);
        TextView dog_2 = findViewById(R.id.second_dog_result);
        TextView dog_3 = findViewById(R.id.third_dog_result);
        if (this.breedPredictions != null) {
            if (this.breedPredictions.getBreed_1() != null) {
                String probability = this.percentage ? " " + this.breedPredictions.getProbability_1() + "%" : "";
                String content = String.format("1.%s %s", this.breedPredictions.getBreed_1(), probability);
                dog_1.setText(content);
                dog_1.setVisibility(View.VISIBLE);
            } else {
                dog_1.setVisibility(View.GONE);
            }
            if (this.breedPredictions.getBreed_2() != null && this.count > 1) {
                String probability = this.percentage ? " " + this.breedPredictions.getProbability_2() + "%" : "";
                String content = String.format("2.%s %s", this.breedPredictions.getBreed_2(), probability);
                dog_2.setText(content);
                dog_2.setVisibility(View.VISIBLE);
            } else {
                dog_2.setVisibility(View.GONE);
            }
            if (this.breedPredictions.getBreed_3() != null && this.count > 2) {
                String probability = this.percentage ? " " + this.breedPredictions.getProbability_3() + "%" : "";
                String content = String.format("3.%s %s", this.breedPredictions.getBreed_3(), probability);
                dog_3.setText(content);
                dog_3.setVisibility(View.VISIBLE);
            } else {
                dog_3.setVisibility(View.GONE);
            }
        }
    }

    private String convertBitmapToStringBase64(Bitmap imageBitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteArrayImage = baos.toByteArray();
        return Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
    }

}