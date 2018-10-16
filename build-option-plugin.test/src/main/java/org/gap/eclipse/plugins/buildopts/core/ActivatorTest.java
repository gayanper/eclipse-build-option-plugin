package org.gap.eclipse.plugins.buildopts.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
* Sample integration test. In Eclipse, right-click > Run As > JUnit-Plugin. <br/>
* In Maven CLI, run "mvn integration-test".
*/
public class ActivatorTest {

	@Test
	public void veryStupidTest() {
		assertEquals("org.gap.eclipse.plugins.buildoptionplugin.core", AutoBuildPlugin.PLUGIN_ID);
		assertTrue("Plugin should be started", AutoBuildPlugin.getDefault().started);
	}
}