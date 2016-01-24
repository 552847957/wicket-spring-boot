package com.giffing.wicket.spring.boot.starter.web.config;

import org.apache.wicket.protocol.ws.javax.JavaxWebSocketFilter;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of the {@link WicketWebInitializerConfig} depending on property
 * conditions and dependency existence.
 * 
 * @author Marc Giffing
 */
@Configuration
public class WicketWebInitializerAutoConfig {

	/**
	 * The {@link StandardWicketWebInitializer} should be configured if no other
	 * {@link WicketWebInitializerConfig} is configured. Its quasi the standard
	 * configuration.
	 * 
	 * @author Marc Giffing
	 */
	@Configuration
	@ConditionalOnMissingBean(WicketWebInitializerConfig.class)
	@AutoConfigureAfter(WebSocketWicketWebInitializerAutoConfiguration.class)
	public static class StandardWicketWebInitializerAutoConfiguration {

		@Bean
		public WicketWebInitializerConfig wicketWebInitializerConfig() {
			return new StandardWicketWebInitializer();
		}
	}
	
	/**
	 * @author Marc Giffing
	 */
	@Configuration
	@ConditionalOnClass(JavaxWebSocketFilter.class)
	@ConditionalOnProperty(prefix = "wicket.external.websocket", value = "enabled", matchIfMissing = true)
	public static class WebSocketWicketWebInitializerAutoConfiguration {

		@Bean
		public WicketWebInitializerConfig wicketWebInitializerConfig() {
			return new WebSocketWicketWebInitializer();
		}

	}
}
