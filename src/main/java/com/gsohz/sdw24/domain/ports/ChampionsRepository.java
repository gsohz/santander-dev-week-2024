package com.gsohz.sdw24.domain.ports;

import java.util.List;
import java.util.Optional;

import com.gsohz.sdw24.domain.model.Champions;

public interface ChampionsRepository {
	List<Champions> findAll();
	
	Optional<Champions> findById(Long id);
}
