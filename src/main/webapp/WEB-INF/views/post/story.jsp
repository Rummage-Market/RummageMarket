<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<main class="main">
	
	<select name="addressRegion" id="addressRegion1"></select> 
	<select	name="address1" id="address1"></select> 
	<select name="address2" id="address2"></select>
	<input type="text" placeholder="검색하실 상품명을 입력하세요." name="item" id="item">
	<button class="cta blue" onclick="searchPostLoad(address1.value,address2.value,item.value)">검색</button>

				
	<section class="container">
		<!--전체 리스트 시작-->
		<article class="story-list" id="storyList">

			


		</article>
	</section>
</main>
<script src="/js/story.js"></script>
</body>
</html>