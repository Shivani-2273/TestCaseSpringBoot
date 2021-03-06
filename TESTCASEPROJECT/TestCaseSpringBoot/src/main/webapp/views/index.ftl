<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Login</title>


<link href="/css/tailwind.output.css" rel="stylesheet"/>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/gh/alpinejs/alpine@v2.x.x/dist/alpine.min.js" defer></script>
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800&display=swap" rel="stylesheet" />
<script type="text/javascript" src="/js/init-alpine.js"></script>

</head>
<body>
	<div
		class="flex items-center min-h-screen p-6 bg-gray-50 dark:bg-gray-900">
		<div
			class="flex-1 h-full max-w-4xl mx-auto overflow-hidden bg-white rounded-lg shadow-xl dark:bg-gray-800">
			<div class="flex flex-col overflow-y-auto md:flex-row">
				<div class="h-32 md:h-auto md:w-1/2">
				
					  <img aria-hidden="true"
						class="object-cover w-full h-full dark:hidden"
						src="/img/login-office.jpg" alt="Office" /> 
						<img aria-hidden="true" class="hidden object-cover w-full h-full dark:block"
						src="/img/login-office-dark.jpeg" alt="Office" />
				</div>
				<div class="flex items-center justify-center p-6 sm:p-12 md:w-1/2">
					<div class="w-full">
						<h1
							class="mb-4 text-xl font-semibold text-gray-700 dark:text-gray-200">
							Login</h1>
						<form action="LoginURL" method="POST" id="login" modelAttribute="LoginForm">
							<label class="block mt-2 mt-4 text-md font-semibold"> <span
								class="text-gray-700 dark:text-gray-400">Email</span> <input
								class="block w-full mt-1 text-sm dark:border-gray-600 dark:bg-gray-700 focus:border-purple-400 focus:outline-none focus:shadow-outline-purple dark:text-gray-300 dark:focus:shadow-outline-gray form-input"
								name="email" placeholder="Jane Doe" id="email" />
								 <span id="email_error"></span>
								
																 
							</label>
							
							
							
							 <label class="block mt-2 mt-4 text-md font-semibold"> <span
								class="text-gray-700 dark:text-gray-400">Password</span> <input
								class="block w-full mt-1 text-sm dark:border-gray-600 dark:bg-gray-700 focus:border-purple-400 focus:outline-none focus:shadow-outline-purple dark:text-gray-300 dark:focus:shadow-outline-gray form-input"
								placeholder="***************" name="password" id="password" type="password" />
								  <span id="password_error"></span>
								 
								 
							</label>

							<!-- You should use a button here, as the anchor is only used for the example  -->
							<input type="submit" value="Log In"
								class="block w-full px-4 py-2 mt-4 text-sm font-medium leading-5 text-center text-white transition-colors duration-150 bg-purple-600 border border-transparent rounded-lg active:bg-purple-600 hover:bg-purple-700 focus:outline-none focus:shadow-outline-purple">
								<span class="error" style="color:red">${errorMsg!''}</span>
						</form>
						<hr class="my-8" />
						<a href="ForgotPassword" class="flex items-center justify-center w-full px-4 py-2 mt-4 text-sm font-medium leading-5 text-white text-gray-700 transition-colors duration-150 border border-gray-300 rounded-lg dark:text-gray-400 active:bg-transparent hover:border-gray-500 focus:border-gray-500 active:text-gray-500 focus:outline-none focus:shadow-outline-gray">
						Forgot Password?
								
						</a>
							<a href="UserRegister" class="flex items-center justify-center w-full px-4 py-2 mt-4 text-sm font-medium leading-5 text-white text-gray-700 transition-colors duration-150 border border-gray-300 rounded-lg dark:text-gray-400 active:bg-transparent hover:border-gray-500 focus:border-gray-500 active:text-gray-500 focus:outline-none focus:shadow-outline-gray">
						Create account
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<script src="/js/customValidation.js"></script>

</body>
</html>
