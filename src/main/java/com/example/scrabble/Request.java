package com.example.scrabble;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Request {
  public static void main(String[] args) {
    try {
      URL url = new URL("http://localhost:8080/game/create/");
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setDoOutput(true);

      String jsonInputString = "{\"playerNames\":[\"Player 1\",\"Player 2\"]}";
      System.out.println("Sending request to: " + url);
      System.out.println("Request body: " + jsonInputString);

      try (OutputStream os = connection.getOutputStream()) {
        byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
        os.write(input, 0, input.length);
      }

      int responseCode = connection.getResponseCode();
      System.out.println("Response Code: " + responseCode);

      // Read the response, including error stream if applicable
      try (BufferedReader br = new BufferedReader(
          new InputStreamReader(
              responseCode >= 400 ? connection.getErrorStream() : connection.getInputStream(),
              StandardCharsets.UTF_8))) {
        StringBuilder response = new StringBuilder();
        String responseLine;
        while ((responseLine = br.readLine()) != null) {
          response.append(responseLine.trim());
        }
        System.out.println("Response: " + response);
      }

      connection.disconnect();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}