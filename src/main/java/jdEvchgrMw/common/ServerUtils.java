package jdEvchgrMw.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;

/**
 * @ Class Name  : ServerUtils.java
 * @ Description : 톰캣 구동시 시작되는 메소드
 * @ author :
 * @ since : 2020-03-23 16:33
 * @
 * @ Modification Information
 * @ 수정일      		   수정자           수정 내용
 * @ -------------     ----------     -------------------------------
 * @ 2020-03-23        임태준               최초 생성
 * @ version : 1.0.0
 * @ see
 * Copyright (C) by MetisInfo All right reserved.
 **/

public class ServerUtils implements ServletContextListener {

    Logger logger = LogManager.getLogger(ServerUtils.class);

    CollectServiceBean csb = new CollectServiceBean();

    //충전기 리스트
    public static ArrayList<String> chgrList = new ArrayList<>();

    // 웹 어플리케이션 종료 메소드
    public void contextDestroyed(ServletContextEvent arg0) {
        // 웹 어플리케이션 종료 시 처리할 로직..
        // 필자는 세션 로그인 관리 기능을 이용하여 시스템 종료 시 로그아웃 일자를 업데이트 하도록 정의 하였다.
    }

    // 웹 어플리케이션 시작 메소드
    public void contextInitialized(ServletContextEvent arg0) {
        // 웹 어플리케이션 시작 시 처리할 로직..

        //톰캣 실행시 DB에 등록된 충전기 검색 => 이후 데이터 수신시 유효한 충전기인지 체크
        try {

            chgrList = csb.beanChgrInfoService().chgrList();

            for (int i=0; i<chgrList.size(); i++) {
                logger.info("chgr : " + chgrList.get(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}