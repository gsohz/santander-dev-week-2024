package com.gsohz.sdw24.adapters.in;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gsohz.sdw24.application.ListChampionsUseCase;
import com.gsohz.sdw24.domain.model.Champions;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Campeões", description = "Endpoints do domínio de Campeões do LOL.")
@RestController
@RequestMapping("/champions")
public record ListChampionsRestController(ListChampionsUseCase useCase) {
	@CrossOrigin
	@GetMapping
	public List<Champions> findAllChampions(){
		return useCase.findAll();
	}
}
