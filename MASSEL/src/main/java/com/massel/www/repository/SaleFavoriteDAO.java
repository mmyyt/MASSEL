package com.massel.www.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.massel.www.domain.SaleFavoriteVO;

public interface SaleFavoriteDAO {

	int insertFavorite(@Param("userID")String userID, @Param("sno")int sno);

	int deleteFavorite(@Param("userID")String userID,@Param("sno")int sno);

	int isFavorite(@Param("sno")int sno,@Param("userID")String userID);

	List<SaleFavoriteVO> myFavoriteList(String userId);

}
