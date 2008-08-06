/*******************************************************************************
 * Copyright (c) 2007 compeople AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    compeople AG - initial API and implementation
 *******************************************************************************/
package org.eclipse.riena.internal.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.equinox.log.Logger;
import org.eclipse.riena.core.RienaPlugin;
import org.eclipse.riena.core.RienaStartupStatus;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.service.log.LogService;

public class Activator extends RienaPlugin {

	/**
	 * 
	 */
	public static final String RIENA_FORCE_START = "Riena-ForceStart"; //$NON-NLS-1$

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.riena.core"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		Activator.plugin = this;
		final Logger LOGGER = getLogger(Activator.class.getName());

		logStage(LOGGER);
		startForcedRienaBundles(LOGGER);

		((RienaStartupStatusSetter) RienaStartupStatus.getInstance()).setStarted(true);
	}

	/**
	 * @param logger
	 */
	private void logStage(Logger logger) {
		IStringVariableManager variableManager = VariablesPlugin.getDefault().getStringVariableManager();
		String stage;
		try {
			stage = variableManager.performStringSubstitution("Riena is running in stage '${riena.stage}'.");
		} catch (CoreException e) {
			stage = "No stage information set.";
		}
		logger.log(LogService.LOG_INFO, stage);
	}

	/**
	 * Force starting of bundles that are marked true with the
	 * <code>RIENA_FORCE_START</code> bundle header.
	 * 
	 * @param context
	 * @param LOGGER
	 * @throws BundleException
	 */
	private void startForcedRienaBundles(final Logger LOGGER) throws BundleException {
		Bundle[] bundles = getContext().getBundles();
		for (Bundle bundle : bundles) {
			boolean forceStart = Boolean.parseBoolean((String) bundle.getHeaders().get(RIENA_FORCE_START));
			if (bundle.getState() != Bundle.ACTIVE
					&& (bundle.getSymbolicName().equals("org.eclipse.equinox.cm") || bundle.getSymbolicName().equals(
							"org.eclipse.equinox.log"))) {
				forceStart = true;
			}
			if (!forceStart)
				continue;

			// TODO STARTING == LAZY, so start that also, STARTING is
			// disabled, bundles with forceStart should not be LAZY
			if (bundle.getState() == Bundle.RESOLVED/*
													 * || bundle.getState() ==
													 * Bundle.STARTING
													 */) {
				try {
					bundle.start();
					LOGGER.log(LogService.LOG_INFO, "Forced start: '" + bundle.getSymbolicName() + "' succesful.");
				} catch (RuntimeException rte) {
					LOGGER.log(LogService.LOG_ERROR, "Forced start: '" + bundle.getSymbolicName()
							+ "' failed with exception.", rte);
					throw rte;
				}
			} else if (bundle.getState() == Bundle.INSTALLED) {
				LOGGER.log(LogService.LOG_ERROR, "Forced start: '" + bundle.getSymbolicName() + "' failed. Header '"
						+ RIENA_FORCE_START + "' is set but is only in state INSTALLED (not RESOLVED).");
			} else if (bundle.getState() == Bundle.ACTIVE) {
				LOGGER.log(LogService.LOG_DEBUG, "Forced start: '" + bundle.getSymbolicName() + "' is already ACTIVE.");
			}
		}
	}

	/*
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		((RienaStartupStatusSetter) RienaStartupStatus.getInstance()).setStarted(false);
		Activator.plugin = null;
		super.stop(context);
	}

	/**
	 * Get the plugin instance.
	 * 
	 * @return
	 */
	public static Activator getDefault() {
		return plugin;
	}
}
