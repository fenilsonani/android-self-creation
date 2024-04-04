package com.example.display_product;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    CustomAdapter adapter;
    ArrayList<Player> playerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        playerList = new ArrayList<>();
        adapter = new CustomAdapter();
        listView.setAdapter(adapter);

        new FetchData().execute("https://demonuts.com/Demonuts/JsonTest/Tennis/json_parsing.php");
    }

    class FetchData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String urlString = strings[0];
            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                StringBuilder stringBuilder = new StringBuilder();
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    stringBuilder.append(new String(buffer, 0, bytesRead));
                }
                return stringBuilder.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray dataArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject dataObject = dataArray.getJSONObject(i);
                        Player player = new Player();
                        player.setName(dataObject.getString("name"));
                        player.setCountry(dataObject.getString("country"));
                        player.setImageUrl(dataObject.getString("imgURL"));
                        player.setCity(dataObject.getString("city"));
                        playerList.add(player);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return playerList.size();
        }

        @Override
        public Object getItem(int position) {
            return playerList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.list_item_layout, parent, false);
            }

            ImageView imageView = convertView.findViewById(R.id.imageView);
            TextView nameTextView = convertView.findViewById(R.id.nameTextView);
            TextView countryTextView = convertView.findViewById(R.id.countryTextView);
            TextView cityTextView=convertView.findViewById(R.id.cityTextView);

            Player player = playerList.get(position);
            nameTextView.setText(player.getName());
            countryTextView.setText(player.getCountry());
            cityTextView.setText(player.getCity());

            // Load image from URL
            new DownloadImageTask(imageView).execute(player.getImageUrl());

            return convertView;
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                imageView.setImageBitmap(result);
            }
        }
    }
}
