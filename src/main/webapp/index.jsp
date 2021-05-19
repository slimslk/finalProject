<%@include file="WEB-INF/jspf/taglib.jspf"%>
<%@include file="WEB-INF/jspf/page.jspf"%>

<html>
<c:set value="Main Page" var="title"/>
<%@include file="WEB-INF/jspf/head.jspf"%>
<body>
<jsp:useBean id="userCrate" class="main.entity.UserCrate" scope="session"/>
<%@include file="WEB-INF/jspf/header.jspf"%>
<br>
<h1>HelloWorld</h1>

</body>

</html>