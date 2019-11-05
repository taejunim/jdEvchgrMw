package jdEvchgrMw.common.service;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @Class Name : ComDefaultVO.java
 * @Description : ComDefaultVO class
 * @Modification Information
 * @ @ 수정일 수정자 수정내용 @ ------- -------- --------------------------- @ 2009.02.01 조재영 최초 생성
 *
 * @author 공통서비스 개발팀 조재영
 * @since 2009.02.01
 * @version 1.0
 * @see
 *
 */
@SuppressWarnings("serial")
public class PageVO implements Serializable {
	
  /** 현재페이지 */
  private int pageIndex = 1;

  /** 페이지갯수 */
  private int pageUnit = 10;

  /** 페이지사이즈 */
  private int pageSize = 10;

  /** firstIndex */
  private int firstIndex = 1;

  /** lastIndex */
  private int lastIndex = 1;

  /** recordCountPerPage */
  private int recordCountPerPage = 10;
  
  /** DB에서의 현재 페이지*/
  private int pageRowIndex = 0;
  
  /** 검색조건 */
  private String searchCondition = "";

  /** 검색Keyword */
  private String searchKeyword = "";
  
  /** 검색KeywordFrom */
  private String searchKeywordFrom = "";

  /** 검색KeywordTo */
  private String searchKeywordTo = "";
  
  private String srtDate;
  private String endDate;
  /** 검색사용여부 */
  private String searchUseYn = "";

  private String searchKeyword1 = "";
  private String searchKeyword2 = "";
  private String searchKeyword3 = "";
  private String searchKeyword4 = "";
  private String searchKeyword5 = "";
  
  public String getSrtDate() {
	return srtDate;
  }

  public void setSrtDate(String srtDate) {
	this.srtDate = srtDate;
  }

public String getEndDate() {
	return endDate;
}

public void setEndDate(String endDate) {
	this.endDate = endDate;
}

public String getSearchKeyword() {
	return searchKeyword;
}

public void setSearchKeyword(String searchKeyword) {
	this.searchKeyword = searchKeyword;
}

public String getSearchUseYn() {
	return searchUseYn;
}

public void setSearchUseYn(String searchUseYn) {
	this.searchUseYn = searchUseYn;
}

  public int getPageRowIndex() {
	return pageRowIndex;
 }

 public void setPageRowIndex(int pageRowIndex) {
	this.pageRowIndex = pageRowIndex;
 }

  public int getFirstIndex() {
    return firstIndex;
  }

  public void setFirstIndex(int firstIndex) {
    this.firstIndex = firstIndex;
  }

  public int getLastIndex() {
    return lastIndex;
  }

  public void setLastIndex(int lastIndex) {
    this.lastIndex = lastIndex;
  }

  public int getRecordCountPerPage() {
    return recordCountPerPage;
  }

  public void setRecordCountPerPage(int recordCountPerPage) {
    this.recordCountPerPage = recordCountPerPage;
  }

  public String getSearchCondition() {
    return searchCondition;
  }

  public void setSearchCondition(String searchCondition) {
    this.searchCondition = searchCondition;
  }

  public String getSearchKeyword1() {
    return searchKeyword1;
  }

  public void setSearchKeyword1(String searchKeyword1) {
    this.searchKeyword1 = searchKeyword1;
  }

  public String getSearchKeyword2() {
    return searchKeyword2;
  }

  public void setSearchKeyword2(String searchKeyword2) {
    this.searchKeyword2 = searchKeyword2;
  }

  public String getSearchKeyword3() {
    return searchKeyword3;
  }

  public void setSearchKeyword3(String searchKeyword3) {
    this.searchKeyword3 = searchKeyword3;
  }

  public String getSearchKeyword4() {
    return searchKeyword4;
  }

  public void setSearchKeyword4(String searchKeyword4) {
    this.searchKeyword4 = searchKeyword4;
  }

  public String getSearchKeyword5() {
    return searchKeyword5;
  }

  public void setSearchKeyword5(String searchKeyword5) {
    this.searchKeyword5 = searchKeyword5;
  }


  public int getPageIndex() {
    return pageIndex;
  }

  public void setPageIndex(int pageIndex) {
    this.pageIndex = pageIndex;
  }

  public int getPageUnit() {
    return pageUnit;
  }

  public void setPageUnit(int pageUnit) {
    this.pageUnit = pageUnit;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }


  /**
   * searchKeywordFrom attribute를 리턴한다.
   *
   * @return String
   */
  public String getSearchKeywordFrom() {
    return searchKeywordFrom;
  }

  /**
   * searchKeywordFrom attribute 값을 설정한다.
   *
   * @param searchKeywordFrom String
   */
  public void setSearchKeywordFrom(String searchKeywordFrom) {
    this.searchKeywordFrom = searchKeywordFrom;
  }

  /**
   * searchKeywordTo attribute를 리턴한다.
   *
   * @return String
   */
  public String getSearchKeywordTo() {
    return searchKeywordTo;
  }

  /**
   * searchKeywordTo attribute 값을 설정한다.
   *
   * @param searchKeywordTo String
   */
  public void setSearchKeywordTo(String searchKeywordTo) {
    this.searchKeywordTo = searchKeywordTo;
  }

  public void setPageVO(PageVO pageVO) {
    this.pageIndex = pageVO.pageIndex;
    this.searchCondition = pageVO.getSearchCondition();
    this.pageUnit = pageVO.pageUnit;
    this.pageSize = pageVO.pageSize;
    this.firstIndex = pageVO.firstIndex;
    this.lastIndex = pageVO.lastIndex;
    this.recordCountPerPage = pageVO.recordCountPerPage;
  }
}
