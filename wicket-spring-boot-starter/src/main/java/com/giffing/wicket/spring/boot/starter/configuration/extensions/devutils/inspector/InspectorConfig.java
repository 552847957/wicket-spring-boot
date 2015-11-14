package com.giffing.wicket.spring.boot.starter.configuration.extensions.devutils.inspector;

import org.apache.wicket.devutils.inspector.LiveSessionsPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import com.giffing.wicket.spring.boot.starter.configuration.WicketApplicationInitConfiguration;

/**
 * Mounts pages from the devutils inspector package. Currently only the
 * {@link LiveSessionsPage} is supported and is mounted if the following
 * condition matches.
 * 
 * 1. The {@link LiveSessionsPage} is in the classpath.
 * 
 * 2. The property enableLiveSessionsPage is enabled
 * 
 * @author Marc Giffing
 *
 */
@Component
@ConditionalOnClass(value = { org.apache.wicket.devutils.inspector.LiveSessionsPage.class, })
@EnableConfigurationProperties({ InspectorProperties.class })
public class InspectorConfig implements WicketApplicationInitConfiguration {

	@Autowired
	private InspectorProperties properties;

	@Override
	public void init(WebApplication webApplication) {
		if (properties.isEnableLiveSessionsPage()) {
			webApplication.mountPage(properties.getLiveSessionPageMount(), LiveSessionsPage.class);
		}
	}

}
