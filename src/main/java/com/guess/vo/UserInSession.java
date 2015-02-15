package com.guess.vo;

import com.guess.constant.Constant;
import com.guess.enums.UserRole;

public class UserInSession {
	public String id;
	public String username;
	public UserRole role;
	public String nickname;
	
	public String getAvatar(){
		return Constant.AVATAR_STORAGE_PATH + id + "." + Constant.IMAGE_DEFAUlT_FORMAT;
	}
}