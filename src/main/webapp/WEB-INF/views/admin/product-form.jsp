<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en" data-theme="light">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin • Product Form</title>
    <style>
        :root {
            --bg: #f4efe8; --surface: #fffaf3; --text: #2f241f; --muted: #7b6a5f;
            --primary: #b3541e; --primary-strong: #8c3f14; --border: #e5d7c6;
            --danger: #b42318; --shadow: rgba(74,48,33,.12);
        }
        :root[data-theme='dark'] {
            --bg: #171311; --surface: #241c18; --text: #f3e9de; --muted: #c8b8aa;
            --primary: #e58f54; --primary-strong: #f2ad7c; --border: #3b2f29;
            --danger: #f97066; --shadow: rgba(0,0,0,.35);
        }
        body { margin:0; font-family: Inter, Segoe UI, sans-serif; background:var(--bg); color:var(--text); }
        .container { max-width: 720px; margin:2rem auto; padding:0 1rem; }
        .card { background:var(--surface); border:1px solid var(--border); border-radius:16px; box-shadow:0 8px 20px var(--shadow); }
        .header { display:flex; justify-content:space-between; align-items:center; padding:1rem 1.25rem; border-bottom:1px solid var(--border); }
        h1 { margin:0; font-size:1.3rem; }
        form { padding:1.2rem; display:grid; gap:.9rem; }
        label { font-weight:600; }
        input, textarea {
            width:100%; padding:.65rem .75rem; border:1px solid var(--border); border-radius:10px;
            background:transparent; color:var(--text); font:inherit;
        }
        textarea { min-height:110px; resize:vertical; }
        .error { background: color-mix(in srgb, var(--danger) 14%, transparent); color:var(--danger); border:1px solid color-mix(in srgb, var(--danger) 30%, var(--border)); padding:.65rem .8rem; border-radius:10px; }
        .actions { display:flex; gap:.65rem; flex-wrap:wrap; }
        .btn { border:1px solid transparent; border-radius:10px; padding:.55rem .95rem; cursor:pointer; font-weight:600; text-decoration:none; }
        .btn-primary { background:var(--primary); color:#fff; }
        .btn-primary:hover { background:var(--primary-strong); }
        .btn-outline { color:var(--text); border-color:var(--border); background:transparent; }
    </style>
</head>
<body>
<div class="container">
    <div class="card">
        <div class="header">
            <h1><c:choose><c:when test="${not empty product}">Edit Product</c:when><c:otherwise>Add Product</c:otherwise></c:choose></h1>
            <button class="btn btn-outline" id="themeToggle" type="button">Toggle theme</button>
        </div>

        <form method="post" action="${pageContext.request.contextPath}/admin/products">
            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>

            <c:if test="${not empty product}">
                <input type="hidden" name="id" value="${product.id}">
            </c:if>

            <div>
                <label for="name">Product Name</label>
                <input id="name" name="name" type="text" required value="${product.name}">
            </div>

            <div>
                <label for="description">Description</label>
                <textarea id="description" name="description" required>${product.description}</textarea>
            </div>

            <div>
                <label for="price">Price</label>
                <input id="price" name="price" type="number" step="0.01" min="0" required value="${product.price}">
            </div>

            <div>
                <label for="stock">Stock</label>
                <input id="stock" name="stock" type="number" min="0" required value="${product.stock}">
            </div>

            <div class="actions">
                <button class="btn btn-primary" type="submit">Save Product</button>
                <a class="btn btn-outline" href="${pageContext.request.contextPath}/admin/products">Cancel</a>
            </div>
        </form>
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
