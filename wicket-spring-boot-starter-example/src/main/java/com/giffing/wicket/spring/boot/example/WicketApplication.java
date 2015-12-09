package com.giffing.wicket.spring.boot.example;

import org.springframework.boot.builder.SpringApplicationBuilder;

import com.giffing.wicket.spring.boot.example.pages.SecondPage;
import com.giffing.wicket.spring.boot.starter.app.WicketBootWebApplication;
import com.giffing.wicket.spring.boot.starter.context.WicketSpringBootApplication;

@WicketSpringBootApplication
public class WicketApplication extends WicketBootWebApplication {
	
	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder()
			.sources(WicketApplication.class)
			.run(args);
	}

	@Override
	protected void init() {
		super.init();
		mountPage("second-page", SecondPage.class);
	}

}
