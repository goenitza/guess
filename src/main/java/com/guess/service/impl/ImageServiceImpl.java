package com.guess.service.impl;

import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.guess.constant.Constant;
import com.guess.service.ImageService;

@Component
public class ImageServiceImpl implements ImageService{

	//set default avatar for user
	public String setDefaultAvatar(String contextPath, String fileName) throws IOException {
		Thumbnails.of(contextPath + Constant.DEFAULT_AVATAR)
			.scale(1)
			.toFile(contextPath + Constant.AVATAR_STORAGE_PATH + fileName);
		return Constant.AVATAR_STORAGE_PATH + fileName + "." + Constant.IMAGE_DEFAUlT_FORMAT;
	}
	//resize, re-format and rename the avatar
	public void saveAtatar(String contextPath, MultipartFile file, String fileName) throws IOException {
		Thumbnails.of(file.getInputStream())
			.outputFormat(Constant.IMAGE_DEFAUlT_FORMAT)
			.scale(1)
			.toFile(contextPath + Constant.AVATAR_STORAGE_PATH + fileName);
	}
	public String saveQuestionContentImage(String contextPath,
			MultipartFile file, String fileName) throws IOException {
		Thumbnails.of(file.getInputStream())
			.outputFormat(Constant.IMAGE_DEFAUlT_FORMAT)
			.scale(1)
			.toFile(contextPath + Constant.QUESTION_IMAGE_PATH + fileName);
		return Constant.QUESTION_IMAGE_PATH + fileName + "." + Constant.IMAGE_DEFAUlT_FORMAT;
	}
	public String saveQuestionOptionImage(String contextPath,
			MultipartFile file, String fileName) throws IOException {
		Thumbnails.of(file.getInputStream())
			.outputFormat(Constant.IMAGE_DEFAUlT_FORMAT)
			.scale(1)
			.toFile(contextPath + Constant.QUESTION_IMAGE_PATH + fileName);
		return Constant.QUESTION_IMAGE_PATH + fileName + "." + Constant.IMAGE_DEFAUlT_FORMAT;
	}
}
