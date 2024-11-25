package com.massel.www.repository;

import com.massel.www.domain.UserImgVO;

public interface UserImgDAO {

	int insertUserImg(UserImgVO uivo);

	UserImgVO getUserImg(String id);

	int updateUserImg(UserImgVO uivo);

	int insertImg(UserImgVO uivo);

}
