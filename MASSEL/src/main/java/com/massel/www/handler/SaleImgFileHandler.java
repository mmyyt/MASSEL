package com.massel.www.handler;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.tika.Tika;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.massel.www.domain.SaleImgVO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Component
public class SaleImgFileHandler {
	
	private final String UP_DIR = "C:\\projectImg\\MASSEL\\MASSELImgFile\\fileUpload\\saleImg";
	
	
public SaleImgVO uploadThumbnailImg(MultipartFile thumbnailFile) {
		
		LocalDate date = LocalDate.now();
		log.info("date >>>> "+date);
		
		String today = date.toString(); //LocalDate 객체 문자열 형태로
		today = today.replace("-", File.separator);
		
		File folders = new File(UP_DIR, today);
		
		if(!folders.exists()) {
			folders.mkdirs();
		}
		
		//DemandSurveyImgVO file = new DemandSurveyImgVO();
		SaleImgVO thumbnailImg = new SaleImgVO();
		
		if(thumbnailFile != null && !thumbnailFile.isEmpty()) {
			
			thumbnailImg.setSaveDir(today);
			thumbnailImg.setFileSize(thumbnailFile.getSize());
			
			String originalThumbnailName = thumbnailFile.getOriginalFilename();
			log.info("originalThumbnailName >>>>> "+originalThumbnailName);
			String onlyThumbnailName = originalThumbnailName.substring(
					originalThumbnailName.lastIndexOf(File.separator)+1);
			log.info("onlyThumbnailName >>>>> "+onlyThumbnailName);
			thumbnailImg.setFileName(onlyThumbnailName);
			
			UUID uuid = UUID.randomUUID();
			thumbnailImg.setUuid(uuid.toString());
			log.info("thumbnail Img uuid >>>>>>>>>"+thumbnailImg.getUuid());
			
			String fullThumbnailFileName = uuid.toString() + "_" + onlyThumbnailName;
			log.info("fullThumbnailFileName >>>>> "+fullThumbnailFileName);
			
			File storeThumbnailFile = new File(folders, fullThumbnailFileName);
			
			try {
				thumbnailFile.transferTo(storeThumbnailFile);
				if(isImageFile(storeThumbnailFile)) {
					thumbnailImg.setFileType(1); //1은 썸네일 이미지란 의미
				}
				
			}catch (Exception e) {
				log.info("Thumbnail File 생성 오류!");
				e.printStackTrace();
			}

		}
		
		return thumbnailImg;
	}


	private boolean isImageFile(File storeFile)throws IOException {
		String mimeType = new Tika().detect(storeFile);
		return mimeType.startsWith("image") ? true : false;
	}
}
