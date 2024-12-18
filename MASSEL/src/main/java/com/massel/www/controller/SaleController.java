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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.massel.www.domain.DemandSurveyImgDTO;
import com.massel.www.domain.DemandSurveyVO;
import com.massel.www.domain.OrderStatus;
import com.massel.www.domain.PagingVO;
import com.massel.www.domain.SaleDTO;
import com.massel.www.domain.SaleFavoriteDTO;
import com.massel.www.domain.SaleFavoriteVO;
import com.massel.www.domain.SaleImgDTO;
import com.massel.www.domain.SaleImgVO;
import com.massel.www.domain.SaleOrdererInfoDTO;
import com.massel.www.domain.SaleOrdererParticipationDTO;

import com.massel.www.domain.SaleOrdererVO;
import com.massel.www.domain.SaleProductDTO;
import com.massel.www.domain.SaleProductImgVO;
import com.massel.www.domain.SaleProductVO;
import com.massel.www.domain.SaleSellerDetailDTO;
import com.massel.www.domain.SaleSellerVO;
import com.massel.www.domain.SaleShipmentVO;
import com.massel.www.domain.SaleVO;
import com.massel.www.domain.UserDemandDTO;
import com.massel.www.domain.UserImgDTO;
import com.massel.www.domain.UserImgVO;
import com.massel.www.domain.UserSaleDTO;
import com.massel.www.domain.UserVO;
import com.massel.www.handler.PagingHandler;
import com.massel.www.handler.SaleImgFileHandler;
import com.massel.www.service.DemandSurveyService;
import com.massel.www.service.SaleService;
import com.massel.www.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/salePath")
@Slf4j
@Controller
public class SaleController {
	
	@Inject
	private SaleImgFileHandler shd;
	
	@Inject
	private SaleService ssv;
	
	@Inject
	private DemandSurveyService dsv;
	
	@Inject
	private UserService usv;

	@GetMapping("/saleRegister")
	public String saleRegisterForm() {
		return "/sale/saleRegister";
	}
	
//	@PostMapping("/register")
//	public String saleRegister(SaleVO svo, RedirectAttributes ra,
//			 @RequestParam(name="thumbnailFile", required=false) MultipartFile thumbnailFile,
//			 @RequestParam("startDay") String startDay,
//			 @RequestParam("startHour") String startHour, 
//			 @RequestParam("startMinute") String startMinute, 
//			 @RequestParam("endDay") String endDay,
//			 @RequestParam("endHour") String endHour,
//			 @RequestParam("endMinute") String endMinute,
//			 @RequestParam("bankName") String bankName,
//			 @RequestParam("accountNumber") String accountNumber,
//			 @RequestParam("accountHolder") String accountHolder) {
//		
//		 log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@판매폼등록시작@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//		
//		SaleImgVO file = shd.uploadThumbnailImg(thumbnailFile);
//		
//		 //날짜,시간 합치기 (먼저 String으로 생성)
//		 String startDateTimeString = startDay + " " + startHour + ":" + startMinute;
//		 String endDateTimeString = endDay + " " + endHour + ":" + endMinute;
//		 
//		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//		 LocalDateTime startDate = LocalDateTime.parse(startDateTimeString, formatter);
//		 LocalDateTime endDate = LocalDateTime.parse(endDateTimeString, formatter);
//		 
//		 String fullDate = startDateTimeString+" ~ "+endDateTimeString;
//		 
//		 log.info("*************>> "+startDate);
//		 log.info("*************>> "+endDate);
//		 
//		 svo.setStartDate(startDate);
//		 svo.setEndDate(endDate);
//		 svo.setFullDate(fullDate);
//		 
//		 //현재 시간이랑 startDate 비교하기
//		 LocalDateTime now = LocalDateTime.now();
//		 log.info("현재시간 >> "+now);
//		 
//		 if(now.isBefore(startDate)) { //now가 인자보다 과거일때 true
//			 //시작전
//			 log.info("시작전");
//			 svo.setStatus("before");
//		 }else if(now.isAfter(startDate)) {  //현재시각이 startDate보다 미래 
//			 log.info("진행중");
//			 svo.setStatus("ongoing");
//		 }
//		 
//		 
//		 SaleImgDTO sidto = new SaleImgDTO(svo, file);
//		 
//		 log.info("sidto >>> "+sidto.toString());
//		 
//		 int isOk = ssv.registerSale(sidto);
//		 
//		 SaleSellerVO slvo = new SaleSellerVO();
//		 
//		 if(isOk>0) {
//			 log.info("판매 폼 등록 성공");
//			 //판매폼 등록 성공하면 계좌정보 등록하기
//			 //방금 insert된 sno 가져오기
//			 int sno = ssv.getLastInsertedSno();
//			 log.info("방금 등록된 글 번호 >>> "+sno);
//			 String writer = svo.getSwriter();
//			 slvo.setSno(sno);
//			 slvo.setSwriter(writer);
//			 slvo.setAccountHolder(accountHolder);
//			 slvo.setAccountNumber(accountNumber);
//			 slvo.setBankName(bankName);
//			 isOk = ssv.insertSellerAccount(slvo);
//			 
//			 log.info((isOk>0)?"계좌정보 등록 성공":"계좌정보 등록 실패");
//			 
//		 }else {
//			 log.info("판매 폼 등록 실패");
//		 }
//		 
//		 
//		 log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@판매폼등록끝@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//		 
//		 return "redirect:/salePath/list";
//
//	}
	
	@PostMapping(value = "/saleRegister", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registerSale(
			@RequestPart("svo") SaleVO svo,
			@RequestPart("accountInfo") SaleSellerVO slvo,
			@RequestPart("productList") List<SaleProductVO> productList,
			@RequestPart("shippingMethod") List<SaleShipmentVO> shippingMethod,
			@RequestPart(value ="thumbnailFile", required =false) MultipartFile thumbnailFile,
			@RequestPart(value = "imageFiles", required = false) List<MultipartFile> imageFiles,
			@RequestPart("dateValue") DateValueVO dateValue){
		
		log.info("svo >>> "+svo.toString());
		log.info("slvo >> "+slvo.toString());
		log.info("productList >> "+productList.toString());
		log.info("shippingMethod >> "+shippingMethod.toString());
		log.info("thumbnailFile >> "+thumbnailFile.toString());
		log.info("imageFiles >> "+imageFiles.toString());
		log.info("dateValue >> "+dateValue.toString());
	
		
		 //글 등록
		 //날짜,시간 합치기 (먼저 String으로 생성)
		 String startDateTimeString = dateValue.getStartDay() + " " + dateValue.getStartHour() + ":" + dateValue.getStartMinute();
		 String endDateTimeString = dateValue.getEndDay() + " " + dateValue.getEndHour() + ":" + dateValue.getEndMinute();
		 
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		 LocalDateTime startDate = LocalDateTime.parse(startDateTimeString, formatter);
		 LocalDateTime endDate = LocalDateTime.parse(endDateTimeString, formatter);
		 
		 String fullDate = startDateTimeString+" ~ "+endDateTimeString;
		 
		 log.info("*************>> "+startDate);
		 log.info("*************>> "+endDate);
		 
		 svo.setStartDate(startDate);
		 svo.setEndDate(endDate);
		 svo.setFullDate(fullDate);
		 
		 //현재 시간이랑 startDate 비교하기
		 LocalDateTime now = LocalDateTime.now();
		 log.info("현재시간 >> "+now);
		 
		 if(now.isBefore(startDate)) { //now가 인자보다 과거일때 true
			 //시작전
			 log.info("시작전");
			 svo.setStatus("before");
		 }else if(now.isAfter(startDate)) {  //현재시각이 startDate보다 미래 
			 log.info("진행중");
			 svo.setStatus("ongoing");
		 }
		
		 
		//썸네일 등록
		SaleImgVO file = shd.uploadThumbnailImg(thumbnailFile);
		
		SaleImgDTO sidto = new SaleImgDTO(svo, file);
		 
		log.info("sidto >>> "+sidto.toString());

		int isOk = ssv.registerSale(sidto);
		
		Map<String, String> response = new HashMap<String, String>();
		
		if(isOk>0) {
			log.info("판매폼 글 등록 성공");
			//판매폼 등록 성공하면 계좌/상품/배송정보 각각 등록하기
			int sno = ssv.getLastInsertedSno();
			log.info("방금 등록된 글 번호 >>> "+sno);
			
			//계좌등록
			String writer = svo.getSwriter();
			slvo.setSno(sno);
			slvo.setSwriter(writer); 
			log.info("등록하려는 계좌 정보 >>>"+slvo.toString());
			isOk = ssv.insertSellerAccount(slvo);
			log.info((isOk>0)?"계좌 등록 성공":"계좌 등록 실패");
			
			//상품등록
			isOk = ssv.registerSaleProduct(productList, imageFiles, sno);
			log.info((isOk>0)?"상품등록 성공":"상품등록 실패");
			
			//배송정보 등록
			isOk = ssv.registerSaleShipping(shippingMethod, sno);
			log.info((isOk>0)?"배송정보등록 성공" :"배송정보등록 실패");	
			
			if(isOk>0) {
				
				response.put("status", "success");
				return ResponseEntity.ok(response);
			}else {
				response.put("status", "failure");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			}
			
		}else {
			log.info("판매폼 글 등록 실패");
			response.put("status", "failure");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}		
	
	
	//썸머노트 등록
	
	@PostMapping("/summernote")
	@ResponseBody
	public String registerSummernote(@RequestParam(name="file", required=false)MultipartFile imageFile,
			HttpServletRequest srq) {
		
		JsonObject jsonObject = new JsonObject();
		
		//저장경로 설정
		String uploadPath = srq.getSession().getServletContext().getRealPath("resources");
		String fileRoot = uploadPath + "\\editorImg\\";
		
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
	
	//리스트 출력
	@GetMapping("/list")
	public String saleList(Model m, PagingVO pvo) {
		log.info("sale List");
		
		List<UserSaleDTO> list = new ArrayList<UserSaleDTO>();

		List<SaleImgDTO> saleList = ssv.getPagingList(pvo);
		for(SaleImgDTO sidto : saleList) {
			String writer = sidto.getSvo().getSwriter();
			UserImgVO uivo = usv.getUserImg(writer);
			
			list.add(new UserSaleDTO(sidto, uivo));
		}
			
		m.addAttribute("list", list);
		
		int totalCount = ssv.getTotalCount(pvo);
		PagingHandler ph = new PagingHandler(pvo, totalCount);
		m.addAttribute("ph",ph);
	
		return "/sale/saleList";
	}

	
	//디테일
	@GetMapping("/detail")
	public String saleDetail(@RequestParam int sno, Model m, HttpServletRequest srq) {
		log.info("sale detail");
		
		//글 saleVO, 썸네일 saleimgVO, 제품 saleProduct, 배송방법 saleShipment, 계좌정보 saleSeller
		SaleDTO sdto = new SaleDTO();
		
		//글가져오기
		SaleVO svo = ssv.getDetail(sno);
		
		//썸네일 가져오기
		SaleImgVO sivo = ssv.getThumbnail(sno);
		
		//제품 list 가져오기
		List<SaleProductDTO> spdto = ssv.getProductList(sno);
		
		for (SaleProductDTO product : spdto) {
		    if (product.getSpivo() == null) {
		        log.warn("spivo가 null입니다. spno: " + product.getSpvo().getSpno());
		    }
		}
		
		log.info("한번 찍어봐 spdto를 !!!!!!!!!!! "+spdto.toString());
		
		//배송방법 list 가져오기
		List<SaleShipmentVO> shipmentList = ssv.getShipment(sno);
		
		//계좌정보 가져오기
		SaleSellerVO sellerAccount = ssv.getSellerAccount(sno);
		
		sdto.setSvo(svo);
		sdto.setSivo(sivo);
		sdto.setSpdto(spdto);
		sdto.setSsvo(shipmentList);
		sdto.setSlvo(sellerAccount);
		
		log.info("총 sdto 값 >> "+sdto.toString());
		m.addAttribute("sdto", sdto);
		
		//글쓴이 이미지 가져오기!!!!!
		String id = svo.getSwriter();
		UserVO uvo = usv.getUser(id);
		UserImgVO uivo = usv.getUserImg(id);
		
		UserImgDTO writerInfo = new UserImgDTO(uvo, uivo);
		
		m.addAttribute("writer",writerInfo);
		
		//session 로그인 사용자가 해당 글에 좋아요를 눌렀는지 확인하기
		//만약 로그인을 했다면!!!!
		
		HttpSession session = srq.getSession();
		UserImgDTO user = (UserImgDTO)session.getAttribute("ses");
		
		if(user == null) {
			//로그인이 안되어있다면 암것도 하지않고 이동
			return "/sale/saleDetail";
		
		}else {
			
			String userID = user.getUvo().getId();
			
			int isFavorite = ssv.isFavorite(sno, userID);
			String state;
			if(isFavorite >0) {
				state = "true"; //사용자가 좋아요 누른 상태
			}else {
				state = "false"; //사용자가 좋아요 누르지 않은 상태
			}
			m.addAttribute("favoriteState", state);
			
			//조회수증가
			//session Id랑 writer랑 달라야만 조회수 증가
			String writer = svo.getSwriter();
			log.info("글쓴이 >>> "+writer+", 로그인ID >>> "+userID);
			if(!writer.equals(userID)) {
				log.info(" 결과 >>>>"+!writer.equals(userID));
				int isOk = ssv.readCount(sno);
				log.info((isOk>0)?"조회수 증가 성공":"조회수 증가 실패");
			}

			//사용자의 구매여부 확인(sale_orderer에 해당 id가 존재하는지 확인)
			int isParticipated = ssv.getIsParticipated(sno, userID);
			
			if(isParticipated>0) { //구매했다는 의미
				log.info("isParticipated >> "+isParticipated);
				m.addAttribute("isParticipated", "true");
			}else {
				log.info("isParticipated >> "+isParticipated);
				m.addAttribute("isParticipated", "false");
			}
			
			return "/sale/saleDetail";
		}

		
	}
	
	
	//수정
	@GetMapping("/editSale")
	public String saleEdit(@RequestParam int sno, Model m) {
		
		//정보 가져가기
		SaleDTO dto = ssv.getSaleDetail(sno);
	
		
		LocalDateTime startDate = dto.getSvo().getStartDate();
		LocalDateTime endDate = dto.getSvo().getEndDate();
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
		
		
		m.addAttribute("dto", dto);
			
		//상품 json형식으로 바꾸기	
		List<SaleProductDTO> productList = dto.getSpdto();
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonProductList = mapper.writeValueAsString(productList);
			m.addAttribute("jsonProductList",jsonProductList);
			log.info(jsonProductList);
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}
		
		//배송방법 json형식으로 바꾸기
		List<SaleShipmentVO> shipmentList = dto.getSsvo();
		
		try {
			String jsonShipmentList = mapper.writeValueAsString(shipmentList);
			m.addAttribute("jsonShipmentList",jsonShipmentList);
		}catch(JsonProcessingException e) {
			e.printStackTrace();
		}
		
		//참가자 구하기
		int participant = ssv.getParticipantCount(sno);
		
		m.addAttribute("participant", participant);
		
		return "/sale/saleEdit";
	}
	
	
	@PostMapping("/modifySale")
	public String saleModify(@ModelAttribute SaleVO svo, RedirectAttributes ra,
			 @RequestParam(name="thumbnailFile", required=false) MultipartFile thumbnailFile,
			 @RequestParam("startDay") String startDay,
			 @RequestParam("startHour") String startHour, 
			 @RequestParam("startMinute") String startMinute, 
			 @RequestParam("endDay") String endDay,
			 @RequestParam("endHour") String endHour,
			 @RequestParam("endMinute") String endMinute,
			 @RequestParam("bankName") String bankName,
			 @RequestParam("accountNumber") String accountNumber,
			 @RequestParam("accountHolder") String accountHolder) {
		
		
		//날짜 다시 조립해서 status적용
		 String startDateTimeString = startDay + " " + startHour + ":" + startMinute;
		 String endDateTimeString = endDay + " " + endHour + ":" + endMinute;
		 
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		 LocalDateTime startDate = LocalDateTime.parse(startDateTimeString, formatter);
		 LocalDateTime endDate = LocalDateTime.parse(endDateTimeString, formatter);
		 
		 String fullDate = startDateTimeString+" ~ "+endDateTimeString;
		 
		 log.info("*************>> "+startDate);
		 log.info("*************>> "+endDate);
		 
		 svo.setStartDate(startDate);
		 svo.setEndDate(endDate);
		 svo.setFullDate(fullDate);

		 LocalDateTime now = LocalDateTime.now();
		 log.info("현재시간 >> "+now);
		 
		 if(now.isBefore(startDate)) { //now가 인자보다 과거일때 true
			 //시작전
			 log.info("시작전");
			 svo.setStatus("before");
		 }else if(now.isAfter(startDate)) {  //현재시각이 startDate보다 미래 
			 log.info("진행중");
			 svo.setStatus("ongoing");
		 }
		 
		 //썸네일 처리
			if(thumbnailFile == null || thumbnailFile.isEmpty()) {
				log.info("썸네일 기존꺼 쓰기");
				int sno = svo.getSno();
				SaleImgVO file = ssv.getThumbnail(sno);
				int isOk = ssv.updateSale(svo, file);
				
				log.info((isOk>0)?"업데이트 성공 ":"업데이트 실패");
			}else {
				log.info("썸네일 변경함");
				//썸네일 파일 다시 등록 
				SaleImgVO file = shd.uploadThumbnailImg(thumbnailFile);
				int isOk = ssv.updateSale(svo, file);
				
				log.info((isOk>0)?"업데이트 성공 ":"업데이트 실패");
			}
		
		//상품처리
		int sno = svo.getSno();
		//판매자 계좌정보 처리
		int sellerIsOk = ssv.updateSellerInfo(bankName, accountNumber, accountHolder, sno);
		log.info((sellerIsOk>0)?"계좌변경 성공 ":"계좌변경 실패");
			
		return  "redirect:/salePath/detail?sno=" + sno;
		
	}
	
	
	//상품 수정처리 따로
	@PostMapping(value="/productListUpdate")
	public ResponseEntity<String> productListUpdate(        
			@RequestPart("productList") List<SaleProductVO> productList,
			@RequestPart("imageFiles") List<MultipartFile> imageFiles,
			@RequestParam("sno")int sno){
		
		log.info("넘어온 productList >> "+productList.toString());
		
		if(imageFiles != null && !imageFiles.isEmpty()) {
			log.info("imageFiles >>> "+imageFiles.toString());
		}
		
		int isOk = ssv.updateProduct(productList, imageFiles, sno);
		
		log.info((isOk>0)?"상품 수정 성공":"상품 수정 실패");
		
		return ResponseEntity.ok("productList 수정 완료");
	
		
	}
	
	//택배수정따로
	@PostMapping(value="/updateSaleShipment", consumes="application/json", produces= {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<?> updateSaleShipment(@RequestBody List<SaleShipmentVO> shippingList){
		
		log.info("들어온 값 >> "+shippingList.toString());
		
		int isOk = ssv.updateShipment(shippingList);

		if(isOk>0) {
			log.info("배송방법 잘 들어옴");
			return ResponseEntity.ok("배송방법 잘들어오ㅓㅁ");
		}else {
			log.info("배송방법 잘 안들어옴");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("실패");
		}	
	}
	
	//글삭제
	
	@GetMapping("/delete")
	public String deleteSale(@RequestParam int sno) {
		log.info(" >>>> "+sno +"번 글 삭제");
		
		int isOk = ssv.deleteSale(sno);
		log.info((isOk>0)?" 글 삭제 성공 ":" 글 삭제 실패");
		
		return "redirect:/";
		
	}

	//글영구삭제
	@GetMapping("/remove")
	public String removeSale(@RequestParam int sno) {
		log.info(" >>>> "+sno +"번 글 삭제");
		
		int isOk = ssv.removeSale(sno);
		log.info((isOk>0)?" 글 삭제 성공 ":" 글 삭제 실패");
		
		return "redirect:/";
		
	}
	
	//사용자가 선택한 제품 list 보내주기
	@PostMapping(value="/choosedList", produces= {MediaType.APPLICATION_JSON_VALUE}, consumes="application/json")
	public ResponseEntity<?> choosedProductList(@RequestBody List<Integer> spnoList){
		
		log.info("서버로 온 spnoList >> "+spnoList.toString());
		
		//해당 spno 목록 갖다바치기 + img도 같이
		List<SaleProductDTO> list = new ArrayList<SaleProductDTO>();
		
		for(int spno : spnoList) {
			SaleProductVO spvo = ssv.getProduct(spno); //해당 제품번호 주고 그 상품 가져오기
			SaleProductImgVO spivo = ssv.getProductImg(spno); //해당 제품번호 주고 그 상품 이미지 가져오기
			
			list.add(new SaleProductDTO(spvo, spivo));
		}
		
		log.info("선택받은 아이들 >> "+list.toString());
		
		//나중에 productImgDTO 만들어서 같이 보내주기!
		return ResponseEntity.ok(list);
		
	}
	
	//구매 참여
	@PostMapping(value="/participation", produces= {MediaType.APPLICATION_JSON_VALUE}, consumes="application/json")
	public ResponseEntity<Map<String, Integer>> saleParticipation(
			@RequestBody SaleOrdererParticipationDTO pdto){
		
		log.info("pdto >>> "+pdto.toString());
		
		int isOk = ssv.insertUserSaleInfo(pdto);
		Map<String, Integer> response = new HashMap<>();
		
		//orderNo 넘겨주기
		if(isOk>0) {
			int orderNo = pdto.getSaleOrdererData().getOrderNo();
			int sno = pdto.getSaleOrdererData().getSno();
			response.put("orderNo", orderNo);
			response.put("sno", sno );
			return ResponseEntity.ok(response);
		
		}else {
			response.put("orderNo", -1);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

	}
	
	
	//좋아요기능
	@PostMapping(value="/toggleFavorite", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> toggleFavorite(@RequestBody SaleFavoriteDTO fdto){
		
		int result = 0;
		String userID = fdto.getId();
		int sno = fdto.getSno();
		
		log.info("좋아요 확인 >>> "+userID+", "+sno+", 상태 >> "+fdto.getAction());
		
		log.info("현재 상태 >>> "+fdto.getAction());
		
		if("add".equals(fdto.getAction())) {
			log.info("add 상태여서 좋아요 insert하러 갑니다");
			result = ssv.insertFavorite(userID, sno);
				if(result>0) {
					log.info("insert성공해서 favoriteCount +1 업데이트!");
					//sale table에 favorite_count 업데이트 해주기!!
					updateFavoriteCount(sno, 1);
				}
		}else if("remove".equals(fdto.getAction())) {
			log.info("remove 상태여서 좋아요 delete하러갑니다");
			result = ssv.deleteFavorite(userID, sno);
				if(result>0) {
					log.info("delete성공해서 favoriteCount -1 업데이트");
					updateFavoriteCount(sno, -1);
				}
		}
		
		//응답 데이터
		Map<String, Object> response = new HashMap<>();
		response.put("result", result);
		
		log.info("result값 >> "+result);
		
		int favoriteCount = ssv.getFavoriteCount(sno);
		log.info("favoriteCount >> "+favoriteCount);
		//response.put("favoriteCount", favoriteCount != null ? favoriteCount : 0);
		response.put("favoriteCount", favoriteCount);
		
		return ResponseEntity.ok(response);
	}
	
	
	private void updateFavoriteCount(int sno, int count) {
		int isOk = ssv.updateFavoriteCount(sno, count);
		if(isOk>0) {
			log.info("업데이트 성공");
		}else {
			log.info("업데이트 실패");
		}
	}
	
	
	//saleForm detail(내가 올린 판매폼 상세보기)
	@GetMapping("/saleFormDetail")
	public String detailSaleForm(@RequestParam int sno, Model m) {
		//해당 sno의 썸네일, 글, 배송방법, 유저참여정보 가져오기
		//SaleSellerDetailDTO 
		
		//글가꼬오기!!
		SaleVO svo = ssv.getDetail(sno);
		//썸네일
		SaleImgVO sivo = ssv.getThumbnail(sno);
		//상품정보
		List<SaleProductDTO> spdto = ssv.getProductList(sno);
		
		SaleSellerDetailDTO detail = new SaleSellerDetailDTO();
		detail.setSvo(svo);
		detail.setSivo(sivo);
		detail.setSpdtoList(spdto);
		
		m.addAttribute("detail",detail);
		
		
		//참여유저정보,상품정보,배송정보(orderStatus가 결제대기 인거 제외하고 가져오기)
		String pendingStatus = OrderStatus.PENDING_PAYMENT.name();
		List<SaleOrdererInfoDTO> orderInfo = ssv.getOrderDetail(sno, pendingStatus);
		m.addAttribute("orderInfo",orderInfo);
		
		log.info("orderInfo >>> "+orderInfo);
		
		return "/sale/saleSellerDetail";
	}
	
	
	
	//상품을 구매한 유저 상세내역(판매자)
	@PostMapping(value="/getOrdererDetail", produces= {MediaType.APPLICATION_JSON_VALUE}, consumes="application/json")
	public ResponseEntity<?> getOrdererDetailInfo(@RequestBody Map<String,Integer> data, Model m){
		int orderNo = data.get("orderNo");
		log.info("orderNo가져옴>> "+orderNo);
		
		SaleOrdererInfoDTO orderdetailInfo = ssv.getOrdererDetail(orderNo);
		
		//m.addAttribute("info", orderdetailInfo);
		
		return ResponseEntity.ok(orderdetailInfo);

	}
	
	//상품구매유저 상세내역(구매자)
	@GetMapping("/saleBuyerFormDetail")
	public String myOrderDetail(@RequestParam int orderNo, Model m) {
		//해당 주문 번호로 sno가져와서 제목,썸네일 가져오기
		SaleOrdererVO sovo = ssv.getSno(orderNo);
		int sno = sovo.getSno();
		
		SaleVO svo = ssv.getDetail(sno);
		SaleImgVO sivo = ssv.getThumbnail(sno);
		
		SaleImgDTO sidto = new SaleImgDTO(svo, sivo);
		
		m.addAttribute("detailVo",sidto);
		//해당 주문 번호로 상세내역 가져오기
		SaleOrdererInfoDTO orderdetailInfo = ssv.getOrdererDetail(orderNo);
		log.info(">>"+orderdetailInfo);
		
		m.addAttribute("detailInfo", orderdetailInfo);
		
		//작성자 은행정보 가져오기
		SaleSellerVO slvo = ssv.getSellerAccount(sno);
		m.addAttribute("slvo",slvo);
		
		return "/sale/saleBuyerDetail";
	}

	//폼 구매 제출 후 입금계좌 나오는 부분
	@GetMapping("/orderPayment/{sno}/{orderNo}")
	public String orderPayment(@PathVariable int sno, @PathVariable int orderNo, Model m,
			HttpServletRequest srq) {
		log.info("orderNO >>> "+orderNo);
		log.info("sno >>> "+sno);
		
		m.addAttribute("orderNo", orderNo);
		
		//계좌정보 가져오기
		SaleSellerVO slvo = ssv.getSellerAccount(sno);
		m.addAttribute("accountInfo",slvo);
		
		//글쓴이정보
		String writer = slvo.getSwriter();
		log.info("작성자(판매자) >>>"+writer);
		m.addAttribute("writer", writer);
		
		//구매정보 가져오기
		SaleOrdererInfoDTO orderData = ssv.getOrdererDetail(orderNo);
		
		m.addAttribute("orderInfo",orderData);
		
		
		
		return "sale/saleOrderPayment";
	}
	
	//판매자의 구매폼 결제취소
	@ResponseBody
	@PostMapping(value = "/cancleOrder", produces = "application/json")
	public int cancleOrder(@RequestParam String orderNo) {
		log.info("유저의 주문 취소~");
		
		int realOrderNo = Integer.parseInt(orderNo);
		
		String status = OrderStatus.PAYMENT_CANCELED.name();
		int isOk = ssv.cancleOrder(realOrderNo, status);
		
		return isOk;
		
	}
	
	//판매자의 구매자 주문상태 변경
	@ResponseBody
	@PostMapping(value ="/updateBuyerOrder", produces="application/json")
	public int updateBuyerOrder(String orderStatus, 
			@RequestParam(value="chekcedOrderNo") ArrayList<Integer> orderNoList ) {
			
		log.info("orderNo >>> "+orderNoList.toString()+" ||| orderStatus >>> "+orderStatus);
		
		int isOk = 1;
		for(int orderNo :orderNoList) {
			isOk *= ssv.updateBuyerOrder(orderNo, orderStatus);	
		}
		
		log.info((isOk>0)? "변경 성공" : "변경 실패");
		return isOk;
		
	}
	
	//판매자의 구매자 주문 삭제
	@ResponseBody
	@PostMapping(value = "/deleteOrderNo", produces = "application/json")
	public int deleteBuyerOrder(@RequestParam(value="chekcedOrderNo") ArrayList<Integer> orderNoList) {
		
		log.info("orderNoList >> "+orderNoList);
		
		int isOk = 1;
		for(int orderNo : orderNoList) {
			isOk *= ssv.deleteOrderNo(orderNo);
		}
		
		log.info((isOk>0)? "삭제 성공" : "삭제 실패");
		
		return isOk;
	}
	
	
	//search
	@GetMapping("/search")
	public String search(@RequestParam("keyword")String keyword, Model m) {
		log.info("검색하려는 단어 >> "+keyword);
		
		m.addAttribute("keyword", keyword);
		
		//세일폼
		List<SaleImgDTO> saleList = ssv.getSaleSearchList(keyword);
		List<UserSaleDTO> saleDtoList = new ArrayList<UserSaleDTO>();
		
		//saleList가 있으면 글쓴이 이미지도 가져오기
		if(saleList != null && saleList.size() > 0) {
			
			for(SaleImgDTO sidto : saleList) {
				String writer = sidto.getSvo().getSwriter();
				UserImgVO uivo = usv.getUserImg(writer);
				
				saleDtoList.add(new UserSaleDTO(sidto, uivo));
			}			
		}

		if(saleList == null || saleList.size() == 0) {
			//찾으려는 내역 없으면 인기순 보여주기
			List<SaleImgDTO> popularList = ssv.getPopularList();
			List<UserSaleDTO> popularDtoList = new ArrayList<UserSaleDTO>();
			
			for(SaleImgDTO sidto : popularList) {
				String writer = sidto.getSvo().getSwriter();
				UserImgVO uivo = usv.getUserImg(writer);
				
				popularDtoList.add(new UserSaleDTO(sidto, uivo));
			}

			log.info("조회수 순 >> "+popularList);
			m.addAttribute("popularDtoList",popularDtoList);
		}else {
			m.addAttribute("saleDtoList",saleDtoList);
		}
		
		//수요조사폼
		List<DemandSurveyImgDTO> demandList = dsv.getDemandSearchList(keyword);
		List<UserDemandDTO> demandDtoList = new ArrayList<UserDemandDTO>();
		
		
		if(demandList != null && demandList.size()>0) {
			for(DemandSurveyImgDTO dsidto : demandList) {
				String writer = dsidto.getDsvo().getId();
				UserImgVO uivo = usv.getUserImg(writer);
				
				demandDtoList.add(new UserDemandDTO(dsidto, uivo));
			}		
		}
		
		log.info("수요조사 폼 왜안나와!!! >>> "+demandDtoList.toString());
		
		if(demandList == null || demandList.size() == 0) {
			//찾으려는 내역 없으면 인기 순 보여주기
			List<DemandSurveyImgDTO> popularDemandList = dsv.getPopularDemand();
			List<UserDemandDTO> popularDemandDtoList = new ArrayList<UserDemandDTO>();
			
			for(DemandSurveyImgDTO dsidto : popularDemandList) {
				String writer = dsidto.getDsvo().getId();
				UserImgVO uivo = usv.getUserImg(writer);
				
				popularDemandDtoList.add(new UserDemandDTO(dsidto, uivo));
				
			}
			
			log.info("인기순 왜 안나와!! >>> "+popularDemandDtoList.toString());
			
			m.addAttribute("popularDemandList", popularDemandDtoList);
		}else {
			m.addAttribute("demandList",demandDtoList);
		}
		
		//유저
		List<UserVO> userList = usv.getUserSearchList(keyword);
		m.addAttribute("userList",userList);
		
		return "/search/result";
	}
	
	//찜목록
	@GetMapping("/favoriteList")
	public String favoriteList(HttpServletRequest srq, Model m) {
		log.info("favoriteList!");
		
		HttpSession session = srq.getSession();
		
		
		UserImgDTO user = (UserImgDTO)session.getAttribute("ses");
		String userId = user.getUvo().getId();
		
		//유저의 찜목록 가져오기
		List<SaleFavoriteVO> favoriteList = ssv.myFavoriteList(userId);
		
		List<SaleImgDTO> svoList = new ArrayList<SaleImgDTO>();
		for(SaleFavoriteVO sfvo : favoriteList) { //favo
			int sno = sfvo.getSno();
			
			SaleVO svo = ssv.getSvoDetail(sno); //svo : 삭제된건 안갖고옴 
			if(svo == null) {
				continue;
			}
			SaleImgVO sivo = ssv.getThumbnail(sno); //삭제된건갖고옴;
			
			svoList.add(new SaleImgDTO(svo, sivo));
		}
		
		m.addAttribute("svoList",svoList);

		return "/user/userFavorite";
		
		
	}
	
}
