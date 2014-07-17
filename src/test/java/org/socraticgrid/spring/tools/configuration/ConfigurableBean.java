/**
 * 
 */
package org.socraticgrid.spring.tools.configuration;

/**
 * @author steven
 * Jul 16, 2014
 *
 */
public class ConfigurableBean {

	private String configurableName = "Change Me";

	public String getConfigurableName() {
		return configurableName;
	}

	public void setConfigurableName(String configurableName) {
		this.configurableName = configurableName;
	}
	
}
