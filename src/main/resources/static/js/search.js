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

// (1) 스토리 로드하기
let page = 0;

function getStoryItem(post) {
	let item = `<div class="story-list__item">
		<div class="sl__item__header">
			<div>
				<img class="profile-image" src="${post.user.profileImage}"
					onerror="this.src='/images/person.jpeg'" />
			</div>
			<div OnClick="location.href ='/user/${post.user.id}'" style="cursor:pointer">${post.user.nickname}</div>
		</div>

		<div class="sl__item__img">
			<img src="${post.imageUrl}" />
		</div>

		<div class="sl__item__contents">

			<div class="sl__item__contents__icon">

				<button>`;
	if (post.interestState) {
		item += `<i class="fas fa-heart active" id="storyInterestIcon-${post.id}" onclick="toggleInterest(${post.id})"></i>`;
	} else {
		item += `<i class="far fa-heart" id="storyInterestIcon-${post.id}" onclick="toggleInterest(${post.id})"></i>`;
	}
	item += `	
					
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
							<b OnClick="location.href ='/user/${comment.user.id}'" style="cursor:pointer">${comment.user.nickname} :</b> ${comment.content}
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
function toggleInterest(postId) {
	let interestIcon = $(`#storyInterestIcon-${postId}`);
	if (interestIcon.hasClass("far")) {

		$.ajax({
			type: "post",
			url: `/api/post/${postId}/interest`,
			dataType: "json"
		}).done(res => {

			let interestCountStr = $(`#storyInterestCount-${postId}`).text();
			let interestCount = Number(interestCountStr) + 1;
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
			let interestCount = Number(interestCountStr) - 1;
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
			      <b OnClick="location.href ='/user/${comment.user.id}'" style="cursor:pointer">${comment.user.nickname} :</b>
			      ${comment.content}
			    </p>
			    
			    <button onclick="deleteComment(${comment.id})"><i class="fas fa-times"></i></button>		    
			  </div>
	`;
		commentList.prepend(content);
	}).fail(error => {
		console.log("오류", error.responseJSON.data.content);
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


// (6) 검색한 게시글 로드하기

function searchPostLoad(address1, address2, item) {
	console.log(address1);
	
	
	let noSearch = `
			  <h2 class=noSearch>검색결과가 없습니다.</h2>
	`;

	$.ajax({
		url: `/api/post/search? + encodeURIComponent(page=${page}&address1=${address1}&address2=${address2}&item=${item}`,
		dataType: "json"
	}).done(res => {
		console.log(res);
		if (res.data.content.length > 0){
			$("#storyList").empty();
			res.data.content.forEach((post) => {
				let storyItem = getStoryItem(post);
				$("#storyList").append(storyItem);
			});
		}else{
			$("#storyList").empty();
			$("#storyList").append(noSearch);
		}
	}).fail(error => {
		console.log("오류", error);
	});
}

// regionselectbox

$('document').ready(function() {
	var area0 = ["서울특별시", "인천광역시", "대전광역시", "광주광역시", "대구광역시", "울산광역시", "부산광역시", "경기도", "강원도", "충청북도", "충청남도", "전라북도", "전라남도", "경상북도", "경상남도", "제주도"];
	var area1 = ["강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"];
	var area2 = ["계양구", "미추홀구", "남동구", "동구", "부평구", "서구", "연수구", "중구", "강화군", "옹진군"];
	var area3 = ["대덕구", "동구", "서구", "유성구", "중구"];
	var area4 = ["광산구", "남구", "동구", "북구", "서구"];
	var area5 = ["남구", "달서구", "동구", "북구", "서구", "수성구", "중구", "달성군"];
	var area6 = ["남구", "동구", "북구", "중구", "울주군"];
	var area7 = ["강서구", "금정구", "남구", "동구", "동래구", "부산진구", "북구", "사상구", "사하구", "서구", "수영구", "연제구", "영도구", "중구", "해운대구", "기장군"];
	var area8 = ["수원시 장안구", "수원시 권선구", "수원시 팔달구", "수원시 영통구", "성남시 수정구", "성남시 중원구", "성남시 분당구", "의정부시", "안양시 만안구", "안양시 동안구", "부천시", "광명시", "평택시", "동두천시", "안산시 상록구", "안산시 단원구", "고양시 덕양구", "고양시 일산동구", "고양시 일산서구", "과천시", "구리시", "남양주시", "오산시", "시흥시", "군포시", "의왕시", "하남시", "용인시 처인구", "용인시 기흥구", "용인시 수지구", "파주시", "이천시", "안성시", "김포시", "화성시", "광주시", "양주시", "포천시", "여주시", "연천군", "가평군", "양평군"];
	var area9 = ["춘천시", "원주시", "강릉시", "동해시", "태백시", "속초시", "삼척시", "홍천군", "횡성군", "영월군", "평창군", "정선군", "철원군", "화천군", "양구군", "인제군", "고성군", "양양군"];
	var area10 = ["청주시 상당구", "청주시 서원구", "청주시 흥덕구", "청주시 청원구", "충주시", "제천시", "보은군", "옥천군", "영동군", "증평군", "진천군", "괴산군", "음성군", "단양군"];
	var area11 = ["천안시 동남구", "천안시 서북구", "공주시", "보령시", "아산시", "서산시", "논산시", "계룡시", "당진시", "금산군", "부여군", "서천군", "청양군", "홍성군", "예산군", "태안군"];
	var area12 = ["전주시 완산구", "전주시 덕진구", "군산시", "익산시", "정읍시", "남원시", "김제시", "완주군", "진안군", "무주군", "장수군", "임실군", "순창군", "고창군", "부안군"];
	var area13 = ["목포시", "여수시", "순천시", "나주시", "광양시", "담양군", "곡성군", "구례군", "고흥군", "보성군", "화순군", "장흥군", "강진군", "해남군", "영암군", "무안군", "함평군", "영광군", "장성군", "완도군", "진도군", "신안군"];
	var area14 = ["포항시 남구", "포항시 북구", "경주시", "김천시", "안동시", "구미시", "영주시", "영천시", "상주시", "문경시", "경산시", "군위군", "의성군", "청송군", "영양군", "영덕군", "청도군", "고령군", "성주군", "칠곡군", "예천군", "봉화군", "울진군", "울릉군"];
	var area15 = ["창원시 의창구", "창원시 성산구", "창원시 마산합포구", "창원시 마산회원구", "창원시 진해구", "진주시", "통영시", "사천시", "김해시", "밀양시", "거제시", "양산시", "의령군", "함안군", "창녕군", "고성군", "남해군", "하동군", "산청군", "함양군", "거창군", "합천군"];
	var area16 = ["서귀포시", "제주시"];

	// 시/도 선택 박스 초기화

	$("select[name^=address1]").each(function() {
		$seladdress1 = $(this);
		$seladdress1.append("<option value=''>시/도 선택</option>");
		$.each(eval(area0), function() {
			$seladdress1.append("<option value='" + this + "'>" + this + "</option>");
		});
		$seladdress1.next().append("<option value=''>구/군 선택</option>");
	});


	// 시/도 선택시 구/군 설정

	$("select[name^=address1]").change(function() {
		var area = "area" + $("option", $(this)).index($("option:selected", $(this))); // 선택지역의 구군 Array
		var $address2 = $(this).next(); // 선택영역 군구 객체
		$("option", $address2).remove(); // 구군 초기화

		if (area == "area0")
			$address2.append("<option value=''>구/군 선택</option>");
		else {
			$address2.append("<option value=''>구/군 선택</option>");
			$.each(eval(area), function() {
				$address2.append("<option value='" + this + "'>" + this + "</option>");
			});
		}
	});

});