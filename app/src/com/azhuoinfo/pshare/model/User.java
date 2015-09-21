package com.azhuoinfo.pshare.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class User implements Parcelable,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 0L;
	private String userId;
	private String avatar;
	private String email;
	private String mobile;
	private String nickname;
	private String gender;
	private String birthday;
	private String location;
	private String thirdAccount;
	private String username;
	public User(){}
	

	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatar() {
		return avatar;
	}


	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getThirdAccount() {
		return thirdAccount;
	}

	public void setThirdAccount(String thirdAccount) {
		this.thirdAccount = thirdAccount;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getNickname() {
		return nickname;
	}



	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(username);
		dest.writeString(avatar);
		dest.writeString(email);
		dest.writeString(mobile);
		dest.writeString(nickname);
		dest.writeString(gender);
		dest.writeString(birthday);
		dest.writeString(location);
		dest.writeString(thirdAccount);
	}

	public static final Parcelable.Creator<User> CREATOR = new Creator<User>() {

		@Override
		public User createFromParcel(Parcel source) {
			User p = new User();
			p.setUsername(source.readString());
			p.setAvatar(source.readString());
			p.setEmail(source.readString());
			p.setMobile(source.readString());
			p.setNickname(source.readString());
			p.setGender(source.readString());
			p.setBirthday(source.readString());
			p.setLocation(source.readString());
			p.setThirdAccount(source.readString());
			return p;
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};

}
