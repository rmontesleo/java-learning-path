package com.demo.overview;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;

public class Main {

    public static void main(String[] args) {
        OpenAIClient client = OpenAIOkHttpClient.fromEnv();

        ResponseCreateParams params = ResponseCreateParams.builder()
            .model("gpt-5.4-nano")
            .input("Write a short bedtime story about a unicorn.")
            .build();

        Response response = client.responses().create(params);
        System.out.println(response.output() );
    }

}
