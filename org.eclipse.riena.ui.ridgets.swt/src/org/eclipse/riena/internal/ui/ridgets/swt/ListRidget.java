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
package org.eclipse.riena.internal.ui.ridgets.swt;

import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.viewers.AbstractListViewer;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;

import org.eclipse.riena.ui.ridgets.ISelectableRidget;
import org.eclipse.riena.ui.ridgets.swt.AbstractListRidget;
import org.eclipse.riena.ui.ridgets.swt.AbstractSWTRidget;

/**
 * Ridget for SWT {@link List} widgets.
 */
public class ListRidget extends AbstractListRidget {

	private ListViewer viewer;

	public ListRidget() {
		selectionTypeEnforcer = new SelectionTypeEnforcer();
	}

	@Override
	protected void checkUIControl(Object uiControl) {
		AbstractSWTRidget.assertType(uiControl, List.class);
	}

	@Override
	public List getUIControl() {
		return (List) super.getUIControl();
	}

	protected int getUIControlSelectionIndex() {
		return getUIControl().getSelectionIndex();
	}

	protected int[] getUIControlSelectionIndices() {
		return getUIControl().getSelectionIndices();
	}

	protected int getUIControlItemCount() {
		return getUIControl().getItemCount();
	}

	@Override
	protected void bindUIControl() {
		final List control = getUIControl();
		if (control != null && rowObservables != null) {
			viewer = new ListViewer(control);
			final ObservableListContentProvider viewerCP = new ObservableListContentProvider();
			viewer.setLabelProvider(new ListLabelProvider(viewerCP.getKnownElements(), rowBeanClass, renderingMethod));
			viewer.setContentProvider(viewerCP);
			viewer.setInput(rowObservables);

			updateComparator();
			updateEnabled(isEnabled());

			control.addSelectionListener(selectionTypeEnforcer);
			control.addMouseListener(doubleClickForwarder);
		}
	}

	@Override
	protected void unbindUIControl() {
		super.unbindUIControl();

		List control = getUIControl();
		if (control != null) {
			control.removeSelectionListener(selectionTypeEnforcer);
			control.removeMouseListener(doubleClickForwarder);
		}
		viewer = null;
	}

	@Override
	protected AbstractListViewer getViewer() {
		return viewer;
	}

	protected void updateEnabled(boolean isEnabled) {
		final String savedBackgroundKey = "oldbg"; //$NON-NLS-1$
		if (isEnabled) {
			if (isBound() && rowObservables != null) {
				refreshViewer();
				createSelectionBindings();
				List list = viewer.getList();
				list.setBackground((Color) list.getData(savedBackgroundKey));
				list.setData(savedBackgroundKey, null);
			}
		} else {
			disposeSelectionBindings();
			if (isBound()) {
				refreshViewer();
				List list = viewer.getList();
				if (MarkerSupport.HIDE_DISABLED_RIDGET_CONTENT) {
					list.deselectAll();
				}
				list.setData(savedBackgroundKey, list.getBackground());
				list.setBackground(list.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
			}
		}
	}

	// helping classes
	// ////////////////

	/**
	 * Enforces selection in the control:
	 * <ul>
	 * <li>disallows selection changes when the ridget is "output only"</li>
	 * <li>disallows multiple selection is the selection type of the ridget is
	 * {@link ISelectableRidget.SelectionType#SINGLE}</li>
	 * </ul>
	 */
	private final class SelectionTypeEnforcer extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			List control = (List) e.widget;
			if (isOutputOnly()) {
				revertSelection(control);
			} else if (SelectionType.SINGLE.equals(getSelectionType())) {
				if (control.getSelectionCount() > 1) {
					// ignore this event
					e.doit = false;
					selectFirstItem(control);
				}
			}
		}

		private void selectFirstItem(List control) {
			// set selection to most recent item
			control.setSelection(control.getSelectionIndex());
			// fire event
			Event event = new Event();
			event.type = SWT.Selection;
			event.doit = true;
			control.notifyListeners(SWT.Selection, event);
		}

		private void revertSelection(List control) {
			control.setRedraw(false);
			try {
				// undo user selection when "output only"
				viewer.setSelection(new StructuredSelection(getSelection()));
			} finally {
				// redraw control to remove "cheese" that is caused when
				// using the keyboard to select the next row
				control.setRedraw(true);
			}
		}
	}

}
