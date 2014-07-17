package org.socraticgrid.spring.tools.configuration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;


/**
 *
 * This class provides property configuration for all beans after
 * the init method if one is specified is called.
 * 
 * In the life of a Spring bean this is known as ProcessAfterInitialization
 * during the ApplicationContext startup.
 * 
 * There does exist another point of configuration - ProcessBeforeInitialization
 * which can be implemented but in this case is not.
 * 
 *
 */
public class SpringConfigurator implements BeanPostProcessor, Ordered {

	//The order in which this BeanPostProcessor will be called
	private static final int ORDER = 0;

	@Autowired
	private ApplicationContext applicationContext;

	private final Logger logger = LoggerFactory.getLogger(SpringConfigurator.class);

	private String configurationFileName;
	private String taskQueueName;
	private ConfigurationTaskQueue taskQueue;
	private ApplicationContext taskContext;
	private ExpressionParser parser = new SpelExpressionParser();


	public Object configure(Object bean, String beanName)
	{
		//Lazy initialization - cannot be put into init so called once here.
		if (configurationFileName != null && taskContext == null)
		{
			taskContext = new ClassPathXmlApplicationContext(
					new String[]{configurationFileName} , applicationContext);
			taskQueue = taskContext.getBean(taskQueueName, ConfigurationTaskQueue.class);
			logger.debug("********* Completed Initialization for *SpringConfigurator*");
		}

		if( taskContext != null && taskQueue != null)
		{
			List<ConfigurationTask> tasks = taskQueue.getItems(beanName);
			if(tasks == null){
				return bean;
			}

			for(ConfigurationTask task : tasks) {
				bean = task.configure(taskContext, parser);
			}
		}

		return bean;
	}

	/**
	 * @return the configurationFileName
	 */
	public String getConfigurationFileName() {
		return configurationFileName;
	}

	/**
	 * @param configurationFileName
	 *            the configurationFileName to set
	 */
	public void setConfigurationFileName(String configurationFileName) {
		this.configurationFileName = configurationFileName;
	}

	/**
	 * @return the taskQueueName
	 */
	public String getTaskQueueName() {
		return taskQueueName;
	}

	/**
	 * @param taskQueueName
	 *            the taskQueueName to set
	 */
	public void setTaskQueueName(String taskQueueName) {
		this.taskQueueName = taskQueueName;
	}

	
	/**
	 * Will process the bean after init is called
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {

		logger.debug("********* Calling ProcessAfterInitialization for *"+beanName+"*");
		//Configure the bean 
		return configure(bean, beanName);
	}

	/**
	 * Will process the bean before init is called.
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {

		logger.debug("********* Calling ProcessBeforeInitialization for *"+beanName+"*");
		//Open for 
		return bean;
	}


	@Override
	public int getOrder() {
		return ORDER;
	}

}
