<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en" data-theme="light">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin • Product Catalog</title>
    <style>
        :root {
            --bg: #f4efe8;
            --surface: #fffaf3;
            --text: #2f241f;
            --muted: #7b6a5f;
            --primary: #b3541e;
            --primary-strong: #8c3f14;
            --border: #e5d7c6;
            --danger: #b42318;
            --chip: #f8e6d7;
            --shadow: rgba(74, 48, 33, 0.12);
        }

        :root[data-theme='dark'] {
            --bg: #171311;
            --surface: #241c18;
            --text: #f3e9de;
            --muted: #c8b8aa;
            --primary: #e58f54;
            --primary-strong: #f2ad7c;
            --border: #3b2f29;
            --danger: #f97066;
            --chip: #3a2a20;
            --shadow: rgba(0, 0, 0, 0.35);
        }

        * { box-sizing: border-box; }
        body {
            margin: 0;
            font-family: Inter, Segoe UI, Roboto, Helvetica, Arial, sans-serif;
            background: var(--bg);
            color: var(--text);
        }
        .container { max-width: 1100px; margin: 2rem auto; padding: 0 1rem; }
        .panel {
            background: var(--surface);
            border: 1px solid var(--border);
            border-radius: 16px;
            box-shadow: 0 8px 20px var(--shadow);
            overflow: hidden;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 1rem 1.25rem;
            border-bottom: 1px solid var(--border);
            gap: .75rem;
        }
        h1 { margin: 0; font-size: 1.35rem; }
        .btn, .link-btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            gap: .35rem;
            border-radius: 10px;
            border: 1px solid transparent;
            cursor: pointer;
            text-decoration: none;
            font-weight: 600;
            padding: .55rem .9rem;
            font-size: .95rem;
        }
        .btn-primary { background: var(--primary); color: #fff; }
        .btn-primary:hover { background: var(--primary-strong); }
        .btn-outline { background: transparent; border-color: var(--border); color: var(--text); }
        .btn-danger { color: var(--danger); border-color: color-mix(in srgb, var(--danger) 45%, var(--border)); }

        table { width: 100%; border-collapse: collapse; }
        th, td { text-align: left; padding: .85rem 1rem; border-bottom: 1px solid var(--border); }
        th { color: var(--muted); font-weight: 600; background: color-mix(in srgb, var(--surface) 90%, var(--chip)); }
        .actions { display: flex; gap: .5rem; flex-wrap: wrap; }
        .empty {
            margin: 1rem;
            padding: 1.2rem;
            border-radius: 12px;
            background: var(--chip);
            color: var(--muted);
            text-align: center;
        }
        .nav-links { padding: 1rem 1.25rem; }
    </style>
</head>
<body>
<div class="container">
    <div class="panel">
        <div class="header">
            <h1>Admin Product Management</h1>
            <div>
                <button class="btn btn-outline" id="themeToggle" type="button">Toggle theme</button>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/products?action=new">+ Add Product</a>
            </div>
        </div>

        <c:choose>
            <c:when test="${empty products}">
                <div class="empty">No products available. Add your first product.</div>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Price</th>
                        <th>Stock</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="product" items="${products}">
                        <tr>
                            <td>${product.id}</td>
                            <td>${product.name}</td>
                            <td>${product.description}</td>
                            <td>$${product.price}</td>
                            <td>${product.stock}</td>
                            <td class="actions">
                                <a class="link-btn btn-outline" href="${pageContext.request.contextPath}/admin/products?action=edit&id=${product.id}">Edit</a>
                                <a class="link-btn btn-danger" href="${pageContext.request.contextPath}/admin/products?action=delete&id=${product.id}" onclick="return confirm('Delete this product?');">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>

        <div class="nav-links">
            <a class="link-btn btn-outline" href="${pageContext.request.contextPath}/products">Open User Storefront</a>
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
