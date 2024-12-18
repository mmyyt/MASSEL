package com.massel.www.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.massel.www.domain.DateValueVO;
import com.massel.www.domain.DemandSurveyDTO;
import com.massel.www.domain.DemandSurveyDataVO;
import com.massel.www.domain.DemandSurveyEditDTO;
import com.massel.www.domain.DemandSurveyImgDTO;
import com.massel.www.domain.DemandSurveyImgVO;
import com.massel.www.domain.DemandSurveyParticipationVO;
import com.massel.www.domain.DemandSurveyProductEditDTO;
import com.massel.www.domain.DemandSurveyProductVO;
import com.massel.www.domain.DemandSurveyUserParticipationDTO;
import com.massel.www.domain.DemandSurveyVO;
import com.massel.www.domain.PagingVO;
import com.massel.www.domain.SaleImgDTO;
import com.massel.www.domain.UserDemandDTO;
import com.massel.www.domain.UserImgDTO;
import com.massel.www.domain.UserImgVO;
import com.massel.www.domain.UserVO;
import com.massel.www.handler.DemandSurveyImgFileHandler;
import com.massel.www.handler.PagingHandler;
import com.massel.www.service.DemandSurveyService;
import com.massel.www.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/demandSurvey")
@Slf4j
@Controller
public class DemandSurveyController {

	@Inject
	private DemandSurveyService dss;

	@Inject
	private DemandSurveyImgFileHandler dshd;
	
	@Inject
	private UserService usv;

	//summernote editor 이미지 파일 업로드
	@PostMapping("/tmp")
	@ResponseBody
	public String tmpRoute2(@RequestParam(name="file", required=false)MultipartFile imageFile,
							HttpServletRequest srq) {
		log.info("에디터 이미지 미리보기@@@@@@@@@");
		
		//업로드 결과를 JSON형태로 반환하기 위해 사용되는 객체
		JsonObject jsonObject = new JsonObject();
		
		//저장경로 설정
		String uploadPath = srq.getSession().getServletContext().getRealPath("resources");
		String fileRoot = uploadPath + "\\editorImg\\";
		
		log.info("실제 경로 >>>> "+fileRoot);
		
		//파일 원본 이름, 확장자
		String originalFileName = imageFile.getOriginalFilename();
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
		
		//UUID생성(중복방지)
		String uuidFileName = UUID.randomUUID().toString() + extension;
		log.info("파일 이름 >>>>> "+uuidFileName);
		
		//File객체 : 파일의 경로와 이름을 나타내는 객체
		File targetFile = new File(fileRoot + uuidFileName);
		
		try {
			
			//업로드 된 파일 내용을 읽어오기 위해 InputStream 사용
			InputStream fileStream = imageFile.getInputStream();
			
			//fileStream으로 읽어온 파일을 targetFile위치에 복사해서 저장
			FileUtils.copyInputStreamToFile(fileStream, targetFile);
			
			//저장된 이미지 파일의 URL 경로를 JSON응답 객체에 추가함 
			jsonObject.addProperty("url", "/resources/editorImg/"+uuidFileName);
			jsonObject.addProperty("responseCode", "success");
			
		}catch(IOException e) {
			//저장 중 문제 생기면 저장된 파일을 삭제
			FileUtils.deleteQuietly(targetFile);
			//에러 응답 반환
			jsonObject.addProperty("responseCode", "error");
			e.printStackTrace();
		}
		
		return jsonObject.toString();
	}
	
	//summernote 에디터 글 작성
	@PostMapping("/dsRegister")
	public String registerDemandSurvey(DemandSurveyVO dsvo) {
		log.info("글 객체 >>>> "+dsvo);
		
		int isOk = dss.registerDS(dsvo);
		if(isOk>0) {
			log.info("글등록성공");
		}else {
			log.info("글등록실패");
		}
		
		return "redirect:/demandSurvey/list";
	}

	// 폼 작성

	@GetMapping("/register")
	public String registerDS() {
		log.info("수요조사 폼 작성");

		return "/demandsurvey/dsRegister";
	}

	//일반파일 제외하고 썸네일 파일만 등록하도록 변경
	 @PostMapping("/register") 
	 public String registerDSProcess(DemandSurveyVO dsvo, RedirectAttributes ra,
			 @RequestParam(name="thumbnailFile", required=false) MultipartFile thumbnailFile,
			 @RequestParam("startDay") String startDay,
			 @RequestParam("startHour") String startHour, 
			 @RequestParam("startMinute") String startMinute, 
			 @RequestParam("endDay") String endDay,
			 @RequestParam("endHour") String endHour,
			 @RequestParam("endMinute") String endMinute){
	 
		 log.info("수요조사 폼 작성 진행중.."); log.info("수요조사 폼 객체 >>>> "+dsvo.toString());
		 log.info("수요조사 폼 dno >>>>>>>>"+dsvo.getDno());
		 log.info("썸네일 파일 >>>>"+thumbnailFile.toString());
	 
		 DemandSurveyImgVO file = dshd.uploadThumbnailImg(thumbnailFile);
		 
		 log.info("@@@@@@@@@@@@@@@@@@@@@@@@>>>>"+file.toString());
		 
		 //날짜,시간 합치기 (먼저 String으로 생성)
		 String startDateTimeString = startDay + " " + startHour + ":" + startMinute;
		 String endDateTimeString = endDay + " " + endHour + ":" + endMinute;
		 
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		 LocalDateTime startDate = LocalDateTime.parse(startDateTimeString, formatter);
		 LocalDateTime endDate = LocalDateTime.parse(endDateTimeString, formatter);
		 
		 String fullDate = startDateTimeString+" ~ "+endDateTimeString;
		 
		 log.info("*************>> "+startDate);
		 log.info("*************>> "+endDate);
		 
		 dsvo.setStartDate(startDate);
		 dsvo.setEndDate(endDate);
		 dsvo.setFullDate(fullDate);
		 
		 log.info("*************>> Start Date: " + dsvo.getStartDate());
		 log.info("*************>> End Date: " + dsvo.getEndDate());
		 
		 LocalDateTime now = LocalDateTime.now();
		 log.info("현재시간 >> "+now);
		 
		 if(now.isBefore(startDate)) { //now가 인자보다 과거일때 true
			 //시작전
			 log.info("시작전");
			 dsvo.setStatus("before");
		 }else if(now.isAfter(startDate)) {  //현재시각이 startDate보다 미래 
			 log.info("진행중");
			 dsvo.setStatus("ongoing");
		 }
		 
		 log.info("dsvo ++++>>>>> "+dsvo.toString());
		 //dsvo, thumbnail
		 DemandSurveyImgDTO dsidto = new DemandSurveyImgDTO(dsvo, file);

		 log.info("dsidto >>>>>>>> "+dsidto.toString());
		 
		 int isOk = dss.registerSurvey(dsidto);
		  
		  if(isOk>0) { 
			  log.info("수요조사 폼 등록 완료"); 
		  }else {
			  log.info("수요조사 폼 등록 실패");
		  }
		  
		  return "redirect:/demandSurvey/list";
	 }

	 //수요조사 폼 등록 한번에 하기
	 @PostMapping(value="/demandRegister", produces = MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<?> registerDsForm(
			 @RequestPart("dsvo") DemandSurveyVO dsvo,
			 @RequestPart(value = "thumbnailFile", required =false) MultipartFile thumbnailFile,
			 @RequestPart("dateValue") DateValueVO dateValue,
			 @RequestPart("productList") List<DemandSurveyProductVO> productList){
		 
		 log.info("dsvo >> "+dsvo.toString());
		 log.info("thumbnailFile >> "+thumbnailFile.toString());
		 log.info("dateValue >> "+dateValue.toString());
		 log.info("productList >> "+productList.toString());
		 
		 
		 //날짜 합치기
		 String startDateTimeString = dateValue.getStartDay() + " " + dateValue.getStartHour() + ":" + dateValue.getStartMinute();
		 String endDateTimeString = dateValue.getEndDay() + " " + dateValue.getEndHour() + ":" + dateValue.getEndMinute();
		 
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		 LocalDateTime startDate = LocalDateTime.parse(startDateTimeString, formatter);
		 LocalDateTime endDate = LocalDateTime.parse(endDateTimeString, formatter);
		 
		 String fullDate = startDateTimeString+" ~ "+endDateTimeString;
		 
		 log.info("*************>> "+startDate);
		 log.info("*************>> "+endDate);
		 
		 dsvo.setStartDate(startDate);
		 dsvo.setEndDate(endDate);
		 dsvo.setFullDate(fullDate);
		 
		 log.info("*************>> Start Date: " + dsvo.getStartDate());
		 log.info("*************>> End Date: " + dsvo.getEndDate());
		 
		 LocalDateTime now = LocalDateTime.now();
		 log.info("현재시간 >> "+now);
		 
		 if(now.isBefore(startDate)) { //now가 인자보다 과거일때 true
			 //시작전
			 log.info("시작전");
			 dsvo.setStatus("before");
		 }else if(now.isAfter(startDate)) {  //현재시각이 startDate보다 미래 
			 log.info("진행중");
			 dsvo.setStatus("ongoing");
		 }
		 
		 
		 //썸네일 등록
		 DemandSurveyImgVO file = dshd.uploadThumbnailImg(thumbnailFile);
		 
		 //글 등록
		 DemandSurveyImgDTO dsidto = new DemandSurveyImgDTO(dsvo, file);
		 int isOk = dss.registerSurvey(dsidto);
		 
		 Map<String, String> response = new HashMap<String, String>();
		 
		 if(isOk>0) { 
			  log.info("수요조사 폼 등록 완료");
			  //상품등록하기
			  int dno = dss.getLastInsertedDno();
			  log.info("글 번호 >> "+dno);
			  isOk = dss.registerProductList(productList, dno);
			  log.info((isOk>0)?"상품등록 성공":"상품등록 실패");
			  
			  response.put("status", "success");
			  return ResponseEntity.ok(response);
			  
		  }else {
			  log.info("수요조사 폼 등록 실패");
				response.put("status", "failure");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		  }		 
	 
	 }
	 	
	// =============================================
	// 수요조사 폼 목록

	@GetMapping("/list")
	public String listDS(Model m, PagingVO pvo) {
		log.info("수요조사 폼 리스트");

		List<UserDemandDTO> list = new ArrayList<UserDemandDTO>();

		List<DemandSurveyImgDTO> dsList = dss.getList(pvo);
		
		for(DemandSurveyImgDTO dsidto : dsList) {
			String writer = dsidto.getDsvo().getId();
			UserImgVO uivo = usv.getUserImg(writer);
			
			list.add(new UserDemandDTO(dsidto, uivo));
		}
		
		m.addAttribute("dsList", list);
		
		int totalCount = dss.getTotalCount(pvo);
		PagingHandler ph = new PagingHandler(pvo, totalCount);
		m.addAttribute("ph",ph);

		return "/demandsurvey/dsList";
	}
	

	// ==================================================
	// 글 상세

	@GetMapping("/detail")
	public String detailDS(@RequestParam int dno, Model m, HttpServletRequest srq) {

		DemandSurveyVO dsvo = dss.getDSDetail(dno);

		m.addAttribute("dsDetail", dsvo);
		
		//썸네일도 같이 가져오기
		DemandSurveyImgDTO dsidto = dss.getDetail(dno);
		m.addAttribute("dsidto", dsidto);
		
		//product list 가져오기
		List<DemandSurveyProductVO> dsproductList = dss.getProductList(dno);
		m.addAttribute("product", dsproductList);
		
		log.info("dsProductList >>>> "+dsproductList);
		
		//유저 프로필이미지
		String id = dsvo.getId();
		UserVO userVo = usv.getUser(id);
		UserImgVO uivo = usv.getUserImg(id);
		
		UserImgDTO writerInfo = new UserImgDTO(userVo, uivo);
		
		m.addAttribute("writer",writerInfo);
		
		//조회수
		String writer = dsvo.getId();
		HttpSession session = srq.getSession();
		
		//로그인했을때만 적용하기	
		UserImgDTO uvo = (UserImgDTO)session.getAttribute("ses");
		
		if(uvo == null) {
			
			return "/demandsurvey/dsDetail";
		}else {
			String userID = uvo.getUvo().getId();
			
			if(!writer.equals(userID)) {
				int isOk = dss.updateReadCount(dno);
				log.info((isOk>0)?"조회수 증가 성공":" 조회수 증가 실패");
			}
			
			//참여 여부
			int isParticipated = dss.getIsParticipated(userID, dno);
			if(isParticipated>0) {
				//참여함
				m.addAttribute("isParticipated","true");
			}else {
				m.addAttribute("isParticipated","false");
			}
			
			return "/demandsurvey/dsDetail";
		}

		
	}

	// ==========================================================
	// 글 수정

	@GetMapping("/edit")
	public String editDS(@RequestParam int dno, Model m) {
		log.info("수요조사 폼 수정 하러 이동~");

		DemandSurveyVO dsvo = dss.getDSDetail(dno);
		//m.addAttribute("dsDetail", dsvo);
		LocalDateTime startDate = dsvo.getStartDate();
		LocalDateTime endDate = dsvo.getEndDate();
		//날짜 분리하기
		int startYear = startDate.getYear();
		int startMonth = startDate.getMonthValue();
		int startDay = startDate.getDayOfMonth();
		int startHour = startDate.getHour();
		int startMinute = startDate.getMinute();

		String startDateStr = String.format("%d-%02d-%02d",  startYear, startMonth, startDay);
		
		int endYear = endDate.getYear();
		int endMonth = endDate.getMonthValue();
		int endDay = endDate.getDayOfMonth();
		int endHour = endDate.getHour();
		int endMinute = endDate.getMinute();

		String endDateStr = String.format("%d-%02d-%02d",  endYear, endMonth, endDay);
		
		m.addAttribute("startDateStr", startDateStr);
		m.addAttribute("endDateStr", endDateStr);
		m.addAttribute("startHour",startHour);
		m.addAttribute("startMinute",startMinute);
		m.addAttribute("endHour",endHour);
		m.addAttribute("endMinute",endMinute);

		DemandSurveyImgVO dsivo = dss.getThumbNailFile(dno);
		
		List<DemandSurveyProductVO> dspvo = dss.getProductList(dno); 
		
		//productList json형식으로 변환
		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonProductList = mapper.writeValueAsString(dspvo);
			m.addAttribute("jsonProductList",jsonProductList);
			log.info(jsonProductList);
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}
		
		int participationCount = dss.getParticipantCount(dno); 
		
		DemandSurveyEditDTO dsedto = new DemandSurveyEditDTO(dsvo, dsivo, dspvo, participationCount);
		
		m.addAttribute("dsedto", dsedto);
		m.addAttribute("dno", dno);
		return "/demandsurvey/dsEdit";
	}

	@PostMapping("/edit")
	public String editDSProcess(@ModelAttribute DemandSurveyVO dsvo, Model m, RedirectAttributes ra,
			@RequestParam(name="thumbnailFile", required=false) MultipartFile thumbnailFile,
			@RequestParam("startDay")String startDay,
			@RequestParam("startHour") String startHour, 
			@RequestParam("startMinute") String startMinute, 
			@RequestParam("endDay") String endDay,
			@RequestParam("endHour") String endHour,
			@RequestParam("endMinute") String endMinute) {
		
		log.info("수요조사 폼 수정 진행중..");
			
		log.info("업데이트 dsvo >>> "+dsvo.toString());
		log.info("update file >>> "+thumbnailFile);
		
		//날짜 다시 조립해서 status적용
		 String startDateTimeString = startDay + " " + startHour + ":" + startMinute;
		 String endDateTimeString = endDay + " " + endHour + ":" + endMinute;
		 
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		 LocalDateTime startDate = LocalDateTime.parse(startDateTimeString, formatter);
		 LocalDateTime endDate = LocalDateTime.parse(endDateTimeString, formatter);
		 
		 String fullDate = startDateTimeString+" ~ "+endDateTimeString;
		 
		 log.info("*************>> "+startDate);
		 log.info("*************>> "+endDate);
		 
		 dsvo.setStartDate(startDate);
		 dsvo.setEndDate(endDate);
		 dsvo.setFullDate(fullDate);

		 LocalDateTime now = LocalDateTime.now();
		 log.info("현재시간 >> "+now);
		 
		 if(now.isBefore(startDate)) { //now가 인자보다 과거일때 true
			 //시작전
			 log.info("시작전");
			 dsvo.setStatus("before");
		 }else if(now.isAfter(startDate)) {  //현재시각이 startDate보다 미래 
			 log.info("진행중");
			 dsvo.setStatus("ongoing");
		 }
		
		if(thumbnailFile == null || thumbnailFile.isEmpty()) {
			log.info("썸네일 기존꺼 쓰기");
			int dno = dsvo.getDno();
			DemandSurveyImgVO file = dss.getThumbNailFile(dno);
			int isOk = dss.updateDemandSurvey(dsvo, file);
			
			log.info((isOk>0)?"업데이트 성공 ":"업데이트 실패");
		}else {
			log.info("썸네일 변경함");
			//썸네일 파일 다시 등록 
			DemandSurveyImgVO file = dshd.uploadThumbnailImg(thumbnailFile);
			int isOk = dss.updateDemandSurvey(dsvo, file);
			
			log.info((isOk>0)?"업데이트 성공 ":"업데이트 실패");
		}


		return "redirect:/demandSurvey/detail?dno=" + dsvo.getDno();
	}
	
	
	//수요조사 상품 -> 수정
	@PostMapping(value = "/productListEdit", produces = {MediaType.TEXT_PLAIN_VALUE}, consumes ="application/json")
	public ResponseEntity<String> productEditList(@RequestBody DemandSurveyProductEditDTO editList){
		
		int isOk = dss.updateProductList(editList);
		
		log.info((isOk>0)?"productList insert 성공":"productList insert 실패");
		
		return ResponseEntity.ok("productList 성공");
	}
	
	//글삭제
	@GetMapping("/delete")
	public String demandDelete(@RequestParam int dno) {
		log.info(dno+" 번 수요조사 폼삭제");
		
		int isOk = dss.deleteDemand(dno);
		
		log.info((isOk>0)?"수요조사 is_del 1 update" : "수죠오사 is_del 1 update 실패");
		
		return "redirect:/";
	}
	
	//==========================================================
	//선택된 상품 정보 내보내기
	@ResponseBody
	@PostMapping(value = "/choosedList", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = "application/json" )
	public ResponseEntity<?> getChoosedProductList(@RequestBody List<Integer> dpnoList ){
		
		log.info("dpnoList 한번보자 >>>"+dpnoList);
		List<DemandSurveyProductVO> productList = new ArrayList<>();
		for(int dpno : dpnoList) {
			DemandSurveyProductVO dspvo = dss.getProduct(dpno);
			productList.add(dspvo);
		}
		
		log.info("보내줄 productList >>>> "+productList);
		
		//만약 나중에 product에 이미지 추가할거면 img추가한 dto만들어서 변경해주기
		
		return ResponseEntity.ok(productList);
		
	}
	
	//===============================================================
	//수요조사 참여
	
	@ResponseBody
	@PostMapping(value ="/participation", produces= {MediaType.APPLICATION_JSON_VALUE}, consumes="application/json")
	public ResponseEntity<String> insertDemandParticipation(@RequestBody List<DemandSurveyParticipationVO> userProductList){
		
		log.info("넘어온 객체 >>> "+userProductList);
		
		int isOk = 1;
		for(DemandSurveyParticipationVO dspcvo : userProductList) {
			isOk *= dss.insertDemandParticipation(dspcvo);
		}
		
		if(isOk>0) {
			//성공
			return new ResponseEntity<String> ("INSERT_OK", HttpStatus.OK);
		}else {
			return new ResponseEntity<String> ("INSERT_ERROR", HttpStatus.BAD_REQUEST);
		}

	}
	
	//내가 작성한 수요조사 폼 상세보기!
	
	@GetMapping("/demandFormDetail")
	public String myDemandFormDetail(@RequestParam int dno, Model m) {
		
		//해당 dno 정보 다 뿌리기..
		//제목, 등록일, 시작, 종료날짜
		DemandSurveyVO dsvo = dss.getDSDetail(dno);
		//썸네일
		DemandSurveyImgVO dsivo = dss.getThumbNailFile(dno);
		//판매내역
		List<DemandSurveyProductVO> dspvo = dss.getProductList(dno);
		//참여자 정보
		List<DemandSurveyParticipationVO> dsppvo = dss.getParticipation(dno);
		//참여자수
		int count = dss.getParticipantCount(dno);
		
		DemandSurveyDataVO data = new DemandSurveyDataVO();
		data.setDsvo(dsvo);
		data.setDsivo(dsivo);
		data.setDspvo(dspvo);
		//data.setDsppvo(dsppvo);
		data.setParticipationCount(count);
		
		m.addAttribute("data", data);
		m.addAttribute("dsppvo",dsppvo);
		return "/demandsurvey/dsFormDetail";
	}
	
	//내가 참여한 수요조사 상세보기
	@GetMapping("/demandBuyerFormDetail")
	public String myBuyerDemandFormDetail(@RequestParam int dno,HttpServletRequest srq, Model m) {
		//해당 dno를 가지고 글 제목, 썸네일, 내가 구매한 상품명/가격/개수 가져오기
		DemandSurveyVO dsvo = dss.getDSDetail(dno);
		DemandSurveyImgVO dsivo = dss.getThumbNailFile(dno);
		
		HttpSession session = srq.getSession();
		UserImgDTO user = (UserImgDTO)session.getAttribute("ses");
		String userId = user.getUvo().getId();
		
		//해당 id, dno를 주고 상품명, 가격, 개수 가져오기
		List<DemandSurveyParticipationVO> dsppvo = dss.getUserDemandFormDetail(userId,dno);
		
		DemandSurveyUserParticipationDTO dsupdto = new DemandSurveyUserParticipationDTO();
		dsupdto.setDsivo(dsivo);
		dsupdto.setDsvo(dsvo);
		
		//이름, 이메일, 연락처, 주문서작성일 <- 중복제거해서 한번만 뿌릴거
		DemandSurveyParticipationVO distinctDsppvo = dss.getParticipation(userId,dno);
		
		m.addAttribute("data", dsupdto);
		m.addAttribute("list",dsppvo);
		m.addAttribute("info", distinctDsppvo);
		
		return "/demandsurvey/dsBuyerFormDetail";
	}
	
}
