package com.google.firebase.codelab.friendlychat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class QueryUtils {
    static private URL url = null;
    static private JSONObject jsonresponse = null;
    private QueryUtils(){

    }
    static JSONObject makeHttpReq(String urlString, String key, String lang, String text){
        try {
            url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(URLEncoder.encode("key", "UTF-8"));
            stringBuilder.append("=");
            stringBuilder.append(URLEncoder.encode(key, "UTF-8"));
            stringBuilder.append("&");
            stringBuilder.append(URLEncoder.encode("lang", "UTF-8"));
            stringBuilder.append("=");
            stringBuilder.append(URLEncoder.encode(lang, "UTF-8"));
            stringBuilder.append("&");
            stringBuilder.append(URLEncoder.encode("text", "UTF-8"));
            stringBuilder.append("=");
            stringBuilder.append(URLEncoder.encode(text, "UTF-8"));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            out.write(stringBuilder.toString());
            out.flush();
            out.close();
            connection.connect();
            jsonresponse = new JSONObject(readJason(connection));
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonresponse;
    }
    private static String readJason(HttpURLConnection connection) throws IOException{
        BufferedReader br;
        br = new BufferedReader((new InputStreamReader(connection.getInputStream())));
        StringBuilder builder = new StringBuilder("");
        String str = br.readLine();
        while (str != null && str.length() > 0){
            builder.append(str);
            str = br.readLine();
        }
        return new String(builder);
    }
}

