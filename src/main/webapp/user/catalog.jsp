<%@include file="../WEB-INF/jspf/taglib.jspf" %>
<%@include file="../WEB-INF/jspf/page.jspf" %>

<html>
<c:set value="Main Page" var="title"/>
<%@include file="../WEB-INF/jspf/head.jspf" %>
<body>
<%@include file="../WEB-INF/jspf/header.jspf" %>
<link href="${pageContext.request.contextPath}/style/sidebars.css" rel="stylesheet">
<template id="tempCard">
    <div class="col-4 my-3">
        <div class="card" style="width: 18rem;">
            <img src="${pageContext.request.contextPath}" class="card-img-top mx-auto" style="width: 75%;height: auto" alt="Some text"
                 id="goods-img">
            <div class="card-body">
                <p class="border-top"></p>
                <h5 class="card-title" id="goods-price">$166</h5>
                <p id="goods-name">2-Piece Floral Tank & Bike Short Set</p>
                <p id="goods-size"><fmt:message key="goods.parameters.size"/></p>
                <div class="d-grid gap-2 justify-content-md-end">
                    <button class="btn btn-primary" value="#" id="goods-addToCart" onclick="addToCart(this.value)">
                        <fmt:message key="user.catalog.add_to_cart"/>
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>
<div class="container-fluid pb-3">
    <div class="row justify-content-end shadow-sm">
        <div class="col-2 text-end my-1">
            <span class="align-middle my-1"><fmt:message key="catalog.jsp.sort_by"/> |</span>
        </div>
        <div class="col-2">
            <select class="form-select" style="width: 200px" aria-label="sort-by" id="sort-by" onchange="doIt()">
                <option selected value="newest"><fmt:message key="sort.by.newest"/></option>
                <option value="name_asc"><fmt:message key="sort.by.name.asc"/></option>
                <option value="name_desc"><fmt:message key="sort.by.name.desc"/></option>
                <option value="price_ltoh"><fmt:message key="sort.by.price.asc"/></option>
                <option value="price_htol"><fmt:message key="sort.by.price.desc"/></option>
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
                                    data-bs-target="#price-collapse" aria-expanded="false"><fmt:message key="goods.parameters.price"/>
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
                                    data-bs-target="#age-collapse" aria-expanded="false"><fmt:message key="goods.parameters.age"/>
                            </button>
                            <aside class="collapse my-1" id="age-collapse">
                                <ul class="btn-toggle-nav list-unstyled fw-normal pb-1">
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckBaby"
                                               value=":baby">
                                        <label class="form-check-label" for="flexCheckBaby"><fmt:message key="goods.parameters.age.baby"/></label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckToddler"
                                               value=":toddler">
                                        <label class="form-check-label" for="flexCheckToddler"><fmt:message key="goods.parameters.age.toddler"/></label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckKid" value=":kid">
                                        <label class="form-check-label" for="flexCheckKid"><fmt:message key="goods.parameters.age.kid"/></label>
                                    </div>
                                </ul>
                            </aside>
                        <li class="border-top my-3"></li>
                        <li class="mb-1">
                            <button class="btn btn-toggle align-items-center rounded collapsed"
                                    data-bs-toggle="collapse"
                                    data-bs-target="#category-collapse" aria-expanded="false">
                                <fmt:message key="goods.parameters.category"/>
                            </button>
                            <aside class="collapse hide my-1" id="category-collapse">
                                <ul class="btn-toggle-nav list-unstyled fw-normal pb-1">
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckBottoms"
                                               value=":bottoms">
                                        <label class="form-check-label" for="flexCheckBottoms"><fmt:message key="goods.parameters.category.bottoms"/></label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckTops"
                                               value=":tops">
                                        <label class="form-check-label" for="flexCheckTops"><fmt:message key="goods.parameters.category.tops"/></label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckPajamas"
                                               value=":pajamas">
                                        <label class="form-check-label" for="flexCheckPajamas"><fmt:message key="goods.parameters.category.pajamas"/></label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckSets"
                                               value=":sets">
                                        <label class="form-check-label" for="flexCheckSets"><fmt:message key="goods.parameters.category.sets"/></label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckShoes"
                                               value=":shoes">
                                        <label class="form-check-label" for="flexCheckShoes"><fmt:message key="goods.parameters.category.shoes"/></label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckDresses-cat"
                                               value=":dresses">
                                        <label class="form-check-label" for="flexCheckDresses-cat"><fmt:message key="goods.parameters.category.dresses"/></label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckRompers-cat"
                                               value=":rompers">
                                        <label class="form-check-label" for="flexCheckRompers-cat"><fmt:message key="goods.parameters.category.rompers"/></label>
                                    </div>
                                </ul>
                            </aside>
                        <li class="border-top my-3"></li>
                        <li class="mb-1">
                            <button class="btn btn-toggle align-items-center rounded collapsed"
                                    data-bs-toggle="collapse"
                                    data-bs-target="#gender-collapse" aria-expanded="false">
                                <fmt:message key="goods.parameters.gender"/>
                            </button>
                            <aside class="collapse hide my-1" id="gender-collapse">
                                <ul class="btn-toggle-nav list-unstyled fw-normal pb-1">
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckBoy" value=":boy">
                                        <label class="form-check-label" for="flexCheckBoy"><fmt:message key="goods.parameters.gender.boy"/></label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckGirl"
                                               value=":girl">
                                        <label class="form-check-label" for="flexCheckGirl"><fmt:message key="goods.parameters.gender.girl"/></label>
                                    </div>
                                </ul>
                            </aside>
                        <li class="border-top my-3"></li>
                        <li class="mb-1">
                            <button class="btn btn-toggle align-items-center rounded collapsed"
                                    data-bs-toggle="collapse"
                                    data-bs-target="#size-collapse" aria-expanded="false">
                                <fmt:message key="goods.parameters.size"/>
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
                                <fmt:message key="goods.parameters.style"/>
                            </button>
                            <aside class="collapse hide my-1" id="style-collapse">
                                <ul class="btn-toggle-nav list-unstyled fw-normal pb-1">
                                    <div class="form-switch form-check">
                                        <input class="form-check-input" type="checkbox" id="flexCheckGraphics"
                                               value=":graphics">
                                        <label class="form-check-label" for="flexCheckGraphics"><fmt:message key="goods.parameters.style.graphics"/></label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckCasual"
                                               value=":casual">
                                        <label class="form-check-label" for="flexCheckCasual"><fmt:message key="goods.parameters.style.casual"/></label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckDresses"
                                               value=":dresses">
                                        <label class="form-check-label" for="flexCheckDresses"><fmt:message key="goods.parameters.style.dresses"/></label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckShorts"
                                               value=":shorts">
                                        <label class="form-check-label" for="flexCheckToddler"><fmt:message key="goods.parameters.style.shorts"/></label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckTopTees"
                                               value=":top_tees">
                                        <label class="form-check-label" for="flexCheckTopTees"><fmt:message key="goods.parameters.style.top_tees"/></label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckSleepPlay"
                                               value=":sleep_play">
                                        <label class="form-check-label" for="flexCheckSleepPlay"><fmt:message key="goods.parameters.style.sleep_play"/></label>
                                    </div>
                                    <div class="form-switch form-check ">
                                        <input class="form-check-input" type="checkbox" id="flexCheckRomper"
                                               value=":romper">
                                        <label class="form-check-label" for="flexCheckRomper"><fmt:message key="goods.parameters.style.romper"/></label>
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
            <div class="row">
                <ul class="pagination justify-content-center" id="paginationUl">
                </ul>
            </div>
        </div>
    </div>
</div>

<script>
    window.onload = function () {
        doIt(0);
        setNoGoodsText('<fmt:message key="catalog.jsp.no_goods"/>');
        setSizeText('<fmt:message key="goods.parameters.size"/>');
    }
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/addToCart.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/cart.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/filter.js"></script>
<script type="text/javascript " src="${pageContext.request.contextPath}/js/sidebars.js"></script>
</body>
</html>