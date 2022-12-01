/**
	2. 스토리 페이지
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 하트, 하트X
	(4) 댓글쓰기
	(5) 댓글삭제
	(6) 검색한 게시글 로드하기
 */
 
// (0) 현재 로긴한 사용자 아이디
let principalId = $("#principalId").val();
let postId = $("#postId").val();
console.log("콘솔창"+postId);

function storyLoad() {
	$.ajax({
		url: `/api/post/${postId}`,
		dataType: "json"
	}).done(res => {
		let post = res.data
		console.log(post);
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

			<div class="sl__item__contents__commentList" id="storyCommentList-${post.id}">`;

			post.comments.forEach((comment) => {
				item += `<div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}">
						<p>
							<b>${comment.user.username} :</b> ${comment.content}
						</p>`;

				if (principalId == comment.user.id) {
					item += `<button onclick="deleteComment(${comment.id})">
								<i class="fas fa-times"></i>
							</button>`;
				}
				item += `</div>`;
			});
			item += `</div>

			<div class="sl__item__input">
				<input type="text" placeholder="댓글 달기..." id="storyCommentInput-${post.id}" />
				<button type="button" onClick="addComment(${post.id})">게시</button>
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

function addComment(postId) {

	let commentInput = $(`#storyCommentInput-${postId}`);
	let commentList = $(`#storyCommentList-${postId}`);

	let data = {
		postId: postId,
		content: commentInput.val()
	}

	/*if (data.content === "") {
		alert("댓글을 작성해주세요!");
		return;
	}*/

	$.ajax({
		type: "post",
		url: "/api/comment",
		data: JSON.stringify(data),
		contentType: "application/json;charset=utf-8",
		dataType: "json"
	}).done(res => {
		console.log("성공", res);

		let comment = res.data;

		let content = `
			  <div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}"> 
			    <p>
			      <b>${comment.user.nickname} :</b>
			      ${comment.content}
			    </p>
			    
			    <button onclick="deleteComment(${comment.id})"><i class="fas fa-times"></i></button>		    
			  </div>
	`;
		commentList.prepend(content);
	}).fail(error => {
		console.log("오류", error.responseJSON.data.content	);
		alert(error.responseJSON.data.content)
	});

	commentInput.val("");
}

 
function deleteComment(commentId) {
	$.ajax({
		type: "delete",
		url: `/api/comment/${commentId}`,
		dataType: "json"
	}).done(res => {
		console.log("성공", res);
		$(`#storyCommentItem-${commentId}`).remove();
	}).fail(error => {
		console.log("오류", error);
	});
}




