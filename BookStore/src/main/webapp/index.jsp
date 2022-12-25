<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<jsp:include page="WEB-INF/jsp/includes/head.jsp">
    <jsp:param name="title" value="Book Store"/>
</jsp:include>
<body>
<%@ include file="WEB-INF/jsp/includes/header.jsp" %>

<main class="container mt-5">
    <h1 class="text-center">Welcome to the Book Store</h1>
    <p class="text-center mt-4">This website was created by Eliott Rogeaux (moi)</p>
    <div class="text-center mt-5">
        <a href="${pageContext.request.contextPath}/books" class="btn btn-primary">Discover New Books</a>
    </div>
</main>

<%@ include file="WEB-INF/jsp/includes/footer.jsp" %>
</body>
</html>
