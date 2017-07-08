package pl.gbielanski.newsapp;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


class NewsUtils {
    private static final String GUARDIAN_API_BASE_URL = "http://content.guardianapis.com/search";
    private static final int HTTP_OK = 200;

    static URL getApiURL(String apiKey) {
        URL url = null;
        if (apiKey == null)
            return null;

        Uri uri = Uri.parse(GUARDIAN_API_BASE_URL).buildUpon()
                .appendQueryParameter("q", "debates")
                .appendQueryParameter("api-key", apiKey)
                .build();

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    static List<News> fetchDataFromServer(URL url) {
        String jsonNewsData = getNewsJsonData(url);
        return parseJson2NewsList(jsonNewsData);
    }

    private static List<News> parseJson2NewsList(String jsonNewsData) {
        ArrayList<News> newsList = new ArrayList<>();
        if (jsonNewsData == null)
            return newsList;

        try {
            JSONObject root = new JSONObject(jsonNewsData);
            JSONObject response = root.getJSONObject("response");
            JSONArray results = response.optJSONArray("results");

            if (results != null) {
                for (int i = 0; i < results.length(); i++) {
                    JSONObject result = results.getJSONObject(i);
                    String title = result.optString("webTitle", "");
                    String section = result.optString("sectionName", "");
                    String webPublicationDate = result.optString("webPublicationDate", "");
                    String publicationDate = formatDate(webPublicationDate);
                    String articleUrl = result.optString("webUrl", "");
                    newsList.add(new News(title, section, publicationDate, articleUrl));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsList;
    }

    private static String formatDate(String webPublicationDate) {

        if(webPublicationDate == null || webPublicationDate.isEmpty())
            return "";

        SimpleDateFormat srcFormat = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        srcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat destFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDateString = null;
        try {
            Date date = srcFormat.parse(webPublicationDate);
            formattedDateString = destFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDateString != null ? formattedDateString : "";
    }

    private static String getNewsJsonData(URL url) {
        String jsonData = null;
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.connect();

            int responseCode = httpURLConnection.getResponseCode();

            if (responseCode == HTTP_OK) {
                InputStream inputStream = httpURLConnection.getInputStream();
                jsonData = readFromStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
        }
        return jsonData;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }

        return stringBuilder.toString();
    }
}
