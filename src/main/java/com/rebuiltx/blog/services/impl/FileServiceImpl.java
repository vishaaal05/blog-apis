package com.rebuiltx.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rebuiltx.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		

		
//		public String uploadImage(String path, MultipartFile file) throws IOException {
	
//
//		    // Validate the file parameter
//		    if (file == null) {
//		        throw new IllegalArgumentException("The file parameter cannot be null.");
//		    }
//
//		    // Validate the file type
//		    if (!file.getContentType().startsWith("image/")) {
//		        throw new IllegalArgumentException("The uploaded file must be an image.");
//		    }
//
//		    // Generate a random name for the uploaded file
//		    String randomID = SecureRandom.getInstanceStrong().nextLong() + "";
//		    String fileName1 = randomID.concat(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
//
//		    // Get the full path to the destination file
//		    String filePath = path + File.separator + fileName1;
//
//		    // Create the destination folder if it doesn't exist
//		    File f = new File(path);
//		    if (!f.exists()) {
//		        f.mkdir();
//		    }
//
//		    // Copy the uploaded file to the destination path
//		    BufferedOutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(Paths.get(filePath)));
//		    InputStream inputStream = file.getInputStream();
//		    byte[] buffer = new byte[1024];
//		    int bytesRead;
//		    while ((bytesRead = inputStream.read(buffer)) > 0) {
//		        outputStream.write(buffer, 0, bytesRead);
//		    }
//
//		    // Close the streams
//		    outputStream.close();
//		    inputStream.close();
//
//		    return fileName1;
//		}

		
//	 
		//file name 
		String name = file.getOriginalFilename();
		
		//random name generate file
		String randomID = UUID.randomUUID().toString();
		String fileName1= randomID.concat(name.substring(name.lastIndexOf(".")));
		
		//Full path
		String filePath = path + File.separator + fileName1;
		
		
		//create folder if not created
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}
		
		//file copy
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		
		
		return fileName1;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath = path + File.separator + fileName;
		InputStream is = new FileInputStream(fullPath);
		//db logic to return inputstream
		
		return is;
	}

}
