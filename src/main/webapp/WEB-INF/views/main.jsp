<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="include/header.jsp"/>

<style>
	#content{
		min-height: 850px;
	}
	
	#contentDiv{
		text-align: center !important;
	}

</style>
<script>
	$(function(){
		$(".delBtn").click(function(e){
			e.preventDefault();
			$(this).parents("form").submit();
		})
	})
	

</script>
<div id="content">
	<div class="list-group" id="contentDiv">
	<c:choose>
		<c:when test="${login == null }">
		  <a class="list-group-item">회원 가입 및 로그인 하세요.</a>
      	</c:when>
      	<c:otherwise>
      		<c:choose>
      			<c:when test="${images != null }">
      				<div class="list-group-item">
      					<a href="${pageContext.request.contextPath }/addPhoto?num=${sessionScope.login.num}">Add Photo</a>
      				</div>
      				<c:forEach var="image" items="${images.images }">
      					<form action="delPhoto" method="post">
	      					<div class="list-group-item">
						 		<div class="w3-card-4">
							  		<a href="displayFile?filename=${image.filepath }&check=true"><img src="displayFile?filename=${image.filepath }&check=false"></a>
							  		 <div class="w3-container w3-center">
							  		 	<input type="hidden" value="${num }" name="num">
							  		 	<input type="hidden" value="${image.filepath }" name="target">
							  		 	<p>${image.originalFile }</p>
							  		 	<p><fmt:formatDate pattern="yyyy-MM-dd" value="${image.regdate }"/> </p>
									    <input type="submit" class="btn btn-danger delBtn" value="삭제">
									  </div>
								</div>
							</div>
						</form>
      				</c:forEach>
      			</c:when>
      			<c:otherwise>
      				<div class="list-group-item">
      					<a href="${pageContext.request.contextPath }/addPhoto?num=${sessionScope.login.num}">Add Photo</a>
      				</div>
      			</c:otherwise>
      		</c:choose>
       	</c:otherwise>
	</c:choose>
	</div>
</div>

<jsp:include page="include/footer.jsp"/>