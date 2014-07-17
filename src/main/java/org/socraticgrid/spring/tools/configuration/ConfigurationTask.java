package org.socraticgrid.spring.tools.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.expression.ExpressionParser;

public interface ConfigurationTask {
	public Object configure(ApplicationContext ctx, ExpressionParser parser);
	public String getBeanName();
}
