<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<!--사진 업로드페이지 중앙배치-->
<main class="uploadContainer">
	<!--사진업로드 박스-->
	<section class="upload">

		<!--사진업로드 로고-->
		<div class="upload-top">
			<a href="home.html" class=""> <img src="/images/logo.jpg" alt="">
			</a>
			<p>사진 업로드</p>
		</div>
		<!--사진업로드 로고 end-->
		<br>
		<!--사진업로드 Form-->
		<form class="upload-form" action="/post" method="post" enctype="multipart/form-data">
			<input type="file" name="file" onchange="imageChoose(this)" />
			<div class="upload-img">
				<img src="/images/person.jpeg" alt="" id="imageUploadPreview" />
			</div>

			<!--사진설명 + 업로드버튼-->
			<div class="upload-form-detail">
				<input type="text" placeholder="제목" name="title" required="required"><input
					type="text" placeholder="상품명" name="item" required="required"> <input
					type="number" placeholder="가격" name="price" required="required"><input
					type="text" placeholder="내용" name="content" required="required">
			</div>
			<br>
			<div class="upload-form-detailform">
				<div>거래할 지역을 선택해주세요</div>
				<select name="addressRegion" id="addressRegion1" required="required"></select> 
				<select	name="address1" id="address1" required="required"></select> 
				<select name="address2"	id="address2" required="required"></select>
			</div>
			<br>
			<div class="upload-form-detail">
				<button class="ctas">업로드</button>
			</div>
			<!--사진설명end-->

		</form>
		<!--사진업로드 Form-->
	</section>
	<!--사진업로드 박스 end-->
</main>
<br />
<br />

<script src="/js/upload.js"></script>
<%@ include file="../layout/footer.jsp"%>