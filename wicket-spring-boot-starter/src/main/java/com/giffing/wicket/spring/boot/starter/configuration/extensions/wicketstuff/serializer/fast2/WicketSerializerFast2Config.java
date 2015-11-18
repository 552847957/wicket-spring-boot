package com.giffing.wicket.spring.boot.starter.configuration.extensions.wicketstuff.serializer.fast2;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.serialize.java.JavaSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.wicketstuff.pageserializer.fast2.Fast2WicketSerializer;

import com.giffing.wicket.spring.boot.starter.configuration.WicketApplicationInitConfiguration;

@Component
@ConditionalOnProperty(prefix = "wicket.wicketstuff.serializer.fast2", value = "enabled", matchIfMissing = true)
@ConditionalOnClass(value = org.wicketstuff.pageserializer.fast2.Fast2WicketSerializer.class)
@EnableConfigurationProperties({ WicketSerializerFast2Properties.class })
public class WicketSerializerFast2Config implements WicketApplicationInitConfiguration {

	@Override
	public void init(WebApplication webApplication) {
		// only set the kryo serializer if its the default serialiter
		if (webApplication.getFrameworkSettings().getSerializer() instanceof JavaSerializer) {
			webApplication.getFrameworkSettings().setSerializer(new Fast2WicketSerializer());
		}
	}

}
