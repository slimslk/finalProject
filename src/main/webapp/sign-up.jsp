<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@include file="/WEB-INF/jspf/taglib.jspf" %>

<html>
<c:set var="title" value="Login page" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body class="text-center">
<link rel="stylesheet" type="text/css" href="style/signin.css">
<main class="form-signin">
    <form id="login_form" action="controller" method="post">
        <input type="hidden" name="command" value="signup">
        <h1 class="h3 mb-3 fw-normal">Enter you data</h1>
        <div class="form-floating">
            <input type="email" name="username" class="form-control" id="floatingInput" placeholder="name@example.com">
            <label for="floatingInput">Email address</label>
        </div>
        <div class="form-floating">
            <input type="password" name="password" class="form-control" id="floatingPassword" placeholder="Password">
            <label for="floatingPassword">Password</label>
        </div>
        <div class="form-floating">
            <input type="text" name="name" class="form-control" placeholder="You name">
            <label for="floatingPassword">Name</label>
        </div>
        <div class="form-floating">
            <input type="text" name="surname" class="form-control" placeholder="Your surname">
            <label for="floatingPassword">Surname</label>
        </div>
        <button class="w-100 btn btn-lg btn-primary" type="submit">Sign in</button>
    </form>
</main>
</body>
</html>