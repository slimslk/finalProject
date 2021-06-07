<%@include file="../WEB-INF/jspf/taglib.jspf" %>
<%@include file="../WEB-INF/jspf/page.jspf" %>

<html>
<c:set value="Main Page" var="title"/>
<%@include file="../WEB-INF/jspf/head.jspf" %>
<body>
<%@include file="../WEB-INF/jspf/header.jspf" %>
<link href="${pageContext.request.contextPath}/style/sidebars.css" rel="stylesheet">
<hr/>
<hr/>
<template id="tempCard">
    <div class="col-4 my-3">
        <div class="card" style="width: 18rem;">
            <img src="${pageContext.request.contextPath}" class="card-img-top" style="width: 100%" alt="Some text"
                 id="goods-img">
            <div class="card-body">
                <p class="border-top"></p>
                <h5 class="card-title" id="goods-price">$166.00</h5>
                <p id="goods-name">2-Piece Floral Tank & Bike Short Set</p>
                <div class="d-grid gap-2 justify-content-md-end">
                    <button class="btn btn-primary" value="#" id="goods-addToCart" onclick="addToCart(this.value)">Add
                        to cart
                    </button>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/addToCart.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/cart.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/filter.js"></script>
<script type="text/javascript " src="${pageContext.request.contextPath}/js/sidebars.js"></script>
</body>
</html>
