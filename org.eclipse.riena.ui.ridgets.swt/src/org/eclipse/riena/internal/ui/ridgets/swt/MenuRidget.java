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
package org.eclipse.riena.internal.ui.ridgets.swt;

import org.eclipse.core.databinding.BindingException;
import org.eclipse.riena.ui.ridgets.AbstractMarkerSupport;
import org.eclipse.riena.ui.ridgets.IMenuRidget;
import org.eclipse.swt.widgets.MenuItem;

/**
 *
 */
public class MenuRidget extends MenuItemRidget implements IMenuRidget {

	@Override
	protected void checkUIControl(Object uiControl) {
		super.checkUIControl(uiControl);
		if (!isMenu((MenuItem) uiControl)) {
			throw new BindingException("Menu item is not a menu!"); //$NON-NLS-1$
		}
	}

	@Override
	protected AbstractMarkerSupport createMarkerSupport() {
		return new MenuMarkerSupport(this, propertyChangeSupport);
	}

}
