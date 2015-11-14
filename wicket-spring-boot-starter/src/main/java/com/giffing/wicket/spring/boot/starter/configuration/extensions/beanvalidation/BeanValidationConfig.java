package com.giffing.wicket.spring.boot.starter.configuration.extensions.beanvalidation;

import org.apache.wicket.bean.validation.BeanValidationConfiguration;
import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import com.giffing.wicket.spring.boot.starter.configuration.WicketApplicationInitConfiguration;

@Component
@ConditionalOnProperty(prefix = "wicket.beanvalidation", value = "enabled", matchIfMissing = true)
@ConditionalOnClass(value = org.apache.wicket.bean.validation.BeanValidationConfiguration.class)
@EnableConfigurationProperties({ BeanValidationProperties.class })
public class BeanValidationConfig implements WicketApplicationInitConfiguration{

	@Override
	public void init(WebApplication webApplication) {
		new BeanValidationConfiguration().configure(webApplication);
	}
	
}
