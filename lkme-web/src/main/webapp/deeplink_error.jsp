<%--
  Created by IntelliJ IDEA.
  User: vontroy
  Date: 2016/6/12
  Time: 17:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>LinkedME</title>
</head>
<body>
<div style="background-color:#ffffff;width:100%;height:100%;position:absolute; top:0;"><img id="imgId" src="img/cn/invalid_link.png" align="center" style="height: 100%;width:100%"></div>
    <script>
        var imgSrc = /^en/.test(navigator.language) ? "img/en/invalid_link.png" : "img/cn/invalid_link.png";
        document.getElementById("imgId").src = imgSrc;
    </script>
</body>
</html>
