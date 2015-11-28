package com.giffing.wicket.spring.boot.starter.configuration.extensions.wicketstuff.datastore.hazelcast;

import org.apache.wicket.DefaultPageManagerProvider;
import org.apache.wicket.pageStore.IDataStore;
import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.wicketstuff.datastores.common.SessionQuotaManagingDataStore;
import org.wicketstuff.datastores.hazelcast.HazelcastDataStore;

import com.giffing.wicket.spring.boot.context.extensions.ApplicationInitExtension;
import com.giffing.wicket.spring.boot.context.extensions.WicketApplicationInitConfiguration;
import com.giffing.wicket.spring.boot.starter.configuration.extensions.wicketstuff.datastore.TypeParser;
import com.hazelcast.core.HazelcastInstance;


@ApplicationInitExtension
@ConditionalOnProperty(prefix = DataStoreHazelcastProperties.PROPERTY_PREFIX, value = "enabled", matchIfMissing = true)
@ConditionalOnClass(HazelcastInstance.class)
@EnableConfigurationProperties({ DataStoreHazelcastProperties.class })
@AutoConfigureAfter(HazelcastAutoConfiguration.class)
public class DataStoreHazelcastConfig implements WicketApplicationInitConfiguration {

	@Autowired
	private DataStoreHazelcastProperties prop;
	
	@Autowired
	private HazelcastInstance hazelcastInstance;

	@Override
	public void init(WebApplication webApplication) {
		
		webApplication.setPageManagerProvider(new DefaultPageManagerProvider(webApplication) {
			protected IDataStore newDataStore() {
				HazelcastDataStore hazelcastDataStore = new HazelcastDataStore(hazelcastInstance);
				return new SessionQuotaManagingDataStore(hazelcastDataStore, TypeParser.parse(prop.getSessionSize(), prop.getSessionUnit()));
			}
		});

	}

}
