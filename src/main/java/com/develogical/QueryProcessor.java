package com.develogical;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryProcessor {

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

        if(query.toLowerCase().contains("largest")) {
            List<Integer> nums = parseLarger(query);
            return String.valueOf(Collections.max(nums));
        }

        if (query.contains("plus")) {
            String[] parts = query.split(" ");
            int num1 = Integer.parseInt(parts[2]);
            int num2 = Integer.parseInt(parts[4]);
    
            // Calculating the result
            int result = num1 + num2;
            return String.valueOf(result);
        }



        if (query.toLowerCase().contains("shakespeare")) {
            return "William Shakespeare (26 April 1564 - 23 April 1616) was an " +
                    "English poet, playwright, and actor, widely regarded as the greatest " +
                    "writer in the English language and the world's pre-eminent dramatist.";
        }

        if (query.contains("your name")) {
            return "brags25";
        }

        return "";


    }

}