/*******************************************************************************
 * Copyright (c) 2007, 2009 compeople AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    compeople AG - initial API and implementation
 *******************************************************************************/
package org.eclipse.riena.security.server.session;

import org.eclipse.riena.security.common.SecurityFailure;

/**
 * Failure in the SessionComponent
 * 
 */
public class SessionFailure extends SecurityFailure {

	private static final long serialVersionUID = 7304563697629745254L;

	/**
	 * Creates a new instance of <code>SessionFailure</code>
	 * 
	 * @param msg
	 *            message text or message code
	 */
	public SessionFailure(String msg) {
		super(msg);
	}

	/**
	 * Creates a new instance of <code>SessionFailure</code>
	 * 
	 * @param msg
	 *            message text or message code
	 * @param cause
	 *            exception which has caused this Failure
	 */
	public SessionFailure(String msg, Throwable cause) {
		super(msg, cause);
	}

}
