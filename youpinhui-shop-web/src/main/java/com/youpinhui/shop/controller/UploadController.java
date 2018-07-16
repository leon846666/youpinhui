package com.youpinhui.shop.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.youpinhui.entity.Result;
import com.youpinhui.utils.FastDFSClient;

@RestController
public class UploadController {

	@Value("${FILE_SERVER_URL}")
	private String file_server_url;
	
	@RequestMapping("/upload")
	public Result upload(MultipartFile file) {
		System.out.println("inside of upload~");
	String originalFilename = file.getOriginalFilename();
	String extName = originalFilename.substring(originalFilename.lastIndexOf('.')+1);
		try {
			FastDFSClient client = new FastDFSClient("classpath:config/fdfs_client.conf");
			String fileId = client.uploadFile(file.getBytes(), extName);
			String fullURL=file_server_url+fileId;
			return new Result(true,fullURL);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Result(false,"upload failed");
		}
	
	}
}
