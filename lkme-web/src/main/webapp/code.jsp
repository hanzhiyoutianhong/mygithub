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
    <script type="text/javascript" src="jsserver/lib/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="jsserver/lib/jquery.qrcode.min.js"></script>
    <title>linkedme</title>
</head>
<body style="background:url(img/background.jpg);background-size:100%;">
<center>
    <div id="code" style="position:absolute;left:50%;top:50%;margin-left:-80px;margin-top:-80px"></div>
</center>
<script>
    $("#code").qrcode({
        render: "table",
        width: 160,
        height:160,
        text: '<%=request.getParameter("code_url")%>'
    });
</script>
</body>
</html>