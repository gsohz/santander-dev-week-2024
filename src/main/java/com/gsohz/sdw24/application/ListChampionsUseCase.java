package com.gsohz.sdw24.application;

import java.util.List;

import com.gsohz.sdw24.domain.model.Champions;
import com.gsohz.sdw24.domain.ports.ChampionsRepository;

public record ListChampionsUseCase(ChampionsRepository repository) {
	public List<Champions> findAll(){
		return repository.findAll();
	}
}
