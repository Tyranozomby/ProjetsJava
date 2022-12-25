<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<jsp:include page="includes/head.jsp">
    <jsp:param name="title" value="Book Store | Tous nos livres"/>
    <jsp:param name="page" value="books_list"/>
</jsp:include>
<body>
<%@ include file="includes/header.jsp" %>

<main class="container-fluid mt-3">
    <div class="row">
        <c:if test="${books.isEmpty()}">
            <div class="col-md-12">
                <h2 class="text-center">No book matches your search</h2>
            </div>
        </c:if>
        <c:if test="${!books.isEmpty()}">
            <c:forEach items="${books}" var="book">
                <div class="col-sm-6 col-md-4 col-lg-3 mb-4 position-relative" id="book-${book.id}">
                    <a href="books?bookId=${book.id}"
                       class="card h-100 position-relative text-decoration-none text-reset pt-3">
                        <img src="${pageContext.request.contextPath}${book.coverArt}" class="card-img-top"
                             style="max-height: 10vw; object-fit: contain" alt="Book cover">
                        <div class="card-body">
                            <h5 class="card-title">${book.title}</h5>
                            <p class="card-text">${book.author}</p>
                            <p class="card-text">${book.genre}</p>
                        </div>
                        <div id="book-${book.id}-price" class="position-absolute text-success"
                             style="bottom: 10px; right: 10px; font-size: larger;">
                                ${book.price}&euro;
                        </div>
                    </a>
                </div>
            </c:forEach>
        </c:if>
    </div>
</main>

<%@ include file="includes/footer.jsp" %>
</body>
</html>