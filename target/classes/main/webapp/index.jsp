<%@include file="WEB-INF/jspf/taglib.jspf"%>
<%@include file="WEB-INF/jspf/page.jspf"%>

<html>
<c:set value="Main Page" var="title"/>
<%@include file="WEB-INF/jspf/head.jspf"%>
<body>
<jsp:useBean id="userCart" class="main.entity.UserCart" scope="session"/>
<%@include file="WEB-INF/jspf/header.jspf"%>
<br>
<h1>HelloWorld</h1>

<form id="catalogItem" action="controller">
    <a href="controller?command=catalog&sort=addDate&start=0&end=5">Catalog</a>
</form>

</body>

</html>