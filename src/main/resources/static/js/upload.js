function update(postId, userId) {
	event.preventDefault();
	
	let profileImageForm = $("#postUpdateForm")[0];
	let formData = new FormData(profileImageForm);

	$.ajax({
		type: "put",
		url: `/api/post/${postId}`,
		data: formData,
		contentType: false, // 필수 : x-www-form-urlencoded로 파싱되는 것을 방지
		processData: false, // 필수 : contentType을 false로 줬을 때 QueryString 자동 설정됨. 해제해야됨.
		enctype: "multipart/form-data",
		dataType: "json"
	}).done(res => {
		console.log("update 성공", res)
		location.href = `/user/${userId}`;
		console.log("수정완료");
	}).fail(error => {
		console.log("오류", error);
	});


}


// (1) 스토리 이미지 업로드를 위한 사진 선택 로직
function imageChoose(obj) {
	let f = obj.files[0];

	if (!f.type.match("image.*")) {
		alert("이미지를 등록해야 합니다.");
		return;
	}

	let reader = new FileReader();
	reader.onload = (e) => {
		$("#imageUploadPreview").attr("src", e.target.result);
	}
	reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.
}

$(function() {
	areaSelectMaker("select[name=addressRegion]");
});

var areaSelectMaker = function(target) {
	if (target == null || $(target).length == 0) {
		console.warn("Unkwon Area Tag");
		return;
	}

	var area = {
		"수도권": {
			"서울특별시": ["강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"],
			"경기도": ["수원시 장안구", "수원시 권선구", "수원시 팔달구", "수원시 영통구", "성남시 수정구", "성남시 중원구", "성남시 분당구", "의정부시", "안양시 만안구", "안양시 동안구", "부천시", "광명시", "평택시", "동두천시", "안산시 상록구", "안산시 단원구", "고양시 덕양구", "고양시 일산동구",
				"고양시 일산서구", "과천시", "구리시", "남양주시", "오산시", "시흥시", "군포시", "의왕시", "하남시", "용인시 처인구", "용인시 기흥구", "용인시 수지구", "파주시", "이천시", "안성시", "김포시", "화성시", "광주시", "양주시", "포천시", "여주시", "연천군", "가평군",
				"양평군"],
			"인천광역시": ["계양구", "미추홀구", "남동구", "동구", "부평구", "서구", "연수구", "중구", "강화군", "옹진군"]
		},
		"강원권": {
			"강원도": ["춘천시", "원주시", "강릉시", "동해시", "태백시", "속초시", "삼척시", "홍천군", "횡성군", "영월군", "평창군", "정선군", "철원군", "화천군", "양구군", "인제군", "고성군", "양양군"]
		},
		"충청권": {
			"충청북도": ["청주시 상당구", "청주시 서원구", "청주시 흥덕구", "청주시 청원구", "충주시", "제천시", "보은군", "옥천군", "영동군", "증평군", "진천군", "괴산군", "음성군", "단양군"],
			"충청남도": ["천안시 동남구", "천안시 서북구", "공주시", "보령시", "아산시", "서산시", "논산시", "계룡시", "당진시", "금산군", "부여군", "서천군", "청양군", "홍성군", "예산군", "태안군"],
			"대전광역시": ["대덕구", "동구", "서구", "유성구", "중구"],
			"세종특별자치시": ["세종특별자치시"]
		},
		"전라권": {
			"전라북도": ["전주시 완산구", "전주시 덕진구", "군산시", "익산시", "정읍시", "남원시", "김제시", "완주군", "진안군", "무주군", "장수군", "임실군", "순창군", "고창군", "부안군"],
			"전라남도": ["목포시", "여수시", "순천시", "나주시", "광양시", "담양군", "곡성군", "구례군", "고흥군", "보성군", "화순군", "장흥군", "강진군", "해남군", "영암군", "무안군", "함평군", "영광군", "장성군", "완도군", "진도군", "신안군"],
			"광주광역시": ["광산구", "남구", "동구", "북구", "서구"]
		},
		"경상권": {
			"경상북도": ["포항시 남구", "포항시 북구", "경주시", "김천시", "안동시", "구미시", "영주시", "영천시", "상주시", "문경시", "경산시", "군위군", "의성군", "청송군", "영양군", "영덕군", "청도군", "고령군", "성주군", "칠곡군", "예천군", "봉화군", "울진군", "울릉군"],
			"경상남도": ["창원시 의창구", "창원시 성산구", "창원시 마산합포구", "창원시 마산회원구", "창원시 진해구", "진주시", "통영시", "사천시", "김해시", "밀양시", "거제시", "양산시", "의령군", "함안군", "창녕군", "고성군", "남해군", "하동군", "산청군", "함양군", "거창군", "합천군"],
			"부산광역시": ["강서구", "금정구", "남구", "동구", "동래구", "부산진구", "북구", "사상구", "사하구", "서구", "수영구", "연제구", "영도구", "중구", "해운대구", "기장군"],
			"대구광역시": ["남구", "달서구", "동구", "북구", "서구", "수성구", "중구", "달성군"],
			"울산광역시": ["남구", "동구", "북구", "중구", "울주군"]
		},
		"제주권": {
			"제주특별자치도": ["서귀포시", "제주시"]
		}
	};

	for (i = 0; i < $(target).length; i++) {
		(function(z) {
			var a1 = $(target).eq(z);
			var a2 = a1.next();
			var a3 = a2.next();

			//초기화
			init(a1, true);

			//권역 기본 생성
			var areaKeys1 = Object.keys(area);
			areaKeys1.forEach(function(Region) {
				a1.append("<option value=" + Region + ">" + Region + "</option>");
			});

			//변경 이벤트
			$(a1).on("change", function() {
				init($(this), false);
				var Region = $(this).val();
				var keys = Object.keys(area[Region]);
				keys.forEach(function(Do) {
					a2.append("<option value=" + Do + ">" + Do + "</option>");
				});
			});

			$(a2).on("change", function() {
				a3.empty().append("<option value=''>선택</option>");
				var Region = a1.val();
				var Do = $(this).val();
				var keys = Object.keys(area[Region][Do]);
				keys.forEach(function(SiGunGu) {
					a3.append("<option value=" + area[Region][Do][SiGunGu] + ">" + area[Region][Do][SiGunGu] + "</option>");
				});
			});
		})(i);

		function init(t, first) {
			first ? t.empty().append("<option value=''>선택</option>") : "";
			t.next().empty().append("<option value=''>선택</option>");
			t.next().next().empty().append("<option value=''>선택</option>");
		}
	}
}