<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script>
	$(function(){
		$("#idCheckBtn").click(function(){
			var idPattern = /[a-zA-Z0-9]{6,15}/;
			var id = $("#signId").val();			
			var idCheck = idPattern.test(id);
			
			if(!idCheck){
				alert("아이디를 올바르게 입력하세요.");
				$("#signId").focus();
				return;
			}
			
			if(id == ""){
				alert("ID를 입력하세요.");
				return;
			}
			
			$.ajax({
				url: "checkid/" + id,
				type: "get",
				dataType : "text",
				success : function(result){
					if(result == "exist"){
						alert("이미 존재하는 아이디입니다.");
						$("#signId").val("");
					}else{
						alert("사용 가능한 아이디입니다.");
						$("#tel").focus();
						$("#signId").attr("readonly", "readonly");
					}
				}
				
			})
		})
		
		
		$("#signBtn").click(function(e){
			e.preventDefault();
		
			var idPattern = /[a-zA-Z0-9]{6,15}/;
			var pwPattern = /[a-zA-Z0-9~!@#$%^&*]{8,20}/;
			var namePattern = /^[가-힣]{2,5}$/;
			var telPattern = /^[0-1]{3}-[0-9]{3,4}-[0-9]{3,4}$/;

			var name = $("#name").val();
			var id = $("#signId").val();
			var tel  = $("#tel").val();
			var email = $("#email").val();
			var pw = $("#signPw").val();
			var checkPw = $("#pwCheck").val();
									
			var idCheck = idPattern.test(id);
			var pwCheck = pwPattern.test(pw);
			var nameCheck = namePattern.test(name);
			var telCheck = telPattern.test(tel);
			var css = $("#signId").attr("readonly");
			
			if(!nameCheck){
				alert("이름를 올바르게 입력하세요.");
				$("#name").focus();
				return;
			}
			
			if(!idCheck){
				alert("아이디를 올바르게 입력하세요.");
				$("#signId").focus();
				return;
			}
			
			if(!telCheck){
				alert("전화번호를 올바르게 입력하세요.");
				$("#tel").focus();
				return;
			}
			
			if(!pwCheck){
				alert("비밀번호를 올바르게 입력하세요.");
				$("#signPw").focus();
				return;
			}
			
			if(pw != checkPw){
				alert("비밀번호를 동일하게 입력하세요.");
				$("#pwCheck").focus();
				return;
			}
			
			if(css == undefined){ 
				alert("아이디 체크를 해주세요.");
				$("#signId").focus();
				return;
			}
			
			$("#signForm").submit();

		})
		
		
		$("#loginBtn").click(function(e){
			e.preventDefault();
			
			var id = $("#id").val();
			var pw = $("#pw").val();

			$.ajax({
				url : "loginCheck?id=" + id + "&pw=" + pw,
				type: "get",
				dataType : "text",
				success : function(result){
					if(result == "correct ID, PW"){
						$("#loginForm").submit();
					}else if(result == "not exist ID"){
						alert("존재하지 않는 아이디입니다.");
					}else if(result == "non-correct PW"){
						alert("비밀번호가 일치하지 않습니다.");
					}
				}
					
			})
			
			
		})
		
				
	})

</script>
<style>
	#idCheckBtn{
		margin-top: 1%;
	}
</style>
</head>
<body>

<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand" href="#">PhotoManager</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li><a href="#">Home</a></li>
      </ul>
      
      <c:choose>
      	<c:when test="${login == null }">
      		<ul class="nav navbar-nav navbar-right">
		        <li><a href="#signModal" data-toggle="modal"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
		        <li><a href="#loginModal" data-toggle="modal"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
      		</ul>
      	</c:when>
      	<c:otherwise>
      		<ul class="nav navbar-nav navbar-right">
		        <li><a>${sessionScope.login.name }님 환영합니다.</a></li> 
		        <li><a href="${pageContext.request.contextPath }/logout" data-toggle="modal"><span class="glyphicon glyphicon-log-in"></span> LogOut</a></li>
      		</ul>
      	</c:otherwise>
      </c:choose>
     
    </div>
  </div>
</nav>

<div id="loginModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Login</h4>
      </div>
      <div class="modal-body">
        <form action="/exam/login" method="post" id="loginForm">
		  <div class="form-group">
		    <label for="id">ID:</label>
		    <input type="text" class="form-control" id="id" name="id">
		  </div>
		  <div class="form-group">
		    <label for="pw">Password:</label>
		    <input type="password" class="form-control" id="pw" name="pw">
		  </div>
		  <button type="submit" class="btn btn-default" id="loginBtn">Submit</button>
		</form>
       </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<div id="signModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Sign</h4>
      </div>
      <div class="modal-body">
        <form action="sign" method="post" id="signForm">
          <div class="form-group">
		    <label for="name">Name:</label> 
		    <input type="text" class="form-control" id="name" name="name">
		  </div>	
		  <div class="form-group">
		    <label for="signId">ID:</label> 
		    <input type="text" class="form-control" id="signId" name="signId" placeholder="영어, 숫자 포함(6자 이상, 15자 이하)">
		    <button id="idCheckBtn" type="button" class="btn btn-default">ID Check</button>
		  </div>
		  <div class="form-group">
		    <label for="tel">Tel:</label>
		    <input type="text" class="form-control" id="tel" name="tel" placeholder="010-xxxx-xxxx">
		  </div>
		  <div class="form-group">
		    <label for="email">Email:</label>
		    <input type="text" class="form-control" id="email" name="email">
		  </div>
		  <div class="form-group">
		    <label for="signPw">Password:</label>
		    <input type="password" class="form-control" id="signPw" name="signPw" placeholder="영어, 숫자, 특수 문자 포함(8자 이상, 20자 이하)">
		  </div>
		  <div class="form-group">
		    <label for="pwCheck">Password Confirm:</label>
		    <input type="password" class="form-control" id="pwCheck">
		  </div>
		  <button type="submit" class="btn btn-default" id="signBtn">Submit</button>
		</form>
       </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

