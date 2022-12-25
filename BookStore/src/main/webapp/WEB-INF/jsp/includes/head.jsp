<%@ page contentType="text/html;charset=UTF-8" %>

<head>
    <title>
        <%= request.getParameter("title")%>
    </title>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <link rel="stylesheet" href="https://bootswatch.com/4/darkly/bootstrap.min.css">

    <link rel="stylesheet"
          href="https://site-assets.fontawesome.com/releases/v6.2.1/css/all.css"/>

    <script>
        window.addEventListener("load", () => syncShopList());

        function syncShopList(newList) {
            const update = (data) => {
                localStorage.setItem("shopping-list", JSON.stringify(data));
                if (data.length > 0) {
                    document.getElementById("shop-list-count").innerText = data.length;
                }
            }

            if (newList && newList.length > 0) {
                update(newList);
            } else {
                fetch('${pageContext.request.contextPath}/api/shopping-list')
                    .then(response => response.json())
                    .then(data => update(data));
            }
        }
    </script>

    <%
        String pageParam = request.getParameter("page");
        if (pageParam != null && pageParam.equals("book")) {
    %>
    <script type="text/javascript">
        window.addEventListener("load", () => {
            let addButton = document.getElementById('add-to-cart');
            addButton.addEventListener('click', book_addToCart);
            if (localStorage.getItem("shopping-list") != null) {
                let shoppingList = JSON.parse(localStorage.getItem("shopping-list"));
                if (shoppingList.find(id => id === <%=request.getParameter("bookId")%>)) {
                    addButton.classList.add("disabled");
                    addButton.disabled = true;
                    addButton.innerText = "Ajouté au panier";
                }
            }
        });

        function book_addToCart() {
            fetch('${pageContext.request.contextPath}/api/shopping-list', {
                method: 'POST',
                body: '${book.id}',
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(res => {
                if (res.ok) {
                    syncShopList();
                    let addButton = document.getElementById("add-to-cart");
                    addButton.classList.add("disabled");
                    addButton.disabled = true;
                    addButton.innerText = "Ajouté au panier";
                } else {
                    alert("Ya eu un problème kek part");
                }
            });
        }
    </script>
    <%
    } else if (pageParam != null && pageParam.equals("books_list")) {
    %>
    <script type="text/javascript">
        window.addEventListener("load", () => {
            const items = localStorage.getItem("shopping-list");
            if (items) {
                let shoppingList = JSON.parse(items);
                shoppingList.forEach(id => {
                    const i = document.createElement("i");
                    i.classList.add("fa-solid", "fa-check-circle");
                    i.title = "In your cart";

                    document.getElementById("book-" + id + "-price").prepend(i);
                })
            }
        })
    </script>
    <%
    } else if (pageParam != null && pageParam.equals("shopping_list")) {
    %>
    <script type="application/javascript">
        function deleteItem(id) {
            fetch('${pageContext.request.contextPath}/api/shopping-list', {
                method: 'DELETE',
                body: id,
            }).then(async res => {
                if (res.ok) {
                    syncShopList(await res.json());
                    // reload page parce que trop de trucs à changer là (panier, prix, livre)
                    window.location.reload();
                }
            });
        }
    </script>
    <%
        }
    %>
</head>