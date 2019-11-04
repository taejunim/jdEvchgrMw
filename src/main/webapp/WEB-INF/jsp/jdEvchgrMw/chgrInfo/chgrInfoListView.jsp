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
			
			console.log("item: "+item.chgrInfoMsg);
			
			dataHtml += "<tr><th scope='row'>"+item.chgrInfoSeq+"</th><td>"+item.chgrInfoMsg+"</td><td class='txt_center'>"+item.regDt+"</td></tr>";
			
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
<table class="table">
	<colgroup>
		<col width="5%">
		<col width="">
		<col width="20%">
	</colgroup>
  <thead class="thead-dark">
    <tr>
      <th scope="col">SEQ.</th>
      <th scope="col">MESSAGE</th>
      <th scope="col">등록 일자</th>
    </tr>
  </thead>
  <tbody id="data_list">
  </tbody>
</table>
</body>
</html>