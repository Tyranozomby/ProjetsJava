<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>

<jsp:include page="includes/head.jsp">
    <jsp:param name="title" value="Book Store | PAYE !"/>
</jsp:include>
<body>
<%@ include file="includes/header.jsp" %>

<main>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header">Payment</div>
                    <div class="card-body">
                        <h4>Books in your shopping list:</h4>
                        <ul>
                            <c:set var="totalPrice" value="0"/>
                            <c:forEach items="${shoppingList}" var="book">
                                <li>${book.title}</li>
                                <c:set var="totalPrice" value="${totalPrice + book.price}"/>
                            </c:forEach>
                        </ul>
                        <h4>Total price:
                            <fmt:formatNumber value="${totalPrice}" type="currency" currencyCode="EUR"/>
                        </h4>
                        <p>Please enter your payment information below to complete the transaction:</p>
                        <form>
                            <div class="form-group">
                                <label for="cardNumber">Card number</label>
                                <input type="text" class="form-control" id="cardNumber" placeholder="Enter card number">
                            </div>
                            <div class="form-group">
                                <label for="cardHolder">Card holder</label>
                                <input type="text" class="form-control" id="cardHolder"
                                       placeholder="Enter card holder name">
                            </div>
                            <div class="form-group">
                                <label for="expirationDate">Expiration date</label>
                                <input type="text" class="form-control" id="expirationDate"
                                       placeholder="Enter expiration date (MM/YY)">
                            </div>
                            <div class="form-group">
                                <label for="securityCode">Security code</label>
                                <input type="text" class="form-control" id="securityCode"
                                       placeholder="Enter security code">
                            </div>
                            <button type="submit" class="btn btn-primary">Pay</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<%@ include file="includes/footer.jsp" %>
</body>
</html>