package org.socraticgrid.spring.tools.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class ExpressionConfigTask implements ConfigurationTask {
	private final Logger logger = LoggerFactory
			.getLogger(ExpressionConfigTask.class);	private String name;
	private String property;
	private String expression;
	


	@Override
	public boolean configure(ApplicationContext ctx, ExpressionParser parser) {
		boolean out = false;
		Object bean = ctx.getBean(name);
		if (bean != null) {
			try {
				StandardEvaluationContext evalContext = new StandardEvaluationContext(
						bean);
				evalContext.setBeanResolver(new BeanFactoryResolver(ctx));

				Object value = parser.parseExpression(expression)
						.getValue();

				parser.parseExpression(property).setValue(evalContext,
						value);
				out = true;
			} catch (Throwable e) {
				logger.error("Problem configuring " + this.toString(), e);
			}

		}
		return out;
	}

	/**
	 * @return the expression
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * @param expression
	 *            the expression to set
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the property
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * @param property
	 *            the property to set
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ExpressionConfigTask [name=" + name + ", property=" + property
				+ ", expression=" + expression + "]";
	}


}
