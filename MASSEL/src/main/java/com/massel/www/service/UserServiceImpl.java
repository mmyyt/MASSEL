package com.massel.www.service;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.massel.www.domain.UserImgDTO;
import com.massel.www.domain.UserImgVO;
import com.massel.www.domain.UserVO;
import com.massel.www.repository.UserDAO;
import com.massel.www.repository.UserImgDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
	
	@Inject
	private UserDAO udao;
	
	@Inject
	private BCryptPasswordEncoder pwEncoder;
	
	@Inject
	private UserImgDAO uidao;
	
	@Override
	public int signUp(UserImgDTO userDto) {

		log.info("회원가입 service 진행중...");
		
		//넘어온 객체의 id가 중복인지 확인
		UserVO user = udao.getUser(userDto.getUvo().getId());
		
		//해당 id를 가진 유저가 이미 존재
		if(user!=null) {
			return 0;
		}
		
		if(userDto.getUvo().getId() == null || userDto.getUvo().getId() == "" || userDto.getUvo().getId().length() == 0) {
			return 0;
		}
		
		if(userDto.getUvo().getPw() == null || userDto.getUvo().getPw().length() == 0 ) {
			return 0;
		}
		
		//비밀번호 암호화
		String pw = userDto.getUvo().getPw();
		log.info("가져온 비밀번호 >>> "+pw);
		
		String encodePw = pwEncoder.encode(pw);
		log.info("암호화한 비밀번호 >>> "+encodePw);
		
		userDto.getUvo().setPw(encodePw);
		
		int isOk = udao.userSignUp(userDto.getUvo());
		
		if(isOk>0) {
			userDto.getUivo().setId(userDto.getUvo().getId());
			isOk = uidao.insertUserImg(userDto.getUivo());
			
			log.info((isOk>0)?"회원가입 성공":"회원가입 실패");
		}

		return isOk;
	}
	
	//이미지가 없는 경우 회원가입
	@Override
	public int signUpWithoutImg(UserVO uvo) {
		//넘어온 객체의 id가 중복인지 확인
		UserVO user = udao.getUser(uvo.getId());
					
		//해당 id를 가진 유저가 이미 존재
		if(user!=null) {
			return 0;
		}
					
		if(uvo.getId() == null || uvo.getId() == "" || uvo.getId().length() == 0) {
			return 0;
		}
					
		if(uvo.getPw() == null || uvo.getPw().length() == 0 ) {
			return 0;
		}
					
		//비밀번호 암호화
		String pw = uvo.getPw();
		log.info("가져온 비밀번호 >>> "+pw);
					
		String encodePw = pwEncoder.encode(pw);
		log.info("암호화한 비밀번호 >>> "+encodePw);
					
		uvo.setPw(encodePw);
					
		int isOk = udao.userSignUp(uvo);

		log.info((isOk>0)?"회원가입 성공":"회원가입 실패");


		return isOk;
	}
	
	

	//====================================================
	//로그인
	
	@Override
	public UserVO isUser(String id, String pw) {
		log.info("로그인 service");
		
		//해당 아이디의 유저가 존재하는지 확인
		UserVO user = udao.getUser(id);
		
		//해당 유저가 존재하지 않으면 null 반환
		if(user == null) {
			return null;		
		}
		
		//pw와 암호화된 비밀번호가 맞는지 확인
		if(pwEncoder.matches(pw, user.getPw())) {
			return user;
		}else {
			return null;
		}
		
	}
	
	//=======================================================
	//유저 정보 수정

	@Override
	public int updateUser(UserVO uvo) {
		
		//비밀번호가 빈칸이면 원래 사용하던걸로 
		if(uvo.getPw().matches("")) {
			UserVO user = udao.getUser(uvo.getId());
			uvo.setPw(user.getPw());
		}else {
			//비밀번호 암호화ㅣ
			String pw = uvo.getPw();
			String encodePw = pwEncoder.encode(pw);
			
			uvo.setPw(encodePw);
		}
		
		int isOk = udao.updateUser(uvo);
		return isOk;
	}

	//==================================================
	//회원탈퇴
	
	@Override
	public int deleteUser(String loginID) {
		log.info("회원탈퇴 service");
		return udao.deleteUser(loginID);
	}

	//==========================================
	//chat을 위해 상대방 유저의 정보 가져옴
	@Override
	public UserVO getUser(String targetUser) {
		
		return udao.getUser(targetUser);
	}

	@Override
	public List<UserVO> getUserSearchList(String keyword) {
		log.info("유저 닉네임 검색!");
		return udao.getUserNickname(keyword);
	}

	//아이디중복확인
	@Override
	public int idCheck(String id) {
		int result = udao.idCheck(id);
		return result;
	}

	//닉네임중복확인
	@Override
	public int nicknameCheck(String nickname) {
		log.info(">>>> nickname check service >>>>");
		int result = udao.nicknameCheck(nickname);
		return result;
	}

	//이메일 중복확인
	@Override
	public int emailCheck(String email) {
		log.info(">>>> email check service >>>> ");
		int result = udao.emailCheck(email);
		return result;
	}

	@Override
	public UserImgVO getUserImg(String id) {
		log.info("get user img service >>> ");
		return uidao.getUserImg(id);
	}

	@Override
	public int updatePw(String id, String pw) {

		String encPw = pwEncoder.encode(pw);
		
		//id가지고 유저 가져오기
		UserVO user = udao.getUser(id);
		user.setPw(encPw);
		
		int isOk = udao.updatePw(id,encPw);
		
		log.info((isOk>0)?"비밀번호 변경 성공":"비밀번호 변경 실패");
		
		return isOk;
	}

	@Override
	public int updateNick(String id, String nickname) {
		
		UserVO user = udao.getUser(id);
		user.setNickname(nickname);
		
		int isOk = udao.updateNick(id, nickname);
		log.info((isOk>0)?"닉네임 변경 성공":"닉네임변경실패");
		return isOk;
	}

	@Override
	public int updateUserImg(UserImgVO uivo) {
		int isOk = uidao.updateUserImg(uivo);
		return isOk;
	}


	@Override
	public int insertImg(UserImgVO uivo) {
		
		return uidao.insertImg(uivo);
	}



}
