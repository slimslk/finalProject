<%@include file="../WEB-INF/jspf/taglib.jspf" %>
<%@include file="../WEB-INF/jspf/page.jspf" %>

<html>
<c:set value="Main Page" var="title"/>
<%@include file="../WEB-INF/jspf/head.jspf" %>
<body>
<%@include file="../WEB-INF/jspf/header.jspf" %>
<div class="container" id="main-container">
    <c:choose>
        <c:when test="${userCart.size>0}">
            <c:set var="total" value="0" scope="page"></c:set>
            <!--Grid row-->
            <div class="row" id="cart-main-div">
                <!--Grid item column-->
                <div class="col-6 pt-4">
                    <c:forEach var="cartItem" items="${cartGoods}">
                        <c:set var="total" value="${total+cartItem.price*userCart.getQuantity(cartItem.goodsParamId)}"/>
                        <!-- Items -->
                        <div class="mb-3 shadow-lg rounded-2 p-3" id="${cartItem.goodsParamId}">
                            <div class="">
                                <div class="row mb-4">
                                    <div class="col-md-5 col-lg-3 col-xl-3">
                                        <div class="rounded mb-3 mb-md-0 shadow-sm">
                                            <img class="img-fluid w-100"
                                                 src="${pageContext.request.contextPath}/img/${cartItem.goods.img}"
                                                 alt="Sample">
                                        </div>
                                    </div>
                                    <div class="col-md-7 col-lg-9 col-xl-9">
                                        <div>
                                            <div class="d-flex justify-content-between">
                                                <div>
                                                    <h5>${cartItem.goods.name}</h5>
                                                    <p class="mb-2 text-muted text-uppercase small">Size
                                                        : ${cartItem.goodsParam.sizeName}</p>
                                                    <p class="mb-2 text-muted text-uppercase small">
                                                        Price: ${cartItem.price}</p>
                                                    <p class="text-muted text-uppercase small">
                                                        Quantity: ${userCart.getQuantity(cartItem.goodsParamId)}</p>
                                                </div>
                                                <button class="btn btn-outline-danger rounded-3 h-25"
                                                        onclick="removeItem(${cartItem.goodsParamId},${userCart.getQuantity(cartItem.goodsParamId)})">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
                                                         fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
                                                        <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
                                                        <path fill-rule="evenodd"
                                                              d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
                                                    </svg>
                                                </button>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-10">
                                                <h4>Total price:</h4>
                                            </div>
                                            <div class="col-1">
                                                <h4>$<span id="${cartItem.goodsParamId}-total">
                                                    <sz:total
                                                            value="${cartItem.price*userCart.getQuantity(cartItem.goodsParamId)}"/>
                                                </span>
                                                </h4>
                                            </div>
                                        </div>

                                    </div>

                                </div>
                                <hr>
                            </div>
                        </div>
                        <!-- Items -->

                    </c:forEach>
                </div>
                <!--Grid column-->
                <div class="col-1"></div>
                <!--Grid column total-->
                <div class="col-4">
                    <div class="pt-4">
                        <div class="row shadow-lg rounded p-3">
                            <h3>Order summary</h3>
                            <hr>
                            <div class="row pb-2">
                                <div class="col">
                                    Subtotal
                                </div>
                                <div class="col-4">
                                    $<span id="subtotal">
                                    <sz:total value="${total}"/>
                                    </span>
                                </div>
                            </div>
                            <div class="row pb-2">
                                <div class="col">
                                    Shipping
                                </div>
                                <div class="col-4">
                                    Free
                                </div>
                            </div>
                            <div class="row pb-2">
                                <div class="col">
                                    <h4>Order total</h4>
                                </div>
                                <div class="col-4">
                                    $<span id="order-total">
                                    <sz:total value="${total}"/>
                                </span>
                                </div>
                            </div>
                            <hr>
                            <c:choose>
                                <c:when test="${not empty user}">
                                    <form name="checkout" action="${pageContext.request.contextPath}/controller" method="GET">
                                        <input type="hidden" name="command" value="addorder">
                                        <c:forEach var="cartItem" items="${cartGoods}">
                                            <input type="hidden" name="goodsId" value="${cartItem.goodsParamId}">
                                            <input type="hidden" name="quantity" value="${userCart.getQuantity(cartItem.goodsParamId)}">
                                        </c:forEach>
                                        <button type="submit" class="btn btn-primary btn-block">Checkout</button>
                                    </form>
                                </c:when>
                                <c:when test="${empty user}">
                                    <button type="button" class="btn btn-outline-primary btn-block">Sing in for
                                        checkout
                                    </button>
                                </c:when>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <!-- Total -->
                <!-- Card -->

                <!--Grid column-->
            </div>
            <!-- Grid row -->
        </c:when>
        <c:when test="${userCart.size<1}">
            <div><h3 class="text-center mt-4">Your shopping cart is empty</h3></div>
        </c:when>
    </c:choose>
</div>
<script src="${pageContext.request.contextPath}/js/cart.js"></script>
</body>

</html>
