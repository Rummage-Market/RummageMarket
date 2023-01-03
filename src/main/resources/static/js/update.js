// 회원수정
function update(userId) {
	event.preventDefault();
	let data = $("#profileUpdate").serialize();
	console.log(data);
	$.ajax({
		type: "put",
		url: `/api/user/${userId}`,
		data: data,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		dataType: "json"
	}).done(res => {
		console.log("update 성공", res)
		location.href = `/user/${userId}`;
	}).fail(error => {
		console.log(error);
		if (error.responseJSON.data == null) {
			alert(error.responseJSON.message)
		} else {
			alert(JSON.stringify(error.responseJSON.data));
		}
	});

}