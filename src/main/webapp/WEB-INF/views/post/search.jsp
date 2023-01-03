<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<main class="main">
				
	<section class="container">
	
	<!--검색-->
	<div class="searchPost">
		<select	name="address1" id="address1"></select> 
		<select name="address2" id="address2"></select>
		<input type="text" placeholder="검색하실 상품명을 입력하세요." name="item" id="item" class="item">
		<button class="ctas" onclick="searchPostLoad(address1.value,address2.value,item.value)">검색</button>
	</div>
	
		<!--전체 리스트 시작-->
		<article class="story-list" id="storyList">
			<h2 class="noSearch">검색된 결과가 없습니다.</h2>
		</article>
	</section>
</main>
<script src="/js/search.js"></script>
</body>
</html>