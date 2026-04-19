<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en" data-theme="light">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Store • Products</title>
    <style>
        :root {
            --bg:#f4efe8; --surface:#fffaf3; --text:#2f241f; --muted:#7b6a5f; --primary:#b3541e; --primary-strong:#8c3f14; --border:#e5d7c6; --chip:#f8e6d7; --shadow:rgba(74,48,33,.12);
        }
        :root[data-theme='dark'] {
            --bg:#171311; --surface:#241c18; --text:#f3e9de; --muted:#c8b8aa; --primary:#e58f54; --primary-strong:#f2ad7c; --border:#3b2f29; --chip:#3a2a20; --shadow:rgba(0,0,0,.35);
        }
        body { margin:0; font-family:Inter,Segoe UI,sans-serif; background:var(--bg); color:var(--text); }
        .container { max-width:1100px; margin:2rem auto; padding:0 1rem; }
        .header { display:flex; justify-content:space-between; align-items:center; margin-bottom:1rem; gap:.75rem; flex-wrap:wrap; }
        .btn { border-radius:10px; border:1px solid transparent; padding:.55rem .9rem; text-decoration:none; font-weight:600; cursor:pointer; }
        .btn-primary { background:var(--primary); color:#fff; }
        .btn-primary:hover { background:var(--primary-strong); }
        .btn-outline { color:var(--text); background:transparent; border-color:var(--border); }
        .grid { display:grid; grid-template-columns:repeat(auto-fill,minmax(260px,1fr)); gap:1rem; }
        .card { background:var(--surface); border:1px solid var(--border); border-radius:14px; box-shadow:0 6px 16px var(--shadow); padding:1rem; display:grid; gap:.55rem; }
        .desc { color:var(--muted); min-height:2.8rem; }
        .price { font-size:1.1rem; font-weight:700; }
        .chip { display:inline-block; background:var(--chip); color:var(--muted); padding:.2rem .55rem; border-radius:999px; font-size:.84rem; }
        form { display:flex; gap:.5rem; align-items:center; }
        input[type='number'] { width:75px; padding:.45rem .55rem; border:1px solid var(--border); border-radius:8px; background:transparent; color:var(--text); }
        .empty { background:var(--surface); border:1px dashed var(--border); border-radius:12px; padding:1rem; text-align:center; color:var(--muted); }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>Product Catalog</h1>
        <div>
            <button id="themeToggle" class="btn btn-outline" type="button">Toggle theme</button>
            <a class="btn btn-outline" href="${pageContext.request.contextPath}/cart">View Cart</a>
            <a class="btn btn-outline" href="${pageContext.request.contextPath}/admin/products">Admin</a>
        </div>
    </div>

    <c:choose>
        <c:when test="${empty products}">
            <div class="empty">No products are available right now.</div>
        </c:when>
        <c:otherwise>
            <div class="grid">
                <c:forEach var="product" items="${products}">
                    <div class="card">
                        <h3>${product.name}</h3>
                        <div class="desc">${product.description}</div>
                        <div class="price">$${product.price}</div>
                        <span class="chip">Stock: ${product.stock}</span>

                        <form method="post" action="${pageContext.request.contextPath}/cart">
                            <input type="hidden" name="productId" value="${product.id}">
                            <label for="qty-${product.id}">Qty</label>
                            <input id="qty-${product.id}" type="number" name="quantity" min="1" value="1" required>
                            <button class="btn btn-primary" type="submit">Add to Cart</button>
                        </form>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<script>
    const root = document.documentElement;
    const savedTheme = localStorage.getItem('ops_theme');
    if (savedTheme) root.setAttribute('data-theme', savedTheme);
    document.getElementById('themeToggle').addEventListener('click', () => {
        const next = root.getAttribute('data-theme') === 'dark' ? 'light' : 'dark';
        root.setAttribute('data-theme', next);
        localStorage.setItem('ops_theme', next);
    });
</script>
</body>
</html>
