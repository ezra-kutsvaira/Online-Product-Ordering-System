<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en" data-theme="light">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Store • Cart</title>
    <style>
        :root { --bg:#f4efe8; --surface:#fffaf3; --text:#2f241f; --muted:#7b6a5f; --primary:#b3541e; --primary-strong:#8c3f14; --border:#e5d7c6; --danger:#b42318; --shadow:rgba(74,48,33,.12); }
        :root[data-theme='dark'] { --bg:#171311; --surface:#241c18; --text:#f3e9de; --muted:#c8b8aa; --primary:#e58f54; --primary-strong:#f2ad7c; --border:#3b2f29; --danger:#f97066; --shadow:rgba(0,0,0,.35); }
        body { margin:0; font-family:Inter,Segoe UI,sans-serif; background:var(--bg); color:var(--text); }
        .container { max-width:980px; margin:2rem auto; padding:0 1rem; }
        .panel { background:var(--surface); border:1px solid var(--border); border-radius:14px; box-shadow:0 8px 18px var(--shadow); }
        .header { display:flex; justify-content:space-between; align-items:center; padding:1rem 1.2rem; border-bottom:1px solid var(--border); gap:.6rem; }
        .btn { border:1px solid transparent; border-radius:10px; padding:.52rem .9rem; font-weight:600; text-decoration:none; cursor:pointer; }
        .btn-primary { background:var(--primary); color:#fff; }
        .btn-primary:hover { background:var(--primary-strong); }
        .btn-outline { color:var(--text); border-color:var(--border); background:transparent; }
        .btn-danger { color:var(--danger); border-color: color-mix(in srgb, var(--danger) 45%, var(--border)); background:transparent; }
        table { width:100%; border-collapse:collapse; }
        th, td { padding:.82rem 1rem; border-bottom:1px solid var(--border); text-align:left; }
        th { color:var(--muted); }
        .content { padding: 0 0 1.1rem; }
        .empty { margin:1rem; border:1px dashed var(--border); border-radius:10px; text-align:center; color:var(--muted); padding:1rem; }
        .error { margin:1rem; background: color-mix(in srgb, var(--danger) 14%, transparent); color:var(--danger); border:1px solid color-mix(in srgb, var(--danger) 30%, var(--border)); padding:.65rem .8rem; border-radius:10px; }
        .checkout { padding: 1rem; border-top:1px solid var(--border); display:grid; gap:.65rem; }
        input[type='text'] { padding:.6rem .7rem; border:1px solid var(--border); border-radius:10px; background:transparent; color:var(--text); max-width:320px; }
    </style>
</head>
<body>
<div class="container">
    <div class="panel">
        <div class="header">
            <h1>Your Cart</h1>
            <div>
                <button class="btn btn-outline" id="themeToggle" type="button">Toggle theme</button>
                <a class="btn btn-outline" href="${pageContext.request.contextPath}/products">Continue Shopping</a>
            </div>
        </div>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <div class="content">
            <c:choose>
                <c:when test="${empty sessionScope.cart}">
                    <div class="empty">Your cart is empty.</div>
                </c:when>
                <c:otherwise>
                    <table>
                        <thead>
                        <tr>
                            <th>Product</th>
                            <th>Unit Price</th>
                            <th>Qty</th>
                            <th>Line Total</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="item" items="${sessionScope.cart}">
                            <tr>
                                <td>${item.productName}</td>
                                <td>$${item.unitPrice}</td>
                                <td>${item.quantity}</td>
                                <td>$${item.lineTotal}</td>
                                <td>
                                    <form method="post" action="${pageContext.request.contextPath}/cart">
                                        <input type="hidden" name="action" value="remove">
                                        <input type="hidden" name="productId" value="${item.productId}">
                                        <button class="btn btn-danger" type="submit">Remove</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                    <div class="checkout">
                        <h3>Place Order</h3>
                        <form method="post" action="${pageContext.request.contextPath}/checkout">
                            <label for="customerName">Customer Name</label><br>
                            <input id="customerName" type="text" name="customerName" required>
                            <button class="btn btn-primary" type="submit">Checkout</button>
                        </form>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
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
