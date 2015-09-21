package com.azhuoinfo.pshare.model;

import android.os.Parcel;
import android.os.Parcelable;

import mobi.cangol.mobile.db.DatabaseField;
import mobi.cangol.mobile.db.DatabaseTable;

@DatabaseTable("Message")
public class Message implements Parcelable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 0L;
	@DatabaseField(primaryKey=true,notNull=true)
	public int _id;
	@DatabaseField(value="message_id",notNull=true)
	private String messageId;
	@DatabaseField(notNull=true)
	private String type;
	@DatabaseField(notNull=true)
	private String title;
	@DatabaseField(notNull=false)
	private String content;
	@DatabaseField(notNull=false)
	private String image;
	@DatabaseField(notNull=false)
	private String url;
	@DatabaseField(notNull=true)
	private String timestamp;
	@DatabaseField(notNull=true)
	private String userId;
	@DatabaseField(notNull=true)
	private String extras;
	@DatabaseField(notNull=true)
	private int status;//0未读,已读-1删除
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getExtras() {
		return extras;
	}
	public void setExtras(String extras) {
		this.extras = extras;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(_id);
		dest.writeString(messageId);
		dest.writeString(type);
		dest.writeString(title);
		dest.writeString(content);
		dest.writeString(image);
		dest.writeString(url);
		dest.writeString(timestamp);
		dest.writeString(userId);
		dest.writeString(extras);
		dest.writeInt(status);
	}

	public static final Parcelable.Creator<Message> CREATOR = new Creator<Message>() {

		@Override
		public Message createFromParcel(Parcel source) {
			Message p = new Message();
			p.set_id(source.readInt());
			p.setMessageId(source.readString());
			p.setType(source.readString());
			p.setTitle(source.readString());
			p.setContent(source.readString());
			p.setImage(source.readString());
			p.setUrl(source.readString());
			p.setTimestamp(source.readString());
			p.setUserId(source.readString());
			p.setExtras(source.readString());
			p.setStatus(source.readInt());
			return p;
		}

		@Override
		public Message[] newArray(int size) {
			return new Message[size];
		}
	};
}
