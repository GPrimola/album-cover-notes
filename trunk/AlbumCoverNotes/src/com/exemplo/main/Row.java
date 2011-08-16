package com.exemplo.main;

import android.graphics.Bitmap;

public class Row {
	
	private long id;
	private String title;
	private String author;
	private String imagePath;
	private Bitmap image;
	
	
	public long getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getAuthor() {
		return author;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public Bitmap getImage() {
		return image;
	}
	public void setImage(Bitmap image) {
		this.image = image;
	}

}
