package com.gsohz.sdw24.adapters.out;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.gsohz.sdw24.domain.ports.GenerativeAiService;

@FeignClient(name = "openAiChatApi", url = "${openai.base-url}")
public interface OpenAiChatApi extends GenerativeAiService {
	
	@PostMapping("/v1/chat/completions")
	OpenAiChatCompletionResp chatCompletion(OpenAiChatCompletionReq req);
	
	@Override
	default String generateContent(String objective, String context) {
		
		String model = "gpt-3.5-turbo";
		List<Message> messages = List.of(
				new Message("system", objective),
				new Message("user", context)
		);
		
		OpenAiChatCompletionReq req = new OpenAiChatCompletionReq(model, messages);
		
		OpenAiChatCompletionResp resp = chatCompletion(req);
		
		return resp.choices().getFirst().message().content;
	}

	record OpenAiChatCompletionReq(String model, List<Message> messages) {}
	record Message(String role, String content) {}
	
	record OpenAiChatCompletionResp(List<Choice> choices) {}
	record Choice (Message message) {}
}
