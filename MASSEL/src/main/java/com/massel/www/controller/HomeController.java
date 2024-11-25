package com.massel.www.controller;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.massel.www.domain.DemandSurveyImgDTO;
import com.massel.www.domain.SaleImgDTO;
import com.massel.www.domain.SaleVO;
import com.massel.www.domain.UserDemandDTO;
import com.massel.www.domain.UserImgVO;
import com.massel.www.domain.UserSaleDTO;
import com.massel.www.service.DemandSurveyService;
import com.massel.www.service.SaleService;
import com.massel.www.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {

	@Inject
	private SaleService ssv;
	
	@Inject
	private DemandSurveyService dsv;
	
	@Inject
	private UserService usv;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
	
		LocalDate now = LocalDate.now();
		int month = now.getMonthValue();
		int day = now.getDayOfMonth();
		
		model.addAttribute("month", month);
		model.addAttribute("day",day);
		
		
		List<SaleImgDTO> popularList = ssv.getPopularList();
		List<UserImgVO> popularUivoList = new ArrayList<UserImgVO>();
		for(SaleImgDTO sidto : popularList) {
			String userId = sidto.getSvo().getSwriter();
			UserImgVO uivo = usv.getUserImg(userId);
			
			popularUivoList.add(uivo);
		}
	
		log.info("조회수 순 >> "+popularList);
		model.addAttribute("popularList",popularList);
		model.addAttribute("popularUivoList",popularUivoList);
		
		
		
		//찜순위 높은 상품
		List<SaleImgDTO> favoriteSaleList = ssv.getFavoriteList();
		
		List<UserSaleDTO> favoriteList = new ArrayList<UserSaleDTO>();
			
		log.info("찜순 >> "+favoriteSaleList);
		
		for(SaleImgDTO sidto : favoriteSaleList) {
			String userId = sidto.getSvo().getSwriter();
			UserImgVO uivo = usv.getUserImg(userId);

			favoriteList.add(new UserSaleDTO(sidto, uivo));
		}

		model.addAttribute("favoriteList",favoriteList);
		
		
		
		//최신순
		List<SaleImgDTO> latestSaleList = ssv.getLatestList();
		
		List<UserSaleDTO> latestList = new ArrayList<UserSaleDTO>();
		
		log.info("최신순 >> "+latestList);
		for(SaleImgDTO sidto : latestSaleList) {
			String userId = sidto.getSvo().getSwriter();
			UserImgVO uivo = usv.getUserImg(userId);
			
			latestList.add(new UserSaleDTO(sidto, uivo));
		}

		model.addAttribute("latestList",latestList);
		
		//수요조사 최신순
		List<DemandSurveyImgDTO> demandList = dsv.getDemandList();
		List<UserDemandDTO> demandDTOList = new ArrayList<UserDemandDTO>();
		
		log.info("수요조사 최신순 >>"+demandList);
		for(DemandSurveyImgDTO dsidto : demandList) {
			String userId = dsidto.getDsvo().getId();
			UserImgVO uivo = usv.getUserImg(userId);
			
			demandDTOList.add(new UserDemandDTO(dsidto, uivo));
		}

		model.addAttribute("demandList",demandDTOList);
		
		return "home";
	}
	
}
