package com.guess.constant;

import java.io.File;

public class Constant {
	public static final String IMAGE_DEFAUlT_FORMAT = "gif";
	public static final String AVATAR_STORAGE_PATH = "resources" 
			+ File.separator + "avatar" + File.separator;
	public static final String ORG_AUTHENTIFICATION_PATH = "resources" 
			+ File.separator + "org_authentification" + File.separator;
	public static final String DEFAULT_AVATAR = AVATAR_STORAGE_PATH 
			+ File.separator + "default" +File.separator +"default.gif";
	public static final String QUESTION_IMAGE_PATH = "resources" 
			+ File.separator + "question" + File.separator;
	public static final String[] IMAGE_FORMATS = {"image/png", "image/jpg", 
			"image/jpeg", "image/gif"};
	public static final long IMAGE_SIZE = 1024*1024;
}
