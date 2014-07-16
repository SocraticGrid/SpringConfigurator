package org.socraticgrid.spring.tools.configuration;

import java.util.List;

public class ConfigurationTaskQueue {
	List<ConfigurationTask> items;

	public ConfigurationTaskQueue()
	{
		
	}
	/**
	 * @return the items
	 */
	public List<ConfigurationTask> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(List<ConfigurationTask> items) {
		this.items = items;
	}
}
