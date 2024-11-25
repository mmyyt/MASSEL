package com.massel.www.handler;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.tika.Tika;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.massel.www.domain.UserImgVO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Component
public class UserImgFileHandler {
	
	private final String UP_DIR = "C:\\projectImg\\MASSEL\\MASSELImgFile\\fileUpload\\userImg";
	
	public UserImgVO uploadUserImg(MultipartFile userImgFile) {
		
		LocalDate date = LocalDate.now();
		log.info("날짜 >>> "+date);
		
		String today = date.toString();
		today = today.replace("-", File.separator);
		
		File folders = new File(UP_DIR, today);
		
		if(!folders.exists()) {
			folders.mkdirs();
		}
		
		UserImgVO img = new UserImgVO();
		
		if(userImgFile != null && !userImgFile.isEmpty()) {
			
			img.setSaveDir(today);
			img.setFileSize(userImgFile.getSize());
			
			String originalFileName = userImgFile.getOriginalFilename();
			log.info("originalFileName >>>> "+originalFileName);
			
			String onlyFileName = originalFileName.substring(
					originalFileName.lastIndexOf(File.separator)+1);
			log.info("onlyFileName >>>> "+onlyFileName);
			img.setFileName(onlyFileName);
			
			UUID uuid = UUID.randomUUID();
			img.setUuid(uuid.toString());
			log.info("uuid >>>> "+img.getUuid());
			
			String fullImgFileName = uuid.toString() + "_" + onlyFileName;
			log.info("fullImgFileName >>>> "+fullImgFileName);
			
			File storeImgFile = new File(folders, fullImgFileName);
			
			try {
				userImgFile.transferTo(storeImgFile);
				if(isImageFile(storeImgFile)) {
					img.setFileType(1);
				}
			}catch (Exception e) {
				log.info("product image 생성 오류!!!!");
				e.printStackTrace();
			}
		}
		
		return img;
	}
	
	
	private boolean isImageFile(File storeFile)throws IOException {
		String mimeType = new Tika().detect(storeFile);
		return mimeType.startsWith("image") ? true : false;
	}
}
