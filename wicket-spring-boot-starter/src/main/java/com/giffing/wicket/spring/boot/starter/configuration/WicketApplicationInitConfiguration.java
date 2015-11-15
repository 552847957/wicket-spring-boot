package com.giffing.wicket.spring.boot.starter.configuration;

import org.apache.wicket.protocol.http.WebApplication;

import com.giffing.wicket.spring.boot.starter.app.WicketBootWebApplication;

/**
 * To provide custom modification of the init Method of Wickets {@link WebApplication}
 * class. An extension class should implement this interface. All classes implementing
 * this interface are injected in the {@link WicketBootWebApplication} as a list and on
 * each implementation the init method is called with the current {@link WebApplication}.
 * 
 * @author Marc Giffing
 *
 */
public interface WicketApplicationInitConfiguration {

	/**
	 * With the given {@link WebApplication} the
	 * {@link WicketApplicationInitConfiguration}s can modify/extend the init()
	 * method of the {@link WebApplication}.
	 * 
	 * @param webApplication
	 *          the current {@link WebApplication}
	 */
	void init(WebApplication webApplication);
}
