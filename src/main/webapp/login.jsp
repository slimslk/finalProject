<%@ include file="/WEB-INF/jspf/page.jspf" %>
<%@include file="/WEB-INF/jspf/taglib.jspf" %>

<html>
<c:set var="title" value="Login page" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body class="text-center">
<%@include file="WEB-INF/jspf/header.jspf" %>
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
<%--    Login form--%>
    <div class="row">
        <div class="col-4"></div>
        <div class="col-4">
            <form id="login_form" action="${pageContext.request.contextPath}/controller" method="post">
                <input type="hidden" name="command" value="login">
                <h1 class="h3 mb-3 fw-normal"><fmt:message key="login.jsp.h1.please_sing_in"/></h1>
                <div class="form-floating">
                    <input type="email" name="username" class="form-control" id="floatingInput" placeholder="name@example.com">
                    <label for="floatingInput"><fmt:message key="login_jsp.label.email"/></label>
                </div>
                <div class="form-floating">
                    <input type="password" name="password" class="form-control" id="floatingPassword" placeholder="Password">
                    <label for="floatingPassword"><fmt:message key="login_jsp.label.password"/></label>
                </div>
                <button class="w-100 btn btn-lg btn-primary" type="submit"><fmt:message
                        key="header.jsp.button.sing.in"/></button>
            </form>
        </div>
        <div class="col-4"></div>
    </div>
<%--    End login form--%>
    <a href="sign-up.jsp"><fmt:message key="header.jsp.button.sing.up"/></a>
</main>
</body>
</html>
