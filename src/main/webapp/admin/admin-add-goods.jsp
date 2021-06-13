<%@include file="../WEB-INF/jspf/taglib.jspf" %>
<%@include file="../WEB-INF/jspf/page.jspf" %>

<html>
<c:set value="Main Page" var="title"/>
<%@include file="../WEB-INF/jspf/head.jspf" %>
<body>
<%@include file="../WEB-INF/jspf/header.jspf" %>
<link href="${pageContext.request.contextPath}/style/sidebars.css" rel="stylesheet">
<jsp:useBean id="gpm" class="main.entity.GoodsParameters"/>
<h1>ADD NEW GOODS TO CATALOG</h1>
    <div class="container-fluid">
        <div class="row">
            <div class="col-1">
            </div>
            <div class="col-11 mt-3">
                <div class="row">
                    <div class="col my-1">
                        <aside class="blog-sidebar rounded shadow-sm">
                            <form id="admin-catalog-form" action="${pageContext.request.contextPath}/controller" method="post" enctype="multipart/form-data">
                                <input type="hidden" name="command" value="admincatalog">
                                <input type="hidden" name="itemCatalogId" value="#" id="itemCatalogId">
                                <input type="hidden" name="goodsParamId" value="#" id="goodsParamId">
                                <input type="hidden" name="goodsId" value="#" id="goodsId">
                                <div class="row g-3 align-items-center p-3">
                                    <div class="col-auto">
                                        <label for="goods-name" class="col-form-label"><fmt:message key="goods.parameters.name"/></label>
                                    </div>
                                    <div class="col-5">
                                        <input name="name" type="text" id="goods-name" class="form-control">
                                    </div>
                                    <div class="col-auto">
                                        <label for="goods-price" class="col-form-label"><fmt:message key="goods.parameters.price"/></label>
                                    </div>
                                    <div class="col-auto">
                                        <input name="price" type="text" id="goods-price" class="form-control" value="0">
                                    </div>
                                    <div class="col-auto">
                                        <label for="goods-quantity" class="col-form-label"><fmt:message key="goods.parameters.quantity"/></label>
                                    </div>
                                    <div class="col-auto">
                                        <input name="quantity" type="text" id="goods-quantity" class="form-control" value="0">
                                    </div>
                                </div>
                                <div class="row g-3 align-items-center p-3">
                                    <div class="col-auto">
                                        <label for="age" class="col-form-label"><fmt:message key="goods.parameters.age"/></label>
                                    </div>
                                    <div class="col-auto" id="age">
                                        <select name="age" class="form-select align-content-end" aria-label="lang" id="goods-age">
                                            <c:forEach items="${gpm.goodsParametersMap.get('age')}" var="ageParam">
                                                <option value="${ageParam.value}">${ageParam.value}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-auto">
                                        <label for="category" class="col-form-label"><fmt:message key="goods.parameters.category"/></label>
                                    </div>
                                    <div class="col-auto" id="category">
                                        <select name="category" class="form-select align-content-end" aria-label="lang" id="goods-category">
                                            <c:forEach items="${gpm.goodsParametersMap.get('category')}" var="categoryParam">
                                                <option value="${categoryParam.value}">${categoryParam.value}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-auto">
                                        <label for="gender" class="col-form-label"><fmt:message key="goods.parameters.gender"/></label>
                                    </div>
                                    <div class="col-auto" id="gender">
                                        <select name="gender" class="form-select align-content-end" aria-label="lang" id="goods-gender">
                                            <c:forEach items="${gpm.goodsParametersMap.get('gender')}" var="genderParam">
                                                <option value="${genderParam.value}">${genderParam.value}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-auto">
                                        <label for="size" class="col-form-label"><fmt:message key="goods.parameters.size"/></label>
                                    </div>
                                    <div class="col-auto" id="size">
                                        <select name="size" class="form-select align-content-end" aria-label="lang" id="goods-size">
                                            <c:forEach items="${gpm.goodsParametersMap.get('size')}" var="sizeParam">
                                                <option value="${sizeParam.value}">${sizeParam.value}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-auto">
                                        <label for="style" class="col-form-label"><fmt:message key="goods.parameters.style"/></label>
                                    </div>
                                    <div class="col-auto" id="style">
                                        <select name="style" class="form-select align-content-end" aria-label="lang" id="goods-style">
                                            <c:forEach items="${gpm.goodsParametersMap.get('style')}" var="styleParam">
                                                <option value="${styleParam.value}">${styleParam.value}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="row g-3 align-items-center p-3">
                                    <div class="col-auto">
                                        <label for="img" class="col-form-label"><span id="goods-img"></span></label>
                                    </div>
                                    <div class="col-auto">
                                        <input name="img" type="file" id="img" class="form-control">
                                    </div>
                                    <div class="col-auto">
                                        <button name="action" class="btn btn-success" type="submit" value="create"><fmt:message key="admin-add-goods.jsp.add"/></button>
                                    </div>
                                </div>
                            </form>
                        </aside>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
