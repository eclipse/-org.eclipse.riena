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
package org.eclipse.riena.navigation;

import java.util.List;
import java.util.Set;

import org.eclipse.riena.core.marker.IMarkable;
import org.eclipse.riena.navigation.common.ITypecastingAdaptable;

/**
 * Summary of all abilities common to all model objects Each object is
 * presentation able. Each object can be a Parent of one another.
 * 
 * The children ability and parent ability is not included because it is
 * different for different model nodes
 */
public interface INavigationNode<C extends INavigationNode<?>> extends ITypecastingAdaptable, IMarkable {

	/**
	 * The states of the navigation node.
	 */
	public enum State {
		CREATED, ACTIVATED, DEACTIVATED, DISPOSED
	};

	/**
	 * Call this method to activate this node. The node forwards the activation
	 * request to the navigation processor. The navigation processor checks
	 * which nodes have to be deactivated before this node can be activated. The
	 * navigation processor creates an INavigationContext and checks all nodes
	 * with allowsDeactivate() and allowsActivate() dependent on the
	 * INavigationContext. Following the NavigationProcessor deactivates and
	 * activates the corresponding nodes using the activation context.
	 */
	void activate();

	/**
	 * Call this method to dispose this node. Depending on the type of the node
	 * the dispose does different things. What the node does is implemented in
	 * the navigation processor. The processor decides e.g. that if the first
	 * module node in a group is disposed, than the group is closed. Before
	 * closing the navigation processor deactivates all affected nodes. Than a
	 * dispose change is fired, and at the end the node itself is removed from
	 * the tree.
	 */
	void dispose();

	/**
	 * Called by a NavigationProcessor to activate the node within an
	 * INavigationContext.
	 * 
	 * @param pContext
	 *            - the Context to activate within
	 */
	void activate(INavigationContext pContext);

	/**
	 * Called by a NavigationProcessor before activating the node within an
	 * INavigationContext.
	 * 
	 * @param pContext
	 *            - the Context to activate within
	 */
	void onBeforeActivate(INavigationContext pContext);

	/**
	 * Called by a NavigationProcessor after activating the node within an
	 * INavigationContext.
	 * 
	 * @param pContext
	 *            - the Context to activate within
	 */
	void onAfterActivate(INavigationContext pContext);

	/**
	 * Called by a NavigationProcessor to dispose the node within an
	 * INavigationContext.
	 * 
	 * @param pContext
	 *            - the Context to activate within
	 */
	void dispose(INavigationContext pContext);

	/**
	 * Called by a NavigationProcessor before disposing the node within an
	 * INavigationContext.
	 * 
	 * @param pContext
	 *            - the Context to activate within
	 */
	void onBeforeDispose(INavigationContext pContext);

	/**
	 * Called by a NavigationProcessor after disposing the node within an
	 * INavigationContext.
	 * 
	 * @param pContext
	 *            - the Context to activate within
	 */
	void onAfterDispose(INavigationContext pContext);

	/**
	 * Called by a NavigationProcessor to deactivate the node within an
	 * INavigationContext.
	 * 
	 * @param pContext
	 *            - the Context to deactivate within
	 */
	void deactivate(INavigationContext pContext);

	/**
	 * Called by a NavigationProcessor before deactivating the node within an
	 * INavigationContext.
	 * 
	 * @param pContext
	 *            - the Context to activate within
	 */
	void onBeforeDeactivate(INavigationContext pContext);

	/**
	 * Called by a NavigationProcessor after deactivating the node within an
	 * INavigationContext.
	 * 
	 * @param pContext
	 *            - the Context to activate within
	 */
	void onAfterDeactivate(INavigationContext pContext);

	/**
	 * Called by a NavigationProcessor to check if a node can be activated
	 * within an INavigationContext.
	 * 
	 * @param pContext
	 *            - the Context to check within
	 */
	boolean allowsActivate(INavigationContext pContext);

	/**
	 * Called by a NavigationProcessor to check if a node can be deactivated
	 * within an INavigationContext.
	 * 
	 * @param pContext
	 *            - the Context to check within
	 */
	boolean allowsDeactivate(INavigationContext pContext);

	/**
	 * Called by a NavigationProcessor to check if a node can be disposed within
	 * an INavigationContext.
	 * 
	 * @param pContext
	 *            - the Context to check within
	 */
	boolean allowsDispose(INavigationContext pContext);

	/**
	 * Called from the navigation processor to remove a node during dispose
	 * Removes the passed node from the list child nodes
	 * 
	 * @param pChild
	 *            - the child node to remove
	 * @param context
	 *            - the navigation context to word in
	 */
	void removeChild(INavigationContext context, INavigationNode<?> pChild);

	/**
	 * @return - an ordered list of child nodes of this node
	 */
	List<C> getChildren();

	/**
	 * Adds the passed child to the list of child nodes
	 * 
	 * @param pChild
	 *            - the child node to add
	 */
	void addChild(C pChild);

	/**
	 * @param pIndex
	 *            - index of the child
	 * @return the child at the specified index or null
	 */
	C getChild(int pIndex);

	/**
	 * @param pChild
	 *            - the child to find
	 * @return the index of the child starting at 0 or -1 if the passed child is
	 *         not a child of the node addressed
	 */
	int getIndexOfChild(INavigationNode<?> pChild);

	/**
	 * @return the Presentation of this node
	 */
	public IPresentation getPresentation();

	/**
	 * Look for the next in the hierarchy available presentation
	 */
	public IPresentation getNextPresentation();

	void setPresentation(IPresentation pPresentation);

	String getLabel();

	void setLabel(String pLabel);

	/**
	 * @return icon or null if no icon should be shown.
	 */
	String getIcon();

	/**
	 * The icon to show.
	 * 
	 * @param icon
	 *            the icon.
	 */
	void setIcon(String icon);

	INavigationNode<?> getParent();

	void setParent(INavigationNode<?> pParent);

	INavigationProcessor getNavigationProcessor();

	void setNavigationProcessor(INavigationProcessor pProcessor);

	/**
	 * @return true, if this navigation node should be presented expanded
	 */
	boolean isExpanded();

	/**
	 * Define the expanded state
	 * 
	 * @param pExpanded
	 */
	void setExpanded(boolean pExpanded);

	/**
	 * Answer true if the node is a leaf = does not have children
	 * 
	 * @return
	 */
	boolean isLeaf();

	/**
	 * @return the markable helper object contained in the navigation node
	 */
	IMarkable getMarkable();

	/**
	 * @return the next in hierarchy available context
	 */
	Object getContext();

	/**
	 * sets the context
	 */
	void setContext(Object pContext);

	/**
	 * Adds an action to the node. Actions can be associated with nodes to
	 * automatically show and hide actions corresponding to the activated node
	 * 
	 * @param pAction
	 *            - the action to add
	 */
	void addAction(IAction pAction);

	/**
	 * Remove an action from the node
	 * 
	 * @param pAction
	 *            - the action to remove
	 */
	void removeAction(IAction pAction);

	/**
	 * @return the actions defined by this node
	 */
	Set<IAction> getActions();

	/**
	 * @return all actions defined by this node and its parents
	 */
	Set<IAction> getAllActions();

	/**
	 * @return the current state of this node.
	 * @see State
	 */
	State getState();

	/**
	 * @return true if this node has the state ACTIVATED
	 */
	boolean isActivated();

	/**
	 * @return true if this node has the state DEACTIVATED
	 */
	boolean isDeactivated();

	/**
	 * @return true if this node has the state DISPOSED
	 */
	boolean isDisposed();

	/**
	 * @return true if this node has the state CREATED
	 */
	boolean isCreated();

	/**
	 * @return the selected
	 */
	boolean isSelected();

	/**
	 * @param selected
	 *            the selected to set
	 */
	void setSelected(boolean selected);

	/**
	 * Returns parent node of type <code>clazz</code> or <code>null</code>.
	 * 
	 * @param clazz
	 *            the type of parent node.
	 * @return the parent node of type <code>clazz</code>.
	 */
	<N extends INavigationNode<?>> N getParentOfType(Class<N> clazz);

	/**
	 * Sets set blocked state of this NavigationNode. If true, the view which
	 * presents the node is blocked for all user input.
	 * 
	 * @param blocked
	 *            true blocks user input
	 */

	void setBlocked(boolean blocked);

	/**
	 * Returns the blocked state of this NagigationNode. True means that user
	 * input is blocked for the view which presents the node.
	 * 
	 * @return true if input is blocked, false otherwise
	 */
	boolean isBlocked();

	INavigationNodeId getPresentationId();

	/**
	 * Creates the specified navigation node and adds it to the application
	 * model it does not already exist.
	 * 
	 * @param targetId
	 *            Refers to an extension point describing the target node.
	 * @see INavigationNodeProvider
	 */
	void create(INavigationNodeId targetId);

	/**
	 * Creates the specified navigation node (if it does not already exist) and
	 * navigates to it.
	 * 
	 * @param targetId
	 *            Refers to an extension point describing the target node.
	 * @see INavigationNodeProvider
	 */
	void navigate(INavigationNodeId targetId);

	/**
	 * Creates the specified navigation node (if it does not already exist) and
	 * navigates to it.
	 * 
	 * @param targetId
	 *            Refers to an extension point describing the target node.
	 * @see INavigationNodeProvider
	 */
	void navigate(INavigationNodeId targetId, Object argument);

}
