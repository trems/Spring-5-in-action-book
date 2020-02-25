package ru.sharashin.tacocloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private IngredientConverter ingredientConverter;

	@Autowired
	public WebConfig(IngredientConverter ingredientConverter) {
		this.ingredientConverter = ingredientConverter;
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(ingredientConverter);
	}
}
