package com.example.fetchrewardsproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String JSON_URL = "https://fetch-hiring.s3.amazonaws.com/hiring.json";

    private RecyclerView recyclerView;
    private FetchItemAdapter adapter;
    private List<FetchItem> fetchItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new FetchItemAdapter(fetchItems);
        recyclerView.setAdapter(adapter);

        // Start fetching data
        new FetchDataTask().execute(JSON_URL);
    }

    private class FetchDataTask extends AsyncTask<String, Void, List<FetchItem>> {

        @Override
        protected List<FetchItem> doInBackground(String... urls) {
            String urlString = urls[0];
            List<FetchItem> resultList = new ArrayList<>();

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(urlString);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                // Read response
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder jsonBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonBuilder.append(line);
                }

                JSONArray jsonArray = new JSONArray(jsonBuilder.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);

                    int id = obj.optInt("id", -1);
                    int listId = obj.optInt("listId", -1);
                    String name = obj.optString("name", null);

                    // Filter out blank or null names
                    if (name == null || name.trim().isEmpty() || name.equals("null")) {
                        continue;
                    }

                    FetchItem item = new FetchItem(id, listId, name);
                    resultList.add(item);
                }

            } catch (IOException | JSONException e) {
                Log.e(TAG, "Error fetching or parsing data", e);
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


            Collections.sort(resultList, new Comparator<FetchItem>() {
                @Override
                public int compare(FetchItem o1, FetchItem o2) {
                    int listIdCompare = Integer.compare(o1.getListId(), o2.getListId());
                    if (listIdCompare != 0) {
                        return listIdCompare;
                    }
                    return o1.getName().compareToIgnoreCase(o2.getName());
                }
            });

            return resultList;
        }

        @Override
        protected void onPostExecute(List<FetchItem> items) {
            super.onPostExecute(items);
            // Update the list and notify adapter
            fetchItems.clear();
            fetchItems.addAll(items);
            adapter.notifyDataSetChanged();
        }
    }
}