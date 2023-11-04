package com.rebuiltx.blog.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	String uploadImage(String path, MultipartFile file) throws IOException;  
	// method taking the path of the directory and
	//MultipartFile object representing the image to be uploaded
	
	InputStream getResource(String path, String fileName) throws FileNotFoundException;
	//path: The path to the directory where the file is located.
	//fileName: The name of the file to download.
}
