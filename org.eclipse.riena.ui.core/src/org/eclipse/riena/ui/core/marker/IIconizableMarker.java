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
package org.eclipse.riena.ui.core.marker;

import org.eclipse.riena.core.marker.IMarker;

/**
 * A marker that can be visualized by marking the icons of a marked element with
 * a marker icon.
 */
public interface IIconizableMarker extends IMarker {

	/**
	 * Returns the key to the configuration of the marker icon. What this
	 * configuration looks like and where it is located depends on the UI
	 * implementation.
	 * 
	 * @return The key to the configuration of the marker icon.
	 */
	String getIconConfiguationKey();

}
