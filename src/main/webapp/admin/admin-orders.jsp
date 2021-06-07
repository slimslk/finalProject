<%@include file="../WEB-INF/jspf/taglib.jspf" %>
<%@include file="../WEB-INF/jspf/page.jspf" %>

<html>
<c:set value="Main Page" var="title"/>
<%@include file="../WEB-INF/jspf/head.jspf" %>
<body>
<%@include file="../WEB-INF/jspf/header.jspf" %>
<h1>ADMIN ORDER</h1>
<c:forEach var="userOrder" items="${userOrders.userOrders}">
    <%--Order--%>
    <div class="container-fluid">
        <div class="row">
            <div class="col-1">
            </div>
            <div class="col-10">
                <div class="row">
                    <div class="col my-3">
                        <aside class="blog-sidebar rounded shadow-lg">
                            <div class="flex-shrink-0 p-3 bg-white">
                                <ul class="list-unstyled ps-0">
                                    <li class="mb-1">
                                        <!--Button status start-->
                                        <div class="row mb-2">
                                            <div class="col mt-2">
                                                <span>Username: <b>${userOrder.value[0].username}</b></span>
                                            </div>
                                            <div class="col-1 mt-2">
                                                <span>Status:</span>
                                            </div>
                                            <div class="col-3">
                                                <span class="text-end">
                                                    <select class="form-select align-content-end" aria-label="lang"
                                                            id="${userOrder.key}">
                                                    <c:forEach items="${applicationScope.orderStatuses}" var="status">
                                                        <c:set var="select"
                                                               value="${status.value.toLowerCase().equals(userOrder.value[0].orderStatus) ? 'selected' : '' }"/>
                                                        <option value="${status.key}" ${select}>${status.value}</option>
                                                    </c:forEach>
                                                    </select>
                                                </span>
                                            </div>
                                            <div class="col-2">
                                                <button class="btn btn-success"
                                                        onclick="changeStatus(${userOrder.key},'orders')">Confirm
                                                </button>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col">
                                                <button class="btn btn-outline-primary rounded collapsed w-100"
                                                        data-bs-toggle="collapse"
                                                        data-bs-target="#order${userOrder.key}" aria-expanded="false">
                                                <span class="row">
                                                    <span class="col-3">
                                                        Order number <b>#${userOrder.key}</b>
                                                    </span>
                                                <span class="col-4">
                                                    Order date: <b>${userOrder.value[0].orderDate}</b>
                                                </span>
                                                <span class="col-3">
                                                    Total price:<b>$
                                                    <c:set var="totalPrice" scope="page" value="0"/>
                                                    <c:forEach var="price" items="${userOrder.value}">
                                                        <c:set var="totalPrice" scope="page"
                                                               value="${totalPrice+price.price*price.quantity}"/>
                                                    </c:forEach>
                                                    <sz:total value="${totalPrice}"/>
                                                </b>

                                                </span>
                                                </span>
                                                </button>
                                            </div>
                                        </div>
                                        <!--ButtonStatus end -->
                                        <!--Order list start-->
                                        <div class="row collapse my-1 m-2" id="order${userOrder.key}">
                                            <!--Item-->
                                            <div class="mb-3 rounded-2 p-3">
                                                <div class="row">
                                                    <c:forEach var="order" items="${userOrder.value}">
                                                        <div class="col-4 shadow-sm">
                                                            <div class="row m-2">
                                                                    <%--Image--%>
                                                                <div class="col-4">
                                                                    <div class="rounded">
                                                                        <img class="img-fluid "
                                                                             src="${pageContext.request.contextPath}/img/${order.goods.img}"
                                                                             alt="Sample">
                                                                    </div>
                                                                </div>
                                                                    <%--End Image--%>
                                                                <div class="col-8">
                                                                    <div>
                                                                        <div class="d-flex justify-content-between">
                                                                            <div>
                                                                                <h5>${order.goods.name}</h5>
                                                                                <p class="mb-2 text-muted text-uppercase small">
                                                                                    Size
                                                                                    : ${order.goodsParam.sizeName}</p>
                                                                                <p class="text-muted text-uppercase small">
                                                                                    Quantity:
                                                                                        ${order.quantity}</p>
                                                                                <p class="mb-2 text-muted text-uppercase small">
                                                                                    Price: $<sz:total
                                                                                        value="${order.price*order.quantity}"/>
                                                                                </p>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                            <!--Item end-->
                                        </div>
                                        <!--Order list end-->
                                    <li class="border-top my-3"></li>
                                </ul>
                            </div>
                        </aside><!-- /.blog-sidebar -->
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:forEach>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/order.js"></script>
</body>
</html>
