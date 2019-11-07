<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<body>
		<table class="table">
			<colgroup>
				<col width="">
				<col width="60%">
				<col width="20%">
			</colgroup>
			<thead class="thead-dark"><tr>
				<th scope="col">전문 구분</th>
				<th scope="col">등록 일자</th>
				<th scope="col"></th>
			</tr></thead>
			<tbody><tr>
				<td class="align_c"><select id="msgSendType" name = "msgSendType">
						<option value = "" selected>전체</option>
						<option value = "req">req</option>
						<option value = "res">res</option>
				</select></td>
				<td class="align_c"><input type="text" class="datepicker srtDate" name="srtDate" id="srtDate" >
					&nbsp;&nbsp;~&nbsp;&nbsp; <input type="text" class="datepicker endDate" name="endDate" id="endDate"></td>
				<td class="align_r"><button type ="button" id = "btn_refresh" class="wd100"><i class="fa fa-sync"></i>새로고침</button>&nbsp;&nbsp;<button type ="button" id = "btn_search" class="wd100"><i class="fa fa-search"></i>조회</button></td>			</tr></tbody>
		</table>	
</body>
</html>