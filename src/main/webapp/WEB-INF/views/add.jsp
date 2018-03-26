<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="include/header.jsp"/>

<style>
	#content{
		min-height: 850px;
		text-align: center !important;
	}

	#previewDiv{
		width: 400px;
		height: 300px;
	}
	
	.targetDiv{
		margin: 1%;
	}
	
	.addImage{
		max-width: 100px;
		max-height: 100px;
		margin: 1%;
	}
	
</style>
<script>
	$(function(){
		$("#images").change(function(e){
			$("#previewDiv").empty();
			
			var files = document.getElementById("images");
			$(files.files).each(function(i, file){
				var reader = new FileReader();
				reader.onload = function(e){
					var imgObj = $("<img>").attr("src", e.target.result).addClass("addImage");
					$("#previewDiv").append(imgObj);
				}
				
				reader.readAsDataURL(file);
				
			})

		})
		
	})
</script>
<div id="content">

	<form class="form-inline" action="addPhoto" method="post" enctype="multipart/form-data">
	  <div class="targetDiv"> 
	  	<input type="hidden" name="num" value="${num }">	  	
	    <input type="file" class="form-control" id="images" name="files" multiple="multiple">
	  </div>
	  <div class="form-control targetDiv" id="previewDiv">
	  	
	  
	  </div>
	  <div class="targetDiv">
	  	<button type="submit" class="btn btn-success">ADD</button>
	  </div>
	  
	</form>



</div>
<jsp:include page="include/footer.jsp"/>