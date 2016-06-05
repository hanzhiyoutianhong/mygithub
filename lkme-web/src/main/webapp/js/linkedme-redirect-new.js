/**
 * Created by LinkedME01 on 16/4/1.
 * modify at 4.15
 */
function DEBUG_ALERT(msg) {
    if (DEBUG) {
        alert(msg);
    }
}

function start() {
    var uriVal = redirectInfo.uri_scheme + "://",
        div_allow_me_deeplink = '<div style="background-image:url(' + baseImgPathLang + 'open_app.png);background-size: 100% 100%;width:100%;height:100%;">    <div style="text-align:center; width:100%; position:absolute; top:35%;">        <p id="textCountDown" style="font-size: 1em; color: #959595; padding: 6px 20px; -webkit-border-radius: 30px; -moz-border-radius: 30px; border-radius: 30px;"></p>    </div></div>';
    if (redirectInfo.isIOS() && (void 0 === redirectInfo.bundle_id || "" === redirectInfo.bundle_id))
        return goToNoAppDiv("ios");
    else if (redirectInfo.isAndroid() && (void 0 === redirectInfo.package_name || "" === redirectInfo.package_name))
        return goToNoAppDiv("android");

    if (redirectInfo.isWechat()) redirectDst("wechat");
    else if (redirectInfo.isQQ()) redirectDst("qq");
    else if (redirectInfo.isWeibo()) redirectDst("weibo");
    else if (redirectInfo.isIOS()) {
        DEBUG_ALERT("isIOS");
        if (redirectInfo.click_id && redirectInfo.click_id.length > 0) {
            uriVal += "?click_id=" + redirectInfo.click_id;
        }
        DEBUG_ALERT(uriVal);

        DEBUG_ALERT("iOS major is " + redirectInfo.ios_major);

        if (redirectInfo.ios_major < 9) {
            iframeDeepLinkLaunch(uriVal, 2e3,
                function () {
                    gotoUrl(redirectInfo.forward_url);
                });
        } else {
            if (redirectInfo.isUniversalLink()) {
                DEBUG_ALERT("isUniversalLink = true");
                if (cookieEnabled()) {
                    DEBUG_ALERT("cookie Enabled; installStatus:" + redirectInfo.installStatus);
                    return void gotoUrl(redirectInfo.forward_url);
                } else {
                    DEBUG && alert("cookie Not Enabled");
                    dstLocation = lkmeAction.destination.dstUniversalLinkLandingPage;
                    DEBUG && alert(dstLocation);
                    div_goto_landingpage = div_goto_landingpage.replace(/{Bg_Url}/g, baseImgPathLang + "bg.png").replace(/{app_name}/g, redirectInfo.app_name).replace(/{logo_url}/g, redirectInfo.logo_url).replace(/{Download_msg}/g, redirectInfo.app_slogan).replace(/{Btn_landingpage_text}/g, gotoAppStore).replace(/{Border_width}/g, "3").replace(/{Element_type}/g, "button");
                    $("body").append(div_goto_landingpage);
                    lkmeAction.reportJSEvent(lkmeAction.actionJSDst, dstLocation);
                    $("#btnGotoLandingPage").click(function () {
                        lkmeAction.reportJSUserClickEvent(lkmeAction.actionJSUserClick, "gotoIosAppStore", "yes");
                        gotoUrl(redirectInfo.forward_url);
                    });
                    lkmeAction.recordId();
                }
            } else if (redirectInfo.isChrome()) {
                DEBUG_ALERT("isChrome");
                deepLinkLocation = uriVal;
                var c = null;
                try {
                    lkmeAction.reportJSEvent(lkmeAction.actionJSDeepLink, uriVal);
                    c = window.open(uriVal);
                    DEBUG && alert("pass");
                    window.history.replaceState("Object", "Title", "0")
                } catch (d) {
                    DEBUG && alert("exception")
                }
                c ? window.close() : gotoUrl(redirectInfo.forward_url);
                ;
            } else {
                DEBUG_ALERT("is safari");
                deeplinkLaunch(uriVal, 2500, function () {
                    gotoUrl(redirectInfo.forward_url);
                });
            }
        }
    } else if (redirectInfo.isAndroid()) {
        DEBUG_ALERT("isAndroid");
        uriVal += redirectInfo.host;
        if (redirectInfo.click_id && redirectInfo.click_id.length > 0) {
            uriVal += "?click_id=" + redirectInfo.click_id;
        }
        DEBUG_ALERT(uriVal);
        if (redirectInfo.isCannotDeeplink()) {
            iframeDeepLinkLaunch(uriVal, 10e3, function () {
                gotoCannotDeeplink();
            });
        } else if (redirectInfo.isQQBrowser()) {
            DEBUG_ALERT("QQ browser");
            if (directToYYBAppDownload()) {
                redirectUrl(redirectInfo.yyb_app_download);
            } else {
                gotoCannotDeeplink();
            }
        } else if (redirectInfo.isUC()) {
            DEBUG_ALERT("UC browser");
            dstLocation = lkmeAction.destination.dstUCBrowser;
            var b = $("body").html();
            $("body").append(div_allow_me_deeplink);
            var c = 6,
                d = function () {
                    c--,
                        $("#textCountDown").html(c),
                        0 === c ? ($("#textCountDown").html(""), iframeDeepLinkLaunch(uriVal, 10e3,
                            function () {
                                $("body").html(b),
                                    gotoAndroidNewInstall()
                            })) : setTimeout(d, 1e3)
                };
            d()
        } else if (redirectInfo.isChrome() && redirectInfo.chrome_major >= 25 && !redirectInfo.isForceUseScheme()) {
            DEBUG_ALERT("chrome_major:" + redirectInfo.chrome_major);
            var crUriVal = redirectInfo.host;
            if (redirectInfo.click_id && redirectInfo.click_id.length > 0) {
                crUriVal += "?click_id=" + redirectInfo.click_id;
            }

            var intentVal = "intent://" + crUriVal + "#Intent;scheme=" + redirectInfo.uri_scheme + ";package=" + redirectInfo.package_name + ";S.browser_fallback_url=" + redirectInfo.forward_url + ";end";
            DEBUG_ALERT("intentVal = " + intentVal);
            deeplinkLaunch(intentVal, 2e3, function () {
                gotoAndroidNewInstall()
            })
        } else {
            DEBUG_ALERT("default browser");
            iframeDeepLinkLaunch(uriVal, 2e3, function () {
                gotoAndroidNewInstall()
            })
        }
    }
}

var visit_id = "visit_id",
    deepLinkLocation = "",
    dstLocation = "",
    imgInfo = "<img src=" + baseImgPathLang + 'open_{mobile-os}_browser.png align="center" style="height: 100%;"/>',
    pageTemplate = '<div class="image-tip" width="100%" height="100%" style="position:relative;">    <div style="background-color:#ffffff;width:100%;height:100%;position:absolute; top:0;">        {img_tip}    </div>    <div style="text-align:center; width:100%; position:absolute; top:67%">        </div></div>',
    gotoTip = function (a, tag) {
        $("body").append(pageTemplate.replace(/{img_tip}/g, imgInfo).replace(/{logo_url}/g, redirectInfo.logo_url).replace(/{mobile-os}/g, a));
        $(".image-tip").show();
        if (window.location.search.indexOf(visit_id) < 0) {
            tag = visit_id + "=" + Math.floor(1e6 * Math.random());
            window.location.search = ( window.location.search == undefined || window.location.search != "" ) ? (window.location.search + "&" + tag) : ("?" + tag);
            DEBUG && alert("Url Add Tag:" + tag)
        }
        dstLocation = tag;
        lkmeAction.reportJSEvent(lkmeAction.actionJSDst, dstLocation);
    },
    div_goto_cannot_deeplink_with_market_btn = '<div style="background-image:url(' + baseImgPathLang + 'cannot_forward.png);background-size: 100% 100%;width:100%;height:100%;">    <div style="text-align:center; width:100%; position:absolute; top:80%;">        <button id="btnGotoAndroidMarket" style="font-size: 1em; background-color:#FFFFFF; border: 3px solid #959595; color: #959595; padding: 6px 20px; -webkit-border-radius: 30px; -moz-border-radius: 30px; border-radius: 30px;">' + gotoStore + "</button>    </div></div>",
    div_goto_cannot_deeplink_with_download_btn = '<div style="background-image:url(' + baseImgPathLang + 'cannot_forward.png);background-size: 100% 100%;width:100%;height:100%;">    <div style="text-align:center; width:100%; position:absolute; top:80%;">        <button id="btnGotoAndroidDownload" style="font-size: 1em; background-color:#FFFFFF; border: 3px solid #959595; color: #959595; padding: 6px 20px; -webkit-border-radius: 30px; -moz-border-radius: 30px; border-radius: 30px;">' + downloadAPK + "</button>    </div></div>",
    gotoCannotDeeplink = function () {
        ////记录identity_id和browser_fingerprint_id
        DEBUG && alert("cannot deeplink");
        if (redirectInfo.isDownloadDirectly()) {
            $("body").append(div_goto_cannot_deeplink_with_download_btn), $("#btnGotoAndroidDownload").click(function () {
                lkmeAction.reportJSUserClickEvent(lkmeAction.actionJSUserClick, "gotoAndroidDirectDownload", "yes");
                gotoUrl(redirectInfo.forward_url);
            })
        } else {
            $("body").append(div_goto_cannot_deeplink_with_market_btn), $("#btnGotoAndroidMarket").click(function () {
                lkmeAction.reportJSUserClickEvent(lkmeAction.actionJSUserClick, "gotoAndroidMarket", "yes");
                gotoAndroidMarket();
            })
        }
        dstLocation = lkmeAction.destination.dstCannotDeepLink;
        lkmeAction.reportJSEvent(lkmeAction.actionJSDst, dstLocation);
        lkmeAction.recordId();
    },
    div_goto_landingpage = '<div style="background-image:url({Bg_Url});background-size: 100% 100%;width:100%;height:100%;">    <div style = "position:absolute; top:20%; width:100%; ">        <div style="text-align:center; width:100%; ">            <img id="appIcon" src={logo_url} style="width:22%;"/>        </div>        <div style="text-align:center; width:100%; margin-top:10px;">            <span id="appName" style="font-size: 1.5em; color: #959595; padding: 15px 10px;">                {app_name}            </span>        </div>    </div>    <div style="text-align:center; width:100%; position:absolute; top:56%;">        <span id="downloadTitle" style="font-size: 1em; color: #959595; padding: 15px 10px;">         </span>    </div>    <div style="text-align:center; width:100%; position:absolute; top:59%;">    </div>    <div style="text-align:center; width:100%; position:absolute; top:70%;">        <{Element_type} id="btnGotoLandingPage" style="background-color:#FFFFFF; border: {Border_width}px solid #959595; color: #959595; padding: 6px 20px; -webkit-border-radius: 30px; -moz-border-radius: 30px; border-radius: 30px;">{Btn_landingpage_text}</{Element_type}>    </div></div>',
    gotoAndroidNewInstall = function () {
        if (redirectInfo.isDownloadDirectly()) {
            dstLocation = lkmeAction.destination.dstAndroidDirectDownloadLandingPage,
            DEBUG && alert(dstLocation);
            div_goto_landingpage = div_goto_landingpage.replace(/{Bg_Url}/g, baseImgPathLang + "bg.png").replace(/{app_name}/g, redirectInfo.app_name).replace(/{logo_url}/g, redirectInfo.logo_url).replace(/{Download_msg}/g, redirectInfo.app_slogan).replace(/{Btn_landingpage_text}/g, downloadAPK).replace(/{Border_width}/g, "3").replace(/{Element_type}/g, "button");
            $("body").append(div_goto_landingpage);
            lkmeAction.reportJSEvent(lkmeAction.actionJSDst, dstLocation);
            $("#btnGotoLandingPage").click(function () {
                lkmeAction.reportJSUserClickEvent(lkmeAction.actionJSUserClick, "gotoAndroidDirectDownload", "yes");
                gotoUrl(redirectInfo.forward_url);
            });
            lkmeAction.recordId();
        } else if (redirectInfo.isCannotGoMarket()) {
            dstLocation = lkmeAction.destination.dstAndroidCannotGoMarketLandingPage;
            DEBUG && alert(dstLocation);
            div_goto_landingpage = div_goto_landingpage.replace(/{Bg_Url}/g, baseImgPathLang + "bg.png").replace(/{app_name}/g, redirectInfo.app_name).replace(/{logo_url}/g, redirectInfo.logo_url).replace(/{Btn_landingpage_text}/g, openStore).replace(/{Border_width}/g, "0").replace(/{Element_type}/g, "p");
            $("body").append(div_goto_landingpage);
            lkmeAction.reportJSEvent(lkmeAction.actionJSDst, dstLocation);
            lkmeAction.recordId();
        } else if (redirectInfo.isCannotGetWinEvent() || redirectInfo.isUC()) {
            dstLocation = lkmeAction.destination.dstAndroidMarketLandingPage;
            DEBUG && alert(dstLocation);
            div_goto_landingpage = div_goto_landingpage.replace(/{Bg_Url}/g, baseImgPathLang + "bg.png").replace(/{app_name}/g, redirectInfo.app_name).replace(/{logo_url}/g, redirectInfo.logo_url).replace(/{Btn_landingpage_text}/g, gotoAppStore).replace(/{Border_width}/g, "3").replace(/{Element_type}/g, "button");
            $("body").append(div_goto_landingpage);
            lkmeAction.reportJSEvent(lkmeAction.actionJSDst, dstLocation);
            $("#btnGotoLandingPage").click(function () {
                lkmeAction.reportJSUserClickEvent(lkmeAction.actionJSUserClick, "gotoAndroidMarket", "yes");
                gotoAndroidMarket();
            });
            lkmeAction.recordId();
        } else {
            gotoAndroidMarket();
        }
    },
    gotoAndroidMarket = function () {
        window.history.replaceState("Object", "Title", "0")
        dstLocation = "market://details?id=" + redirectInfo.package_name;
        DEBUG && alert(dstLocation);
        lkmeAction.reportJSEvent(lkmeAction.actionJSDst, dstLocation);
        window.location = dstLocation;
        lkmeAction.recordId();
    },
    gotoUrl = function (redirectUrl) {
        //记录identity_id和browser_fingerprint_id
        window.history.replaceState("Object", "Title", "0")
        dstLocation = redirectUrl
        lkmeAction.reportJSEvent(lkmeAction.actionJSDst, redirectUrl);
        window.location = redirectUrl;
    },
    div_platform_not_available = '<div style="background-image:url(' + baseImgPathLang + 'no_{Platform}.png);background-size: 100% 100%;width:100%;height:100%;"></div>',
    goToNoAppDiv = function (a) {
        var div_platform_NA = div_platform_not_available.replace(/{Platform}/g, a);
        $("body").append(div_platform_NA);
        dstLocation = lkmeAction.destination.dstplatformNA.replace(/{Platform}/g, a);
        lkmeAction.reportJSEvent(lkmeAction.actionJSDst, dstLocation);
    },
    deeplinkLaunch = function (dll, b, c) {
        // a = "intent://linkedme?click_id=sWpK2qR01#Intent;scheme=linkedmedemo;package=com.microquation.linkedme.android;S.browser_fallback_url=https://www.baidu.com;end";
        deepLinkLocation = dll;
        DEBUG && alert(deepLinkLocation);
        lkmeAction.reportJSEvent(lkmeAction.actionJSDeepLink, dll);
        window.location = dll;
        var timeOut = setTimeout(function () {
                c()
            },
            b);
        clearTimeoutOnPageUnload(timeOut);
    },
    iframeDeepLinkLaunch = function (dll, b, c) {
        var child = document.createElement("iframe");
        child.style.width = "1px";
        child.style.height = "1px";
        child.border = "none";
        child.style.display = "none";
        child.src = dll;
        document.body.appendChild(d);
        deepLinkLocation = dll;
        lkmeAction.reportJSEvent(lkmeAction.actionJSDeepLink, dll);
        var timeOut = setTimeout(function () {
                c();
            },
            b);
        clearTimeoutOnPageUnload(timeOut);
    },
    clearTimeoutOnPageUnload = function (a) {
        window.addEventListener("pagehide",
            function () {
                DEBUG_ALERT("window event pagehide"),
                    clearTimeout(a),
                    window.history.replaceState("Object", "Title", "0")
            }),
            window.addEventListener("blur",
                function () {
                    DEBUG_ALERT("window event blur"),
                        clearTimeout(a),
                        window.history.replaceState("Object", "Title", "0")
                }),
            window.addEventListener("unload",
                function () {
                    DEBUG_ALERT("window event unload"),
                        clearTimeout(a),
                        window.history.replaceState("Object", "Title", "0")
                }),
            document.addEventListener("webkitvisibilitychange",
                function () {
                    DEBUG_ALERT("window event webkitvisibilitychange"),
                    document.webkitHidden && (clearTimeout(a), window.history.replaceState("Object", "Title", "0"))
                }),
            window.addEventListener("beforeunload",
                function () {
                    DEBUG_ALERT("window event beforeunload")
                }),
            window.addEventListener("focus",
                function () {
                    DEBUG_ALERT("window event focus")
                }),
            window.addEventListener("focusout",
                function () {
                    DEBUG_ALERT("window event focusout"),
                        clearTimeout(a),
                        window.history.replaceState("Object", "Title", "0")
                })
    },
    directToYYBAppDownload = function () {
        return void 0 !== redirectInfo.yyb_app_download && "" !== redirectInfo.yyb_app_download && !redirectInfo.isIOS() && redirectInfo.isYYBAppDownloadAvailable()
    },
    redirectUrl = function (type) {
        DEBUG_ALERT(type);
        gotoUrl(type);
    },
    redirectTip = function (type, dst) {
        DEBUG_ALERT("is " + type);
        gotoTip(type, dst);
    },
    redirectDst = function (app) {
        DEBUG_ALERT(app);
        if (directToYYBAppDownload()) {
            redirectUrl(redirectInfo.yyb_app_download);
        } else {
            if (redirectInfo.isIOS()) {
                redirectTip("ios", "dst" + "-" + app + "-" + "android");
            } else if (redirectInfo.isAndroid()) {
                redirectTip("android", "dst" + "-" + app + "-" + "android");
            }
        }
    },
    cookieEnabled = function () {
        var a = !1;
        try {
            localStorage.test = 2
        } catch (b) {
            DEBUG && alert("private mode"),
                a = !0
        }
        return navigator.cookieEnabled && !a
    },
    lkmeAction = {
        recordIdUrl: "/i/js/record_id",
        trackingUrl: "/i/js/actions/",
        actionJSDeepLink: "/i/js/deeplink",
        actionJSDst: "/i/js/dst",
        actionJSUserClick: "/i/js/userclick",
        destination: {
            dstCannotDeepLink: "dst-cannot-deeplink",
            dstUCBrowser: "dst-uc-browser",
            dstUniversalLinkLandingPage: "dst-universallink-landingpage",
            dstAndroidDirectDownloadLandingPage: "dst-android-direct-download-landingpage",
            dstAndroidMarketLandingPage: "dst-android-market-landingpage",
            dstAndroidCannotGoMarketLandingPage: "dst-android-cannot-gomarket-landingpage",
            dstplatformNA: "dst-{Platform}-not-available"
        },
        <!--a = /i/js/dst, b = dst-ios-not-available-->
        deeplink_id: '${deepLinkId}',
        browser_fingerprint_id: '${browserFingerprintId}',
        identity_id: '${identityId}',
        is_valid_identity: '${isValidIdentity}',

        recordId: function () {
            var dataJson = {
                identity_id: redirectInfo.identity_id,
                is_valid_identityid: redirectInfo.is_valid_identity,
                browser_fingerprint_id: redirectInfo.browser_fingerprint_id,
                deeplink_id: redirectInfo.deeplink_id
            };
            $.ajax({
                method: "POST",
                contentType: "application/json; charset=utf-8",
                url: this.recordIdUrl,
                data: JSON.stringify(dataJson),
                success: function () {
                },
                error: function () {
                }
            })
        },
        reportJSEvent: function (a, b) {
            var c = {
                action: a,
                kvs: {
                    click_id: redirectInfo.click_id,
                    destination: b,
                    visit_id: redirectInfo.visit_id
                }
            },
            d = JSON.stringify(c);
            $.post(this.trackingUrl + redirectInfo.app_id, d,
                function (a) {
                }).error(function () {
            })
        },
        reportJSUserClickEvent: function (a, b, c) {
            var d = {
                    action: a,
                    kvs: {
                        click_id: redirectInfo.click_id,
                        user_btn: b,
                        user_choice: c,
                        visit_id: redirectInfo.visit_id
                    }
                },
                e = JSON.stringify(d);
            $.post(this.trackingUrl + redirectInfo.app_id, e,
                function (a) {
                }).error(function () {
            })
        }
    };




