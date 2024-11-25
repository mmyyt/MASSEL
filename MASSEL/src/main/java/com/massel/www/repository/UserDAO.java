package com.massel.www.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.massel.www.domain.UserImgVO;
import com.massel.www.domain.UserVO;

public interface UserDAO {

	UserVO getUser(String id);

	int userSignUp(UserVO uvo);

	int updateUser(UserVO uvo);

	int deleteUser(String loginID);

	List<UserVO> getUserNickname(String keyword);

	int idCheck(String id);

	int nicknameCheck(String nickname);

	int emailCheck(String email);

	int updatePw(@Param("id")String id,@Param("encPw")String encPw);

	int updateNick(@Param("id")String id,@Param("nickname")String nickname);


}
