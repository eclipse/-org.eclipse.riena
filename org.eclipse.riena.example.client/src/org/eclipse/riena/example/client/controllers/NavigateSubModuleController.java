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
package org.eclipse.riena.example.client.controllers;

import org.eclipse.riena.beans.common.Person;
import org.eclipse.riena.example.client.navigation.model.DemoTargetNodeAssembler;
import org.eclipse.riena.internal.example.client.beans.PersonModificationBean;
import org.eclipse.riena.navigation.ISubModuleNode;
import org.eclipse.riena.navigation.NavigationArgument;
import org.eclipse.riena.navigation.NavigationNodeId;
import org.eclipse.riena.navigation.NodePositioner;
import org.eclipse.riena.navigation.model.ModuleGroupNode;
import org.eclipse.riena.navigation.model.ModuleNode;
import org.eclipse.riena.navigation.model.SubApplicationNode;
import org.eclipse.riena.navigation.ui.controllers.SubModuleController;
import org.eclipse.riena.ui.ridgets.IActionListener;
import org.eclipse.riena.ui.ridgets.IActionRidget;

/**
 * Controller of a sub module to demonstrate the navigate method of {@code
 * INavigationNode}.
 */
public class NavigateSubModuleController extends SubModuleController {

	public NavigateSubModuleController() {
		this(null);
	}

	public NavigateSubModuleController(ISubModuleNode navigationNode) {
		super(navigationNode);
	}

	@Override
	public void configureRidgets() {

		IActionRidget comboAndList = getRidget(IActionRidget.class, "comboAndList"); //$NON-NLS-1$
		comboAndList.setText("Combo and List (SubApplication 1)"); //$NON-NLS-1$
		comboAndList.addListener(new ComboAndListListener());

		IActionRidget tableTextAndTree = getRidget(IActionRidget.class, "tableTextAndTree"); //$NON-NLS-1$
		tableTextAndTree.setText("Table, Text and Tree (SubApplication 2) [First Position]"); //$NON-NLS-1$
		tableTextAndTree.addListener(new TableTextAndTreeListener());

		IActionRidget textAssembly = getRidget(IActionRidget.class, "textAssembly"); //$NON-NLS-1$
		textAssembly.setText("Text Assembly (SubApplication 1)"); //$NON-NLS-1$
		textAssembly.addListener(new TextAssemblyListener());

		IActionRidget openAsFirstModule = getRidget(IActionRidget.class, "openAsFirstModule"); //$NON-NLS-1$
		openAsFirstModule.setText("Open Module As First"); //$NON-NLS-1$
		openAsFirstModule.addListener(new OpenModuleAsFirstListener());

		IActionRidget openAsFirstSubModule = getRidget(IActionRidget.class, "openAsFirstSubModule"); //$NON-NLS-1$
		openAsFirstSubModule.setText("Open SubModule As FIRST in 'Combo And List'"); //$NON-NLS-1$
		openAsFirstSubModule.addListener(new OpenSubModuleAsFirstListener());

		IActionRidget openAsThirdSubModule = getRidget(IActionRidget.class, "openAsThirdSubModule"); //$NON-NLS-1$
		openAsThirdSubModule.setText("Open SubModule As THIRD in 'Combo And List'"); //$NON-NLS-1$
		openAsThirdSubModule.addListener(new OpenSubModuleAsThirdListener());

		IActionRidget openAsOrdinal10 = getRidget(IActionRidget.class, "openAsOrdinal10"); //$NON-NLS-1$
		openAsOrdinal10.setText("Open SubModule with ORDINAL index 10"); //$NON-NLS-1$
		openAsOrdinal10.addListener(new OpenSubModuleOrdinal10Listener());

		IActionRidget openAsOrdinal5 = getRidget(IActionRidget.class, "openAsOrdinal5"); //$NON-NLS-1$
		openAsOrdinal5.setText("Open SubModule with ORDINAL index 5"); //$NON-NLS-1$
		openAsOrdinal5.addListener(new OpenSubModuleOrdinal5Listener());

		IActionRidget addToModule = getRidget(IActionRidget.class, "addToModule"); //$NON-NLS-1$
		addToModule.setText("Add SubModule to current Module at index 2"); //$NON-NLS-1$
		addToModule.addListener(new AddSubModuleToCurrentModule());

		IActionRidget moveModule = getRidget(IActionRidget.class, "moveModule"); //$NON-NLS-1$
		moveModule.setText("Move Active Module"); //$NON-NLS-1$
		moveModule.addListener(new MoveActiveModule());

		IActionRidget moveInActiveModule = getRidget(IActionRidget.class, "moveInActiveModule"); //$NON-NLS-1$
		moveInActiveModule.setText("Move Inactive Module"); //$NON-NLS-1$
		moveInActiveModule.addListener(new MoveInActiveModule());

		IActionRidget demoTarget = getRidget(IActionRidget.class, "jumpToTarget"); //$NON-NLS-1$
		demoTarget.setText("Jump To Target"); //$NON-NLS-1$
		demoTarget.addListener(new JumpToTarget());

		final PersonModificationBean bean = new PersonModificationBean();
		bean.setPerson(new Person("Doe", "Jane")); //$NON-NLS-1$ //$NON-NLS-2$
		IActionRidget navigateRidget = getRidget(IActionRidget.class, "btnNavigateToRidget"); //$NON-NLS-1$
		navigateRidget.addListener(new IActionListener() {
			public void callback() {
				getNavigationNode().navigate(new NavigationNodeId("org.eclipse.riena.example.combo"), //$NON-NLS-1$
						new NavigationArgument(bean, "textFirst")); //$NON-NLS-1$

			}
		});

	}

	private class ComboAndListListener implements IActionListener {

		/**
		 * {@inheritDoc}
		 */
		public void callback() {
			NavigationArgument naviAgr = new NavigationArgument(new Integer(2));
			getNavigationNode().navigate(
					new NavigationNodeId("org.eclipse.riena.example.navigate.comboAndList"), naviAgr); //$NON-NLS-1$
		}

	}

	private class TableTextAndTreeListener implements IActionListener {

		/**
		 * {@inheritDoc}
		 */
		public void callback() {
			NavigationArgument naviAgr = new NavigationArgument();
			naviAgr.setNodePositioner(NodePositioner.ADD_BEGINNING);
			getNavigationNode().navigate(
					new NavigationNodeId("org.eclipse.riena.example.navigate.tableTextAndTree"), naviAgr); //$NON-NLS-1$
		}

	}

	private class OpenModuleAsFirstListener implements IActionListener {

		/**
		 * {@inheritDoc}
		 */
		public void callback() {
			NavigationArgument naviAgr = new NavigationArgument();
			naviAgr.setNodePositioner(NodePositioner.ADD_BEGINNING);
			getNavigationNode().navigate(
					new NavigationNodeId("org.eclipse.riena.example.navigate.firstmodule"), naviAgr); //$NON-NLS-1$

		}

	}

	private class OpenSubModuleAsFirstListener implements IActionListener {

		/**
		 * {@inheritDoc}
		 */
		public void callback() {
			NavigationArgument naviAgr = new NavigationArgument();
			naviAgr.setNodePositioner(NodePositioner.ADD_BEGINNING);
			getNavigationNode().navigate(
					new NavigationNodeId("org.eclipse.riena.example.navigate.firstSubModule"), naviAgr); //$NON-NLS-1$

		}

	}

	private class OpenSubModuleAsThirdListener implements IActionListener {

		/**
		 * {@inheritDoc}
		 */
		public void callback() {
			NavigationArgument naviAgr = new NavigationArgument();
			naviAgr.setNodePositioner(NodePositioner.indexed(2));
			getNavigationNode().navigate(
					new NavigationNodeId("org.eclipse.riena.example.navigate.thirdSubModule"), naviAgr); //$NON-NLS-1$

		}

	}

	private class OpenSubModuleOrdinal10Listener implements IActionListener {

		/**
		 * {@inheritDoc}
		 */
		public void callback() {
			NavigationArgument naviAgr = new NavigationArgument();
			naviAgr.setNodePositioner(NodePositioner.ordinal(10));
			getNavigationNode().navigate(
					new NavigationNodeId("org.eclipse.riena.example.navigate.ordinal10SubModule"), naviAgr); //$NON-NLS-1$

		}

	}

	private class OpenSubModuleOrdinal5Listener implements IActionListener {

		/**
		 * {@inheritDoc}
		 */
		public void callback() {
			NavigationArgument naviAgr = new NavigationArgument();
			naviAgr.setNodePositioner(NodePositioner.ordinal(5));
			getNavigationNode().navigate(
					new NavigationNodeId("org.eclipse.riena.example.navigate.ordinal5SubModule"), naviAgr); //$NON-NLS-1$

		}

	}

	private class AddSubModuleToCurrentModule implements IActionListener {

		private int instanceId = 1;

		/**
		 * {@inheritDoc}
		 */
		public void callback() {
			NavigationArgument naviAgr = new NavigationArgument();
			naviAgr.setNodePositioner(NodePositioner.indexed(2));
			getNavigationNode()
					.create(
							new NavigationNodeId(
									"org.eclipse.riena.example.navigate.submoduleToModuleAtIndex2", String.valueOf(instanceId++)), naviAgr); //$NON-NLS-1$

		}

	}

	private class MoveActiveModule implements IActionListener {

		/**
		 * {@inheritDoc}
		 */
		public void callback() {
			getNavigationNode().getParentOfType(ModuleNode.class).moveTo(
					new NavigationNodeId("org.eclipse.riena.example.moduleGroup1.1.1")); //$NON-NLS-1$

		}
	}

	private class MoveInActiveModule implements IActionListener {

		/**
		 * {@inheritDoc}
		 */
		public void callback() {
			ModuleNode moduleNode = (ModuleNode) getNavigationNode().getParentOfType(SubApplicationNode.class)
					.findNode(new NavigationNodeId("org.eclipse.riena.example.module.1.1.1")); //$NON-NLS-1$
			moduleNode.moveTo(getNavigationNode().getParentOfType(ModuleGroupNode.class).getNodeId());

		}
	}

	private class JumpToTarget implements IActionListener {

		/**
		 * {@inheritDoc}
		 */
		public void callback() {
			getNavigationNode().jump(new NavigationNodeId(DemoTargetNodeAssembler.ID));

		}
	}

	private class TextAssemblyListener implements IActionListener {

		/**
		 * {@inheritDoc}
		 */
		public void callback() {
			getNavigationNode().navigate(new NavigationNodeId("org.eclipse.riena.example.client.textExamplesGroup")); //$NON-NLS-1$
		}

	}

}
