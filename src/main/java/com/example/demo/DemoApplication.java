package com.example.demo;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
@SpringBootApplication
public class DemoApplication {
	public record ChatRequest(String text) {}
	public record ChatResponse(String text) {}
	@Value("${google.genai.apikey}")
	String apiKey;

	@PostMapping
	public ChatResponse chat(@RequestBody ChatRequest req) {
		// GOOGLE_API_KEY
		try (Client client = Client.builder().apiKey(apiKey).build();) {
			GenerateContentResponse response =
					client.models.generateContent("gemini-2.0-flash", req.text + " 앞의 요청에 대해서 일본어로 답해줘.", null);
			return new ChatResponse(response.text());
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
