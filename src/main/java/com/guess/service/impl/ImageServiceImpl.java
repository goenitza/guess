package com.guess.service.impl;

import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.guess.service.ImageService;
import com.guess.util.Constant;

@Component
public class ImageServiceImpl implements ImageService{

	//set default avatar for user
	public String setDefaultAvatar(String contextPath, String username) throws IOException {
		Thumbnails.of(contextPath + Constant.DEFAULT_AVATAR)
			.scale(1)
			.toFile(contextPath + Constant.AVATAR_STORAGE_PATH + username);
		return Constant.AVATAR_STORAGE_PATH + username;
	}
	//resize, re-format and rename the avatar
	public void saveAtatar(String contextPath, MultipartFile file, String username) throws IOException {
		
		
		Thumbnails.of(file.getInputStream())
			.outputFormat(Constant.IMAGE_FORMAT)
			.scale(1)
			.toFile(contextPath + Constant.AVATAR_STORAGE_PATH + username);
	}
}
