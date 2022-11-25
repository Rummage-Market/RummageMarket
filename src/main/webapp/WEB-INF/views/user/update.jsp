<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<!--프로필셋팅 메인-->
<main class="main">
	<!--프로필셋팅 섹션-->
	<section class="setting-container">
		<!--프로필셋팅 아티클-->
		<article class="setting__content">

			<!--프로필셋팅 아이디영역-->
			<div class="content-item__01">
				<div class="item__img">
					<img src="#" onerror="this.src='/images/person.jpeg'" />
				</div>
				<div class="item__username">
					<h2>${principal.user.username}</h2>
				</div>
			</div>
			<!--프로필셋팅 아이디영역end-->

			<!--프로필 수정-->
			<form id="profileUpdate" onsubmit="update(${principal.user.id}, event)">
				<div class="content-item__01">
					<div class="item__title">유저네임</div>
					<div class="item__input">
						<input type="text" name="username" placeholder="유저네임"
							value="${principal.user.username}" readonly="readonly" />
					</div>
				</div>
				<div class="content-item__02">
					<div class="item__title">패스워드</div>
					<div class="item__input">
						<input type="password" name="password" placeholder="패스워드"  required="required"/>
					</div>
				</div>
				<div class="content-item__03">
					<div class="item__title">닉네임</div>
					<div class="item__input">
						<input type="text" name="name" placeholder="닉네임"
							value="${principal.user.nickname}" required="required"/>
					</div>
				</div>
				<div class="content-item__04">
					<div class="item__title">소개</div>
					<div class="item__input">
						<textarea name="bio" id="" rows="3" placeholder="소개글을 작성하세요!" >${principal.user.bio}</textarea>
					</div>
				</div>
				<div class="content-item__05">
					<div class="item__title">이메일</div>
					<div class="item__input">
						<input type="text" name="email" placeholder="이메일"
							value="${principal.user.email}" readonly="readonly" />
					</div>
				</div>

				<!--제출버튼-->
				<div class="content-item__6">
					<div class="item__title"></div>
					<div class="item__input">
						<button>제출</button>
					</div>
				</div>
				<!--제출버튼end-->

			</form>
			<!--프로필수정 form end-->
		</article>
	</section>
</main>

<script src="/js/update.js"></script>

<%@ include file="../layout/footer.jsp"%>