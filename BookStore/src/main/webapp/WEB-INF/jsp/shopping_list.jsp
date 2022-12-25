<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<jsp:include page="includes/head.jsp">
    <jsp:param name="title" value="Book Store | My List"/>
    <jsp:param name="page" value="shopping_list"/>
</jsp:include>
<body>
<%@ include file="includes/header.jsp" %>

<main class="container mt-5">
    <h1>Shopping List</h1>
    <div class="row justify-content-end mt-3">
        <div class="col-auto">
            <c:set var="totalPrice" value="0"/>
            <c:forEach var="book" items="${shoppingList}">
                <c:set var="totalPrice" value="${totalPrice + book.price}"/>
            </c:forEach>
            <a href="${pageContext.request.contextPath}/payment"
               class="btn btn-success ${totalPrice == 0 ? 'disabled': ''}" ${totalPrice == 0 ? 'disabled': ''}>
                <strong>Total:</strong> <fmt:formatNumber value="${totalPrice}" type="currency" currencyCode="EUR"/>
            </a>
        </div>
    </div>
    <div class="row">
        <c:forEach var="book" items="${shoppingList}">
            <div id="shopping-${book.id}" class="col-md-3 mb-3 d-flex flex-column">
                <a href="books?bookId=${book.id}" target="_blank"
                   class="card h-100 d-flex flex-column text-decoration-none text-reset">
                    <img src="${pageContext.request.contextPath}${book.coverArt}" class="card-img-top" alt="Cover art">
                    <div class="card-body">
                        <h5 class="card-title">${book.title}</h5>
                        <p class="card-text">${book.author}</p>
                        <p class="card-text text-success">
                            <strong>
                                <fmt:formatNumber value="${book.price}" type="currency" currencyCode="EUR"/>
                            </strong>
                        </p>
                    </div>
                </a>
                <button class="btn btn-danger" onclick="deleteItem(${book.id})">Delete</button>
            </div>
            <c:set var="totalPrice" value="${totalPrice + book.price}"/>
        </c:forEach>
    </div>
</main>

<%@ include file="includes/footer.jsp" %>
</body>
</html>

