package com.guess.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService{
	String setDefaultAvatar(String contextPath, String fielName) throws IOException;
	void saveAtatar(String contextPath, MultipartFile file, String fielName) throws IOException;
	String saveQuestionContentImage(String contextPath, MultipartFile file, String fileName) throws IOException;
	String saveQuestionOptionImage(String contextPath, MultipartFile file, String fileName) throws IOException;
}
