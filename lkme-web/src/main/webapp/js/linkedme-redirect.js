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
    if ("" !== Params.button_text) {
        downloadAPK = Params.button_text;
        gotoAppStore = Params.button_text;
    }

    if (hasIOS()) {
        return void goToNoAppDiv("ios");
    }

    if (hasAndroid()) {
        return void goToNoAppDiv("android");
    }

    if (Params.isWechat()) {
        DEBUG_ALERT("isWeChat");
        if (shouldGotoYYB()) {
            DEBUG_ALERT(Params.yyb_url);
            gotoUrl(Params.yyb_url);
        } else {
            if (Params.isIOS()) {
                DEBUG_ALERT("isIOS");
                gotoTip("ios", lkmeAction.destination.dstWeChatIOS);
            } else if (Params.isAndroid()) {
                DEBUG_ALERT("isAndroid");
                gotoTip("android", lkmeAction.destination.dstWeChatAndroid)
            }
        }
    } else if (Params.isQQ()) {
        DEBUG_ALERT("isQQ");
        if (shouldGotoYYB()) {
            DEBUG_ALERT(Params.yyb_url);
            gotoUrl(Params.yyb_url);
        } else {
            if (Params.isIOS()) {
                DEBUG_ALERT("isIOS");
                gotoTip("ios", lkmeAction.destination.dstQQIOS);
            } else if (Params.isAndroid()) {
                DEBUG_ALERT("isAndroid");
                gotoTip("android", lkmeAction.destination.dstQQAndroid);
            }
        }
    } else if (Params.isWeibo()) {
        DEBUG_ALERT("isWeibo");
        if (Params.isIOS()) {
            DEBUG_ALERT("isIOS");
            gotoTip("ios", lkmeAction.destination.dstWeiboIOS);
        } else if (Params.isAndroid()) {
            DEBUG_ALERT("isAndroid");
            gotoTip("android", lkmeAction.destination.dstWeiboAndroid);
        }
    } else if (Params.isIOS()) {
        DEBUG_ALERT("isIOS");
        var a = Params.uri_scheme + "://";
        if (Params.click_id && Params.click_id.length > 0) {
            a += "?click_id=" + Params.click_id;
        }
        DEBUG_ALERT(a);
        if (Params.ios_major < 9) {
            DEBUG_ALERT("iOS major < 9:" + Params.ios_major);
            iframeDeeplinkLaunch(a, 2e3,
                function () {
                    gotoUrl(Params.forward_url)

                });
        } else {
            DEBUG_ALERT("iOS major >= 9:" + Params.ios_major);
            if (Params.isChrome()) {
                DEBUG_ALERT("isChrome");
                chiosDeeplinkLaunch(a,
                    function () {
                        gotoUrl(Params.forward_url)

                    });
            } else if (Params.isUniversalLink()) {
                DEBUG_ALERT("isUniversalLink = true");
                if (env.cookieEnabled()) {
                    DEBUG_ALERT("cookie Enabled; installStatus:" + Params.installStatus);
                    switch (parseInt(Params.installStatus, 10)) {
                        case CONST_APP_INS_STATUS.Installed:
                            return void gotoIOSLandingPage();
                        case CONST_APP_INS_STATUS.NotInstall:
                            return void gotoUrl(Params.forward_url);
                        case CONST_APP_INS_STATUS.Unclear:
                            return void gotoIOSLandingPage();
                        default:
                            return void gotoIOSLandingPage()
                    }
                } else {
                    DEBUG && alert("cookie Not Enabled");
                    gotoIOSLandingPage();
                }
            } else {
                DEBUG_ALERT("is safari");
                deeplinkLaunch(a, 2500,
                    function () {
                        gotoUrl(Params.forward_url)

                    });
            }
        }
    } else if (Params.isAndroid()) {
        DEBUG_ALERT("isAndroid");
        a = Params.uri_scheme + "://" + Params.host;
        if (Params.click_id && Params.click_id.length > 0) {
            a += "?click_id=" + Params.click_id;
        }
        DEBUG_ALERT(a);
        if (Params.isCannotDeeplink()) {
            iframeDeeplinkLaunch(a, 10e3,
                function () {
                    gotoCannotDeeplink()
                });
        } else if (Params.isQQBrowser()) {
            DEBUG_ALERT("QQ browser");
            if (shouldGotoYYB()) {
                DEBUG_ALERT(Params.yyb_url);
                gotoUrl(Params.yyb_url);
            } else {
                gotoCannotDeeplink();
            }
        } else if (Params.isUC()) {
            DEBUG_ALERT("UC browser");
            gotoUC(a);
        } else if (Params.isChrome() && Params.chrome_major >= 25 && !Params.isForceUseScheme()) {
            DEBUG_ALERT("chrome_major:" + Params.chrome_major);
            var b = Params.host;
            if (Params.click_id && Params.click_id.length > 0) {
                b += "?click_id=" + Params.click_id;
            }
            var c = Params.package_name,
                d = "intent://" + b + "#Intent;scheme=" + Params.uri_scheme + ";package=" + c + ";S.browser_fallback_url=" + Params.forward_url + ";end";
            DEBUG_ALERT("d=" + d);
            deeplinkLaunch(d, 2e3, function () {
                gotoAndroidNewInstall()
            })
        } else {
            DEBUG_ALERT("default browser");
            iframeDeeplinkLaunch(a, 2e3,
                function () {
                    gotoAndroidNewInstall()
                })
        }

    }
}

var winWidth = $(window).width(),
    winHeight = $(window).height(),
    visit_id = "visit_id",
    deeplinkLocation = "",
    dstLocation = "",
    lkmeAction = {
        trackingUrl: "/i/js/actions/",
        actionJSDeepLink: "/i/js/deeplink",
        actionJSDst: "/i/js/dst",
        actionJSUserClick: "/i/js/userclick",
        destination: {
            dstWeChatAndroid: "dst-wechat-android",
            dstWeChatIOS: "dst-wechat-ios",
            dstQQAndroid: "dst-qq-android",
            dstQQIOS: "dst-qq-ios",
            dstWeiboAndroid: "dst-weibo-android",
            dstWeiboIOS: "dst-weibo-ios",
            dstCannotDeepLink: "dst-cannot-deeplink",
            dstUCBrowser: "dst-uc-browser",
            dstUniversalLinkLandingPage: "dst-universallink-landingpage",
            dstAndroidDirectDownloadLandingPage: "dst-android-direct-download-landingpage",
            dstAndroidMarketLandingPage: "dst-android-market-landingpage",
            dstAndroidCannotGoMarketLandingPage: "dst-android-cannot-gomarket-landingpage",
            dstplatformNA: "dst-{Platform}-not-available"
        },
        <!--a = /i/js/dst, b = dst-ios-not-available-->
        reportJSEvent: function (a, b) {
            var c = {
                    action: a,
                    kvs: {
                        click_id: Params.click_id,
                        destination: b,
                        visit_id: Params.visitId
                    }
                },
                d = JSON.stringify(c);
            $.post(this.trackingUrl + Params.app_id, d,
                function (a) {
                }).error(function () {
            })
        },
        reportJSUserClickEvent: function (a, b, c) {
            var d = {
                    action: a,
                    kvs: {
                        click_id: Params.click_id,
                        user_btn: b,
                        user_choice: c,
                        visit_id: Params.visitId
                    }
                },
                e = JSON.stringify(d);
            $.post(this.trackingUrl + Params.app_id, e,
                function (a) {
                }).error(function () {
            })
        }
    },
    CONST_APP_INS_STATUS = {
        NotInstall: 0,
        Installed: 1,
        Unclear: 2
    },

    wechatTemplate = '<div class="image-tip" width="100%" height="100%" style="position:relative;">    <div style="background-color:#ffffff;width:100%;height:100%;position:absolute; top:0;">        {img_tip}    </div>    <div style="text-align:center; width:100%; position:absolute; top:67%">        </div></div>',
    imgInfo = "<img src=" + baseImgPathLang + 'open_{mobile-os}_browser.png align="center" style="height: 100%;"/>',
    div_goto_landingpage = '<div style="background-image:url({Bg_Url});background-size: 100% 100%;width:100%;height:100%;">    <div style = "position:absolute; top:20%; width:100%; ">        <div style="text-align:center; width:100%; ">            <img id="appIcon" src={logo_url} style="width:22%;"/>        </div>        <div style="text-align:center; width:100%; margin-top:10px;">            <span id="appName" style="font-size: 1.5em; color: #959595; padding: 15px 10px;">                {app_name}            </span>        </div>    </div>    <div style="text-align:center; width:100%; position:absolute; top:56%;">        <span id="downloadTitle" style="font-size: 1em; color: #959595; padding: 15px 10px;">            {Download_title}        </span>    </div>    <div style="text-align:center; width:100%; position:absolute; top:59%;">    </div>    <div style="text-align:center; width:100%; position:absolute; top:70%;">        <{Element_type} id="btnGotoLandingPage" style="background-color:#FFFFFF; border: {Border_width}px solid #959595; color: #959595; padding: 6px 20px; -webkit-border-radius: 30px; -moz-border-radius: 30px; border-radius: 30px;">{Btn_landingpage_text}</{Element_type}>    </div></div>',
    div_goto_cannot_deeplink_with_market_btn = '<div style="background-image:url(' + baseImgPathLang + 'cannot_forward.png);background-size: 100% 100%;width:100%;height:100%;">    <div style="text-align:center; width:100%; position:absolute; top:80%;">        <button id="btnGotoAndroidMarket" style="font-size: 1em; background-color:#FFFFFF; border: 3px solid #959595; color: #959595; padding: 6px 20px; -webkit-border-radius: 30px; -moz-border-radius: 30px; border-radius: 30px;">' + gotoStore + "</button>    </div></div>",
    div_goto_cannot_deeplink_with_download_btn = '<div style="background-image:url(' + baseImgPathLang + 'cannot_forward.png);background-size: 100% 100%;width:100%;height:100%;">    <div style="text-align:center; width:100%; position:absolute; top:80%;">        <button id="btnGotoAndroidDownload" style="font-size: 1em; background-color:#FFFFFF; border: 3px solid #959595; color: #959595; padding: 6px 20px; -webkit-border-radius: 30px; -moz-border-radius: 30px; border-radius: 30px;">' + downloadAPK + "</button>    </div></div>",
    div_allow_me_deeplink = '<div style="background-image:url(' + baseImgPathLang + 'open_app.png);background-size: 100% 100%;width:100%;height:100%;">    <div style="text-align:center; width:100%; position:absolute; top:35%;">        <p id="textCountDown" style="font-size: 1em; color: #959595; padding: 6px 20px; -webkit-border-radius: 30px; -moz-border-radius: 30px; border-radius: 30px;"></p>    </div></div>',

    div_platform_not_available = '<div style="background-image:url(' + baseImgPathLang + 'no_{Platform}.png);background-size: 100% 100%;width:100%;height:100%;"></div>',

    env = {
        windowLocation: function (a) {
            window.location = a
        },
        windowOpen: function (a) {
            window.open(a)
        },
        windowClose: function () {
            window.close()
        },
        windowChangeHistory: function () {
            window.history.replaceState("Object", "Title", "0")
        },
        windowAddEventListener: function (a, b) {
            window.addEventListener(a, b)
        },
        windowUrlAddFlag: function () {
            if (window.location.search.indexOf(visit_id) < 0) {
                var a = Math.floor(1e6 * Math.random()),
                    b = visit_id + "=" + a;
                if (window.location.search == undefined || window.location.search != "") {
                    window.location.search += "&" + b;
                } else {
                    window.location.search = "?" + b;
                }
                DEBUG && alert("Url Add Tag:" + b)
            }
        },
        cookieEnabled: function () {
            var a = !1;
            try {
                localStorage.test = 2
            } catch (b) {
                DEBUG && alert("private mode"),
                    a = !0
            }
            return navigator.cookieEnabled && !a
        }
    },
    clearTimeoutOnPageUnload = function (a) {
        env.windowAddEventListener("pagehide",
            function () {
                DEBUG && alert("window event pagehide"),
                    clearTimeout(a),
                    env.windowChangeHistory()
            }),
            env.windowAddEventListener("blur",
                function () {
                    DEBUG && alert("window event blur"),
                        clearTimeout(a),
                        env.windowChangeHistory()
                }),
            env.windowAddEventListener("unload",
                function () {
                    DEBUG && alert("window event unload"),
                        clearTimeout(a),
                        env.windowChangeHistory()
                }),
            document.addEventListener("webkitvisibilitychange",
                function () {
                    DEBUG && alert("window event webkitvisibilitychange"),
                    document.webkitHidden && (clearTimeout(a), env.windowChangeHistory())
                }),
            env.windowAddEventListener("beforeunload",
                function () {
                    DEBUG && alert("window event beforeunload")
                }),
            env.windowAddEventListener("focus",
                function () {
                    DEBUG && alert("window event focus")
                }),
            env.windowAddEventListener("focusout",
                function () {
                    DEBUG && alert("window event focusout"),
                        clearTimeout(a),
                        env.windowChangeHistory()
                })
    },
    gotoTip = function (a, b) {
        weixin_tip = wechatTemplate.replace(/{img_tip}/g, imgInfo).replace(/{logo_url}/g, Params.logo_url).replace(/{mobile-os}/g, a),
            $("body").append(weixin_tip),
            $(".image-tip").show(),
            env.windowUrlAddFlag(),
            dstLocation = b,
            lkmeAction.reportJSEvent(lkmeAction.actionJSDst, dstLocation)
    },
    gotoCannotDeeplink = function () {
        DEBUG && alert("cannot deeplink"),
            Params.isDownloadDirectly() ? ($("body").append(div_goto_cannot_deeplink_with_download_btn), $("#btnGotoAndroidDownload").click(function () {
                lkmeAction.reportJSUserClickEvent(lkmeAction.actionJSUserClick, "gotoAndroidDirectDownload", "yes"),
                    gotoUrl(Params.forward_url)
            })) : ($("body").append(div_goto_cannot_deeplink_with_market_btn), $("#btnGotoAndroidMarket").click(function () {
                lkmeAction.reportJSUserClickEvent(lkmeAction.actionJSUserClick, "gotoAndroidMarket", "yes"),
                    gotoAndroidMarket()
            })),
            dstLocation = lkmeAction.destination.dstCannotDeepLink,
            lkmeAction.reportJSEvent(lkmeAction.actionJSDst, dstLocation)
    },
    gotoAndroidNewInstall = function () {
        Params.isDownloadDirectly() ? gotoAndroidDownloadLandingPage() : Params.isCannotGoMarket() ? gotoAndroidCannotGoMarketLandingPage() : Params.isCannotGetWinEvent() || Params.isUC() ? gotoAndroidMarketLandingPage() : gotoAndroidMarket()
    },
    gotoIOSLandingPage = function () {
        dstLocation = lkmeAction.destination.dstUniversalLinkLandingPage,
        DEBUG && alert(dstLocation),
            div_goto_landingpage = div_goto_landingpage.replace(/{Bg_Url}/g, baseImgPathLang + "bg.png").replace(/{app_name}/g, Params.app_name).replace(/{logo_url}/g, Params.logo_url).replace(/{Download_title}/g, Params.app_title).replace(/{Download_msg}/g, Params.app_slogan).replace(/{Btn_landingpage_text}/g, gotoAppStore).replace(/{Border_width}/g, "3").replace(/{Element_type}/g, "button"),
            $("body").append(div_goto_landingpage),
            lkmeAction.reportJSEvent(lkmeAction.actionJSDst, dstLocation),
            $("#btnGotoLandingPage").click(function () {
                lkmeAction.reportJSUserClickEvent(lkmeAction.actionJSUserClick, "gotoIosAppStore", "yes"),
                    gotoUrl(Params.forward_url)
            })
    },
    gotoAndroidMarketLandingPage = function () {
        dstLocation = lkmeAction.destination.dstAndroidMarketLandingPage,
        DEBUG && alert(dstLocation),
            div_goto_landingpage = div_goto_landingpage.replace(/{Bg_Url}/g, baseImgPathLang + "bg.png").replace(/{app_name}/g, Params.app_name).replace(/{logo_url}/g, Params.logo_url).replace(/{Download_title}/g, Params.app_title).replace(/{Download_msg}/g, Params.app_slogan).replace(/{Btn_landingpage_text}/g, gotoAppStore).replace(/{Border_width}/g, "3").replace(/{Element_type}/g, "button"),
            $("body").append(div_goto_landingpage),
            lkmeAction.reportJSEvent(lkmeAction.actionJSDst, dstLocation),
            $("#btnGotoLandingPage").click(function () {
                lkmeAction.reportJSUserClickEvent(lkmeAction.actionJSUserClick, "gotoAndroidMarket", "yes"),
                    gotoAndroidMarket()
            })
    },
    gotoAndroidCannotGoMarketLandingPage = function () {
        dstLocation = lkmeAction.destination.dstAndroidCannotGoMarketLandingPage,
        DEBUG && alert(dstLocation),
            div_goto_landingpage = div_goto_landingpage.replace(/{Bg_Url}/g, baseImgPathLang + "bg.png").replace(/{app_name}/g, Params.app_name).replace(/{logo_url}/g, Params.logo_url).replace(/{Download_title}/g, Params.app_title).replace(/{Download_msg}/g, Params.app_slogan).replace(/{Btn_landingpage_text}/g, openStore).replace(/{Border_width}/g, "0").replace(/{Element_type}/g, "p"),
            $("body").append(div_goto_landingpage),
            lkmeAction.reportJSEvent(lkmeAction.actionJSDst, dstLocation)
    },
    gotoAndroidDownloadLandingPage = function () {
        dstLocation = lkmeAction.destination.dstAndroidDirectDownloadLandingPage,
        DEBUG && alert(dstLocation),
            div_goto_landingpage = div_goto_landingpage.replace(/{Bg_Url}/g, baseImgPathLang + "bg.png").replace(/{app_name}/g, Params.app_name).replace(/{logo_url}/g, Params.logo_url).replace(/{Download_title}/g, Params.app_title).replace(/{Download_msg}/g, Params.app_slogan).replace(/{Btn_landingpage_text}/g, downloadAPK).replace(/{Border_width}/g, "3").replace(/{Element_type}/g, "button"),
            $("body").append(div_goto_landingpage),
            lkmeAction.reportJSEvent(lkmeAction.actionJSDst, dstLocation),
            $("#btnGotoLandingPage").click(function () {
                lkmeAction.reportJSUserClickEvent(lkmeAction.actionJSUserClick, "gotoAndroidDirectDownload", "yes"),
                    gotoUrl(Params.forward_url)
            })
    },
    gotoUC = function (a) {
        dstLocation = lkmeAction.destination.dstUCBrowser;
        var b = $("body").html();
        $("body").append(div_allow_me_deeplink);
        var c = 6,
            d = function () {
                c--,
                    $("#textCountDown").html(c),
                    0 === c ? ($("#textCountDown").html(""), iframeDeeplinkLaunch(a, 10e3,
                        function () {
                            $("body").html(b),
                                gotoAndroidNewInstall()
                        })) : setTimeout(d, 1e3)
            };
        d()
    },
    gotoAndroidMarket = function () {
        env.windowChangeHistory(),
            dstLocation = "market://details?id=" + Params.package_name,
        DEBUG && alert(dstLocation),
            lkmeAction.reportJSEvent(lkmeAction.actionJSDst, dstLocation),
            env.windowLocation(dstLocation)
    },
    gotoUrl = function (a) {
        env.windowChangeHistory(),
            dstLocation = a,
            lkmeAction.reportJSEvent(lkmeAction.actionJSDst, a),
            env.windowLocation(a)
    },
    goToNoAppDiv = function (a) {
        div_platform_NA = div_platform_not_available.replace(/{Platform}/g, a),
            $("body").append(div_platform_NA),
            dstLocation = lkmeAction.destination.dstplatformNA.replace(/{Platform}/g, a),
            lkmeAction.reportJSEvent(lkmeAction.actionJSDst, dstLocation)
    },
    deeplinkLaunch = function (a, b, c) {
        deeplinkLocation = a,
        DEBUG && alert(deeplinkLocation),
            lkmeAction.reportJSEvent(lkmeAction.actionJSDeepLink, a),
            // a = "intent://linkedme?click_id=sWpK2qR01#Intent;scheme=linkedmedemo;package=com.microquation.linkedme.android;S.browser_fallback_url=https://www.baidu.com;end";
            env.windowLocation(a);
        var d = setTimeout(function () {
                c()
            },
            b);
        clearTimeoutOnPageUnload(d)
    },
    chiosDeeplinkLaunch = function (a, b) {
        deeplinkLocation = a;
        var c = null;
        try {
            lkmeAction.reportJSEvent(lkmeAction.actionJSDeepLink, a),
                c = env.windowOpen(a),
            DEBUG && alert("pass"),
                env.windowChangeHistory()
        } catch (d) {
            DEBUG && alert("exception")
        }
        c ? env.windowClose() : b()
    },
    iframeDeeplinkLaunch = function (a, b, c) {
        var d = document.createElement("iframe");
        d.style.width = "1px",
            d.style.height = "1px",
            d.border = "none",
            d.style.display = "none",
            d.src = a,
            document.body.appendChild(d),
            deeplinkLocation = a,
            lkmeAction.reportJSEvent(lkmeAction.actionJSDeepLink, a);
        var e = setTimeout(function () {
                c()
            },
            b);
        clearTimeoutOnPageUnload(e)
    },
    hasIOS = function () {
        return Params.isIOS() && (void 0 === Params.bundle_id || "" === Params.bundle_id)
    },
    hasAndroid = function () {
        return Params.isAndroid() && (void 0 === Params.package_name || "" === Params.package_name)
    },
    shouldGotoYYB = function () {
        return void 0 !== Params.yyb_url && "" !== Params.yyb_url && !Params.isIOS()
    };