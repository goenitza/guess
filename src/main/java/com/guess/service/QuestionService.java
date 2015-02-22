package com.guess.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.guess.model.Question;

public interface QuestionService extends BaseService<Question, String>{
	String save(String nickname, String avatar, Question question, MultipartFile contentImage, String options, MultipartFile[] optionsImages, String contextPath, List<String> users) throws IOException;
	void share(String userId, String questionId, String avatar, String questionId2, List<String> userIds);
	void publish(String questionId, String avatar, String questionId2, boolean isPublic, List<String> userIds);
}
