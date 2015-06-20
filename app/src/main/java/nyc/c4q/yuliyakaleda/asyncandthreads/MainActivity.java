package nyc.c4q.yuliyakaleda.asyncandthreads;


import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private static final String url = "https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1";
    private static final String TAG_ITEMS = "items";
    private static final String TAG_MEDIA = "media";
    private static final String TAG_M = "m";
    private static final int MAX_RESULTS = 5;

    private ListView lv;
    private JSONArray items;
    private GetPictures myAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button reload = (Button) findViewById(R.id.reload);

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAsyncTask = new GetPictures();
                myAsyncTask.execute();
            }
        });
    }

    class GetPictures extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            ArrayList<String> arrayImages = new ArrayList<String>();
            BufferedReader reader;
            String line;

            try {
                URL urlString = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlString.openConnection();
                StringBuilder stringBuilder = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                String resultString = stringBuilder.toString();

                if (resultString != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(resultString);
                        items = jsonObj.getJSONArray(TAG_ITEMS);
                        for (int i = 0; i < MAX_RESULTS; i++) {
                            JSONObject c = items.getJSONObject(i);
                            JSONObject obj = c.getJSONObject(TAG_MEDIA);
                            String m = obj.getString(TAG_M);
                            arrayImages.add(m);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                //Log.e("MainActivity", "Caught  while ... ", e);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return arrayImages;
        }


        @Override
        protected void onPostExecute(ArrayList<String> imagesArray) {
            lv = (ListView) findViewById(R.id.lv);
            ImageAdapter adapter = new ImageAdapter(MainActivity.this, imagesArray);
            lv.setAdapter(adapter);
        }
    }
}


