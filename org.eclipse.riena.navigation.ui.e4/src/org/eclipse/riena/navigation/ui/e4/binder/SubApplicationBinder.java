/*******************************************************************************
 * Copyright (c) 2007, 2012 compeople AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    compeople AG - initial API and implementation
 *******************************************************************************/
package org.eclipse.riena.navigation.ui.e4.binder;

import javax.inject.Inject;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.riena.internal.ui.ridgets.swt.uiprocess.UIProcessRidget;
import org.eclipse.riena.navigation.IModuleGroupNode;
import org.eclipse.riena.navigation.IModuleNode;
import org.eclipse.riena.navigation.ISubApplicationNode;
import org.eclipse.riena.navigation.NavigationNodeId;
import org.eclipse.riena.navigation.listener.SubApplicationNodeListener;
import org.eclipse.riena.navigation.ui.controllers.SubApplicationController;
import org.eclipse.riena.navigation.ui.e4.rendering.RienaWBWRenderer;
import org.eclipse.riena.navigation.ui.swt.binding.DelegatingRidgetMapper;
import org.eclipse.riena.navigation.ui.swt.binding.InjectSwtViewBindingDelegate;
import org.eclipse.riena.navigation.ui.swt.presentation.SwtViewProvider;
import org.eclipse.riena.navigation.ui.swt.views.SubApplicationView;
import org.eclipse.riena.ui.ridgets.controller.IController;
import org.eclipse.riena.ui.ridgets.swt.uibinding.AbstractViewBindingDelegate;
import org.eclipse.riena.ui.ridgets.swt.uibinding.SwtControlRidgetMapper;
import org.eclipse.riena.ui.swt.uiprocess.UIProcessControl;

/**
 * replacement for {@link SubApplicationView}
 */
@SuppressWarnings("restriction")
public class SubApplicationBinder {

	@Inject
	private IEclipseContext context;

	@Inject
	private IEventBroker eventBroker;

	private final AbstractViewBindingDelegate binding;
	private final ISubApplicationNode subApplicationNode;
	private SubApplicationListener subApplicationListener;

	public SubApplicationBinder(final ISubApplicationNode subApplicationNode) {
		this.subApplicationNode = subApplicationNode;
		binding = createBinding();

	}

	protected AbstractViewBindingDelegate createBinding() {
		final DelegatingRidgetMapper ridgetMapper = new DelegatingRidgetMapper(SwtControlRidgetMapper.getInstance());
		ridgetMapper.addMapping(UIProcessControl.class, UIProcessRidget.class);
		return new InjectSwtViewBindingDelegate(ridgetMapper);
	}

	private void createUIProcessControl(final Shell shell) {
		final UIProcessControl uiControl = new UIProcessControl(shell);
		uiControl.setPropertyName("uiProcessRidget"); //$NON-NLS-1$
		binding.addUIControl(uiControl);
	}

	/**
	 * Binds the navigation node to the view. Creates the widgets and the controller if necessary.<br>
	 * Also the menus and the tool bar items are binded.
	 * 
	 * @see org.eclipse.riena.navigation.ui.swt.views.INavigationNodeView#bind(org.eclipse.riena.navigation.INavigationNode)
	 */
	public void bind() {
		final Object shell = context.get(IServiceConstants.ACTIVE_SHELL);
		if (null == shell) {
			eventBroker.subscribe(RienaWBWRenderer.SHELL_CREATED, new LazyBinder());
			return;
		}
		internalBind(shell);
	}

	/**
	 * Lazy binding for the case that the {@link Shell} is not available yet.
	 */
	private final class LazyBinder implements EventHandler {
		public void handleEvent(final Event event) {
			internalBind(event.getProperty(IEventBroker.DATA));
		}
	}

	private void internalBind(final Object shell) {
		createUIProcessControl((Shell) shell);
		bindController();
	}

	private void bindController() {
		if (null == getNavigationNode().getNavigationNodeController()) {
			final SubApplicationController subApplicationController = new SubApplicationController(subApplicationNode);
			binding.injectRidgets(subApplicationController);
			binding.bind(subApplicationController);

			subApplicationListener = new SubApplicationListener();
			getNavigationNode().addListener(subApplicationListener);
			subApplicationController.afterBind();

		}
	}

	private class SubApplicationListener extends SubApplicationNodeListener {
		@Override
		public void block(final ISubApplicationNode source, final boolean block) {
			super.block(source, block);
			for (final IModuleGroupNode group : source.getChildren()) {
				for (final IModuleNode module : group.getChildren()) {
					module.setBlocked(block);
				}
			}
		}

		@Override
		public void disposed(final ISubApplicationNode source) {
			unbind();
		}

		@Override
		public void nodeIdChange(final ISubApplicationNode source, final NavigationNodeId oldId, final NavigationNodeId newId) {
			SwtViewProvider.getInstance().replaceNavigationNodeId(source, oldId, newId);
		}
	}

	public void unbind() {
		if (getNavigationNode() != null) {

			getNavigationNode().removeListener(subApplicationListener);

			if (getNavigationNode().getNavigationNodeController() instanceof IController) {
				final IController controller = (IController) getNavigationNode().getNavigationNodeController();
				binding.unbind(controller);
				//				if (menuItemBindingManager != null) {
				//					menuItemBindingManager.unbind(controller, getUIControls());
				//				}
			}
		}
	}

	private ISubApplicationNode getNavigationNode() {
		return subApplicationNode;
	}
}