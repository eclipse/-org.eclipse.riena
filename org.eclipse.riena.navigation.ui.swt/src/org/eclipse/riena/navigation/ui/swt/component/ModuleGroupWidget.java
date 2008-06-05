package org.eclipse.riena.navigation.ui.swt.component;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.riena.navigation.IModuleGroupNode;
import org.eclipse.riena.navigation.IModuleNode;
import org.eclipse.riena.navigation.ISubModuleNode;
import org.eclipse.riena.navigation.ui.swt.lnf.LnfManager;
import org.eclipse.riena.navigation.ui.swt.lnf.rienadefault.ModuleGroupRenderer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class ModuleGroupWidget extends Canvas {

	private List<ModuleItem> items;

	private int itemHeight;

	private IModuleGroupNode moduleGroupNode;
	private ModuleItem openItem;

	private ModuleGroupRenderer renderer;

	private PaintDelegation paintDelegation;

	private SelectionListener selectionListener;

	public ModuleGroupWidget(Composite parent, int style, IModuleGroupNode moduleGroupNode) {
		super(parent, style);
		this.moduleGroupNode = moduleGroupNode;
		itemHeight = computeItemHeight();
		items = new ArrayList<ModuleItem>();
		addListeners();
	}

	private int computeItemHeight() {
		GC gc = new GC(this);
		int h = getRenderer().computeItemHeight(gc);
		gc.dispose();
		return h;
	}

	protected void addListeners() {
		paintDelegation = new PaintDelegation();
		addPaintListener(paintDelegation);
		selectionListener = new SelectionListener();
		addMouseListener(selectionListener);
		addMouseTrackListener(selectionListener);
		addMouseMoveListener(selectionListener);
	}

	private void removeListeners() {
		removePaintListener(paintDelegation);
		removeMouseListener(selectionListener);
		removeMouseTrackListener(selectionListener);
		removeMouseMoveListener(selectionListener);
	}

	public int calcBounds(int hint) {
		Point p = computeSize(SWT.DEFAULT, SWT.DEFAULT);
		FormData fd = new FormData();
		fd.top = new FormAttachment(0, hint);
		fd.left = new FormAttachment(0, 10);
		hint += p.y;
		fd.width = p.x;
		fd.bottom = new FormAttachment(0, hint);
		setLayoutData(fd);
		layout();
		update();
		hint += 3;
		return hint;
	}

	protected void openItem(ModuleItem item) {
		hidePrevious();
		if (item != openItem) {
			openItem = item;
		} else if (allowsDeactivate()) {
			openItem = null;
		}

		redraw();
		// wrong place???
		item.getModuleNode().activate();
	}

	protected void selectActive() {
		if (openItem != null) {
			ISubModuleNode sm = openItem.getSelection();
			if (sm != null) {
				sm.activate();
			}
		}
	}

	protected boolean allowsDeactivate() {
		return false;
	}

	protected void hidePrevious() {
		if (openItem != null) {
			openItem.getBody().setBounds(0, 0, 0, 0);
			openItem.getBody().setVisible(false);
		}

	}

	/**
	 * Returns the module at the given point.
	 * 
	 * @param point -
	 *            point over module item
	 * @return module item; or null, if not item was found
	 */
	protected ModuleItem getItem(Point point) {

		int itemWidth = getRenderer().getItemWidth();
		int xs = (getBounds().width - itemWidth) / 2;
		int xe = xs + itemWidth;
		return getItem(point, xs, xe);

	}

	/**
	 * Returns the module at the given point, if the point is over the close
	 * "button".
	 * 
	 * @param point -
	 *            point over module item
	 * @return module item; or null, if not item was found
	 */
	protected ModuleItem getClosingItem(Point point) {

		getRenderer().setBounds(getBounds());
		GC gc = new GC(this);
		Rectangle closeBounds = getRenderer().computeCloseButtonBounds(gc);
		int xs = closeBounds.x;
		int xe = xs + closeBounds.width;
		ModuleItem item = getItem(point, xs, xe);
		if (item != null) {
			IModuleNode moduleNode = item.getModuleNode();
			if (!moduleNode.isCloseable()) {
				item = null;
			}
		}
		gc.dispose();
		return item;

	}

	/**
	 * Returns the module at the given point.
	 * 
	 * @param point -
	 *            point over module item
	 * @param xs -
	 * @param xe -
	 * @return module item; or null, if not item was found
	 */
	private ModuleItem getItem(Point point, int xs, int xe) {

		if (point.x < xs || point.x > xe) {
			return null;
		}

		int ys = 2;
		int ye = 0;
		for (ModuleItem item : getItems()) {
			ye = ys + itemHeight - 2;
			if (point.y >= ys && point.y <= ye) {
				return item;
			}
			ye += 4;
			if (item.getBody().isVisible()) {
				ye += item.getOpenHeight();
			}
			ys = ye;
		}
		return null;

	}

	protected List<ModuleItem> getItems() {
		return items;
	}

	protected void onPaint(PaintEvent e) {

		getRenderer().setItems(getItems());
		Point size = getRenderer().computeSize(e.gc, SWT.DEFAULT, SWT.DEFAULT);
		getRenderer().setBounds(0, 0, size.x, size.y);
		getRenderer().paint(e.gc, getModuleGroupNode());

	}

	protected void registerItem(ModuleItem navigationItem) {
		getItems().add(navigationItem);
		redraw();
	}

	protected void unregisterItem(ModuleItem navigationItem) {
		getItems().remove(navigationItem);
		if (navigationItem == openItem) {
			closeCurrent();
		}
		navigationItem.getBody().dispose();
		redraw();
	}

	/**
	 * @see org.eclipse.swt.widgets.Control#computeSize(int, int)
	 */
	@Override
	public Point computeSize(int wHint, int hHint) {

		GC gc = new GC(Display.getCurrent());
		getRenderer().setItems(getItems());
		Point size = getRenderer().computeSize(gc, wHint, hHint);
		gc.dispose();
		return size;

	}

	public void closeCurrent() {
		if (openItem != null) {
			hidePrevious();
			openItem = null;
			redraw();
		}

	}

	private ModuleGroupRenderer getRenderer() {
		if (renderer == null) {
			renderer = (ModuleGroupRenderer) LnfManager.getLnf().getRenderer("ModuleGroup.renderer"); //$NON-NLS-1$
		}
		return renderer;
	}

	/**
	 * @return the moduleGroupNode
	 */
	private IModuleGroupNode getModuleGroupNode() {
		return moduleGroupNode;
	}

	/**
	 * After any mouse operation a method of this listener is called. The item
	 * under the current mouse position is selected, pressed or "hovered".
	 */
	private class SelectionListener implements MouseListener, MouseTrackListener, MouseMoveListener {

		private ModuleItem mouseDownItem;
		private ModuleItem mouseHoverItem;

		/**
		 * @see org.eclipse.swt.events.MouseListener#mouseUp(org.eclipse.swt.events.MouseEvent)
		 */
		public void mouseUp(MouseEvent e) {

			if (mouseDownItem == null) {
				return;
			}

			Point point = new Point(e.x, e.y);
			ModuleItem item = getClosingItem(point);
			if (item == mouseDownItem) {
				IModuleNode node = item.getModuleNode();
				if (!node.isDisposed()) {
					node.dispose();
				}
			} else {
				item = getItem(point);
				if (item == mouseDownItem) {
					openItem(item);
				}
			}
			setMouseNotDown();

		}

		/**
		 * @see org.eclipse.swt.events.MouseListener#mouseDown(org.eclipse.swt.events.MouseEvent)
		 */
		public void mouseDown(MouseEvent e) {
			mouseDownItem = getItem(new Point(e.x, e.y));
			if (mouseDownItem != null) {
				mouseDownItem.setPressed(true);
			}
		}

		/**
		 * @see org.eclipse.swt.events.MouseListener#mouseDoubleClick(org.eclipse.swt.events.MouseEvent)
		 */
		public void mouseDoubleClick(MouseEvent e) {
			// nothing to do
		}

		/**
		 * Sets everything in such a way that no mouse item is "down".
		 */
		private void setMouseNotDown() {
			if (mouseDownItem != null) {
				mouseDownItem.setPressed(false);
			}
			mouseDownItem = null;
		}

		/**
		 * Sets everything in such a way that no mouse item is "hover".
		 */
		private void setMouseNotHover() {
			if (mouseHoverItem != null) {
				mouseHoverItem.setHover(false);
			}
			mouseHoverItem = null;
		}

		/**
		 * Switches the hover state of the item under the given position.
		 * 
		 * @param x -
		 *            x coordinate of the position
		 * @param y -
		 *            y coordinate of the position
		 */
		private void hoverOrNot(int x, int y) {

			ModuleItem item = getItem(new Point(x, y));
			if ((item == null) || (item != mouseDownItem)) {
				setMouseNotDown();
			}
			if ((item == null) || (item != mouseHoverItem)) {
				setMouseNotHover();
				mouseHoverItem = item;
				if (mouseHoverItem != null) {
					mouseHoverItem.setHover(true);
				}
			}

		}

		/**
		 * @see org.eclipse.swt.events.MouseTrackListener#mouseEnter(org.eclipse.swt.events.MouseEvent)
		 */
		public void mouseEnter(MouseEvent e) {
			hoverOrNot(e.x, e.y);
		}

		/**
		 * @see org.eclipse.swt.events.MouseTrackListener#mouseExit(org.eclipse.swt.events.MouseEvent)
		 */
		public void mouseExit(MouseEvent e) {
			hoverOrNot(e.x, e.y);
		}

		/**
		 * @see org.eclipse.swt.events.MouseTrackListener#mouseHover(org.eclipse.swt.events.MouseEvent)
		 */
		public void mouseHover(MouseEvent e) {
			hoverOrNot(e.x, e.y);
		}

		/**
		 * @see org.eclipse.swt.events.MouseMoveListener#mouseMove(org.eclipse.swt.events.MouseEvent)
		 */
		public void mouseMove(MouseEvent e) {
			hoverOrNot(e.x, e.y);
		}

	}

	private class PaintDelegation implements PaintListener {

		public void paintControl(PaintEvent e) {
			onPaint(e);
		}
	}

	/**
	 * @see org.eclipse.swt.widgets.Widget#dispose()
	 */
	@Override
	public void dispose() {
		removeListeners();
		super.dispose();
	}

}
