<%@include file="../WEB-INF/jspf/taglib.jspf" %>
<%@include file="../WEB-INF/jspf/page.jspf" %>

<html>
<c:set value="Main Page" var="title"/>
<%@include file="../WEB-INF/jspf/head.jspf" %>
<body>
<%@include file="../WEB-INF/jspf/header.jspf" %>
<h1>ADMIN USERS</h1>
<c:forEach var="a_user" items="${userList}">
    <c:if test="${a_user.roleId>2}">
        <div class="container-fluid">
            <div class="row">
                <div class="col-1">
                </div>
                <div class="col-10">
                    <div class="row">
                        <div class="col my-1">
                            <aside class="blog-sidebar rounded shadow-lg">
                                <div class="flex-shrink-0 p-3 bg-white">
                                    <ul class="list-unstyled ps-0">
                                        <li class="mb-1">
                                            <!--Button status start-->
                                            <div class="row">
                                                <div class="col">
                                                    <div class="row">
                                                        <div class="col">
                                                            Username: <b>${a_user.username}</b>
                                                        </div>
                                                    </div>
                                                    <span class="row">
                                                <span class="col">
                                                    Name: <b>${a_user.userInfo.name}</b>
                                                </span>
                                                <span class="col">
                                                    Surname: <b>${a_user.userInfo.surname}</b>
                                                </span>
<%--To display user role uncomment below and in the CommandUser class--%>
<%--                                                <span class="col">--%>
<%--                                                        Role: <b>${sessionScope.userRoles.get(a_user.roleId)}</b>--%>
<%--                                                </span>--%>
                                                <span class="col text-end">
                                                    <div class="col">
                                                <span class="text-end">
                                                    <select class="form-select align-content-end" aria-label="lang"
                                                            id="${a_user.username}">
                                                    <c:forEach items="${sessionScope.userStatuses}" var="status">
                                                        <c:set var="selected"
                                                               value="${status.key == a_user.statusId ? 'selected' : '' }"/>
                                                        <option value="${status.key}" ${selected}>${status.value}</option>
                                                    </c:forEach>
                                                    </select>
                                                   <span>
                                                      <button class="btn btn-success"
                                                              onclick="changeStatus('${a_user.username}','users')">Confirm
                                                </button>
                                                   </span>
                                                </span>
                                            </div>
                                                </span>
                                            </span>
                                                </div>
                                            </div>
                                        <li class="border-top my-3"></li>
                                    </ul>
                                </div>
                            </aside><!-- /.blog-sidebar -->
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </c:if>
</c:forEach>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/order.js"></script>
</body>
</html>
