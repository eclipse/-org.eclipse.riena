/*******************************************************************************
 * Copyright (c) 2007, 2008 compeople AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    compeople AG - initial API and implementation
 *******************************************************************************/
package org.eclipse.riena.ui.filter.extension;

import org.eclipse.riena.core.injector.extension.ExtensionInterface;
import org.eclipse.riena.core.injector.extension.MapName;

/**
 * Interface for a UIFiltere extension that defines how to create a filter with
 * a list of rules.
 */
@ExtensionInterface
public interface IUIFilterExtension {

	/**
	 * Returns the filterID
	 */
	String getFilterId();

	/**
	 * Returns the nodeIds
	 */
	@MapName("nodeId")
	IFilterNodeId[] getNodeIds();

	@MapName("ruleMarkerRidget")
	IRuleMarkerRidget[] getRuleMarkerRidgets();

	@MapName("ruleMarkerMenuItem")
	IRuleMarkerMenuItem[] getRuleMarkerMenuItems();

	@MapName("ruleMarkerNavigation")
	IRuleMarkerNavigation[] getRuleMarkerNavigations();

	@MapName("ruleValidatorRidget")
	IRuleValidatorRidget[] getRuleValidatorRidgets();

}
