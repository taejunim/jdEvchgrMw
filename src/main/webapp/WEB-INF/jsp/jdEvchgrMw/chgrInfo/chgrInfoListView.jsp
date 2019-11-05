<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/jdEvchgrMw/common/resources_common.jsp"%>
<title>충전기 설치정보</title>
<script>
$(document).ready(function(){

	get_data();
});	

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
			dataHtml = "<tr><td colspan='3' align='center' >- 데이터가 존재하지 않습니다. -</td></tr>"
		
		$.each(data, function(i, item) {
			
			dataHtml += "<tr><th scope='row'>"+item.msgDataSeq+"</th><td class='txt_center'>"+item.msgSendType+"</td><td class='txt_center'>"+item.msgActionType+"</td>";
			dataHtml += "<td class='txt_center'>"+item.msgData+"</td><td class='txt_center'>"+item.regDt+"</td></tr>";
			
		});
		
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
      <th scope="col">SEQ.</th>
      <th scope="col">전문 구분</th>
      <th scope="col">명령 구분</th>
      <th scope="col">MSG_DATA</th>
      <th scope="col">등록 일자</th>
    </tr>
  </thead>
  <tbody id="data_list">
  </tbody>
</table>

<div>

</div>
</body>
</html>