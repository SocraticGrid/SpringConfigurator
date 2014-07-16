package org.socraticgrid.spring.tools.configuration;

import java.io.FileNotFoundException;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class SpringConfigurator {

	private final Logger logger = LoggerFactory
			.getLogger(SpringConfigurator.class);

	private String configurationFileName;
	private String taskQueueName;

	public boolean configure(ApplicationContext subjectCtx)
	{
		boolean out=false;
		try
		{
			String[] locations = new String[1];
			if (configurationFileName != null)
			{
				locations[0]=configurationFileName;
				
				ApplicationContext ctx = new ClassPathXmlApplicationContext(locations,subjectCtx);
				ExpressionParser parser = new SpelExpressionParser();
				
				ConfigurationTaskQueue taskQueue = ctx.getBean(taskQueueName,	ConfigurationTaskQueue.class);
				
				if (taskQueue != null)
				{
					Iterator<ConfigurationTask> itr = taskQueue.getItems().iterator();
					while (itr.hasNext())
					{
						ConfigurationTask item = itr.next();
						if (item.configure(ctx,parser)==false)
							{
								logger.error("Failed to configure item: "+item.toString());
							}
					}
				}
				else
				{
					logger.info(taskQueueName+" Not found in configuration file");
					out = true;
				}
			}
			else
			{
				logger.info("No configuration file name defined");
				out = true;
			}
			
		}
		catch(BeanDefinitionStoreException e)
		{
			if (e.getCause() instanceof FileNotFoundException)
			{
				logger.warn(configurationFileName+" is not found on the path, running with application defaults");
			}
			else
			{
				//logger.error("Cause "+e.getCause().toString());
				logger.error("Failed on loading "+configurationFileName,e);	
			}
		}
		catch(Throwable e)
		{
	
			logger.error("Failed on loading "+configurationFileName,e);
		}
		
		return out;
		
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




}
