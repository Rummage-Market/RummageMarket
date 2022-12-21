<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>RummageMarket</title>
<link rel="stylesheet" href="/css/style.css">
<link rel="stylesheet"
	href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
	integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p"
	crossorigin="anonymous" />
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Caveat:wght@700&family=Gowun+Batang&display=swap"
	rel="stylesheet">
</head>

<body>

	<input type="hidden" id="error" value="${error}" />

	<div class="container">
		<main class="loginMain">
			<section class="login">
				<article class="login__form__container">

					<div class="login__form">

						<h1>RummageMarket</h1>

						<form class="login__input" action="/auth/signin" method="POST">
							<input type="text" name="username" placeholder="유저네임"
								required="required" /> <input type="password" id="password"
								name="password" placeholder="비밀번호" required="required"
								class="loginPasswordInput" /> <span> <c:if
									test="${error}">
									<p id="valid" class="alert-danger">${exception}</p>
								</c:if>
							</span>
							<div class="checks-remember-me">
								<input type="checkbox" id="remember-me" name="remember-me"
									class="rememberMe" /> <label for="remember-me"
									class=rememberMeLabel>로그인 유지</label>
							</div>
							<button>
								<div>로그인</div>
							</button>
						</form>

						<div class="login__horizon">
							<div class="br"></div>
							<div class="or">또는</div>
							<div class="br"></div>
						</div>

						<div class="login__facebook">
							<button
								onclick="javascript:location.href='/oauth2/authorization/facebook'">
								<i class="fab fa-facebook-square"></i> <span>Facebook으로	로그인</span>
							</button>
						</div>

					</div>

					<div class="login__register">
						<span>계정이 없으신가요?</span> 
						<a href="/auth/signup">회원가입</a>
					</div>

				</article>
			</section>
		</main>

	</div>
</body>

</html>