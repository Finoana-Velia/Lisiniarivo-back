package com.Lisiniarivo.Application.Core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileManagement {
	
	private static String home = System.getProperty("user.home");
	private static String rootPath = home + File.separator + "Lisiniarivo_files" + File.separator;
	private static String filePath;

	private static void createMainFolder() {
		File mainFolder = new File(rootPath);
		if(!mainFolder.exists()) {
			mainFolder.mkdir();
			log.info("Main folder was created in the source path");
		}
	}
	
	private static void createFolder(String folderName) {
		filePath = rootPath + File.separator + folderName + File.separator;
		File folder = new File(filePath);
		if(!folder.exists()) {
			folder.mkdir();
			log.info("Folder : " + folderName + " was created");
		}
	}
	
	public static void registerFile(
			MultipartFile file,
			String folderName,
			Long id
			) throws IllegalStateException, IOException {
		if(!file.isEmpty()) {
			createMainFolder();
			createFolder(folderName);
			file.transferTo(new File(filePath + id));
			log.info("File " + file.getOriginalFilename() + " has been saved");
		}else {
			log.warn("File not found");
		}
	}
	
	public static void updateFile(
			MultipartFile file,
			String folderName,
			Long id
			) throws IllegalStateException, IOException {
		if(!file.isEmpty()) {
			createMainFolder();
			createFolder(folderName);
			Path fileUpdate = Paths.get(filePath + id);
			file.transferTo(fileUpdate);
			log.info("File with id " + id + " in folder " + folderName + " has been changed");
		}else {
			log.warn("File not updated");
		}
	}
	
	public static File getFile(Long id,String folderName) {
		filePath = rootPath + File.separator + folderName + File.separator;
		File file = new File(filePath + id);
		if(file.exists()) {
			return file;
		}else {
			return null;
		}
	}
	
	public static void deleteFile(Long id, String folder) {
		filePath = rootPath + File.separator + folder + File.separator + id;
		File file = new File(filePath);
		if(file.exists()) {
			file.delete();
			log.warn("File with id " + id + " into " + folder + " was deleted");
		}else {
			log.warn("This file doesn't exists");
		}
	}
}
