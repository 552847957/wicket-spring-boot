package com.giffing.wicket.spring.boot.starter.configuration.extensions.dev.spring.devtools;

import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.devtools.autoconfigure.LocalDevToolsAutoConfiguration;
import org.springframework.boot.devtools.restart.ConditionalOnInitializedRestarter;

import com.giffing.wicket.spring.boot.context.extensions.ApplicationInitExtension;
import com.giffing.wicket.spring.boot.context.extensions.WicketApplicationInitConfiguration;

/**
 * The Wicket serializer does not working with Spring Boot Devtools so we have to provide a custom {@link SpringDevToolsSerializer}.
 * It should be active by default if the following conditions matches.
 * 
 * 1. The {@link LocalDevToolsAutoConfiguration} class is in the classpath. This means that the Spring Boot Devtools is available in the classpath.
 * 
 * 2. The property "spring.devtools.restart.enabled" is set to true. (default is true). There is maybe a better option to check
 * 
 * 3. Restarter condition is active {@link ConditionalOnInitializedRestarter}.
 * 
 * @author Marc Giffing
 *
 */
@ApplicationInitExtension
@ConditionalOnProperty(prefix = "spring.devtools.restart", value = "enabled", matchIfMissing = true)
@ConditionalOnClass(LocalDevToolsAutoConfiguration.class)
@ConditionalOnInitializedRestarter
public class SpringDevtoolsSerializerConfig implements WicketApplicationInitConfiguration {

	@Override
	public void init(WebApplication webApplication) {
		webApplication.getFrameworkSettings().setSerializer(new SpringDevToolsSerializer());
	}
	
}
