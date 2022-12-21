// 중복 유저네임 체크
function checkUsername() {
	let username = $('#username').val();
	$.ajax({
		url: '/auth/usernameCheck',
		type: 'post',
		data: { username: username }
	}).done(res => {
		if (res.data == false) {
			$('#usernameOk').css("display", "inline-block");
			$('#usernameAlready').css("display", "none");
		} else {
			$('#usernameAlready').css("display", "inline-block");
			$('#usernameOk').css("display", "none");
			$('#username').val('');
			$('#username').focus();
		}
	}).fail(error => {
		console.log(error);
	});
};