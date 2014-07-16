package org.socraticgrid.spring.tools.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class PropertyAssignmentTask implements ConfigurationTask {

	private final Logger logger = LoggerFactory
			.getLogger(PropertyAssignmentTask.class);
	
	
	private String name;
	private String property;
	private Object value;
	

	/**
	 * @return the bean name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the bean name to set
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

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	public boolean configure(ApplicationContext ctx, ExpressionParser parser) {
		boolean out = false;
		Object bean = ctx.getBean(name);
		if (bean != null) {
			try {
				StandardEvaluationContext evalContext = new StandardEvaluationContext(
						bean);
				parser.parseExpression(property).setValue(evalContext,
						value);
				out = true;
			} catch (Throwable e) {
				logger.error("Problem configuring " + this.toString(), e);
			}

		}
		return out;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PropertyAssignmentTask [name=" + name + ", property="
				+ property + ", value=" + value + "]";
	}



}
