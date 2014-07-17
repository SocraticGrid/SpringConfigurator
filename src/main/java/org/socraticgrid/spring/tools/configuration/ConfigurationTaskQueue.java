package org.socraticgrid.spring.tools.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ConfigurationTaskQueue {
	Map<String, List<ConfigurationTask>> items = new HashMap<>();

	/**
	 * @return the items
	 */
	public List<ConfigurationTask> getItems(String beanName) {
		return items.get(beanName);
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(List<ConfigurationTask> taskList) {
		for(ConfigurationTask task : taskList) {
			List<ConfigurationTask> tasks = items.get(task.getBeanName());
			if(tasks == null){
				tasks = new ArrayList<>();
			}
			
			tasks.add(task);
			items.put(task.getBeanName(),tasks);
		}
	}
}
