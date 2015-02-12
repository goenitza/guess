package com.guess.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.guess.enums.MessageType;

@Entity
public class Message extends BaseModel{
	private MessageType type;
	private String receiverId;
	private String senderId;
	private String senderNickname;
	private String senderAvatar;
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
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	@Column(nullable = false)
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	@Column()
	public String getSenderNickname() {
		return senderNickname;
	}
	public void setSenderNickname(String senderNickname) {
		this.senderNickname = senderNickname;
	}
	@Column()
	public String getSenderAvatar() {
		return senderAvatar;
	}
	public void setSenderAvatar(String senderAvatar) {
		this.senderAvatar = senderAvatar;
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
