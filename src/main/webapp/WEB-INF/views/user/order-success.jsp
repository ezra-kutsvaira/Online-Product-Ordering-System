<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en" data-theme="light">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Confirmed</title>
    <style>
        :root { --bg:#f4efe8; --surface:#fffaf3; --text:#2f241f; --muted:#7b6a5f; --primary:#b3541e; --primary-strong:#8c3f14; --border:#e5d7c6; --shadow:rgba(74,48,33,.12); }
        :root[data-theme='dark'] { --bg:#171311; --surface:#241c18; --text:#f3e9de; --muted:#c8b8aa; --primary:#e58f54; --primary-strong:#f2ad7c; --border:#3b2f29; --shadow:rgba(0,0,0,.35); }
        body { margin:0; min-height:100vh; display:grid; place-items:center; font-family:Inter,Segoe UI,sans-serif; background:var(--bg); color:var(--text); }
        .card { width:min(560px, 92vw); background:var(--surface); border:1px solid var(--border); border-radius:16px; box-shadow:0 10px 22px var(--shadow); padding:1.35rem; text-align:center; }
        h1 { margin:.3rem 0 .45rem; }
        .order-id { font-size:1.2rem; font-weight:700; margin:.6rem 0; }
        .muted { color:var(--muted); }
        .btn { display:inline-block; margin-top:1rem; border-radius:10px; border:1px solid transparent; padding:.55rem .95rem; text-decoration:none; font-weight:600; }
        .btn-primary { background:var(--primary); color:#fff; }
        .btn-primary:hover { background:var(--primary-strong); }
        .btn-outline { border-color:var(--border); color:var(--text); background:transparent; cursor:pointer; }
    </style>
</head>
<body>
<div class="card">
    <button class="btn btn-outline" type="button" id="themeToggle">Toggle theme</button>
    <h1>Thank You! 🎉</h1>
    <p class="muted">Your order has been placed successfully.</p>
    <p class="order-id">Order ID: #${orderId}</p>
    <a class="btn btn-primary" href="${pageContext.request.contextPath}/products">Back to Products</a>
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