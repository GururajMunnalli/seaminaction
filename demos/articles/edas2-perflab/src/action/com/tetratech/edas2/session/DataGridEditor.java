package com.tetratech.edas2.session;

import java.util.HashSet;
import java.util.Set;
import org.jboss.seam.annotations.Out;

public abstract class DataGridEditor<T> extends QueryManager<T> {

	@Out(required = false) protected T itemDetails = null;
	
	@Out private String detailMode = "display";
	
	@Out private boolean displayMode = false;
	
	@Out private boolean editMode = false;
	
	@Out(required = false) protected T itemInEditMode = null;
	
	protected Set<T> itemsInEditMode = new HashSet<T>();
	
	private int callsToEditing;
	
	public int getCallsToEditing() {
		int val = callsToEditing;
		callsToEditing = 0;
		return val;
	}

	public T getItemInEditMode() {
		return itemInEditMode;
	}
	
	public void selectItem() {
		itemDetails = selectedItem;
		displayMode();
	}
	
	public void previousItem() {
		int prevIndex = getIndexOfSelectedItem() - 1;
		if (prevIndex < 0) {
			prevIndex = results.size() - 1;
		}
		selectedItem = itemDetails = results.get(prevIndex);
	}

	public void nextItem() {
		int nextIndex = getIndexOfSelectedItem() + 1;
		if (nextIndex >= results.size()) {
			nextIndex = 0;
		}
		selectedItem = itemDetails = results.get(nextIndex);
	}

	public void editItemDetail() {
		editMode();
	}

	public void cancelEditItemDetail() {
		getEntityManager().refresh(itemDetails);
		displayMode();
	}

	public void closeItemDetail() {
		itemDetails = null;
	}
	
	public void saveItemDetail() {
		getEntityManager().flush();
		displayMode();
	}
	
	public abstract void addItem(T item);
	public abstract void saveItem();
	public abstract void deleteItem();
	
	public void editItem() {
		leaveEditMode();
		enterEditMode(selectedItem);
	}

	public void cancelEditItem() {
		getEntityManager().refresh(selectedItem);
		leaveEditMode(selectedItem);
	}
	
	public boolean editing(T item) {
		callsToEditing++;
		return itemInEditMode == item;
	}
	
	public boolean editing2(T item) {
		return itemsInEditMode.contains(item);
	}

	protected void leaveEditMode(T item) {
		leaveEditMode();
	}

	protected void enterEditMode(T item) {
		itemInEditMode = item;
		itemsInEditMode.add(item);
	}

	protected void leaveEditMode() {
		itemInEditMode = null;
		itemsInEditMode.clear();
	}

	protected void displayMode() {
		detailMode = "display";
		displayMode = true;
		editMode = false;
	}

	protected void editMode() {
		detailMode = "edit";
		displayMode = false;
		editMode = true;
	}
}
