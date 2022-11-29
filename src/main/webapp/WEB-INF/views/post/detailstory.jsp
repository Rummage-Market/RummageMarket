<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<main class="main">

	<section class="container">

		<!--전체 리스트 시작-->
		<article class="story-list" id="storyList">

			<script>
			let principalId = $("#principalId").val();
	
			function storyLoad() {
				$.ajax({
					url: `/api/post/${post.id}`,
					dataType: "json"
				}).done(res => {
					let post = res.data
					let storyItem = getStoryItem(post);
					$("#storyList").append(storyItem);
				
				}).fail(error => {
					console.log("오류", error);
				});
			}
	
			storyLoad();
	
			function getStoryItem(post) {
				let item = `<div class="story-list__item">
				<div class="sl__item__header">
					<div>
						<img class="profile-image" src="/upload/${post.user.profileImage}"
							onerror="this.src='/images/person.jpeg'" />
					</div>
					<div OnClick="location.href ='/user/${post.user.id}'" style="cursor:pointer">${post.user.nickname}</div>
				</div>
	
				<div class="sl__item__img">
					<img src="/upload/${post.imageUrl}" />
				</div>
	
				<div class="sl__item__contents">
					<div class="sl__item__contents__icon">
	
						
						<button>`;
							if(post.interestState){
								item +=`<i class="fas fa-heart active" id="storyInterestIcon-${post.id}" onclick="toggleInterest(${post.id})"></i>`;
							}else{
								item +=`<i class="far fa-heart" id="storyInterestIcon-${post.id}" onclick="toggleInterest(${post.id})"></i>`;
							}
						item +=`	
							
						</button>
						
						<span class="interest"><b id="storyInterestCount-${post.id}">${post.interestCount} </b>interests</span>
					</div>
	
					<div class="sl__item__contents__content">
						<h1>${post.title}</h1><br/>
						<p>${post.content}</p>
						<br/>
						<div>
								<div><b>중고물품 :</b> ${post.item}</div>
								<div><b>거래지역 :</b> ${post.address1} ${post.address2}</div>
								<div><b>거래가격 :</b> ${post.price} 원</div>
							</div>
							<br/>
					</div>
	
					<div id="storyCommentList-${post.id}">`;
	
				item += `
					</div>
	
				</div>
			</div>`;
				return item;
			}
		
			// (3) 하트, 하트X
			function toggleInterest(postId){
				let interestIcon = $(`#storyInterestIcon-${postId}`);
				if (interestIcon.hasClass("far")) { 
					
					$.ajax({
					type: "post",
					url: `/api/post/${postId}/interest`,
					dataType: "json"
				}).done(res => {
				
					let interestCountStr = $(`#storyInterestCount-${postId}`).text();
					let interestCount = Number(interestCountStr) +1;
					$(`#storyInterestCount-${postId}`).text(interestCount);
				
					interestIcon.addClass("fas");
					interestIcon.addClass("active");
					interestIcon.removeClass("far");
					
				}).fail(error => {
					console.log("오류", error);
				});
	
				} else {
				
					$.ajax({
					type: "delete",
					url: `/api/post/${postId}/interest`,
					dataType: "json"
				}).done(res => {
				
					let interestCountStr = $(`#storyInterestCount-${postId}`).text();
					let interestCount = Number(interestCountStr) -1;
					$(`#storyInterestCount-${postId}`).text(interestCount);	
				
					interestIcon.removeClass("fas");
					interestIcon.removeClass("active");
					interestIcon.addClass("far");
					
				}).fail(error => {
					console.log("오류", error);
				});
	
				}
			}
			
			</script>
		</article>
	</section>
</main>
<script src="/js/detailstory.js"></script>
</body>
</html>