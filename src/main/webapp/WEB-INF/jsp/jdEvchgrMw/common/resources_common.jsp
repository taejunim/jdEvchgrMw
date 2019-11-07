<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>	
<meta http-equiv="Content-Type"/>

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jdEvchgrMw/main.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jdEvchgrMw/table.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jdEvchgrMw/menu.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jdEvchgrMw/button/pagination.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/jdEvchgrMw/jquery-ui.css">
<!-- Fontawesom -->
<link rel="stylesheet" type="text/css" href="https://use.fontawesome.com/releases/v5.1.0/css/all.css" >

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

<!-- jquery SETTING -->
<script src="https://code.jquery.com/jquery-1.8.3.js"></script>
<script src="https://code.jquery.com/ui/1.10.0/jquery-ui.js"></script>

<!-- js -->
<script src="<%=request.getContextPath()%>/js/json.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.modal.js"></script>
<script src="<%=request.getContextPath()%>/js/jquery.datepicker.js"></script>

<script type="text/javascript">
	
	var contextPath = '${pageContext.request.contextPath}';
	
	$.ajaxSetup({cache:false});	//아작스 2번호출을 위한 캐쉬 설정
	
</script>