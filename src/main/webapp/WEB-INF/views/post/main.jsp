<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<section class="introduce">
	<div class="title">
		<h1>RummageMarket</h1>
		<p>숨겨진 보물을 찾으러 가볼까요?</p>
		<button onClick="location.href='/post/story'">start!</button>
	</div>
</section>

<section class="service">
	<div class="service-introduce">
		<div class="img">
			<img src="/images/sell.jpg" alt="">
		</div>
		<div class="text">
			<h1>우리 서비스를 소개합니다</h1>
			<p>원하는 지역에서 거래를 할 수 있어요!<p>
		</div>
	</div>
</section>


<section class="popular">
	<div class="popular-post-logo">
		<div class="text">
			<h1>중고거래 인기매물</h1>
		</div>
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

								
				<div class="img-box">
					<a href=""> <img src="/images/home.jpg" />
					</a>
					<div class="comment">
						<a href="#" class=""> 							
							<div><h4><b>제목 넣기</b></h4></div>
						    <div><p>서울시 노원구</p></div>
						    <i class="far fa-heart"></i><span>0</span>	
						    <i class="far fa-comments"></i><span>0</span>		
						</a>
					</div>
				</div>

				<div class="img-box">
					<a href=""> <img src="/images/home.jpg" />
					</a>
					<div class="comment">
						<a href="#" class=""> 							
							<div><h4><b>제목 넣기</b></h4></div>
						    <div><p>서울시 노원구</p></div>
						    <i class="far fa-heart"></i><span>0</span>	
						    <i class="far fa-comments"></i><span>0</span>		
						</a>
					</div>
				</div>

				<div class="img-box">
					<a href=""> <img src="/images/home.jpg" />
					</a>
					<div class="comment">
						<a href="#" class=""> 							
							<div><h4><b>제목 넣기</b></h4></div>
						    <div><p>서울시 노원구</p></div>
						    <i class="far fa-heart"></i><span>0</span>	
						    <i class="far fa-comments"></i><span>0</span>		
						</a>
					</div>
				</div>

				<div class="img-box">
					<a href=""> <img src="/images/home.jpg" />
					</a>
					<div class="comment">
						<a href="#" class=""> 							
							<div><h4><b>제목 넣기</b></h4></div>
						    <div><p>서울시 노원구</p></div>
						    <i class="far fa-heart"></i><span>0</span>	
						    <i class="far fa-comments"></i><span>0</span>		
						</a>
					</div>
				</div>
				
								<div class="img-box">
					<a href=""> <img src="/images/home.jpg" />
					</a>
					<div class="comment">
						<a href="#" class=""> 							
							<div><h4><b>제목 넣기</b></h4></div>
						    <div><p>서울시 노원구</p></div>
						    <i class="far fa-heart"></i><span>0</span>	
						    <i class="far fa-comments"></i><span>0</span>		
						</a>
					</div>
				</div>
				
								<div class="img-box">
					<a href=""> <img src="/images/home.jpg" />
					</a>
					<div class="comment">
						<a href="#" class=""> 							
							<div><h4><b>제목 넣기</b></h4></div>
						    <div><p>서울시 노원구</p></div>
						    <i class="far fa-heart"></i><span>0</span>	
						    <i class="far fa-comments"></i><span>0</span>		
						</a>
					</div>
				</div>

				<!--아이템들end-->
			</div>
		</div>
	</div>
</section>




<script src="/js/main.js"></script>

<%@ include file="../layout/footer.jsp"%>