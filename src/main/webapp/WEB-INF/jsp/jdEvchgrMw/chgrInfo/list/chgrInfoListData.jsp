<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/jdEvchgrMw/common/resources_common.jsp"%>
<title>충전기 설치정보</title>
<script>
$(document).ready(function(){

	alert("충전기 설치정보");
});	

function get_data() {
	
	$.ajax({
		
		url : '<c:url value="/sys/commCodeSelectAll.do"/>',
		type : 'POST',
		data : {
			cdGrp : 'CD09'
		},
		success : function(data) {
			
			$.each(data, function(i, item) {
				
				$("#searchKeyword1").append("<option value='" + item.cd + "'>"+ item.cdNm + "</option> ");
			});
		},
		error : function(request,status,error){
			
		}
	}).done(function(data) { 														
			
		$("#searchKeyword1").val('${outVO.searchKeyword1}').prop("selected", true);							//VO 값 선택 
	}); 
}

</script>
</head>
<body>

</body>
</html>