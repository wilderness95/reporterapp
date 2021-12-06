<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
	type="text/css">
<link rel="stylesheet"
	href="https://static.pingendo.com/bootstrap/bootstrap-4.2.1.css">
<meta charset="UTF-8">
<title>Registration page</title>
</head>
<body>

	<div class="py-5 text-center">
		<div class="container h-75">
			<div class="row h-100">
				<div class="mx-auto col-lg-6 col-10">
					<h1>Regisztráció</h1>
					<form class="text-left h-75" action="lofasz" method="post">
						<div class="form-group">
							<label for="form16">Username</label> <input type="text"
								class="form-control" name="name" placeholder="Username">
						</div>
						<div class="form-group">
							<label for="form17">Nick name</label> <input type="text"
								class="form-control" name="nickName" placeholder="nickName">
						</div>
						<div class="form-group">
							<label for="form17">Email</label> <input type="text"
									class="form-control" name="email" placeholder="email">
						</div>
						<div class="form-group">
							<label for="form16">Password</label> <input type="password"
								class="form-control" name="password" placeholder="password">
						</div>

						
						<div class="row">
							<div class="col-md-4"></div>
							<div class="col-md-4 text-center mt-1">
								<input type="submit" class="btn btn-info" value="registration">
								<div class="col-md-4"></div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>