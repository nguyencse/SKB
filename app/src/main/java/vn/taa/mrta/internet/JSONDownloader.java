package vn.taa.mrta.internet;

import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Putin on 3/4/2017.
 */

public class JSONDownloader extends AsyncTask<Void, Void, String> {

    private String url;
    private List<HashMap<String, String>> attributes;
    private StringBuilder data = new StringBuilder();

    public JSONDownloader(String url) {
        this.url = url;
        attributes = new ArrayList<>();
    }

    public JSONDownloader(String url, List<HashMap<String, String>> attributes) {
        this.url = url;
        this.attributes = attributes;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            if (attributes.size() == 0) {
                getDataGET(connection);
            } else {
                getDataPOST(connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data.toString();
    }

    private void getDataGET(HttpURLConnection connection) {
        readStream(connection);
    }

    private void getDataPOST(HttpURLConnection connection) {
        try {
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            Uri.Builder builder = new Uri.Builder();
            int n = attributes.size();
            String key = "";
            String value = "";
            for (int i = 0; i < n; i++) {
                for (Map.Entry entry : attributes.get(i).entrySet()) {
                    key = (String) entry.getKey();
                    value = (String) entry.getValue();
                    builder.appendQueryParameter(key, value);
                }
            }
            String query = builder.build().getEncodedQuery();

            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            bw.write(query);
            bw.flush();

            bw.close();
            osw.close();
            os.close();

            readStream(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readStream(HttpURLConnection connection) {
        try {
            connection.connect();

            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String line = "";
            while ((line = br.readLine()) != null) {
                data.append(line);
            }

            br.close();
            isr.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}