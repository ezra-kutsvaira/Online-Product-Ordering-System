<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en" data-theme="light">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${empty errorTitle ? 'Error' : errorTitle}</title>
    <style>
        :root { --bg:#f4efe8; --surface:#fffaf3; --text:#2f241f; --muted:#7b6a5f; --danger:#b42318; --border:#e5d7c6; --shadow:rgba(74,48,33,.12); }
        :root[data-theme='dark'] { --bg:#171311; --surface:#241c18; --text:#f3e9de; --muted:#c8b8aa; --danger:#f97066; --border:#3b2f29; --shadow:rgba(0,0,0,.35); }
        body { margin:0; min-height:100vh; display:grid; place-items:center; font-family:Inter,Segoe UI,sans-serif; background:var(--bg); color:var(--text); }
        .card { width:min(620px, 92vw); background:var(--surface); border:1px solid var(--border); border-radius:16px; box-shadow:0 10px 22px var(--shadow); padding:1.2rem; }
        .title { margin:.2rem 0 .6rem; color:var(--danger); }
        .msg { color:var(--muted); margin:0 0 1rem; }
        .actions { display:flex; gap:.6rem; flex-wrap:wrap; }
        .btn { border-radius:10px; border:1px solid var(--border); padding:.52rem .92rem; text-decoration:none; font-weight:600; color:var(--text); background:transparent; cursor:pointer; }
    </style>
</head>
<body>
<div class="card">
    <button class="btn" type="button" id="themeToggle">Toggle theme</button>
    <h1 class="title">${empty errorTitle ? 'Something went wrong' : errorTitle}</h1>
    <p class="msg">${empty errorMessage ? 'An unexpected error occurred. Please try again.' : errorMessage}</p>
    <div class="actions">
        <a class="btn" href="${pageContext.request.contextPath}/products">Back to Products</a>
        <a class="btn" href="${pageContext.request.contextPath}/admin/products">Admin Products</a>
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
