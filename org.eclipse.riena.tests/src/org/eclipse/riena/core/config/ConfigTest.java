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
package org.eclipse.riena.core.config;

import junit.framework.AssertionFailedError;

import org.eclipse.riena.tests.RienaTestCase;

/**
 * @author christian
 * 
 */
public class ConfigTest extends RienaTestCase {

	/**
	 * @throws java.lang.Exception
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		startBundles("org\\.eclipse\\.equinox\\.cm.*", null);
		startBundles("org\\.eclipse\\.riena\\.core", null);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Override
	protected void tearDown() throws Exception {
	}

	/**
	 * test that config is set in business class
	 */
	public void testConfigBusinessClass() {
		// create new business class instance
		final BusinessClass bc = new BusinessClass();
		final ErrorStatus status = new ErrorStatus();

		assertTrue(bc.getName().equals("default"));

		// wrap with proxy
		ConfigUtility cu = new ConfigUtility(getContext());
		cu.createConfigProxy(bc, "org.eclipse.riena.business.pid");

		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					// wait a short time until config is applied
					Thread.sleep(200);
					// invoke the business call method
					bc.invoke("first call");
					assertTrue(bc.getName().equals("christian campo compeople ag"));
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (AssertionFailedError e) {
					status.setTestSuccesfull(false);
				}
			}

		};
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		cu.removeConfigProxy();
		assertTrue(status.isTestSuccesfull());
	}

	/**
	 * test that config is also set in a derived business class (=subclass)
	 */
	public void testConfigDerivedBusinessClass() {
		// create new business class instance
		final DerivedBusinessClass bc = new DerivedBusinessClass();
		final ErrorStatus status = new ErrorStatus();

		assertTrue(bc.getName().equals("default"));

		// wrap with proxy
		ConfigUtility cu = new ConfigUtility(getContext());
		cu.createConfigProxy(bc, "org.eclipse.riena.business.pid");

		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					// wait a short time until config is applied
					Thread.sleep(200);
					// invoke the business call method
					bc.invoke("first call");
					assertTrue(bc.getName().equals("christian campo compeople ag"));
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (AssertionFailedError e) {
					status.setTestSuccesfull(false);
				}
			}

		};
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		cu.removeConfigProxy();
		assertTrue(status.isTestSuccesfull());
	}

	public class ErrorStatus {
		private boolean testSuccesfull = true;

		public boolean isTestSuccesfull() {
			return testSuccesfull;
		}

		public void setTestSuccesfull(boolean testSuccesfull) {
			this.testSuccesfull = testSuccesfull;
		}

	}
}