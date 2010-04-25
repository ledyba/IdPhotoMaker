
<%@ page import="app.Photo" %>
<html>
  <head>
    <meta name="layout" content="main" />
    <g:javascript library="scriptaculous" />
    <g:javascript src="pdf_create.js" />
    <title>エラーが発生しました</title>
  </head>
  <body>
    <p>エラーが発生しています。次の項目を確認してください。</p>
  <ul>
    <g:each in="${errorMessages}">
         <li>${it}</li>
    </g:each>
  </ul>
</body>
</html>
