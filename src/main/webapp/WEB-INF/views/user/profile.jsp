<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<!--프로필 섹션-->
<section class="profile">
	<!--유저정보 컨테이너-->
	<div class="profileContainer">

		<!--유저이미지-->
		<div class="profile-left">
			<div class="profile-img-wrap story-border"
				onclick="popup('.modal-image')">
				<form id="userProfileImageForm">
					<input type="file" name="profileImageFile" style="display: none;"
						id="userProfileImageInput" />
				</form>

				<img class="profile-image" src="/upload/${dto.user.profileImage}"
					onerror="this.src='/images/person.jpeg'" id="userProfileImage" />
			</div>
		</div>
		<!--유저이미지end-->

		<!--유저정보 및 사진등록 이웃맺기-->
		<div class="profile-right">
			<div class="name-group">
				<h2>${dto.user.nickname}</h2>
				
				<c:choose>
					<c:when test="${dto.pageOwnerState}">
						<button class="cta" onclick="location.href='/post/upload'">게시글 작성</button>
						<button class="modi" onclick="popup('.modal-info')">
							<i class="fas fa-cog"></i>
						</button>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${dto.neighborState}">
								<button class="cta blue" onclick="toggleSubscribe(${dto.user.id},this)">이웃취소</button>
							</c:when>
							<c:otherwise>
								<button class="cta" onclick="toggleSubscribe(${dto.user.id},this)">이웃맺기</button>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</div>

			<div class="subscribe">
				<ul>
					<li><a href="">게시물<span>${dto.postCount}</span>
					</a></li>
					<li><a href="javascript:subscribeInfoModalOpen(${dto.user.id});"> 구독정보<span>${dto.neighborCount}</span>
					</a></li>
				</ul>
			</div>
			<div class="state">
				<h4>${dto.user.bio}</h4>
			</div>
		</div>
		<!--유저정보 및 사진등록 이웃맺기-->

	</div>
</section>

<!--게시물컨섹션-->
<section id="tab-content">
	<!--게시물컨컨테이너-->
	<div class="profileContainer">
		<!--그냥 감싸는 div (지우면이미지커짐)-->
		<div id="tab-1-content" class="tab-content-item show">
			<!--게시물컨 그리드배열-->
			<div class="tab-1-content-inner">

				<!--아이템들-->
	
				<c:forEach var="post" items="${dto.user.posts}">
				<div class="img-box">
					<a href="/post/${post.id} " class="">
						<img src="/upload/${post.imageUrl}" />
						<div class="information">
								<p><b>${post.title}</b><br/>
							    ${post.address1} ${post.address2} <br/>
							    <i class="far fa-heart"></i>&nbsp;&nbsp;<span>${post.interestCount}</span>&nbsp;&nbsp;
							    <i class="far fa-comments"></i>&nbsp;&nbsp;<span>${post.commentCount}</span></p>			    
						</div>
					</a>
				</div>
				</c:forEach>

				<!--아이템들end-->
			</div>
		</div>
	</div>
</section>

<!--회원정보변경 모달-->
<div class="modal-info" onclick="modalInfo()">
	<div class="modal">
		<button onclick="location.href='/user/${principal.user.id}/update'">회원정보 변경</button>
		<button onclick="closePopup('.modal-info')">취소</button>
	</div>
</div>
<!--회원정보변경 모달 end-->

<!--프로필사진 바꾸기 모달-->
<div class="modal-image" onclick="modalImage()">
	<div class="modal">
		<p>프로필 사진 바꾸기</p>
		<button onclick="profileImageUpload(${dto.user.id},${principal.user.id})">프로필 사진변경</button>
		<button onclick="closePopup('.modal-image')">취소</button>
	</div>
</div>

<!--프로필사진 바꾸기 모달end-->

<div class="modal-subscribe">
	<div class="subscribe">
		<div class="subscribe-header">
			<span>구독정보</span>
			<button onclick="modalClose()">
				<i class="fas fa-times"></i>
			</button>
		</div>

		<div class="subscribe-list" id="subscribeModalList">

		</div>
	</div>

</div>


<script src="/js/profile.js"></script>

<%@ include file="../layout/footer.jsp"%>