package vsb.cs.java1.java1_pav0546_projekt.game_api;


import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkRequest {
    public static Pair<Integer, String> makeRequest(String urlParam, String method, String contentType, String accept, String authToken, String jsonInputString) {
        try {
            URL url = new URL(urlParam);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type", contentType);
            conn.setRequestProperty("Accept", accept);

            if (authToken != null) {
                conn.setRequestProperty("Authorization", "Bearer " + authToken);
            }

            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                return new Pair<>(responseCode, null);
            } else {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    return new Pair<>(responseCode, response.toString());
                }
            }
        } catch (IOException e) {
            System.out.println("Error while making request: " + e.getMessage());
            return null;
        }
    }
}
