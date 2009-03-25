package com.tetratech.edas2.util;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

@Name("stopwatch")
@BypassInterceptors
public class Stopwatch {
	
	private String id;
	
	private boolean running;
	
	private long startTime;
	
	private long lastSplitTime;
	
	private long lastSplit = -1;
	
	private long endTime = -1;
	
	private long elapsed = -1;
	
	public Stopwatch() {}
	
	public Stopwatch start() {
		return start(null);
	}
	
	public Stopwatch start(String id) {
		this.id = id;
		startTime = now();
		running = true;
		return this;
	}
	
	public Stopwatch split() {
		if (!running) {
			throw new IllegalStateException("Cannot take a split until stopwatch has been started.");
		}
		
		long now = now();
		lastSplit = now - lastSplitTime;
		lastSplitTime = now;
		return this;
	}
	
	public Stopwatch stop() {
		if (!running) {
			throw new IllegalStateException("Cannot stop a stopwatch which has not been started.");
		}
		
		endTime = now();
		elapsed = endTime - startTime;
		running = false;
		return this;
	}
	
	public String getId() {
		return id;
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public long getEndTime() {
		return endTime;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public long getElapsed() {
		return elapsed;
	}
	
	public String getElapsedWithUnits() {
		return elapsed + "ms";
	}
	
	public long getLastSplit() {
		return lastSplit;
	}
	
	public String getLastSplitWithUnits() {
		return lastSplit + "ms";
	}
	
	private long now() {
		return System.currentTimeMillis();
	}
}
