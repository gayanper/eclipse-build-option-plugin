package org.gap.eclipse.plugins.buildopts.core;

import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IProcess;

public class AutoBuildDisabler implements IDebugEventSetListener {
	private int toggleCount = 0;

	@Override
	public void handleDebugEvents(DebugEvent[] events) {
		for (DebugEvent event : events) {
			Object source = event.getSource();
			if (source instanceof IProcess) {
				ILaunchConfiguration configuration = ((IProcess) source).getLaunch().getLaunchConfiguration();
				try {
					if (supported(configuration)) {
						switch (event.getKind()) {
						case DebugEvent.CREATE: {
							onCreate(configuration);
							break;
						}
						case DebugEvent.TERMINATE: {
							onTerminate(configuration);
							break;
						}
						}
					}
				} catch (CoreException e) {
					AutoBuildPlugin.getDefault().log(e);
				}
			}
		}

	}

	private void onTerminate(ILaunchConfiguration configuration) {
		if (toggleCount == 1) {
			try {
				updateAutoBuild(true);
			} catch (CoreException e) {
				AutoBuildPlugin.getDefault().log(e);
			}
			toggleCount = 0;
		} else if (toggleCount > 0) {
			toggleCount--;
		}
	}

	private void onCreate(ILaunchConfiguration configuration) {
		boolean value;
		try {
			value = configuration.getAttribute(Constants.ATTR_DISABLE_AUTO_BUILD, false);
			if (value && updateAutoBuild(false)) {
				toggleCount++;
			}
		} catch (CoreException e) {
			AutoBuildPlugin.getDefault().log(e);
		}
	}

	private boolean updateAutoBuild(boolean value) throws CoreException {
		IWorkspaceDescription description = ResourcesPlugin.getWorkspace().getDescription();
		// disable only if it is enabled, but allow to enable always.
		if (description.isAutoBuilding() || value) {
			description.setAutoBuilding(value);
			ResourcesPlugin.getWorkspace().setDescription(description);
			return true;
		}
		return false;
	}

	private boolean supported(ILaunchConfiguration configuration) throws CoreException {
		return configuration.hasAttribute(Constants.ATTR_DISABLE_AUTO_BUILD);
	}

}
