package com.giffing.wicket.spring.boot.starter.condition;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Conditional;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(WicketSettingsCondition.class)
public @interface ConditionalOnWicket {
	
	/**
	 * The major java version to check with the current value 
	 */
	int value();
	
	/**
	 * Defines how the given major version should be checked with the current version
	 */
	Range range() default Range.EQUALS_OR_HIGHER;

	

	
	enum Range {
		/**
		 * The Wicket major version equals the 
		 */
		EQUALS,
		/**
		 * The Wicket major version equals or is newer
		 */
		EQUALS_OR_HIGHER,
		
		/**
		 * The Wicket major version equals or is lower
		 */
		EQUALS_OR_LOWER,

	}
}
