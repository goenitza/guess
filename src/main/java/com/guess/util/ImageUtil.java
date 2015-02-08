package com.guess.util;

import org.springframework.web.multipart.MultipartFile;

import com.guess.constant.Constant;

public class ImageUtil {
	public static boolean check(MultipartFile image){
		boolean valid = false;
		String contentType = image.getContentType();
		for(String format : Constant.IMAGE_FORMATS){
			if(contentType.equals(format)){
				valid = true;
				break;
			}
		}
		if(valid){
			if(image.getSize() > Constant.IMAGE_SIZE){
				valid = false;
			}
		}
		
		return valid;
	}
}
