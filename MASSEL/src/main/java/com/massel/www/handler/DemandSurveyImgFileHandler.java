package com.massel.www.handler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.tika.Tika;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.massel.www.domain.DemandSurveyImgVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@AllArgsConstructor
@Slf4j
@Component
public class DemandSurveyImgFileHandler {
	
	private final String UP_DIR = "C:\\projectImg\\MASSEL\\MASSELImgFile\\fileUpload\\demandSurveyImg";
	
	public List<DemandSurveyImgVO> uploadFiles(MultipartFile[] generalFiles, MultipartFile thumbnailFile){
		
		LocalDate date = LocalDate.now(); //현재 날짜를 구함
		log.info(">>>> date : "+date); 
		
		String today = date.toString();  //date.toString() 메서드를 호출해 LocalDate객체를 문자열 형식으로 변환
										 //YYYY-MM-DD 형식임.
		today = today.replace("-", File.separator); //java.io.File import
		
		File folders = new File(UP_DIR, today);
		//new File(UP_DIR, today)를 사용하여 UP_DIR(디렉토리 경로)과 변환된 날짜 문자열(today)을 결합하여 
		//새로운 File 객체를 생성. 
		
		if(!folders.exists()) {
			folders.mkdirs();
		}
		//지정된 디렉토리 존재하는지 확인. 존재하지 않으면 생성함
	
		//==========================================================
		
		List<DemandSurveyImgVO> fileList = new ArrayList<DemandSurveyImgVO>();
		
		//썸네일 파일을 처리
		if(thumbnailFile != null && !thumbnailFile.isEmpty()) {
			
			DemandSurveyImgVO thumbnailImg = new DemandSurveyImgVO();
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
			
			String fullThumbnailFileName = uuid.toString() + "_" + onlyThumbnailName;
			log.info("fullThumbnailFileName >>>>>>"+fullThumbnailFileName);
			
			File storeThumbnailFile = new File(folders, fullThumbnailFileName);
			
			try {
				thumbnailFile.transferTo(storeThumbnailFile);
				if(isImageFile(storeThumbnailFile)) {
					thumbnailImg.setFileType(1);  //1이 썸네일 파일
					//File thumbnail = new File(folders, uuid.toString() + "_th_"+onlyThumbnailName)
					//Thumbnails.of(storeThumbnailFile).size(100, 100).toFile(thumbnail);
				}
			}catch (Exception e) {
				log.info(">>>>thumbnailfile생성 오류!!!!!!!!");
				e.printStackTrace();
			}
			
			fileList.add(thumbnailImg);
			
		}
		
		
		//일반 파일을 처리, 일반 파일의 경우 여러개이므로 
		for(MultipartFile file : generalFiles) {
			if(file != null && !file.isEmpty()) {
				DemandSurveyImgVO generalImg = new DemandSurveyImgVO();
				generalImg.setSaveDir(today);
				generalImg.setFileSize(file.getSize());
				
				String originalFileName = file.getOriginalFilename();
				String onlyFileName = originalFileName.substring(originalFileName.lastIndexOf(File.separator)+1);
				log.info("onlyFileName >>>> "+onlyFileName);
				generalImg.setFileName(onlyFileName);
				
				UUID uuid = UUID.randomUUID();
				generalImg.setUuid(uuid.toString());
				
				String fullFileName = uuid.toString() + "_" + onlyFileName;
				File storeFile = new File(folders, fullFileName);
				
				try {
					file.transferTo(storeFile);
					if(isImageFile(storeFile)) {
						generalImg.setFileType(0); //일반파일
					}
				}catch (Exception e) {
					// TODO: handle exception
					log.info(">>>>>>>>일반파일 생성 오류!!!!!!!!!!!!");
					e.printStackTrace();
				}
				
				fileList.add(generalImg);
			
			}
		}
		
		return fileList;
		
	}

	private boolean isImageFile(File storeFile)throws IOException {
		String mimeType = new Tika().detect(storeFile);
		return mimeType.startsWith("image") ? true : false;
	}
	
	
	//썸네일만 
	public DemandSurveyImgVO uploadThumbnailImg(MultipartFile thumbnailFile) {
		
		LocalDate date = LocalDate.now();
		log.info("date >>>> "+date);
		
		String today = date.toString(); //LocalDate 객체 문자열 형태로
		today = today.replace("-", File.separator);
		
		File folders = new File(UP_DIR, today);
		
		if(!folders.exists()) {
			folders.mkdirs();
		}
		
		//DemandSurveyImgVO file = new DemandSurveyImgVO();
		DemandSurveyImgVO thumbnailImg = new DemandSurveyImgVO();
		
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

	
}
