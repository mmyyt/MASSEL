package com.massel.www.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.massel.www.domain.DemandSurveyImgDTO;
import com.massel.www.domain.DemandSurveyImgVO;
import com.massel.www.domain.DemandSurveyParticipantDTO;
import com.massel.www.domain.DemandSurveyParticipationVO;
import com.massel.www.domain.DemandSurveyUserParticipationDTO;
import com.massel.www.domain.DemandSurveyVO;
import com.massel.www.domain.SaleImgDTO;
import com.massel.www.domain.SaleImgVO;
import com.massel.www.domain.SaleOrdererVO;
import com.massel.www.domain.SaleParticipationDTO;
import com.massel.www.domain.SaleUserParticipationVO;
import com.massel.www.domain.SaleVO;
import com.massel.www.domain.UserImgDTO;
import com.massel.www.domain.UserImgVO;
import com.massel.www.domain.UserVO;

import com.massel.www.handler.UserImgFileHandler;
import com.massel.www.service.DemandSurveyService;
import com.massel.www.service.SaleService;
import com.massel.www.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/member")
@Controller
public class UserController {
	
	@Inject
	private UserService usv;
	
	@Inject
	private DemandSurveyService dss;
	
	@Inject
	private SaleService ssv;
	
	@Inject
	private BCryptPasswordEncoder encodePw;

	@Inject
	private UserImgFileHandler uhd;
	//회원가입
	@GetMapping("/signUp")
	public String signUpMember() {
		log.info("회원가입 페이지로 이동");
		
		return "/user/userJoin";
	}
	
	@PostMapping("/signUp")
	public String signUp(UserVO uvo, Model m,
			 @RequestParam(name="userImgFile", required=false) MultipartFile userImgFile,
			 RedirectAttributes redirectAttributes) {
		log.info("회원가입 하려고 넘어온 객체 >>>> "+uvo);
		
		//이미지가 없는경우 이미지 등록 안하고 그냥 회원가입
		if(userImgFile == null || userImgFile.isEmpty()) {
			int isOk = usv.signUpWithoutImg(uvo);
			
			if(isOk>0) {
				
				log.info("회원가입 성공");
				//m.addAttribute("msg_signUp", 1);
				redirectAttributes.addFlashAttribute("msg_signUp", 1);
			}else {
				log.info("회원가입 실패");
				//m.addAttribute("msg_signUp", 0);
				redirectAttributes.addFlashAttribute("msg_signUp", 0);
			}
		}else {
			
			log.info("회원가입 이미지 >> "+userImgFile.toString());
			//이미지 등록
			UserImgVO uivo = uhd.uploadUserImg(userImgFile);
			if(uivo != null) {
				
				UserImgDTO userDto = new UserImgDTO(uvo, uivo);
				
				int isOk = usv.signUp(userDto);

				if(isOk>0) {
						
					log.info("회원가입 성공");
					//m.addAttribute("msg_signUp", 1);
					redirectAttributes.addFlashAttribute("msg_signUp", 1);
				}else {
					log.info("회원가입 실패");
					m.addAttribute("msg_signUp", 0);
					redirectAttributes.addFlashAttribute("msg_signUp", 0);
				}
			}	
		}

		return "redirect:/";
		
	}
	
	
	//회원가입  중복검증
	
	// 아이디 중복확인
	@ResponseBody
	@RequestMapping(value = "/idCheck", method = RequestMethod.POST)
	public int idCheck(String id) {
		log.info("user id Check 진입 >>>>");

		int result = usv.idCheck(id);

		log.info("id>>>>" + id);
		log.info("결과값 >>> " + result);

		return result;
	}

	// 닉네임 중복확인
	@ResponseBody
	@RequestMapping(value = "/nicknameCheck", method = RequestMethod.POST)
	public int nicknameCheck(String nickname) {
		log.info(">>nickname check 진입 >>>");

		int result = usv.nicknameCheck(nickname);

		log.info("nickname >>>" + nickname);
		log.info("nickname 중복 결과>>" + result);

		return result;
	}

	// 이메일 중복확인
	@ResponseBody
	@RequestMapping(value = "/emailCheck", method = RequestMethod.POST)
	public int emailCheck(String email) {
		log.info(">>> email check 진입 >>>>");
		int result = usv.emailCheck(email);
		log.info("email >>>" + email);
		log.info("email 중복 결과 >>>" + result);

		return result;
	}


	//---------------------------------------------------
	
	//로그인
	@GetMapping("/login")
	public String userLogin() {
		log.info("로그인 하러 이동..");
		
		return "/user/userLogin";
	}
	
	@PostMapping("/login")
	public String userLoginProcess(UserVO uvo, HttpServletRequest srq, Model m) {
		
		log.info("로그인 하려고 넘어온 id >> "+uvo.getId());
		UserVO user = usv.isUser(uvo.getId(), uvo.getPw());
		
		if(user == null) {
			log.info("로그인 실패, 해당 유저 존재하지 않음");
		}
		
		if(user != null) {
			
			UserImgVO uivo = usv.getUserImg(user.getId());

			UserImgDTO udto = new UserImgDTO(user, uivo);
			
			//세션에 해당 유저 집어넣기
			HttpSession session = srq.getSession();
			session.setAttribute("ses", udto);
			session.setMaxInactiveInterval(60*100); //100분
			
			log.info("로그인 성공");
			
			m.addAttribute("msg_login",1);
			return "redirect:/";
		}else {
			m.addAttribute("msg_login",0);
			log.info("로그인 실패");
			return "/user/userLogin";
		}

	}
	
	//============================================================================
	//로그아웃
	
	@GetMapping("/logout")
	public String userLogout(HttpServletRequest srq) {
		log.info("로그아웃 진행중");
		
		srq.getSession().invalidate();
		srq.getSession().removeAttribute("ses");
		
		log.info("로그아웃 성공");
		
		return "redirect:/";
		
	}
	
	
	//==============================================================================
	//회원정보
	
	@GetMapping("/info")
	public String userShowInfo(HttpServletRequest srq, Model m) {
		log.info("유저 정보");
		
		HttpSession session = srq.getSession();
		
		UserImgDTO user = (UserImgDTO)session.getAttribute("ses");
	
		m.addAttribute("userInfo", user);
		
		return "/user/userInfo";
	
	}
	
	
	//=====================================================================================
	//회원정보 수정
	
	@GetMapping("/edit")
	public String userEdit() {
		log.info("회원정보 수정");
		
		return "/user/userInfoEdit";
	}
	
	@PostMapping("/edit")
	public String userEditProcess(@ModelAttribute UserVO uvo, HttpServletRequest srq) {
		
		int isOk = usv.updateUser(uvo);
		
		if(isOk>0) {
			log.info("회원정보 수정 성공");
			HttpSession session = srq.getSession();
			session.setAttribute("ses", uvo);	
		}else {
			log.info("회원정보 수정 실패");
		}
		
		return "/user/userInfo";
	}
	
	
	
	//비밀번호 수정
	@PostMapping("/pwEdit")
	public ResponseEntity<Integer> userPwEdit(@RequestParam("pw")String pw,
			HttpServletRequest srq){
		
		HttpSession session = srq.getSession();
		UserImgDTO user = (UserImgDTO)session.getAttribute("ses");
		String id = user.getUvo().getId();
		log.info("수정해달라고 넘어온 비밀번호 >>"+pw);
		
		int isOk = usv.updatePw(id,pw);
		
		if(isOk>0) {
			session.setAttribute("ses", user);
		}else {
			log.info("user pw update fail");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(isOk); 
	}
	
	
	//닉네임 수정
	@PostMapping("/nickEdit")
	public ResponseEntity<Integer> userNicknameEdit(@RequestParam("nickname")String nickname,
			HttpServletRequest srq){
		
		HttpSession session = srq.getSession();
		UserImgDTO user = (UserImgDTO)session.getAttribute("ses");
		String id = user.getUvo().getId();
		
		int isOk = usv.updateNick(id, nickname);
		if(isOk>0) {
			user.getUvo().setNickname(nickname);
			session.setAttribute("ses", user);
		}else {
			log.info("user nickname update fail");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(isOk);
	}
	
	//프로필 이미지 수정
	@PostMapping("/imgUpdate")
	public String userProfileImgUpdate(@RequestParam(name="userImgFile", required=false) MultipartFile userImgFile,
			HttpServletRequest srq) {
		
		//이미지 새로 등록
		UserImgVO uivo = uhd.uploadUserImg(userImgFile);
		
		HttpSession session = srq.getSession();
		UserImgDTO user = (UserImgDTO)session.getAttribute("ses");
		String id = user.getUvo().getId();
		
		uivo.setId(id);
		
		//이미지가 없으면 이미지를 새로 insert
		UserImgVO eixistImg = usv.getUserImg(id);
		if(eixistImg == null) {
			
			int isOk = usv.insertImg(uivo);
			
			if(isOk>0) {
				log.info("이미지 설정 성공");
				UserVO uvo = usv.getUser(id);
				UserImgVO uvoImg = usv.getUserImg(id);
				
				UserImgDTO udto = new UserImgDTO(uvo, uvoImg);
				session.setAttribute("ses", udto);				
			}
			
			return "redirect:/member/info";
		}else {
			int isOk = usv.updateUserImg(uivo);
			if(isOk>0) {
				log.info("이미지 변경 성공");
				UserVO uvo = usv.getUser(id);
				UserImgVO uvoImg = usv.getUserImg(id);
				
				UserImgDTO udto = new UserImgDTO(uvo, uvoImg);
				session.setAttribute("ses", udto);
			}else {
				log.info("변경 실패");
			}
			
			return "redirect:/member/info";
		}
		


	}
	
	
	//=========================================================
	//회원탈퇴
	
	@GetMapping("/delete")
	public String userDelete(HttpServletRequest srq){
		
		log.info("탈퇴 진행");
		//현재 세션에 올라가 있는 유저 정보 가져오기
		
		HttpSession session = srq.getSession();
		
		UserImgDTO user = (UserImgDTO)session.getAttribute("ses");
		
		String loginID = user.getUvo().getId();
		
		int isOk = usv.deleteUser(loginID);
		
		if(isOk>0) {
			log.info("탈퇴 성공");
			
			srq.getSession().removeAttribute("ses");
			srq.getSession().invalidate();
		}else {
			log.info("탈퇴 실패");
		}
		
		return "home";
	}
	
	
	
	//==================================================
	//판매자 폼 (상품)
	
	@GetMapping("/sellerForm")
	public String mySaleForm(HttpServletRequest srq, Model m) {
		log.info("내가 올린 판매 폼 보기");
		
		//내가 올린 판매 폼 리스트
		//판매폼 썸네일, 제목, 번호, 등록일 가져오기
		
		HttpSession session = srq.getSession();
		UserImgDTO user = (UserImgDTO)session.getAttribute("ses");
		
		String userID = user.getUvo().getId();
		
		List<SaleParticipationDTO> spdtolist = new ArrayList<SaleParticipationDTO>();
		
		//글 리스트 가져오기
		List<SaleVO> list = ssv.getMySaleList(userID);
		log.info("글 리스트 가져온거 >> "+list.toString());
		
		for(SaleVO svo : list) {
			int sno = svo.getSno();
			SaleImgVO sivo = ssv.getThumbnail(sno);
			
			//SaleImgDTO sidto = new SaleImgDTO();
			SaleParticipationDTO spdto = new SaleParticipationDTO();
			spdto.setSvo(svo);
			spdto.setSivo(sivo);
			
			//참여자 수 구하기
			int count = ssv.getParticipationCount(sno);
			spdto.setParticipationCount(count);
			
			spdtolist.add(spdto);
		}
		
		log.info("판매 폼 리스트 >>> "+spdtolist.toString());
		
		m.addAttribute("list", spdtolist);
		
			
		return "/user/userSellerForm";
	}
	
	
	//수요조사
	@GetMapping("/sellerDemandForm")
	public String mySaleDemandForm(HttpServletRequest srq, Model m) {

		//session Id
		HttpSession session = srq.getSession();
		UserImgDTO user = (UserImgDTO)session.getAttribute("ses");
		
		String userID = user.getUvo().getId();
		
		//수요조사 폼 썸네일, 제목, 번호, 등록일, 참여자수 가져오기
		List<DemandSurveyParticipantDTO> list = new ArrayList<>();
		
		//폼
		List<DemandSurveyVO> dsvoList = dss.getDsList(userID); 
		
		for(DemandSurveyVO dsvo : dsvoList) { //49번글
			int dno = dsvo.getDno(); //49
			
			//해당 dno로 썸네일 가져오기
			DemandSurveyImgVO dsivo = dss.getThumbNailFile(dno); //49번
			
			//해당 dno로 참가수 가져오기
			int count = dss.getParticipantCount(dno);
	
			DemandSurveyParticipantDTO dto = new DemandSurveyParticipantDTO();
			dto.setDsvo(dsvo);
			dto.setDsivo(dsivo);
			dto.setParticipantCount(count);
			
			
			list.add(dto);
		}
		
		log.info("수요조사글+참여인원 >>>>>> "+list.toString());
		m.addAttribute("list", list);
		
		
		return "/user/userSellerDemandForm";
	}
	
	
	
	//=====================================================================
	//구매자 폼
	
	@GetMapping("/userDemandForm")
	public String myDemandSurveyParticipation(HttpServletRequest srq, Model m) {
		//내가 수요조사 참여한 내역 리스트...~!
		HttpSession session = srq.getSession();
		UserImgDTO user = (UserImgDTO)session.getAttribute("ses");
		String id = user.getUvo().getId(); 
		
		//내가 참여한 수요조사 가져오기(글번호, id, 참여날짜)
		List<DemandSurveyParticipationVO> dspvolist = dss.getMyParticipation(id);
		
		log.info("참여내역 "+dspvolist);
		
		List<DemandSurveyUserParticipationDTO> list = new ArrayList<DemandSurveyUserParticipationDTO>();
		
		for(DemandSurveyParticipationVO dsppvo : dspvolist) {
			int dno = dsppvo.getDno(); 
			//해당 dno의 글제목, 썸네일 가져오기
			DemandSurveyVO dsvo = dss.getDSDetail(dno);
			DemandSurveyImgVO dsivo = dss.getThumbNailFile(dno);
			
			DemandSurveyUserParticipationDTO dsupdto = new DemandSurveyUserParticipationDTO();
			dsupdto.setDsivo(dsivo);
			dsupdto.setDsppvo(dsppvo);
			dsupdto.setDsvo(dsvo);
			
			list.add(dsupdto);
			
		}
		
		log.info("내가 참여한 수요조사 목록 >>>> "+list);
		
		m.addAttribute("list", list);
	
		
		return "/user/userBuyerDemandForm";
		
	}
	
	
	//===============================================================================
	
	//상품 판매 구매자(자신이 구매한 상품 글 목록)
	
	@GetMapping("/userSaleForm")
	public String mySaleParticipationList(HttpServletRequest srq, Model m) {
		//유저가 참여한 상품 구매 폼 리스트
		//썸네일, 글제목, 구매날짜, 주문번호
		
		HttpSession session = srq.getSession();
		UserImgDTO user = (UserImgDTO)session.getAttribute("ses");
		String id = user.getUvo().getId();
		
		//내 id를 주고 내가 order에 참여한 내역 가쟈오기
		List<SaleOrdererVO> orderList = ssv.getMyParticipationList(id);
		
		//리스트를 저장할 변수
		List<SaleUserParticipationVO> list = new ArrayList<SaleUserParticipationVO>(); 
		//sno뽑아내기
		for(SaleOrdererVO sovo : orderList) {
			int sno = sovo.getSno();
			
			//해당 sno의 글제목, 썸네일, 구매날짜, 주문번호 가져오기!
			String orderDate = sovo.getOrderDate();
			int orderNo = sovo.getOrderNo();
			
			SaleVO svo = ssv.getDetail(sno);
			SaleImgVO sivo = ssv.getThumbnail(sno);
			
			SaleUserParticipationVO spvo = new SaleUserParticipationVO();
			spvo.setOrderDate(orderDate);
			spvo.setOrderNo(orderNo);
			spvo.setSivo(sivo);
			spvo.setSvo(svo);
			
			list.add(spvo);
		}
		
		m.addAttribute("list",list);
		
		return "/user/userBuyerSaleForm";
	}
	
	//default img 등록
	@GetMapping("/defaultRegister")
	public String defaultImg() {
		log.info("default img register");
		
		return "/management/defaultImg";
	}


	
}
