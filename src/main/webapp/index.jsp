<%@include file="WEB-INF/jspf/taglib.jspf" %>
<%@include file="WEB-INF/jspf/page.jspf" %>

<html>
<c:set value="Main Page" var="title"/>
<%@include file="WEB-INF/jspf/head.jspf" %>
<body>
<c:if test="${empty userCart}">
    <jsp:useBean id="userCart" class="com.epam.finalProject.entity.UserCart" scope="session"/>
</c:if>
<%@include file="WEB-INF/jspf/header.jspf" %>
<br>
<%--<h1>HelloWorld</h1>--%>
<%--<sz:total value="15.55555"/>--%>
<%--<c:if test="${not empty user}">--%>
<%--    <a href="${pageContext.request.contextPath}/controller?command=orders">User orders</a>--%>
<%--</c:if>--%>
<%--&lt;%&ndash;ASC and DESC &ndash;%&gt;--%>
<%--    <a href="${pageContext.request.contextPath}/controller?command=catalog">Catalog</a>--%>
<c:redirect url="user/catalog.jsp"/>
</body>
</html>