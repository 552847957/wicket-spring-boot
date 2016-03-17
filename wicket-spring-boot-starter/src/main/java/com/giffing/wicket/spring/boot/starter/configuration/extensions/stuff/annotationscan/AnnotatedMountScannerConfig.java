package com.giffing.wicket.spring.boot.starter.configuration.extensions.stuff.annotationscan;

import org.apache.wicket.protocol.http.WebApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.wicketstuff.annotation.scan.AnnotatedMountScanner;

import com.giffing.wicket.spring.boot.context.extensions.ApplicationInitExtension;
import com.giffing.wicket.spring.boot.context.extensions.WicketApplicationInitConfiguration;
import com.giffing.wicket.spring.boot.starter.app.classscanner.candidates.WicketClassCandidate;
import com.giffing.wicket.spring.boot.starter.app.classscanner.candidates.WicketClassCandidatesHolder;

/**
 * Auto configuration for the {@link AnnotatedMountScanner}.
 * 
 * It uses the user defined {@link WebApplication} as the default package scan
 * root directory.
 * 
 * Enables annotate mount scanner if the following two condition matches:
 * 
 * 1. The {@link AnnotatedMountScanner} is in the classpath.
 * 
 * 2. The property {@link AnnotatedMountScannerProperties#PROPERTY_PREFIX}
 * .enabled is true (default = true)
 * 
 * 
 * @author Marc Giffing
 *
 */
@ApplicationInitExtension
@ConditionalOnProperty(prefix = AnnotatedMountScannerProperties.PROPERTY_PREFIX, value = "enabled", matchIfMissing = true)
@ConditionalOnClass(value = org.wicketstuff.annotation.scan.AnnotatedMountScanner.class)
@EnableConfigurationProperties({ AnnotatedMountScannerProperties.class })
@Order(ApplicationInitExtension.DEFAULT_PRECEDENCE + 50)
public class AnnotatedMountScannerConfig implements WicketApplicationInitConfiguration {

	@Autowired
	private AnnotatedMountScannerProperties prop;
	
	@Autowired
	private WicketClassCandidatesHolder candidates;
	
	@Override
	public void init(WebApplication webApplication) {
		String packagename = webApplication.getClass().getPackage().getName();
		if (prop.getPackagename() != null) {
			packagename = prop.getPackagename();
		} else if(candidates.getSpringBootApplicationCandidates().size() > 0){
			WicketClassCandidate springBootApplicationCandidates = candidates.getSpringBootApplicationCandidates().get(0);
			packagename = springBootApplicationCandidates.getCandidate().getPackage().getName();
		}
		
		new AnnotatedMountScanner().scanPackage(packagename).mount(webApplication);
	}

}
