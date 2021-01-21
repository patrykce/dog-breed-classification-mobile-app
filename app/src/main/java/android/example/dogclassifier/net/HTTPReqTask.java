package android.example.dogclassifier.net;

import android.example.dogclassifier.entities.BreedPredictions;
import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HTTPReqTask extends AsyncTask<Void, Void, BreedPredictions> {

    private final String encodedImage;
    private final String urlConnection;
    private final OkHttpClient httpClient = new OkHttpClient();
    private static final String TAG = "HTTPRequest";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public HTTPReqTask(String encodedImage, String urlConnection) {
        super();
        this.encodedImage = encodedImage;
        this.urlConnection = urlConnection;
    }

    @Override
    protected BreedPredictions doInBackground(Void... params) {
        try {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("image", encodedImage);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = RequestBody.create(jsonObject.toString(), JSON);
            Request request = new Request.Builder()
                    .url(this.urlConnection)
                    .addHeader("User-Agent", "OkHttp Bot")
                    .post(body)
                    .build();
            try (Response response = httpClient.newCall(request).execute()) {

                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                ObjectMapper objectMapper = new ObjectMapper();
                String data = response.body().string();
                BreedPredictions breedPredictions = objectMapper.readValue(data, BreedPredictions.class);
                Log.i(TAG, String.format("Breed predictions: %s", breedPredictions));
                return breedPredictions;
            }
        } catch (Exception e) {
            Log.e(TAG, String.format("%s %s", e, Arrays.toString(e.getStackTrace())));
        }
        return new BreedPredictions();
    }

}