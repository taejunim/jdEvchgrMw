<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/jdEvchgrMw/common/resources_common.jsp"%>
<title>충전기 설치정보</title>
<script>
$(document).ready(function(){

	get_data();
});	
//페이지 이동 스크립트
function fn_page(pageNo){
	frm.pageIndex.value = pageNo;
	document.frm.action = '<c:url value="/chgrInfoListView.do"/>';
	document.frm.submit();
}
function get_data() {
	
 	$.ajax({
		url : '<c:url value="/chgrInfoListData.do"/>',
		type : 'POST',
		data : {},
		success : function(data) {},
		error : function(request,status,error){}
	}).done(function(data) { 														
			
		var dataHtml = "";
		if(data == null || data == "")
			dataHtml = "<tr><td colspan='3' align='center' >- 데이터가 존재하지 않습니다. -</td></tr>";
		/*
			dataHtml += "<c:forEach var='resultList' items='${resultList}' varStatus='status'>";
			dataHtml += "<c:set var='index' value='${paginationInfo.totalRecordCount - ((paginationInfo.currentPageNo-1) * paginationInfo.recordCountPerPage + status.index)}'/>";
			dataHtml += "<tr id='${(status.count)}' ><input type='hidden' value = '${resultList.msgDataSeq}'><th scope='row'>${paginationInfo.totalRecordCount - 10 * (chgrInfoVO.pageIndex-1)-status.index}</th><td class='txt_center'>${resultList.msgSendType}</td><td class='txt_center'>${resultList.msgActionType}</td>";
			dataHtml += "<td class='txt_center'></td><td class='txt_center'>${resultList.regDt}</td></tr>";
		*/
			$.each(data, function(i, item) {
				dataHtml += "<c:forEach var='resultList' items='${resultList}' varStatus='status'>";
			dataHtml += "<c:set var='index' value='${paginationInfo.totalRecordCount - ((paginationInfo.currentPageNo-1) * paginationInfo.recordCountPerPage + status.index)}'/>";
			dataHtml += "<tr id='${(status.count)}' ><input type='hidden' value = '" + item.msgDataSeq + "'><th scope='row'>${paginationInfo.totalRecordCount - 10 * (chgrInfoVO.pageIndex-1)+index}</th><td class='txt_center'>"+item.msgSendType+"</td><td class='txt_center'>"+item.msgActionType+"</td>";
			dataHtml += "<td class='txt_center'>"+item.msgData+"</td><td class='txt_center'>"+item.regDt+"</td></tr>";
			dataHtml += "</c:forEach>";
			});
			/*dataHtml += "</c:forEach>";*/
		
		$("#data_list").html(dataHtml);
	});  
}

</script>
</head>
<body>

<div class="page-header">
  <h1>CHGR INFO<small> MESSAGE LIST</small></h1>
</div>
	<%@include file="/WEB-INF/jsp/jdEvchgrMw/menu/menu.jsp"%>	
	<form:form id="frm" name="frm" method="post" autocomplete="off">
	<input type="hidden" id="pageIndex" name="pageIndex" value="${chgrInfoVO.pageIndex}">
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
 			 </tbody>
		</table>
	</form:form>
</body>
</html>