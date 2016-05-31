<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/16
  Time: 14:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript" src="js/lib/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="js/lib/jquery.qrcode.min.js"></script>
    <title>linkedme</title>
</head>
<body style="background:url(img/background.jpg);background-size:100%;">
<div id="code" style="position:absolute;left:50%;top:50%;margin-left:-200px;margin-top:-200px"></div>
<script>
    $("#code").qrcode({
        render: "table",
        width: 300,
        height: 300,
        text: '<%=request.getParameter("code_url")%>'
    });
</script>
</body>
</html>