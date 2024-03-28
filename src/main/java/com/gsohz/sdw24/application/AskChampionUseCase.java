package com.gsohz.sdw24.application;

import com.gsohz.sdw24.domain.exception.ChampionNotFoundException;
import com.gsohz.sdw24.domain.model.Champions;
import com.gsohz.sdw24.domain.ports.ChampionsRepository;
import com.gsohz.sdw24.domain.ports.GenerativeAiService;

public record AskChampionUseCase(ChampionsRepository repository, GenerativeAiService genAiApi) {
	
	public String askChampion(Long championId ,String question) {
		Champions champion = repository.findById(championId)
				.orElseThrow(() -> new ChampionNotFoundException(championId));
		
		String context = champion.generateContextByQuestion(question);
		String objective = """
				Atue como um assistente com a habilidade de se comportar como os Campeões do League of Legends (LOL).
				Responda perguntas incorporando a personalidade e estilo de um determinado Campeão.
				Segue a pergunta, o nome do Campeão e sua respectiva lore (história):
				
				""";
		
		genAiApi.generateContent(objective, context);
		
		return context;
	}
	
}
