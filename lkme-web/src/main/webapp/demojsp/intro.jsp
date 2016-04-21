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
    <title>产品简介</title>
    <meta charset="utf-8" />
    <link href="<%=basePath %>demoh5/CSS/LinkedMe.css" rel="stylesheet" />
    <meta name="viewport" content="initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
</head>
<body>
    <div id="lb" class="banner">
        <div class="banner_left" onclick="javascript:location.href = '#';">
            <!--<div style="display:inline-block">
                <img class="close_img" src="<%=basePath %>demoh5/Images/Close.png" onclick="javascript:document.getElementById('lb').style.display='none';" />
            </div>-->
            <div style="display:inline-block;vertical-align:middle;">
                <img class="banner_img" src="<%=basePath %>demoh5/Images/LinkedME.png" />
            </div>
            <div class="banner_text">
                LinkedME 跨平台链接
            </div>
        </div>
        <div style="float:right;margin-top:12px;top:0;">
            <a href="" id="link">
                <img src="<%=basePath %>demoh5/Images/open.png" height="30" style="vertical-align:middle;margin-right:5px;" />
            </a>
            <script>
                var btn = document.getElementById('link');
                var search = location.search;
                var linkedme = /^\?linkedme=(.*)/.exec(search);
                linkedme && btn.setAttribute('href', linkedme[1]);
            </script>
        </div>
        <div style="clear:both"></div>
    </div>
    
    <div class="div_intro_center" style="background-image:url(<%=basePath %>demoh5/Images/Intro/backgroundline.png);background-repeat:no-repeat;background-size:contain;background-position-y:100px;">
        <div class="intro_row">
            <p class="intro_default_font">LinkedME</p>
            <p class="intro_default_font">是<span class="intro_font_green">跨平台链接</span>的解决方案，</p>
            <p class="intro_default_font">针对应用内<span class="intro_font_green">所有页面定义唯一链接，</span></p>
            <p class="intro_default_font">使得应用不再是信息的孤岛！</p>
        </div>
        <div class="intro_row">
            <p class="intro_default_font">LinkedME</p>
            <p class="intro_default_font">是<span class="intro_font_green">企业级的免费链接服务</span></p>
            <p class="intro_default_font">移动互联网按照文档集成SDK</p>
            <p class="intro_default_font">即可实现LinkMe的技术特性！</p>
        </div>
        <div class="intro_row">
            <div class="intro_box">
                <p class="intro_default_font">LinkedME产品的</p>
                <p class="intro_default_font">技术服务主要分成如下几个方面：</p>
                <div style="padding-left:65px">
                    <p class="intro_default_font align-left"><img class="intro_key" src="<%=basePath %>demoh5/Images/Intro/arrow.png" /> App页面的唯一指标</p>
                    <p class="intro_default_font align-left"><img class="intro_key" src="<%=basePath %>demoh5/Images/Intro/arrow.png" /> 深度内容分享</p>
                    <p class="intro_default_font align-left"><img class="intro_key" src="<%=basePath %>demoh5/Images/Intro/arrow.png" /> App间互联</p>
                    <p class="intro_default_font align-left"><img class="intro_key" src="<%=basePath %>demoh5/Images/Intro/arrow.png" /> Web与App间互通</p>
                    <p class="intro_default_font align-left"><img class="intro_key" src="<%=basePath %>demoh5/Images/Intro/arrow.png" /> 可视化数据分析中心</p>
                    <p class="intro_default_font align-left"><img class="intro_key" src="<%=basePath %>demoh5/Images/Intro/arrow.png" /> 精细化运营</p>
                </div>

            </div>
        </div>


    </div>
    <div class="footer">
        Copyright@2014-2015 LinkedME 北京微方程科技有限公司
    </div>
</body>
</html>
