package com.gsohz.sdw24.application;

import com.gsohz.sdw24.domain.exception.ChampionNotFoundException;
import com.gsohz.sdw24.domain.model.Champions;
import com.gsohz.sdw24.domain.ports.ChampionsRepository;

public record AskChampionUseCase(ChampionsRepository repository) {
	
	public String askChampion(Long championId ,String question) {
		Champions champion = repository.findById(championId)
				.orElseThrow(() -> new ChampionNotFoundException(championId));
		
		String championContext = champion.generateContextByQuestion(question);
		
		return championContext;
	}
	
}
