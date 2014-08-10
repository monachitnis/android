package com.android.app.mydrawingapp;

public enum BRUSH_SIZE {
	SMALL(10),
	MEDIUM(20),
	LARGE(30);
	
	int size;
	
	private BRUSH_SIZE(int i) {
		this.size = i;
	}
	
	public int get() {
		return size;
	}
}
