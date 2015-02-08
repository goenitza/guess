package com.guess.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.guess.enums.MessageType;

@Entity
public class Message extends BaseModel{
	private MessageType type;
	private String receiver;
	private String sender;
	private String relatedObject;// 
	private String content;
	private Date date;
	private boolean isProcessed;
	@Column(nullable = false)
	public MessageType getType() {
		return type;
	}
	public void setType(MessageType type) {
		this.type = type;
	}
	@Column(nullable = false)
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	@Column()
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	@Column()
	public String getRelatedObject() {
		return relatedObject;
	}
	public void setRelatedObject(String relatedObject) {
		this.relatedObject = relatedObject;
	}
	@Column()
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(nullable = false)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Column
	public boolean getIsProcessed() {
		return isProcessed;
	}
	public void setIsProcessed(boolean isProcessed) {
		this.isProcessed = isProcessed;
	}
}
