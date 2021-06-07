<%@include file="../WEB-INF/jspf/taglib.jspf" %>
<%@include file="../WEB-INF/jspf/page.jspf" %>

<html>
<c:set value="Main Page" var="title"/>
<%@include file="../WEB-INF/jspf/head.jspf" %>
<body>
<%@include file="../WEB-INF/jspf/header.jspf" %>
<link href="${pageContext.request.contextPath}/style/sidebars.css" rel="stylesheet">
<jsp:useBean id="gpm" class="main.entity.GoodsParameters"/>
<h1>ADMIN CATALOG</h1>
<template id="tempCard">
    <div class="container-fluid">
        <div class="row">
            <div class="col-1">
            </div>
            <div class="col-11 mt-3">
                <div class="row">
                    <div class="col my-1">
                        <aside class="blog-sidebar rounded shadow-sm">
                            <form id="admin-catalog-form" action="${pageContext.request.contextPath}/controller" method="post">
                                <input type="hidden" name="command" value="admincatalog">
                                <input type="hidden" name="itemCatalogId" value="#" id="itemCatalogId">
                                <input type="hidden" name="goodsParamId" value="#" id="goodsParamId">
                                <input type="hidden" name="goodsId" value="#" id="goodsId">
                                <div class="row g-3 align-items-center p-3">
                                    <div class="col-auto">
                                        <label for="goods-name" class="col-form-label">Name</label>
                                    </div>
                                    <div class="col-5">
                                        <input name="name" type="text" id="goods-name" class="form-control">
                                    </div>
                                    <div class="col-auto">
                                        <label for="goods-price" class="col-form-label">Price</label>
                                    </div>
                                    <div class="col-auto">
                                        <input name="price" type="text" id="goods-price" class="form-control">
                                    </div>
                                    <div class="col-auto">
                                        <label for="goods-quantity" class="col-form-label">Quantity</label>
                                    </div>
                                    <div class="col-auto">
                                        <input name="quantity" type="text" id="goods-quantity" class="form-control">
                                    </div>
                                </div>
                                <div class="row g-3 align-items-center p-3">
                                    <div class="col-auto">
                                        <label for="age" class="col-form-label">Age</label>
                                    </div>
                                    <div class="col-auto" id="age">
                                        <select name="age" class="form-select align-content-end" aria-label="lang" id="goods-age">
                                            <c:forEach items="${gpm.goodsParametersMap.get('age')}" var="ageParam">
                                                <option value="${ageParam.value}">${ageParam.value}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-auto">
                                        <label for="category" class="col-form-label">Category</label>
                                    </div>
                                    <div class="col-auto" id="category">
                                        <select name="category" class="form-select align-content-end" aria-label="lang" id="goods-category">
                                        <c:forEach items="${gpm.goodsParametersMap.get('category')}" var="categoryParam">
                                            <option value="${categoryParam.value}">${categoryParam.value}</option>
                                        </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-auto">
                                        <label for="gender" class="col-form-label">Gender</label>
                                    </div>
                                    <div class="col-auto" id="gender">
                                        <select name="gender" class="form-select align-content-end" aria-label="lang" id="goods-gender">
                                        <c:forEach items="${gpm.goodsParametersMap.get('gender')}" var="genderParam">
                                            <option value="${genderParam.value}">${genderParam.value}</option>
                                        </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-auto">
                                        <label for="size" class="col-form-label">Gender</label>
                                    </div>
                                    <div class="col-auto" id="size">
                                        <select name="size" class="form-select align-content-end" aria-label="lang" id="goods-size">
                                        <c:forEach items="${gpm.goodsParametersMap.get('size')}" var="sizeParam">
                                            <option value="${sizeParam.value}">${sizeParam.value}</option>
                                        </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-auto">
                                        <label for="style" class="col-form-label">Gender</label>
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
                                        <button name="action" class="btn btn-success" type="submit" value="confirm">Confirm</button>
                                    </div>
                                    <div class="col-auto">
                                        <button name="action" class="btn btn-danger" type="submit" value="remove">Remove</button>
                                    </div>
                                </div>
                            </form>
                        </aside>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<div class="container-fluid pb-3">
    <div class="row justify-content-end shadow-sm">
        <div class="col-2 text-end my-1">
            <span class="align-middle my-1">Sort by |</span>
        </div>
        <div class="col-2">
            <select class="form-select" style="width: 200px" aria-label="sort-by" id="sort-by" onchange="doIt()">
                <option selected value="newest">Newest</option>
                <option value="name_asc">Name ascending</option>
                <option value="name_desc">Name descending</option>
                <option value="price_ltoh">Price Low to High</option>
                <option value="price_htol">Price High to Low</option>
            </select>
        </div>
    </div>
    <div class="row">
        <div class="col-2 my-3">
            <aside class="blog-sidebar rounded shadow-sm">
                <div class="flex-shrink-0 p-3 bg-white" style="width: 200px;">
                    <ul class="list-unstyled ps-0">
                        <li class="mb-1">
                            <button class="btn btn-toggle align-items-center rounded collapsed"
                                    data-bs-toggle="collapse"
                                    data-bs-target="#price-collapse" aria-expanded="false">Price
                            </button>
                            <aside class="collapse show my-2" id="price-collapse">
                                <ul class="btn-toggle-nav list-unstyled fw-normal pb-1">
                                    <li class="row">
                                        <div class="col">
                                            <input class="form-control" type="text" value="0" id="min-price"
                                                   onchange="doIt()">
                                        </div>
                                        <div class="col">
                                            <input class="form-control" type="text" value="9999" id="max-price"
                                                   onchange="doIt()">
                                        </div>
                                    </li>
                                </ul>
                            </aside>
                        <li class="border-top my-3"></li>
                        <li class="mb-1">
                            <button class="btn btn-toggle align-items-center rounded collapsed"
                                    data-bs-toggle="collapse"
                                    data-bs-target="#age-collapse" aria-expanded="false">Age
                            </button>
                            <aside class="collapse my-1" id="age-collapse">
                                <ul class="btn-toggle-nav list-unstyled fw-normal pb-1">
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckBaby"
                                               value=":baby">
                                        <label class="form-check-label" for="flexCheckBaby">Baby</label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckToddler"
                                               value=":toddler">
                                        <label class="form-check-label" for="flexCheckToddler">Toddler</label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckKid" value=":kid">
                                        <label class="form-check-label" for="flexCheckKid">Kid</label>
                                    </div>
                                </ul>
                            </aside>
                        <li class="border-top my-3"></li>
                        <li class="mb-1">
                            <button class="btn btn-toggle align-items-center rounded collapsed"
                                    data-bs-toggle="collapse"
                                    data-bs-target="#category-collapse" aria-expanded="false">
                                Category
                            </button>
                            <aside class="collapse hide my-1" id="category-collapse">
                                <ul class="btn-toggle-nav list-unstyled fw-normal pb-1">
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckBottoms"
                                               value=":bottoms">
                                        <label class="form-check-label" for="flexCheckBottoms">Bottoms</label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckTops"
                                               value=":tops">
                                        <label class="form-check-label" for="flexCheckTops">Tops</label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckPajamas"
                                               value=":pajamas">
                                        <label class="form-check-label" for="flexCheckPajamas">Pajamas</label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckSets"
                                               value=":sets">
                                        <label class="form-check-label" for="flexCheckSets">Sets</label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckShoes"
                                               value=":shoes">
                                        <label class="form-check-label" for="flexCheckShoes">Shoes</label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckDresses-cat"
                                               value=":dresses">
                                        <label class="form-check-label" for="flexCheckDresses-cat">Dresses</label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckRompers-cat"
                                               value=":rompers">
                                        <label class="form-check-label" for="flexCheckRompers-cat">Rompers</label>
                                    </div>
                                </ul>
                            </aside>
                        <li class="border-top my-3"></li>
                        <li class="mb-1">
                            <button class="btn btn-toggle align-items-center rounded collapsed"
                                    data-bs-toggle="collapse"
                                    data-bs-target="#gender-collapse" aria-expanded="false">
                                Gender
                            </button>
                            <aside class="collapse hide my-1" id="gender-collapse">
                                <ul class="btn-toggle-nav list-unstyled fw-normal pb-1">
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckBoy" value=":boy">
                                        <label class="form-check-label" for="flexCheckBoy">Boy</label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckGirl"
                                               value=":girl">
                                        <label class="form-check-label" for="flexCheckGirl">Girl</label>
                                    </div>
                                </ul>
                            </aside>
                        <li class="border-top my-3"></li>
                        <li class="mb-1">
                            <button class="btn btn-toggle align-items-center rounded collapsed"
                                    data-bs-toggle="collapse"
                                    data-bs-target="#size-collapse" aria-expanded="false">
                                Size
                            </button>
                            <aside class="collapse hide my-1" id="size-collapse">
                                <ul class="btn-toggle-nav list-unstyled fw-normal pb-1">
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheck3m" value=":3m">
                                        <label class="form-check-label" for="flexCheck3m">3M</label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheck6m" value=":6m">
                                        <label class="form-check-label" for="flexCheck6m">6M</label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheck9m" value=":9m">
                                        <label class="form-check-label" for="flexCheck9m">9M</label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheck12m" value=":12m">
                                        <label class="form-check-label" for="flexCheck12m">12M</label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheck18m" value=":18m">
                                        <label class="form-check-label" for="flexCheck18m">18M</label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheck24m" value=":24m">
                                        <label class="form-check-label" for="flexCheck24m">24M</label>
                                    </div>
                                </ul>
                            </aside>
                        <li class="border-top my-3"></li>
                        <li class="mb-1">
                            <button class="btn btn-toggle align-items-center rounded collapsed"
                                    data-bs-toggle="collapse"
                                    data-bs-target="#style-collapse" aria-expanded="false" id="styleButton"
                                    value="style">
                                Style
                            </button>
                            <aside class="collapse hide my-1" id="style-collapse">
                                <ul class="btn-toggle-nav list-unstyled fw-normal pb-1">
                                    <div class="form-switch form-check">
                                        <input class="form-check-input" type="checkbox" id="flexCheckGraphics"
                                               value=":graphics">
                                        <label class="form-check-label" for="flexCheckGraphics">Graphics</label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckCasual"
                                               value=":casual">
                                        <label class="form-check-label" for="flexCheckCasual">Casual</label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckDresses"
                                               value=":dresses">
                                        <label class="form-check-label" for="flexCheckDresses">Dresses</label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckShorts"
                                               value=":shorts">
                                        <label class="form-check-label" for="flexCheckToddler">Shorts</label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckTopTees"
                                               value=":top_tees">
                                        <label class="form-check-label" for="flexCheckTopTees">Top&Tees</label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckSleepPlay"
                                               value=":sleep_play">
                                        <label class="form-check-label" for="flexCheckSleepPlay">Sleep&Play</label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckRomper"
                                               value=":romper">
                                        <label class="form-check-label" for="flexCheckRomper">Romper</label>
                                    </div>
                                </ul>
                            </aside>
                        <li class="border-top my-3"></li>
                    </ul>
                </div>
            </aside><!-- /.blog-sidebar -->
        </div>
        <div class="col-10">
            <div class="row" id="mainRow">
            </div>
        </div>
    </div>
</div>
<script>
    window.onload = function () {
        doIt();
    }
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/admin_catalog.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/cart.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/filter.js"></script>
<script type="text/javascript " src="${pageContext.request.contextPath}/js/sidebars.js"></script>
</body>
</html>
