package org.preflight.ui;

import java.io.Serializable;

public class SelectableItem<E> implements Serializable {
	
	private static final long serialVersionUID = -8608083257302330437L;
	
	private boolean selected;
	private E item;

	public SelectableItem() {}
	
	public SelectableItem(E item) {
		this.item = item;
	}
	
	public SelectableItem(E item, boolean initialState) {
		this(item);
		this.selected = initialState;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public E getItem() {
		return item;
	}

	public void setItem(E item) {
		this.item = item;
	}
}
