<%@include file="../WEB-INF/jspf/taglib.jspf" %>
<%@include file="../WEB-INF/jspf/page.jspf" %>

<html>
<c:set value="Main Page" var="title"/>
<%@include file="../WEB-INF/jspf/head.jspf" %>
<body>
<%@include file="../WEB-INF/jspf/header.jspf" %>

<hr/>
<hr/>
<c:set var="count" value="0" scope="page" />
<c:forEach var="catalogItem" items="${catalog}">
    <form name="add_to_cart${count}">
        <input type="hidden" name="item_id" value="${catalogItem.id}">
            <p>${catalogItem.goods.name} = ${catalogItem.price}</p>
        <button class="w-100 btn btn-lg btn-primary" onclick="addToCart(${count})">Sign in</button>
        <c:set var="count" value="${count + 1}" scope="page"/>
    </form>
</c:forEach>
<p>${userCrate}</p>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/addToCart.js"></script>
</body>
</html>
