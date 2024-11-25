package com.massel.www.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.massel.www.domain.OrderStatus;
import com.massel.www.domain.SaleOrdererInfoDTO;
import com.massel.www.service.SaleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/excel")
@Controller
public class ExcelController {
	
	@Inject
	private SaleService ssv;

	@GetMapping("/orderDownload")
	public void generateOrderExcel(@RequestParam int sno ,HttpServletResponse response) throws IOException {
		
		String pendingStatus = OrderStatus.PENDING_PAYMENT.name();
		List<SaleOrdererInfoDTO> list = ssv.getOrderDetail(sno, pendingStatus);
		
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("주문목록");	//excel sheet 이름. Sheet import =  poi.ss
		//Cell cell = null;
		
		//row(행 생성) 첫번째 행은 헤더
		//헤더작성하기
		Row headerRow = sheet.createRow(0); //0번째 행
		headerRow.createCell(0).setCellValue("주문번호");
		headerRow.createCell(1).setCellValue("주문상태");
		headerRow.createCell(2).setCellValue("주문날짜");
		headerRow.createCell(3).setCellValue("아이디");
		headerRow.createCell(4).setCellValue("이름");
		headerRow.createCell(5).setCellValue("이메일");
		headerRow.createCell(6).setCellValue("연락처");
		headerRow.createCell(7).setCellValue("수령자 이름");
		headerRow.createCell(8).setCellValue("수령자 연락처");
		headerRow.createCell(9).setCellValue("수령자 우편번호");
		headerRow.createCell(10).setCellValue("수령자 주소");
		headerRow.createCell(11).setCellValue("배송방법");
		headerRow.createCell(12).setCellValue("주문상품");
		headerRow.createCell(13).setCellValue("환불계좌");
		headerRow.createCell(14).setCellValue("환불은행/예금주");
		headerRow.createCell(15).setCellValue("총가격");
		headerRow.createCell(16).setCellValue("배송메모");
		
		//두번째 행 . 데이터
		int rowNum = 1;
		for(SaleOrdererInfoDTO order : list) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(order.getOrderNo());
			row.createCell(1).setCellValue(order.getOrderStatus());
			row.createCell(2).setCellValue(order.getOrderDate());
			row.createCell(3).setCellValue(order.getId());
			row.createCell(4).setCellValue(order.getOrdererName());
			row.createCell(5).setCellValue(order.getOrdererEmail());
			row.createCell(6).setCellValue(order.getOrdererPhone());
			row.createCell(7).setCellValue(order.getRecipientName());
			row.createCell(8).setCellValue(order.getRecipientPhoneNumber());
			row.createCell(9).setCellValue(order.getRecipientPostalCode());
			row.createCell(10).setCellValue(order.getRecipientAddress()+" / "+order.getRecipientDetailAddress());
			row.createCell(11).setCellValue(order.getShippingMethod());
			row.createCell(12).setCellValue(order.getProducts());
			row.createCell(13).setCellValue(order.getRefundAccount());
			row.createCell(14).setCellValue(order.getRefundBank()+" / "+order.getRefundAccountHolder());
			row.createCell(15).setCellValue(order.getTotalPrice());
			row.createCell(16).setCellValue(order.getShippingNote());
			
		}
		
		//타입, 파일명 지정하기
		String fileName = "MASSEL_주문내역";
		fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
		//response.setContentType("ms-vnd/excel"); 이건 xls형식임
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); //.xlsx 파일생성하는 경우
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
		workbook.write(response.getOutputStream());
		workbook.close();
		
	}
	
}
