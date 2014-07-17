package org.socraticgrid.spring.tools.configuration.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.socraticgrid.spring.tools.configuration.ConfigurableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:beans.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ConfiguratorTest {
	
	@Autowired
	private ConfigurableBean configurableBean;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testConfigurableBean() {
	
		assertNotNull("ConfigurableBean is NULL!",configurableBean);
		
		String configuralbeName = configurableBean.getConfigurableName();
		assertNotNull("ConfiguralbeName is NULL!",configuralbeName);
		assertTrue("ConfiguralbeName should be configured to *SpringConfigurator was here!* according to src/test/resources/RuntimeConfiguration.xml",
				"SpringConfigurator was here!".equals(configuralbeName));

	}

}
