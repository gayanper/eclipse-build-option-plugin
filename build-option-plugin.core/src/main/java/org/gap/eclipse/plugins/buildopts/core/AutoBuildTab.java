package org.gap.eclipse.plugins.buildopts.core;


import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class AutoBuildTab extends AbstractLaunchConfigurationTab {

	private Button chkDisableBuild;

	@Override
	public void createControl(Composite parent) {
	    Composite autoBuildComposite = new Composite(parent, SWT.NONE);
	    setControl(autoBuildComposite);

		GridLayout layout = new GridLayout();
		layout.numColumns = 5;
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		autoBuildComposite.setLayout(layout);
		autoBuildComposite.setLayoutData(gridData);
		autoBuildComposite.setFont(parent.getFont());

		chkDisableBuild = createCheckButton(autoBuildComposite, "Disable auto build while execution");
		chkDisableBuild.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		chkDisableBuild.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				entriesChange();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				entriesChange();
			}
		});

	}

	private void entriesChange() {
		setDirty(true);
		updateLaunchConfigurationDialog();
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(Constants.ATTR_DISABLE_AUTO_BUILD, false);
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			chkDisableBuild.setSelection(configuration.getAttribute(Constants.ATTR_DISABLE_AUTO_BUILD, false));
		} catch (CoreException e) {
			AutoBuildPlugin.getDefault().log(e);
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(Constants.ATTR_DISABLE_AUTO_BUILD, chkDisableBuild.getSelection());
	}

	@Override
	public String getName() {
		return "Auto Build";
	}

}
