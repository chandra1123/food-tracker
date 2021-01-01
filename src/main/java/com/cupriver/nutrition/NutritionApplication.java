package com.cupriver.nutrition;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Launcher class for the Spring Boot Application.
 * 
 * @author Chandra Prakash (www.cupriver.com)
 *
 */
@SpringBootApplication
@EnableSwagger2
public class NutritionApplication {

	public static void main(String[] args) {
		SpringApplication.run(NutritionApplication.class, args);
	}

	/**
	 * Bean in order to invoke external REST services.
	 * @return
	 */
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	/**
	 * API documentation using swagger.
	 * @return
	 */
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.cupriver.nutrition.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(getApiInfo());
	}
	
	private ApiInfo getApiInfo() {
	    return new ApiInfo(
	            "Calorie Tracker",
	            "Track Calories and Get Fit",
	            "1.0",
	            "",
	            new Contact("Chandra Prakash","www.cupriver.com","chandra1123@gmail.com"),
	            "",
	            "",
	            Collections.emptyList()
	    );
	}
}
