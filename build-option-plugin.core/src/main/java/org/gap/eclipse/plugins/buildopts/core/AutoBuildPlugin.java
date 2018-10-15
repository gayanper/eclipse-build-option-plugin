package org.gap.eclipse.plugins.buildopts.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class AutoBuildPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "build-option-plugin.core";

	// The shared instance
	private static AutoBuildPlugin plugin;
	
	boolean started;

	private AutoBuildDisabler autoBuildDisabler = new AutoBuildDisabler();

	/**
	 * The constructor
	 */
	public AutoBuildPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin  = this;
		started = true;
		DebugPlugin.getDefault().addDebugEventListener(autoBuildDisabler);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		DebugPlugin.getDefault().removeDebugEventListener(autoBuildDisabler);
		plugin = null;
		started = false;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static AutoBuildPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	public void log(CoreException e) {
		getLog().log(new Status(Status.ERROR, PLUGIN_ID, e.getMessage(), e));
	}
}
