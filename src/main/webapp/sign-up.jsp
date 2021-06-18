<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@include file="/WEB-INF/jspf/taglib.jspf" %>

<html>
<c:set var="title" value="Login page" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body class="text-center">
<%@include file="WEB-INF/jspf/header.jspf" %>
<%--<link rel="stylesheet" type="text/css" href="style/signin.css">--%>
<main class="form-signin" style="padding-top: 10%">
    <c:if test="${not empty incorrect}">
        <div class="row" id="close">
            <div class="col-4"></div>
            <div class="col-4">
                <div class="container">
                    <div class="alert alert-danger" role="alert">
                        <fmt:message key="${incorrect}"/>
                    </div>
                </div>
            </div>
            <div class="col-4"></div>
        </div>
        <c:remove var="incorrect"/>
    </c:if>
    <div class="row">
        <div class="col-4"></div>
        <div class="col-4">
            <form id="login_form" action="controller" method="post">
                <input type="hidden" name="command" value="signup">
                <h1 class="h3 mb-3 fw-normal"><fmt:message key="sing-up.jsp.h1.Enter_your_data"/></h1>
                <div class="form-floating">
                    <input type="email" name="username" class="form-control" id="floatingInput" placeholder="name@example.com">
                    <label for="floatingInput"><fmt:message key="login_jsp.label.email"/></label>
                </div>
                <div class="form-floating">
                    <input type="password" name="password" class="form-control" id="floatingPassword" placeholder="Password">
                    <label for="floatingPassword"><fmt:message key="login_jsp.label.password"/></label>
                </div>
                <div class="pt-3"></div>
                <div class="form-floating">
                    <input type="text" name="name" class="form-control" placeholder="You name">
                    <label for="floatingPassword"><fmt:message key="sing-up.jsp.label.name"/></label>
                </div>
                <div class="form-floating">
                    <input type="text" name="surname" class="form-control" placeholder="Your surname">
                    <label for="floatingPassword"><fmt:message key="sing-up.jsp.label.surname"/></label>
                </div>
                <button class="w-100 btn btn-lg btn-primary" type="submit"><fmt:message key="header.jsp.button.sing.up"/></button>
            </form>
        </div>
        <div class="col-4"></div>
    </div>
</main>
</body>
</html>