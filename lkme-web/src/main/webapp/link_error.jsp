<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String basePath = "https://lkme.cc";
%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <script src="<%=basePath %>/js/lib/jquery-2.1.4.min.js"></script>
    <script type="text/javascript">
        var ErrorParams = {
            invalidLink: function () {
                return true
            }
        };
        var lang = navigator.language;
        var isEng = /^en/.test(lang);
        if (isEng) {
            document.write("<script src='<%=basePath %>/js/en/langconfig.js'><\/script>");
        } else {
            document.write("<script src='<%=basePath %>/js/cn/langconfig.js'><\/script>");
        }
    </script>
    <script src="<%=basePath %>/js/linkedme-link-error.js"></script>
    <script type="text/javascript">
        window.onload = function () {
            begin();
        };
    </script>
    <title></title>
    <style type="text/css">
        * {
            margin: 0;
            padding: 0;
        }

        a {
            text-decoration: none;
        }

        img {
            max-width: 100%;
            height: auto;
        }

        .image-tip {
            text-align: center;
            display: none;
            position: fixed;
            left: 0;
            top: 0;
            bottom: 0;
            background: rgba(0, 0, 0, 0.4);
            filter: alpha(opacity=40);
            height: 100%;
            width: 100%;
            z-index: 100;
        }
    </style>
</head>
<body style="width:100%; height:100%;">

</body>

</html>

