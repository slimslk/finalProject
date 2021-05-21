<%@include file="WEB-INF/jspf/taglib.jspf" %>
<%@include file="WEB-INF/jspf/page.jspf" %>

<html>
<c:set value="Main Page" var="title"/>
<%@include file="WEB-INF/jspf/head.jspf" %>
<body>
<%@include file="WEB-INF/jspf/header.jspf" %>
    <ul class="list-group">
        <button type="button" class="btn btn-primary" onclick="alertFunction()">Add</button>
    </ul>
<script>
    function alertFunction(){
Java.type()
        alert("added to cart")
    }
</script>
<c:forEach var="catalogItem" items="${catalog}">
    <form id="add_to_cart" action="controller" >
        <input type="hidden" name="command" value="cart">
        <input type="hidden" name="command" value="${catalogItem.goodsId}">
        <p>${catalogItem.goods.name} = ${catalogItem.price}</p>
        <button class="w-100 btn btn-lg btn-primary" onclick="alertFunction()" type="submit">Add to cart</button>
    </form>
</c:forEach>
<p>${userCrate}</p>
</body>
</html>
