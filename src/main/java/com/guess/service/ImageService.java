package com.guess.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService{
	String setDefaultAvatar(String contextPath, String username) throws IOException;
	void saveAtatar(String contextPath, MultipartFile file, String username) throws IOException;
}
