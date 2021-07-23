<%@ include file="WEB-INF/jspf/page.jspf" %>
<%@include file="WEB-INF/jspf/taglib.jspf" %>

<html>
<c:set var="title" value="Error page" scope="page"/>
<%@ include file="WEB-INF/jspf/head.jspf" %>
<body>
<h1>${sessionScope.errorMessage}</h1>
<hr>
<a href="${pageContext.request.contextPath}/controller?command=catalog"><fmt:message key="error.back"/></a>

</body>
</html>
