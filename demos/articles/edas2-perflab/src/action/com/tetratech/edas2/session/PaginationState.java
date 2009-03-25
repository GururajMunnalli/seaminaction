package com.tetratech.edas2.session;

public class PaginationState {
	private long start;
	private long end;
	private long totalRecordCount;
	private int pageSize;
	private boolean nextAvailable;
	private boolean previousAvailable;

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	public boolean isNextAvailable() {
		return nextAvailable;
	}

	public void setNextAvailable(boolean nextAvailable) {
		this.nextAvailable = nextAvailable;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isPreviousAvailable() {
		return previousAvailable;
	}

	public void setPreviousAvailable(boolean previousAvailable) {
		this.previousAvailable = previousAvailable;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getTotalRecordCount() {
		return totalRecordCount;
	}

	public void setTotalRecordCount(long totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
}
