package io.github.varconf.client.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtil {
    public static String get(String url) throws IOException {
        BufferedReader reader = null;
        try {
            URL uri = new URL(url);
            URLConnection conn = uri.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.connect();

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String result = "", line;
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            return result;
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                throw ex;
            }
        }
    }

    public static String post(String url, String body) throws IOException {
        PrintWriter writer = null;
        BufferedReader reader = null;
        try {
            URL uri = new URL(url);
            URLConnection conn = uri.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            writer = new PrintWriter(conn.getOutputStream());
            writer.print(body);
            writer.flush();

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String result = "", line;
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            return result;
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                throw ex;
            }
        }
    }
}
