<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="include/header.jsp"/>

<style>
	#content{
		min-height: 850px;
	}
	
	#contentDiv{
		text-align: center !important;
	}

</style>

<div id="content">
	<c:choose>
		<c:when test="${login == null }">
      		<div class="list-group" id="contentDiv">
			  <a class="list-group-item">회원 가입 및 로그인 하세요.</a>
			</div>
      	</c:when>
      	<c:otherwise>
      		<div class="list-group" id="contentDiv">
			  <a href="#" class="list-group-item">First item</a>
			</div>
      	</c:otherwise>
	</c:choose>
	
	



</div>

<jsp:include page="include/footer.jsp"/>