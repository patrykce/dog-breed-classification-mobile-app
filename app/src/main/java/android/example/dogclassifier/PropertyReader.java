package android.example.dogclassifier;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Nirmal Dhara on 12-07-2015.
 */
public class PropertyReader {
    public static String[] getConnectionProperty(Context context) throws IOException {
        Properties properties = new Properties();
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open("configuration.properties");
        properties.load(inputStream);
        return new String[]{properties.getProperty("remote.api.host"), properties.getProperty("remote.api.port")};
    }
}
