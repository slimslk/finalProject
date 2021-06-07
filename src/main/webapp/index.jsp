<%@include file="WEB-INF/jspf/taglib.jspf" %>
<%@include file="WEB-INF/jspf/page.jspf" %>

<html>
<c:set value="Main Page" var="title"/>
<%@include file="WEB-INF/jspf/head.jspf" %>
<body>
<c:if test="${empty userCart}">
    <jsp:useBean id="userCart" class="main.entity.UserCart" scope="session"/>
</c:if>
<%@include file="WEB-INF/jspf/header.jspf" %>
<br>
<h1>HelloWorld</h1>
<sz:total value="15.55555"/>
<c:if test="${not empty user}">
    <a href="${pageContext.request.contextPath}/controller?command=orders">User orders</a>
</c:if>
<%--ASC and DESC --%>
<form id="catalogItem" action="controller">
    <a href="${pageContext.request.contextPath}/controller?command=catalog">Catalog</a>
</form>
<fmt:message key="login_jsp.button.login"></fmt:message>
${header['referer']}
${sessionScope.currentLocale}
</body>

</html>