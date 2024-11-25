package com.massel.www.service;

import java.util.List;

import com.massel.www.domain.UserImgDTO;
import com.massel.www.domain.UserImgVO;
import com.massel.www.domain.UserVO;

public interface UserService {

	int signUp(UserImgDTO userDto);

	UserVO isUser(String id, String pw);

	int updateUser(UserVO uvo);

	int deleteUser(String loginID);

	//chat을 위해 상대방 유저 정보 가져오기
	UserVO getUser(String targetUser);

	//유저검색
	List<UserVO> getUserSearchList(String keyword);
	
	//아이디 중복검색
	int idCheck(String id);
	//닉네임 중복검색
	int nicknameCheck(String nickname);
	//이메일 중복검색
	int emailCheck(String email);
	
	//유저 이미지가져오기
	UserImgVO getUserImg(String id);

	//유저 비밀번호 변경
	int updatePw(String id, String pw);

	int updateNick(String id, String nickname);

	int updateUserImg(UserImgVO uivo);

	int insertImg(UserImgVO uivo);

	int signUpWithoutImg(UserVO uvo);



}
