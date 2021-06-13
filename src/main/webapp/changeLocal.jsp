<%@ include file="WEB-INF/jspf/head.jspf" %>

<fmt:setLocale value="${param.locale}" scope="session"/>

<%-- load the bundle (by locale) --%>
<fmt:setBundle basename="resources"/>
${param.locale}
<%-- set current locale to session --%>
<c:set var="currentLocale" value="${param.locale}" scope="session"/>

<%-- goto back to the settings--%>
<c:if test="${empty user||user.roleId>2}">
    <jsp:forward page="user/catalog.jsp"/>
</c:if>
<c:if test="${not empty user}">
    <jsp:forward page="admin/admin-orders.jsp"/>
</c:if>

