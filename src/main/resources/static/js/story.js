/**
	2. 스토리 페이지
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 하트, 하트X
	(4) 댓글쓰기
	(5) 댓글삭제
 */
 
 // (0) 현재 로긴한 사용자 아이디
 
 // (1) 스토리 로드하기
let page = 0;

function storyLoad() {
	$.ajax({
		url: `/api/post?page=${page}`,
		dataType: "json"
	}).done(res => {
		res.data.content.forEach((post)=>{
			let storyItem = getStoryItem(post);
			$("#storyList").append(storyItem);
		});
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
		<div>${post.user.nickname}</div>
	</div>

	<div class="sl__item__img">
		<img src="/upload/${post.imageUrl}" />
	</div>

	<div class="sl__item__contents">
		<div class="sl__item__contents__icon">

			<button>
				<i class="fas fa-hand-holding-heart" id="storyInterestIcon-${post.id}" onclick="toggleInterest(${post.id})"></i>
			</button>
			
			<span class="interest"><b id="storyInterestCount-1">3 </b>interests</span>
		</div>

		
		<div class="sl__item__contents__content">
			<p>${post.content}</p>
		</div>

		<div id="storyCommentList-1">

			<div class="sl__item__contents__comment" id="storyCommentItem-1"">
				<p>
					<b>Lovely :</b> 부럽습니다.
				</p>

				<button>
					<i class="fas fa-times"></i>
				</button>

			</div>

		</div>

		<div class="sl__item__input">
			<input type="text" placeholder="댓글 달기..." id="storyCommentInput-1" />
			<button type="button" onClick="addComment()">게시</button>
		</div>

	</div>
</div>`

	return item;
}

// (2) 스토리 스크롤 페이징하기
$(window).scroll(() => {

	let checkNum = $(window).scrollTop() - ($(document).height() - $(window).height());

	if (checkNum < 1 && checkNum > -1) {
		page++;
		storyLoad();
	}
});

// (3) 하트, 하트X
function toggleInterest(postId){
	let interestIcon = $(`#storyInterestIcon-${postId}`);
	if (interestIcon.hasClass("fas fa-hand-holding-heart")) { 
		interestIcon.removeClass("fas fa-hand-holding-heart");
		interestIcon.addClass("fas fa-hand-holding");
	} else {
		interestIcon.removeClass("fas fa-hand-holding");
		interestIcon.addClass("fas fa-hand-holding-heart");
	}
}
