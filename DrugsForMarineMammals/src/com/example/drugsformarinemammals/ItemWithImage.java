package com.example.drugsformarinemammals;

public class ItemWithImage {
	private String image;
	private int imageId;
	private String title;
	//private String url;

	public ItemWithImage() {
		super();
	}

	public ItemWithImage(String image, String title) {
		super();
		this.image = image;
		this.title = title;
	}

	public ItemWithImage(int image, String title) {
		super();
		this.imageId = image;
		this.title = title;
	}
	
	public String getImage() {
		return image;
	}

	public int getImageId(){
		return imageId;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
