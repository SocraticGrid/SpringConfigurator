package org.socraticgrid.spring.tools.configuration;



import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class AddToListTask  implements ConfigurationTask {
	private final Logger logger = LoggerFactory
			.getLogger(AddToListTask.class);
	
	
	private String beanName;
	private String property;
	private Object value;
	

	/**
	 * @return the bean name
	 */
	public String getBeanName() {
		return beanName;
	}

	/**
	 * @param name
	 *            the bean name to set
	 */
	public void setBeanName(String name) {
		this.beanName = name;
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

	@SuppressWarnings("unchecked")
	public Object configure(ApplicationContext ctx, ExpressionParser parser) {
		Object bean = ctx.getBean(beanName);
		if (bean != null) {
			try {
				StandardEvaluationContext evalContext = new StandardEvaluationContext(
						bean);
				@SuppressWarnings("rawtypes")
				List list = (List)parser.parseExpression(property).getValue(evalContext);
				list.add(value);
			} catch (Throwable e) {
				logger.error("Problem configuring " + this.toString(), e);
			}

		}
		return bean;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PropertyAssignmentTask [name=" + beanName + ", property="
				+ property + ", value=" + value + "]";
	}



}
