<%@include file="WEB-INF/jspf/taglib.jspf"%>
<%@include file="WEB-INF/jspf/page.jspf"%>

<html>
<c:set value="Main Page" var="title"/>
<%@include file="WEB-INF/jspf/head.jspf"%>
<body>
<c:if  test="${empty userCart}">
    <jsp:useBean id="userCart" class="main.entity.UserCart" scope="session"/>
</c:if>
<%@include file="WEB-INF/jspf/header.jspf"%>
<br>
<h1>HelloWorld</h1>
<%--ASC and DESC --%>
<form id="catalogItem" action="controller">
    <a href="controller?command=catalog&param=ageName='baby'&sort=addDate&direction=DESC&start=0&end=999">Catalog</a>
</form>

</body>

</html>