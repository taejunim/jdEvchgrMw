<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/jdEvchgrMw/common/resources_common.jsp"%>
<title>경보이력</title>
<script>
$(document).ready(function(){
	fn_init();
	$("input:checkbox[id='group-1']").prop("checked", true);
	$( "#btn_search" ).click(function() {
		fn_selectList();
	});
	$( "#btn_refresh" ).click(function() {
		location.href = "<c:url value='/alarmHistoryListView.do'/>";
	});
	

});	
//페이지 이동 스크립트
function fn_page(pageNo){
	frm.pageIndex.value = pageNo;
	document.frm.action = '<c:url value="/alarmHistoryListView.do"/>';
	document.frm.submit();
}
// 목록 조회
function fn_selectList() {	
	frm.pageIndex.value = 1;     
	document.frm.action = "<c:url value='/alarmHistoryListView.do'/>";
	document.frm.submit();
}
function fn_init() {
	
	var srtDate = '${chgrInfoVO.srtDate}';
	var endDate = '${chgrInfoVO.endDate}';
	var msgSendType = '${chgrInfoVO.msgSendType}';
	
	if(msgSendType != ""){
		$("#msgSendType").val(msgSendType);
	}
	
	if(srtDate != ""){
		$("#srtDate").val(srtDate);
	}
	if(endDate != ""){
		$("#endDate").val(endDate);
	}
}	   
</script>
</head>
<body>

<div class="page-header">
  <h1>ALARM HISTORY<small> MESSAGE LIST</small></h1>
</div>
<div class = "body">
	<%@include file="/WEB-INF/jsp/jdEvchgrMw/menu/menu.jsp"%>	
	<form:form id="frm" name="frm" method="post" autocomplete="off">
	<input type="hidden" id="msgActionType" name="msgActionType" value="alarmHistory">
	<input type="hidden" id="pageIndex" name="pageIndex" value="${chgrInfoVO.pageIndex}">
	<div class = "pl5">
		<table class="table">
			<colgroup>
				<col width="10%">
				<col width="80%">
				<col width="10%">
			</colgroup>
			<thead class="thead-dark"><tr>
				<th scope="col">전문 구분</th>
				<th scope="col">등록 일자</th>
				<th scope="col"></th>
			</tr></thead>
			<tbody><tr>
				<td><select id="msgSendType" name = "msgSendType">
						<option value = "" selected>전체</option>
						<option value = "req">req</option>
						<option value = "res">res</option>
				</select></td>
				<td><input type="text" class="datepicker srtDate" name="srtDate" id="srtDate" >
					&nbsp;&nbsp;~&nbsp;&nbsp; <input type="text" class="datepicker endDate" name="endDate" id="endDate"></td>
				<td><input type ="button" id = "btn_refresh" value ="새로고침"/>&nbsp;&nbsp;<input type ="button" id = "btn_search" value ="검색"/></td>
			</tr></tbody>
		</table>	
		<table class="table">
			<colgroup>
			<col width="5%">
			<col width="7%">
			<col width="10%">
			<col width="">
			<col width="10%">
			</colgroup>
 			 <thead class="thead-dark">
   			 <tr>
      			<th scope="col">NO</th>
     			<th scope="col">전문 구분</th>
     			<th scope="col">명령 구분</th>
      			<th scope="col">MSG_DATA</th>
      			<th scope="col">등록 일자</th>
   			 </tr>
  			</thead>
  			<tbody id="data_list">
  			<c:forEach var="resultList" items="${resultList}" varStatus="status">
			<c:set var="index" value="${paginationInfo.totalRecordCount - ((paginationInfo.currentPageNo-1) * paginationInfo.recordCountPerPage + status.index) }"/>
			 	<tr>
			 		<td>${paginationInfo.totalRecordCount - 10 * (chgrInfoVO.pageIndex-1) - status.index}</td>
			 		<td>${resultList.msgSendType}</td>
			 		<td>${resultList.msgActionType}</td>
			 		<td>${resultList.msgData}</td>
			 		<td>${resultList.regDt}</td>
			 	</tr>
			</c:forEach>
 			 </tbody>
		</table>
	<div id="pagination" class="pagingBox align_c">
		<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="fn_page"/>
	</div>
	</div>
	</form:form>
</div>
</body>
</html>