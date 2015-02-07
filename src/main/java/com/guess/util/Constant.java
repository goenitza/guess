package com.guess.util;

import java.io.File;

public interface Constant {
	String IMAGE_FORMAT = "gif";
	String AVATAR_STORAGE_PATH = "resources" + File.separator + "avatar" + File.separator;
	String ORG_AUTHENTIFICATION_PATH = "resources" + File.separator + "org_authentification" + File.separator;
	String DEFAULT_AVATAR = AVATAR_STORAGE_PATH + File.separator + "default" 
			+File.separator +"default.gif";
	String QUESTION_IMAGE_PATH = "resources" + File.separator + "question" + File.separator;
}
