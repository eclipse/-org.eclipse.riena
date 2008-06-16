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
package org.eclipse.riena.ui.core.resource.internal;

/**
 * Constants for icon states.
 */
public final class IconState {

	/** Icon state NORMAL. */
	public static final IconState NORMAL = new IconState("");

	/** Icon state HOVER. */
	public static final IconState HOVER = new IconState("_h_");

	/** Icon state PRESSED. */
	public static final IconState PRESSED = new IconState("_p_");

	/** Icon state DISABLED. */
	public static final IconState DISABLED = new IconState("_d_");

	/** Icon state SELECTED. */
	public static final IconState SELECTED = new IconState("_a_");

	/** Icon state SELECTED_HOVER. */
	public static final IconState SELECTED_HOVER = new IconState("_ah_");

	/** Icon state SELECTED_DISABLED. */
	public static final IconState SELECTED_DISABLED = new IconState("_ad_");

	/** Icon state DEFAULT. */
	public static final IconState DEFAULT = new IconState("_s_");

	/** Icon state HAS_FOCUS. */
	public static final IconState HAS_FOCUS = new IconState("_f_");

	/** Icon state HAS_ROLLOVER_FOCUS. */
	public static final IconState HAS_ROLLOVER_FOCUS = new IconState("_hf_");

	private String defaultMapping;

	private IconState(String defaultMapping) {
		this.defaultMapping = defaultMapping;
	}

	/**
	 * @return The filename character the icon state is mapped to.
	 */
	public String getDefaultMapping() {
		return defaultMapping;
	}

}
