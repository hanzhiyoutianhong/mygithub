var lkmeAction = {
    recordIdUrl: "/" + Params.live_test_flag + "/js/record_id",
    recordJsEventUrl: "/" + Params.live_test_flag + "/js/record_event",
    recordJsUserClickEventUrl: "/" + Params.live_test_flag + "/js/record_click_event",
    destination: {
        iOSScheme: "dst_ios_scheme",
        iOSUniversalLink: "dst_ios_universe_links",
        iOSBrowser: "dst_ios_{browserName}_browser",
        androidBrowser: "dst_android_{browserName}_browser",
        androidCannotForward: "dst_android_cannot_forward_goto_app_{dest}",
        androidLoadLandingPage: "dst_android_app_{dest}_landingpage",
        androidCannotLoadLandingPage: "dst_android_cannot_goto_{dest}_landingpage",
        androidGotoMarket: "dst_android_goto_market",
        androidGotoDirectDownload: "dst_android_goto_direct_download_url",
        androidGotoChromeIntent: "dst_android_chrome_intent",
        iOSPlatform: "dst_{channel}_ios",
        androidPlatform: "dst_{channel}_android",
        yybPlatform: "dst_{tx_channel}_yyb",
        noPlatform: "dst_{platform}_not_available",
    },
    recordId: function () {
        var param = {
            app_id: Params.app_id,
            identity_id: Params.identity_id,
            is_valid_identityid: Params.is_valid_identity,
            browser_fingerprint_id: Params.browser_fingerprint_id,
            deeplink_id: Params.deeplink_id
        };
        $.ajax({
            method: "POST",
            url: this.recordIdUrl,
            data: param,
            success: function () {
            },
            error: function () {
            }
        });
    },
    recordJSEvent: function (destination) {
        var param = {
            destination: destination,
            identity_id: Params.identity_id,
            app_id: Params.app_id,
            deeplink_id: Params.deeplink_id
        };
        $.ajax({
            method: "POST",
            url: this.recordJsEventUrl,
            data: param,
            success: function () {
            },
            error: function () {
            }
        });
    },
    recordJSUserClickEvent: function (destination) {
        var param = {
            destination: destination,
            identity_id: Params.identity_id,
            app_id: Params.app_id,
            deeplink_id: Params.deeplink_id,
        };
        $.ajax({
            method: "POST",
            url: this.recordJsUserClickEventUrl,
            data: param,
            success: function () {
            },
            error: function () {
            }
        });
    }
};

function start() {
    if (Params.isIOS() && (void 0 === Params.bundle_id || "" === Params.bundle_id)) {
        return gotoNoPlatformPage("ios");
    } else if (Params.isAndroid() && (void 0 === Params.package_name || "" === Params.package_name)) {
        return gotoNoPlatformPage("android");
    }

    var launchAppUrl = (Params.uri_scheme.indexOf("://") >= 0) ? Params.uri_scheme : (Params.uri_scheme + "://");
    if (Params.isWechat()) {
        gotoQQ("wechat");
    } else if (Params.isQQ()) {
        gotoQQ("qq");
    } else if (Params.isWeibo()) {
        gotoPlatform("weibo");
    } else if (Params.isIOS()) {
        DEBUG_ALERT("isIOS");
        if (Params.click_id && Params.click_id.length > 0) {
            launchAppUrl = launchAppUrl + "?click_id=" + Params.click_id;
        }
        DEBUG_ALERT(launchAppUrl);

        if (Params.ios_major < 9) {
            DEBUG_ALERT("iOS major < 9: " + Params.ios_major);
            iframeDeepLinkLaunch(launchAppUrl, 2e3,
                function () {
                    lkmeAction.recordId();
                    var destination = lkmeAction.destination.iOSScheme;
                    gotoUrl(Params.forward_url, destination);
                });
        } else {
            if (Params.isUniversalLink()) {
                DEBUG_ALERT("isUniversalLink = true");
                lkmeAction.recordId();
                var destination = lkmeAction.destination.iOSUniversalLink;
                gotoUrl(Params.forward_url, destination);
            } else if (Params.isChrome()) {
                DEBUG_ALERT("isChrome");
                iOSChromeLaunch(a, function () {
                    lkmeAction.recordId();
                    var destination = lkmeAction.destination.iOSBrowser.replace(/{browserName}/g, "chrome");
                    gotoUrl(Params.forward_url, destination);
                });
            } else {
                DEBUG_ALERT("isSafari or other browser");
                iOSSafariLaunch(launchAppUrl, 2500, function () {
                    lkmeAction.recordId();
                    var destination = lkmeAction.destination.iOSBrowser.replace(/{browserName}/g, "safari");
                    gotoUrl(Params.forward_url, destination);
                });
            }
        }
    } else if (Params.isAndroid()) {
        DEBUG_ALERT("isAndroid");
        launchAppUrl = launchAppUrl + Params.host;
        if (Params.click_id && Params.click_id.length > 0) {
            launchAppUrl += "?click_id=" + Params.click_id;
        }
        DEBUG_ALERT(launchAppUrl);
        if (Params.isQQBrowser()) {
            DEBUG_ALERT("QQ browser");
            if (Params.isYYBAvailable()) {
                var destination = lkmeAction.destination.androidBrowser.replace(/{browserName}/g, "qq")
                gotoUrl(Params.yyb_download_url, destination);
            } else {
                gotoCannotForwardPage();
            }
        } else if (Params.isUC()) {
            DEBUG_ALERT("UC browser");
            gotoUC(launchAppUrl);
        } else {
            DEBUG_ALERT("default browser");
            iframeDeepLinkLaunch(launchAppUrl, 2e3, function () {
                gotoAndroidAppInstall()
            });
        }
    }
}

function gotoNoPlatformPage(platform) {
    var div_no_platform_template = '<div style="background-image:url(' + baseImgPathLang + 'no_{platform}.png);background-size: 100% 100%;width:100%;height:100%;"></div>';
    var div_no_platform = div_no_platform_template.replace(/{platform}/g, platform);
    $("body").append(div_no_platform);
    var destination = lkmeAction.destination.noPlatform.replace(/{platform}/g, platform);
    lkmeAction.recordJSEvent(destination);
}

function gotoUrl(forwardUrl, destination) {
    window.history.replaceState("Object", "Title", "0");
    lkmeAction.recordJSEvent(destination);
    window.location = forwardUrl;
}

function gotoTip(platform, destination) {
    var imgInfo = "<img src=" + baseImgPathLang + 'open_{mobile-os}_browser.png align="center" style="height: 100%;"/>';
    var pageTemplate = '<div class="image-tip" width="100%" height="100%" style="position:relative;"><div style="background-color:#ffffff;width:100%;height:100%;position:absolute; top:0;">{img_tip}</div><div style="text-align:center; width:100%; position:absolute; top:67%"></div></div>';
    $("body").append(pageTemplate.replace(/{img_tip}/g, imgInfo).replace(/{logo_url}/g, Params.logo_url).replace(/{mobile-os}/g, platform));
    $(".image-tip").show();
    if (window.location.search.indexOf("visit_id") < 0) {
        var visitFlag = "visit_id=" + Math.floor(1e6 * Math.random());
        window.location.search = (window.location.search != "") ? (window.location.search + "&" + visitFlag) : ("?" + visitFlag);
        DEBUG_ALERT("Url Add flag:" + visitFlag)
    }
    lkmeAction.recordJSEvent(destination);
}

function gotoPlatform(channel) {
    if (Params.isIOS()) {
        gotoTip("ios", lkmeAction.destination.iOSPlatform.replace(/{channel}/g, channel));
    } else if (Params.isAndroid()) {
        gotoTip("android", lkmeAction.destination.androidPlatform.replace(/{channel}/g, channel));
    }
}

function gotoQQ(txChannel) {
    DEBUG_ALERT(txChannel);
    if (Params.isYYBAvailable()) {
        gotoUrl(Params.yyb_download_url, lkmeAction.destination.yybPlatform.replace(/{tx_channel}/g, txChannel));
    } else {
        gotoPlatform(txChannel);
    }
}

function iframeDeepLinkLaunch(a, b, c) {
    var child = document.createElement("iframe");
    child.style.width = "1px";
    child.style.height = "1px";
    child.border = "none";
    child.style.display = "none";
    child.src = a;
    document.body.appendChild(child);
    lkmeAction.recordJSEvent(a);
    var timeOut = setTimeout(function () {
            c();
        },
        b);
    clearTimeoutOnPageUnload(timeOut);
}

function iOSChromeLaunch(a, b) {
    var c = null;
    try {
        lkmeAction.recordJSEvent(a);
        c = window.open(a);
        window.history.replaceState("Object", "Title", "0");
    } catch (d) {
        DEBUG_ALERT("exception");
    }
    c ? window.close() : b();
}

function iOSSafariLaunch(a, b, c) {
    DEBUG_ALERT(a);
    lkmeAction.recordJSEvent(a);
    window.location = a;
    var d = setTimeout(function () {
            c();
        },
        b);
    clearTimeoutOnPageUnload(d);
}

function gotoUC(a) {
    var div_allow_deeplink_forward = '<div style="background-image:url(' + baseImgPathLang + 'open_app.png);background-size: 100% 100%;width:100%;height:100%;"><div style="text-align:center; width:100%; position:absolute; top:35%;"><p id="textCountDown" style="font-size: 1em; color: #959595; padding: 6px 20px; -webkit-border-radius: 30px; -moz-border-radius: 30px; border-radius: 30px;"></p></div></div>';
    dstLocation = lkmeAction.destination.androidBrowser.replace(/{browserName}/g, "UC");
    var b = $("body").html();
    $("body").append(div_allow_deeplink_forward);
    var c = 4,
        d = function () {
            c--;
            $("#textCountDown").html(c);
            0 === c ? ($("#textCountDown").html(""), iframeDeepLinkLaunch(a, 1e3,
                function () {
                    $("body").html(b),
                        gotoAndroidAppInstall()
                })) : setTimeout(d, 1e3);
        };
    d();
}

function gotoCannotForwardPage() {
    DEBUG_ALERT("cannot launch app");
    var destination;
    if (Params.isDownloadDirectly()) {
        destination = lkmeAction.destination.androidCannotForward.replace(/{dest}/g, "download");
        var div_cannot_forward_with_download_btn = '<div style="background-image:url(' + baseImgPathLang + 'cannot_forward.png);background-size: 100% 100%;width:100%;height:100%;">    <div style="text-align:center; width:100%; position:absolute; top:80%;">        <button id="btnGotoAndroidDownload" style="font-size: 1em; background-color:#FFFFFF; border: 3px solid #959595; color: #959595; padding: 6px 20px; -webkit-border-radius: 30px; -moz-border-radius: 30px; border-radius: 30px;">' + downloadAPK + "</button>    </div></div>";
        $("body").append(div_cannot_forward_with_download_btn), $("#btnGotoAndroidDownload").click(function () {
            lkmeAction.recordJSUserClickEvent("gotoAndroidDirectDownload");
            gotoUrl(Params.forward_url);
        });
    } else {
        destination = lkmeAction.destination.androidCannotForward.replace(/{dest}/g, "market");
        var div_cannot_forward_with_market_btn = '<div style="background-image:url(' + baseImgPathLang + 'cannot_forward.png);background-size: 100% 100%;width:100%;height:100%;">    <div style="text-align:center; width:100%; position:absolute; top:80%;">        <button id="btnGotoAndroidMarket" style="font-size: 1em; background-color:#FFFFFF; border: 3px solid #959595; color: #959595; padding: 6px 20px; -webkit-border-radius: 30px; -moz-border-radius: 30px; border-radius: 30px;">' + gotoStore + "</button></div></div>";
        $("body").append(div_cannot_forward_with_market_btn), $("#btnGotoAndroidMarket").click(function () {
            lkmeAction.recordJSUserClickEvent("gotoAndroidMarket");
            gotoAndroidMarket();
        });
    }

    lkmeAction.recordJSEvent(destination);
    lkmeAction.recordId();
}

function gotoAndroidMarket() {
    window.history.replaceState("Object", "Title", "0");
    var forwardUrl = "market://details?id=" + Params.package_name;
    DEBUG_ALERT(forwardUrl);
    var destination = lkmeAction.destination.androidGotoMarket;
    lkmeAction.recordJSEvent(destination);
    window.location = forwardUrl;
}

function gotoAndroidAppInstall() {
    lkmeAction.recordId();
    var destination;
    var div_goto_landingpage = '<div style="background-image:url({Bg_Url});background-size: 100% 100%;width:100%;height:100%;">    <div style = "position:absolute; top:20%; width:100%; ">        <div style="text-align:center; width:100%; ">            <img id="appIcon" src={logo_url} style="width:22%;"/>        </div>        <div style="text-align:center; width:100%; margin-top:10px;">            <span id="appName" style="font-size: 1.5em; color: #959595; padding: 15px 10px;">                {app_name}            </span>        </div>    </div>    <div style="text-align:center; width:100%; position:absolute; top:56%;">        <span id="downloadTitle" style="font-size: 1em; color: #959595; padding: 15px 10px;">            {Download_title}        </span>    </div>    <div style="text-align:center; width:100%; position:absolute; top:59%;">    </div>    <div style="text-align:center; width:100%; position:absolute; top:70%;">        <{Element_type} id="btnGotoLandingPage" style="background-color:#FFFFFF; border: {Border_width}px solid #959595; color: #959595; padding: 6px 20px; -webkit-border-radius: 30px; -moz-border-radius: 30px; border-radius: 30px;">{Btn_landingpage_text}</{Element_type}>    </div></div>';
    if (Params.isDownloadDirectly()) {
        destination = lkmeAction.destination.androidLoadLandingPage.replace(/{dest}/g, "download");
        DEBUG_ALERT(destination);
        div_goto_landingpage = div_goto_landingpage.replace(/{Bg_Url}/g, baseImgPathLang + "bg.png").replace(/{app_name}/g, Params.app_name).replace(/{logo_url}/g, Params.logo_url).replace(/{Download_title}/g, Params.app_title).replace(/{Download_msg}/g, Params.app_slogan).replace(/{Btn_landingpage_text}/g, downloadAPK).replace(/{Border_width}/g, "3").replace(/{Element_type}/g, "button");
        $("body").append(div_goto_landingpage);
        lkmeAction.recordJSEvent(destination);
        $("#btnGotoLandingPage").click(function () {
            lkmeAction.recordJSUserClickEvent("gotoAndroidDirectDownload");
            gotoUrl(Params.forward_url, lkmeAction.destination.androidGotoDirectDownload);
        });
    } else if (Params.isCannotGoMarket()) {
        destination = lkmeAction.destination.androidCannotLoadLandingPage.replace(/{dest}/g, "market");
        DEBUG_ALERT(destination);
        div_goto_landingpage = div_goto_landingpage.replace(/{Bg_Url}/g, baseImgPathLang + "bg.png").replace(/{app_name}/g, Params.app_name).replace(/{logo_url}/g, Params.logo_url).replace(/{Download_title}/g, Params.app_title).replace(/{Download_msg}/g, Params.app_slogan).replace(/{Btn_landingpage_text}/g, openStore).replace(/{Border_width}/g, "0").replace(/{Element_type}/g, "p");
        $("body").append(div_goto_landingpage);
        lkmeAction.recordJSEvent(destination);
    } else if (Params.isUC()) {
        destination = lkmeAction.destination.androidLoadLandingPage.replace(/{dest}/g, "uc_browser_market");
        DEBUG_ALERT(destination);
        div_goto_landingpage = div_goto_landingpage.replace(/{Bg_Url}/g, baseImgPathLang + "bg.png").replace(/{app_name}/g, Params.app_name).replace(/{logo_url}/g, Params.logo_url).replace(/{Download_title}/g, Params.app_title).replace(/{Download_msg}/g, Params.app_slogan).replace(/{Btn_landingpage_text}/g, gotoAppStore).replace(/{Border_width}/g, "3").replace(/{Element_type}/g, "button");
        $("body").append(div_goto_landingpage);
        lkmeAction.recordJSEvent(destination);
        $("#btnGotoLandingPage").click(function () {
            lkmeAction.reportJSUserClickEvent("gotoAndroidMarket");
            gotoAndroidMarket();
        });
    } else {
        gotoAndroidMarket();
    }
}

function clearTimeoutOnPageUnload(a) {
    window.addEventListener("pagehide",
        function () {
            DEBUG_ALERT("window event pagehide");
            clearTimeout(a);
            window.history.replaceState("Object", "Title", "0");
        });
    window.addEventListener("blur",
        function () {
            DEBUG_ALERT("window event blur");
            clearTimeout(a);
            window.history.replaceState("Object", "Title", "0");
        });
    window.addEventListener("unload",
        function () {
            DEBUG_ALERT("window event unload");
            clearTimeout(a);
            window.history.replaceState("Object", "Title", "0");
        });
    window.addEventListener("beforeunload",
        function () {
            DEBUG_ALERT("window event beforeunload")
        });
    window.addEventListener("focus",
        function () {
            DEBUG_ALERT("window event focus");
        });
    window.addEventListener("focusout",
        function () {
            DEBUG_ALERT("window event focusout");
            clearTimeout(a);
            window.history.replaceState("Object", "Title", "0");
        });
    document.addEventListener("webkitvisibilitychange",
        function () {
            DEBUG_ALERT("window event webkitvisibilitychange");
            if (document.webkitHidden) {
                clearTimeout(a);
                window.history.replaceState("Object", "Title", "0");
            }
        });
}

function DEBUG_ALERT(msg) {
    if (DEBUG) {
        alert(msg);
    }
}