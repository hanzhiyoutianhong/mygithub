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
<body>
<center>
    <div id="code"></div>
</center>
<script>
    $("#code").qrcode({
        render: "table",
        width: 400,
        height:400,
        text: '<%=request.getParameter("code_url")%>'
    });
</script>
</body>
</html>
