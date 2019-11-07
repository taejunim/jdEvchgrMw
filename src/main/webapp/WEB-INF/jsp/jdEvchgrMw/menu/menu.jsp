<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script>

</script>
<header role="banner">
  <nav class="nav" role="navigation">
    <ul class="nav__list">
      <li>
        <input id="group-1" type="checkbox" hidden/>
        <label for="group-1"> 충전기<span> > </span></label>
        <ul class="group-list">
          <li id="btn_chgrInfo"><a href="<c:url value='/chgrInfoListView.do'/>">충전기 설치정보</a></li>
          <li id="btn_chgrStatus"><a href="<c:url value='/chgrStatusListView.do'/>">충전기 상태정보</a></li>
          <li><a href="<c:url value='/userListView.do'/>">사용자 인증요청</a></li>
          <li><a href="<c:url value='/chargingStartListView.do'/>">충전 시작정보</a></li>
          <li><a href="<c:url value='/chargingInfoListView.do'/>">충전 진행정보</a></li>
          <li><a href="<c:url value='/chargingEndListView.do'/>">충전 완료정보</a></li>
          <li><a href="<c:url value='/chargePavmentListView.do'/>">충전 결제정보</a></li>
          <li><a href="<c:url value='/sendSmsListView.do'/>">문자전송</a></li>
          <li><a href="<c:url value='/alarmHistoryListView.do'/>">경보이력</a></li>
          <li><a href="<c:url value='/reportUpateListView.do'/>">펌웨어 Update 결과 알림</a></li>
          <li><a href="<c:url value='/dChargingStartListView.do'/>">덤프_충전 시작정보</a></li>
          <li><a href="<c:url value='/dChargingEndListView.do'/>">덤프_충전 완료정보</a></li>
          <li><a href="<c:url value='/dChargePavmentListView.do'/>">덤프_충전 결제정보</a></li>
          <li><a href="<c:url value='/dAlarmHistoryListView.do'/>">덤프_경보이력</a></li>
          <li><a href="<c:url value='/dReportUpdateListView.do'/>">덤프_펌웨어 Update 결과 알림</a></li>
        </ul>
      </li>
       <li>
        <input id="group-2" type="checkbox" hidden/>
        <label for="group-2">충전정보시스템<span> > </span></label>
        <ul class="group-list">
          <li><a href="<c:url value='/resetListView.do'/>">충전기 RESET 요청</a></li>
		  <li><a href="<c:url value='/pricesListView.do'/>">단가정보</a></li>
		  <li><a href="<c:url value='/changeModeListView.do'/>">충전기 모드변경</a></li>
		  <li><a href="<c:url value='/displayBrightnessListView.do'/>">충전기 화면밝기정보</a></li>
		  <li><a href="<c:url value='/soundListView.do'/>">충전기 소리정보</a></li>
		  <li><a href="<c:url value='/askVerListView.do'/>">펌웨어 버전 정보 확인</a></li>
		  <li><a href="<c:url value='/notifyVerUpgradeListView.do'/>">펌웨어 버전 정보 Upgrade 알림</a></li>
         </ul>
      </li>
    </ul>
        
  </nav>
</header>
