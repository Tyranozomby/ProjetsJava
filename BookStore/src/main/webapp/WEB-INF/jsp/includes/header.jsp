<header>
    <nav class="navbar navbar-expand-lg">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">Book Store</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="${pageContext.request.contextPath}/">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/books">Book List</a>
                </li>
            </ul>
            <form class="form-inline my-2 my-lg-0" action="${pageContext.request.contextPath}/books" method="get">
                <input class="form-control mr-sm-2" type="search" name="search" placeholder="Search"
                       aria-label="Search" value="${search}">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>
            <a class="nav-link p-0 ml-2" href="${pageContext.request.contextPath}/shopping-list">
                <i class="fa-solid fa-basket-shopping" style="font-size: 1.5rem"></i>
                <span id="shop-list-count" class="badge badge-pill badge-secondary"></span>
            </a>
        </div>
    </nav>
</header>