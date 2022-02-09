package br.com.userregistration.common.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    /**
	 * Inject ModelMapper to convert MODEL to DTO and vice versa.
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	    
}
