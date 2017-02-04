package org.humanResources.config;

import org.humanResources.json.WebServicesObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebServicesConfig {

	@Bean(name="webServicesObjectMapper")
	public WebServicesObjectMapper webServicesObjectMapper() {
		WebServicesObjectMapper webServicesObjectMapper = new WebServicesObjectMapper();
		return webServicesObjectMapper;
	}

}