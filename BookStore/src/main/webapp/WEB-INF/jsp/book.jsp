<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>

<jsp:include page="includes/head.jsp">
    <jsp:param name="title" value="Book Store | ${book.title}"/>
    <jsp:param name="page" value="book"/>
</jsp:include>
<body>
<%@ include file="includes/header.jsp" %>

<main class="container-fluid">
    <div class="row">
        <div class="col-md-4">
            <img src="${pageContext.request.contextPath}${book.coverArt}" class="img-fluid" alt="Book cover">
        </div>
        <div class="col-md-8">
            <h1>${book.title}</h1>
            <p>by <em>${book.author}</em></p>
            <p>Publisher: ${book.publisher}</p>
            <p>Publication date: <fmt:formatDate value="${book.publicationDate}" pattern="MMMM yyyy"/></p>
            <p>Genre: ${book.genre}</p>
            <p>Description: ${book.description}</p>
            <p>Page count: ${book.pageCount}</p>
            <p>Price: ${book.price}&euro;</p>
            <p>Availability: ${book.available ? 'Yes' : 'No'}</p>
            <button id="add-to-cart"
                    class="btn ${book.available ? 'btn-primary' : 'btn-danger'}" ${book.available ? '' : 'disabled'}>
                ${book.available ? 'Ajouter au panier' : 'Plus en stock'}
            </button>
        </div>
    </div>
</main>

<%@ include file="includes/footer.jsp" %>
</body>
</html>
