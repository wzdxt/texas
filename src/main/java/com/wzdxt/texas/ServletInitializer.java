package com.wzdxt.texas;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

//public class ServletInitializer extends SpringBootServletInitializer {
public class ServletInitializer {

//	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TexasApplication.class);
	}

}
