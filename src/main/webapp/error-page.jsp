<%@ include file="WEB-INF/jspf/page.jspf" %>
<%@include file="WEB-INF/jspf/taglib.jspf" %>

<html>
<c:set var="title" value="Error page" scope="page"/>
<%@ include file="WEB-INF/jspf/head.jspf" %>
<body>
<h1>${sessionScope.errorMessage}</h1>
</body>
</html>
