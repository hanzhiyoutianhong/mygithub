<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
//String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String basePath = "https://lkme.cc/";
%>

<!DOCTYPE html>
<html>
<head>
    <title></title>
    <meta charset="utf-8" />
    <link href="<%=basePath %>demoh5/CSS/LinkedMe.css" rel="stylesheet" />
    <!-- meta name="viewport" content="width=device-width, initial-scale=1.0" -->
    <meta name="viewport" content="initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
</head>
<body>

    <div class="div_intro_center" style="background-image:url(<%=basePath %>demoh5/Images/Intro/backgroundline.png);background-repeat:no-repeat;background-size:contain;background-position-y:50px">
        &nbsp;
        <div class="entry_row">
            <img src="<%=basePath %>demoh5/Images/Entry/logo.png" />
        </div>
        <div class="entry_row2">
            <div>
                <div class="entry_icon_left">
                    <a href="<%=basePath %>h5/partner"> <img class="entry_icon_img" src="<%=basePath %>demoh5/Images/Entry/app.png" /></a>
                </div>
                <div class="entry_icon_right">
                    <a href="<%=basePath %>h5/feature"> <img class="entry_icon_img" src="<%=basePath %>demoh5/Images/Entry/feature.png" /></a>
                </div>
            </div>
            <div style="margin-top:30px">
                <div class="entry_icon_left">
                    <a href="#"><img class="entry_icon_img" src="<%=basePath %>demoh5/Images/Entry/demo.png" /></a>
                </div>
                <div class="entry_icon_right">
                    <a href="<%=basePath %>h5/summary"><img class="entry_icon_img" src="<%=basePath %>demoh5/Images/Entry/intro.png" /></a>
                </div>
            </div>
        </div>
    </div>

    <div class="footer">
        Copyright@2014-2015 LinkedME 北京微方程科技有限公司
    </div>

</body>
</html>
