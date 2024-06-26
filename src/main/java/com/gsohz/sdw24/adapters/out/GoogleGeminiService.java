package com.gsohz.sdw24.adapters.out;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PostMapping;

import com.gsohz.sdw24.domain.ports.GenerativeAiService;

import feign.FeignException;
import feign.RequestInterceptor;

@ConditionalOnProperty(name = "generative-ai.provider", havingValue = "GEMINI")
@FeignClient(name = "geminiApi", url = "${gemini.base-url}", configuration = GoogleGeminiService.Config.class)
public interface GoogleGeminiService extends GenerativeAiService {
	
	@PostMapping("/v1beta/models/gemini-pro:generateContent")
	GoogleGeminiResp textOnlyInput(GoogleGeminiReq req);
	
	@Override
    default String generateContent(String objective, String context) {
        String prompt = """
                %s
                %s
                """.formatted(objective, context);

        GoogleGeminiReq req = new GoogleGeminiReq(
                List.of(new Content(List.of(new Part(prompt))))
        );
        try {
            GoogleGeminiResp resp = textOnlyInput(req);
            return resp.candidates().getFirst().content().parts().getFirst().text();
        } catch (FeignException httpErrors) {
            return "Deu ruim! Erro de comunicação com a API do Google Gemini.";
        } catch (Exception unexpectedError) {
            return "Deu mais ruim ainda! O retorno da API do Google Gemini não contem os dados esperados.";
        }
    }

	record GoogleGeminiReq(List<Content> contents) {}
	record Content(List<Part> parts) {}
	record Part(String text) {}
	
	record GoogleGeminiResp(List<Candidate> candidates) {}
	record Candidate(Content content) {}

	@PropertySource("classpath:application.yml")
	class Config{
		@Bean
        RequestInterceptor apiKeyRequestInterceptor(@Value("${gemini.api-key}") String apiKey) {
            return requestTemplate -> requestTemplate.query("key", apiKey);
        }
	}
}