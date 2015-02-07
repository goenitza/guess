package com.guess.vo;

public class QuestionOption {
	int index;
	String content;
	boolean containsImage;
	String imageUrl;
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public boolean getContainsImage() {
		return containsImage;
	}
	public void setContainsImage(boolean containsImage) {
		this.containsImage = containsImage;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
