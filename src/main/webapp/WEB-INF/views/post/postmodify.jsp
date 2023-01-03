<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<!--수정페이지 중앙배치-->
<main class="uploadContainer">
	<!--수정 박스-->
	<section class="upload">

		<!--사진업로드 로고-->
		<div class="upload-top">
			<a href="home.html" class=""> <img src="/images/logo.jpg" alt="">
			</a>
			<p>사진 업로드</p>
		</div>
		<!--사진업로드 로고 end-->
		<br>
		<!--수정 Form-->
		<form id="postUpdateForm" class="upload-form" onsubmit="update(${post.id},${post.user.id})">
			<!--사진 업로드-->
			<input type="file" name="file" onchange="imageChoose(this)" />
			<div class="upload-img">
				<img src="${post.imageUrl}" alt="" id="imageUploadPreview" />
			</div>

			<!--중고품 설명 + 수정버튼-->
			<div class="upload-form-detail">
				<input type="text" placeholder="제목" value="${post.title}"name="title" required="required"><input
					type="text" placeholder="상품명" value="${post.item}" name="item" required="required"> <input
					type="number" placeholder="가격" name="price" value="${post.price}" required="required"><input
					type="text" placeholder="내용" name="content" value="${post.content}" required="required">
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
				<button class="ctas">게시글 수정</button>
			</div>
			<!--중고품 설명 end-->

		</form>
		<!--수정 Form-->
	</section>
	<!--수정 박스 end-->
</main>
<br />
<br />

<script src="/js/upload.js"></script>
<%@ include file="../layout/footer.jsp"%>