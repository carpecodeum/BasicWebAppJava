package com.develogical;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import okhttp3.*;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryProcessor {
    private static final String OPENAI_API_KEY = "<your api key>"; // Replace this with actual API key
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private final OkHttpClient client;
    private final Gson gson;

    public QueryProcessor() {
        this.client = new OkHttpClient();
        this.gson = new Gson();
    }

    private String callOpenAI(String query) {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", "gpt-3.5-turbo");
        
        JsonObject message = new JsonObject();
        message.addProperty("role", "user");
        message.addProperty("content", query);
        
        JsonArray messages = new JsonArray();
        messages.add(message);
        
        requestBody.add("messages", messages);

        RequestBody body = RequestBody.create(requestBody.toString(),
            MediaType.parse("application/json"));

        Request request = new Request.Builder()
            .url(OPENAI_API_URL)
            .addHeader("Authorization", "Bearer " + OPENAI_API_KEY)
            .addHeader("Content-Type", "application/json")
            .post(body)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected response " + response);
            
            JsonObject jsonResponse = gson.fromJson(response.body().string(), JsonObject.class);
            return jsonResponse.getAsJsonArray("choices")
                .get(0).getAsJsonObject()
                .getAsJsonObject("message")
                .get("content").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Sorry, I encountered an error processing your request.";
        }
    }

    public List<Integer> parseLarger(String query) {
        ArrayList<Integer> numbers = new ArrayList<>();

        // Define a regex pattern to find numbers
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(query);

        // Add all matched numbers to the ArrayList
        while (matcher.find()) {
            numbers.add(Integer.parseInt(matcher.group()));
        }
        return numbers;
    }

    public String process(String query) {


        if (query.contains("your name")) {
            return "brags25";
        }

        // For all other queries, use OpenAI
        String mod = ".Return only the answer as a single number or word, without any explanation or sentence. Query: " + query;
        return callOpenAI(mod);
    }
}