function start() {
    if ("" === Params.Download_msg && (Params.Download_msg = ResDefaultDownloadMsg), "" !== Params.Download_btn_text && (ResDownloadAPK = Params.Download_btn_text, ResGotoAppStoreDownload = Params.Download_btn_text), isIosNotAvailable()) return void gotoDivPlatformNotAvail("ios");
    if (isAndroidNotAvailable()) return void gotoDivPlatformNotAvail("android");
    if (Params.isWechat()) if (DEBUG && alert("isWeChat"), Params.isIOS() && Params.Ios_major >= 9) switch (parseInt(Params.AppInsStatus, 10)) {
        case CONST_APP_INS_STATUS.Installed:
            return void gotoTip("ios", dsAction.destination.dstweixintipios, Params.UserConf_Bg_WechatIosTip_url);
        case CONST_APP_INS_STATUS.NotInstall:
            return void("" !== Params.Uninstall_url ? gotoUrl(Params.Uninstall_url) : gotoTencentProduct(dsAction.destination.dstweixintipios, dsAction.destination.dstweixintipandroid));
        case CONST_APP_INS_STATUS.Unclear:
            return void("" !== Params.Uninstall_url ? gotoUrl(Params.Uninstall_url) : gotoTencentProduct(dsAction.destination.dstweixintipios, dsAction.destination.dstweixintipandroid));
        default:
            return void gotoTip("ios", dsAction.destination.dstweixintipios, Params.UserConf_Bg_WechatIosTip_url)
    } else gotoTencentProduct(dsAction.destination.dstweixintipios, dsAction.destination.dstweixintipandroid);
    else if (Params.isQQ()) DEBUG && alert("isQQ"),
        gotoTencentProduct(dsAction.destination.dstqqtipios, dsAction.destination.dstqqtipandroid);
    else if (Params.isWeibo()) DEBUG && alert("isWeibo"),
        Params.isIOS() ? (DEBUG && alert("isIOS"), gotoTip("ios", dsAction.destination.dstweibotipios, Params.UserConf_Bg_WechatIosTip_url)) : Params.isAndroid() && (DEBUG && alert("isAndroid"), gotoTip("android", dsAction.destination.dstweibotipandroid, Params.UserConf_Bg_WechatAndroidTip_url));
    else if (Params.isFacebook()) DEBUG && alert("isFacebook"),
        Params.isIOS() ? (DEBUG && alert("isIOS"), gotoTip("ios_right_down", dsAction.destination.dstfacebooktipios, "")) : Params.isAndroid() && (DEBUG && alert("isAndroid"), gotoTip("android", dsAction.destination.dstfacebooktipandroid, ""));
    else if (Params.isTwitter()) DEBUG && alert("isTwitter"),
        Params.isIOS() ? (DEBUG && alert("isIOS"), gotoTip("ios_right_down", dsAction.destination.dsttwittertipios, "")) : Params.isAndroid() && (DEBUG && alert("isAndroid"), gotoTip("android", dsAction.destination.dsttwittertipandroid, ""));
    else if (Params.isIOS()) {
        DEBUG && alert("isIOS");
        var a = Params.Scheme + "://";
        if (Params.Match_id && Params.Match_id.length > 0 && (a += "?click_id=" + Params.Match_id), DEBUG && alert(a), Params.Ios_major < 9) DEBUG && alert("IOS Major below 9:" + Params.Ios_major),
            iframeDeeplinkLaunch(a, 2e3,
                function() {
                    gotoUrl(Params.Url)
                });
        else if (DEBUG && alert("IOS Major upper 9:" + Params.Ios_major), Params.isChrome()) DEBUG && alert("isChrome"),
            chiosDeeplinkLaunch(a,
                function() {
                    gotoUrl(Params.Url)
                });
        else if (Params.isUniversallink()) if (DEBUG && alert("isUniversallink = true"), env.cookieEnabled()) switch (DEBUG && alert("cookie Enabled; AppInsStatus:" + Params.AppInsStatus), parseInt(Params.AppInsStatus, 10)) {
            case CONST_APP_INS_STATUS.Installed:
                return void gotoIOSLandingPage();
            case CONST_APP_INS_STATUS.NotInstall:
                return void gotoUrl(Params.Url);
            case CONST_APP_INS_STATUS.Unclear:
                return void gotoIOSLandingPage();
            default:
                return void gotoIOSLandingPage()
        } else DEBUG && alert("cookie Not Enabled"),
            gotoIOSLandingPage();
        else DEBUG && alert("is safari"),
                deeplinkLaunch(a, 500,
                    function() {
                        gotoUrl(Params.Url)
                    })
    } else if (Params.isAndroid())
        if (DEBUG && alert("isAndroid"), a = Params.Scheme + "://" + Params.Host, Params.Match_id && Params.Match_id.length > 0 && (a += "?click_id=" + Params.Match_id), DEBUG && alert(a), Params.isCannotDeeplink())
            iframeDeeplinkLaunch(a, 2e3,
        function() {
            gotoCannotDeeplink()
        });
    else if (Params.isQQBrowser()) DEBUG && alert("QQ browser"),
        Params.isYYBEnableAndroid() ? (DEBUG && alert(Params.YYB_url), gotoUrl(Params.YYB_url)) : gotoCannotDeeplink();
    else if (Params.isUC()) DEBUG && alert("UC browser"),
        gotoUC(a);
    else if (Params.isChrome() && Params.Chrome_major >= 25 && !Params.isForceUseScheme()) {
        DEBUG && alert("Chrome_major:" + Params.Chrome_major);
        var b = Params.Host;
        Params.Match_id && Params.Match_id.length > 0 && (b += "?click_id=" + Params.Match_id);
        var c = Params.Pkg,
            d = "intent://" + b + "#Intent;scheme=" + toLowerCase(Params.Scheme) + ";package=" + c + ";S.browser_fallback_url=" + Params.Url + ";end";
        deeplinkLaunch(d, 2e3,
            function() {
                gotoAndroidNewInstall()
            })
    } else DEBUG && alert("default browser"),
        iframeDeeplinkLaunch(a, 2e3,
            function() {
                gotoAndroidNewInstall()
            })
}
function toLowerCase(a) {
    return "string" == typeof a ? a.toLowerCase() : "undefined" == typeof a || null === a ? "": (a = "" + a, a.toLowerCase())
}
var winWidth = $(window).width(),
    winHeight = $(window).height(),
    CONST_DS_TAG = "ds_tag",
    CONST_DSCOOKIE = "dscookie",
    CONST_W_DSCOOKIE = "wcookie",
    deeplinkLocation = "",
    dstLocation = "",
    ResPath = "../../jsserver/",
    ResPathImg = ResPath + "images/",
    LANDING_BG_TOP = "https://ds-static.fds.so/ds-static/sharelink/landing/landing-bg-top.png",
    DRAGDOWN = "https://ds-static.fds.so/ds-static/sharelink/landing/dragdown.png",
    dsAction = {
        trackingUrl: "/v2/dsactions/",
        actionJSDeepLink: "js/deeplink",
        actionJSDst: "js/dst",
        actionJSUserClick: "js/userclick",
        destination: {
            dstweixintipandroid: "dst-weixin-tip-android",
            dstweixintipios: "dst-weixin-tip-ios",
            dstqqtipandroid: "dst-qq-tip-android",
            dstqqtipios: "dst-qq-tip-ios",
            dstweibotipandroid: "dst-weibo-tip-android",
            dstweibotipios: "dst-weibo-tip-ios",
            dstfacebooktipandroid: "dst-facebook-tip-android",
            dstfacebooktipios: "dst-facebook-tip-ios",
            dsttwittertipandroid: "dst-twitter-tip-android",
            dsttwittertipios: "dst-twitter-tip-ios",
            dstcannotdeeplink: "dst-cannot-deeplink",
            dstucbrowser: "dst-uc-browser",
            dstios9UniversalLinkLandPage: "dst-ios9-universallink-landpage",
            dstandroidDirectDownloadLandPage: "dst-android-direct-download-landpage",
            dstandroidMarketLandPage: "dst-android-market-landpage",
            dstandroidCannotGoMarketLandPage: "dst-android-cannot-gomarket-landpage",
            dstplatformNA: "dst-{Platform}-not-available"
        },
        reportDSJSEvent: function(a, b) {
            var c = {
                    action: a,
                    kvs: {
                        click_id: Params.Match_id,
                        destination: b,
                        ds_tag: Params.DsTag
                    }
                },
                d = JSON.stringify(c),
                e = this.trackingUrl + Params.AppID;
            $.ajax({
                url: e,
                type: "POST",
                data: d
            })
        },
        reportDSJSUserClickEvent: function(a, b, c) {
            var d = {
                    action: a,
                    kvs: {
                        click_id: Params.Match_id,
                        user_btn: b,
                        user_choice: c,
                        ds_tag: Params.DsTag
                    }
                },
                e = JSON.stringify(d),
                f = this.trackingUrl + Params.AppID;
            $.ajax({
                url: f,
                type: "POST",
                data: e
            })
        }
    },
    CONST_APP_INS_STATUS = {
        NotInstall: 0,
        Installed: 1,
        Unclear: 2
    },
    env = {
        windowLocation: function(a) {
            window.location = a
        },
        windowOpen: function(a) {
            window.open(a)
        },
        windowClose: functinnnon() {
            window.close()
        },
        windowChangeHistory: function() {
            window.history.replaceState("Object", "Title", "0")
        },
        windowAddEventListener: function(a, b) {
            window.addEventListener(a, b)
        },
        windowUrlAddTag: function() {
            if (window.location.search.indexOf(CONST_DS_TAG) < 0) {
                var a = Math.floor(1e6 * Math.random()),
                    b = Params.Match_id,
                    c = "?" + CONST_DS_TAG + "=" + a + "&" + CONST_W_DSCOOKIE + "=" + b;
                window.location.search = c,
                DEBUG && alert("Url Add Tag:" + c)
            }
        },
        cookieEnabled: function() {
            var a = !1;
            try {
                localStorage.test = 2
            } catch(b) {
                DEBUG && alert("private mode"),
                    a = !0
            }
            return navigator.cookieEnabled && !a
        }
    },
    renderTemplete = function(a, b, c) {
        var d = $("#" + a),
            e = $("#" + b);
        if (d.length > 0 && e.length > 0) {
            var f = d.html();
            Mustache.parse(f, ["${", "}"]);
            var g = Mustache.render(f, c);
            e.html(g),
                e.removeClass("hide")
        }
    },
    clearTimeoutOnPageUnload = function(a) {
        env.windowAddEventListener("pagehide",
            function() {
                DEBUG && alert("window event pagehide"),
                    clearTimeout(a),
                    env.windowChangeHistory()
            }),
            env.windowAddEventListener("blur",
                function() {
                    DEBUG && alert("window event blur"),
                        clearTimeout(a),
                        env.windowChangeHistory()
                }),
            env.windowAddEventListener("unload",
                function() {
                    DEBUG && alert("window event unload"),
                        clearTimeout(a),
                        env.windowChangeHistory()
                }),
            document.addEventListener("webkitvisibilitychange",
                function() {
                    DEBUG && alert("window event webkitvisibilitychange"),
                    document.webkitHidden && (clearTimeout(a), env.windowChangeHistory())
                }),
            env.windowAddEventListener("beforeunload",
                function() {
                    DEBUG && alert("window event beforeunload")
                }),
            env.windowAddEventListener("focus",
                function() {
                    DEBUG && alert("window event focus")
                }),
            env.windowAddEventListener("focusout",
                function() {
                    DEBUG && alert("window event focusout"),
                        clearTimeout(a),
                        env.windowChangeHistory()
                })
    },
    gotoTip = function(a, b, c) {
        var d = "",
            e = {};
        if ("" !== c) d = "weixinTipUserConfigTemplate",
            e = {
                userconfigBg: c
            };
        else {
            var f, g, h, i, j, k;
            d = "weixinTipTemplate",
                "ios" == a || "ios_right_down" == a ? (f = "https://ds-static.fds.so/ds-static/sharelink/openbrowser_ios/bg.png", g = "https://ds-static.fds.so/ds-static/sharelink/openbrowser_ios/icons.png", h = "ios_right_down" == a ? ResiOSOpenbrowserStep1RightDown: ResiOSOpenbrowserStep1, i = ResiOSOpenbrowserStep2, j = ResiOSOpenbrowserStep3, k = ResiOSOpenbrowserStep4) : (f = "https://ds-static.fds.so/ds-static/sharelink/openbrowser_android/bg.png", g = "https://ds-static.fds.so/ds-static/sharelink/openbrowser_android/icons.png", h = ResAndroidOpenbrowserStep1, i = ResAndroidOpenbrowserStep2, j = ResAndroidOpenbrowserStep3, k = ResAndroidOpenbrowserStep4),
                e = {
                    iconUrl: Params.IconUrl,
                    resOpenbrowserMsg: ResOpenbrowserMsg,
                    bgImgUrl: f,
                    iconsImgUrl: g,
                    openbrowserStep1: h,
                    openbrowserStep2: i,
                    openbrowserStep3: j,
                    openbrowserStep4: k
                }
        }
        renderTemplete(d, "gotoTip", e),
            $(".image-tip").show(),
            env.windowUrlAddTag(),
            dstLocation = b,
            dsAction.reportDSJSEvent(dsAction.actionJSDst, dstLocation)
    },
    gotoTencentProduct = function(a, b) {
        Params.isIOS() ? (DEBUG && alert("isIOS"), Params.Ios_major < 9 && Params.isYYBEnableIosBelow9() || Params.Ios_major >= 9 && Params.isYYBEnableIosAbove9() ? (DEBUG && alert(Params.YYB_url), gotoUrl(Params.YYB_url)) : gotoTip("ios", a, Params.UserConf_Bg_WechatIosTip_url)) : Params.isAndroid() && (DEBUG && alert("isAndroid"), Params.isYYBEnableAndroid() ? (DEBUG && alert(Params.YYB_url), gotoUrl(Params.YYB_url)) : gotoTip("android", b, Params.UserConf_Bg_WechatAndroidTip_url))
    },
    gotoCannotDeeplink = function() {
        DEBUG && alert("cannot deeplink"),
            Params.isDownloadDirectly() ? (renderTemplete("gotoCannotDeeplinkWithDownloadBtnTemplate", "gotoCannotDeeplink", {
                bgTopImgUrl: ResPathImg + "oops/oops-bg-top.png",
                iconUrl: ResPathImg + "oops/oops-icon.png",
                bgBottomImgUrl: ResPathImg + "oops/oops-bg-jump.png",
                resOopsMsg: ResOopsMsg,
                resOopsTips: ResOopsTips,
                resDownloadAPK: ResDownloadAPK
            }), $("#btnGotoAndroidDownload").click(function() {
                dsAction.reportDSJSUserClickEvent(dsAction.actionJSUserClick, "gotoAndroidDirectDownload", "yes"),
                    gotoUrl(Params.Url)
            })) : (renderTemplete("gotoCannotDeeplinkWithMarketBtnTemplate", "gotoCannotDeeplink", {
                bgTopImgUrl: ResPathImg + "oops/oops-bg-top.png",
                iconUrl: ResPathImg + "oops/oops-icon.png",
                bgBottomImgUrl: ResPathImg + "oops/oops-bg-jump.png",
                resOopsMsg: ResOopsMsg,
                resOopsTips: ResOopsTips,
                resGotoAppStore: ResGotoAppStoreDownload
            }), $("#btnGotoAndroidMarket").click(function() {
                dsAction.reportDSJSUserClickEvent(dsAction.actionJSUserClick, "gotoAndroidMarket", "yes"),
                    gotoAndroidMarket()
            })),
            dstLocation = dsAction.destination.dstcannotdeeplink,
            dsAction.reportDSJSEvent(dsAction.actionJSDst, dstLocation)
    },
    gotoAndroidNewInstall = function() {
        Params.isDownloadDirectly() ? gotoAndroidDownloadLandingPage() : Params.isCannotGoMarket() ? gotoAndroidCannotGoMarketLandingPage() : Params.isCannotGetWinEvent() || Params.isUC() ? gotoAndroidMarketLandingPage() : gotoAndroidMarket()
    },
    gotoIOSLandingPage = function() {
        dstLocation = dsAction.destination.dstios9UniversalLinkLandPage,
        DEBUG && alert(dstLocation),
            renderTemplete("gotoLandingpageTemplate", "gotoLandingpage", {
                bgTop: LANDING_BG_TOP,
                appName: Params.AppName,
                iconUrl: Params.IconUrl,
                downloadTitle: Params.Download_title,
                downloadMsg: Params.Download_msg,
                btnLandingpageText: ResGotoAppStoreDownload,
                elementType: "button",
                dragdownIcon: DRAGDOWN,
                dragdownTip: ResDragdownTip,
                "dragdown-display": ""
            }),
            dsAction.reportDSJSEvent(dsAction.actionJSDst, dstLocation),
            $("#btnGotoLandingPage").click(function() {
                dsAction.reportDSJSUserClickEvent(dsAction.actionJSUserClick, "gotoIosAppStore", "yes"),
                    gotoUrl(Params.Url)
            })
    },
    gotoAndroidMarketLandingPage = function() {
        dstLocation = dsAction.destination.dstandroidMarketLandPage,
        DEBUG && alert(dstLocation),
            renderTemplete("gotoLandingpageTemplate", "gotoLandingpage", {
                bgTop: LANDING_BG_TOP,
                appName: Params.AppName,
                iconUrl: Params.IconUrl,
                downloadTitle: Params.Download_title,
                downloadMsg: Params.Download_msg,
                btnLandingpageText: ResGotoAppStoreDownload,
                elementType: "button",
                dragdownIcon: "",
                dragdownTip: "",
                "dragdown-display": "hide"
            }),
            dsAction.reportDSJSEvent(dsAction.actionJSDst, dstLocation),
            $("#btnGotoLandingPage").click(function() {
                dsAction.reportDSJSUserClickEvent(dsAction.actionJSUserClick, "gotoAndroidMarket", "yes"),
                    gotoAndroidMarket()
            })
    },
    gotoAndroidCannotGoMarketLandingPage = function() {
        dstLocation = dsAction.destination.dstandroidCannotGoMarketLandPage,
        DEBUG && alert(dstLocation),
            renderTemplete("gotoLandingpageTemplate", "gotoLandingpage", {
                bgTop: LANDING_BG_TOP,
                appName: Params.AppName,
                iconUrl: Params.IconUrl,
                downloadTitle: Params.Download_title,
                downloadMsg: Params.Download_msg,
                btnLandingpageText: ResPleaseOpenAppStore,
                elementType: "p",
                dragdownIcon: "",
                dragdownTip: "",
                "dragdown-display": "hide"
            }),
            dsAction.reportDSJSEvent(dsAction.actionJSDst, dstLocation)
    },
    gotoAndroidDownloadLandingPage = function() {
        dstLocation = dsAction.destination.dstandroidDirectDownloadLandPage,
        DEBUG && alert(dstLocation),
            renderTemplete("gotoLandingpageTemplate", "gotoLandingpage", {
                bgTop: LANDING_BG_TOP,
                appName: Params.AppName,
                iconUrl: Params.IconUrl,
                downloadTitle: Params.Download_title,
                downloadMsg: Params.Download_msg,
                btnLandingpageText: ResDownloadAPK,
                elementType: "button",
                dragdownIcon: "",
                dragdownTip: "",
                "dragdown-display": "hide"
            }),
            $("#btnGotoLandingPage").click(function() {
                dsAction.reportDSJSUserClickEvent(dsAction.actionJSUserClick, "gotoAndroidDirectDownload", "yes"),
                    gotoUrl(Params.Url)
            }),
            dsAction.reportDSJSEvent(dsAction.actionJSDst, dstLocation)
    },
    gotoUC = function(a) {
        dstLocation = dsAction.destination.dstucbrowser;
        var b = $("body").html();
        renderTemplete("allowMeDeeplinkTemplate", "allowMeDeeplink", {
            bgTopImgUrl: ResPathImg + "allowme/allowme-bg-top.png",
            bgBottomImgUrl: ResPathImg + "allowme/allowme-bg-bottom.png",
            resAllowMeMsg: ResAllowMeMsg,
            resAllowMeTips: ResAllowMeTips,
            resOr: ResOr,
            resAllowMeThisTime: ResAllowMeThisTime,
            resAllowMeAlways: ResAllowMeAlways
        });
        var c = 6,
            d = function() {
                c--,
                    $("#textCountDown").html(c),
                    0 === c ? ($("#textCountDown").html(""), iframeDeeplinkLaunch(a, 3e3,
                        function() {
                            $("body").html(b),
                                gotoAndroidNewInstall()
                        })) : setTimeout(d, 1e3)
            };
        d()
    },
    gotoAndroidMarket = function() {
        env.windowChangeHistory(),
            dstLocation = "market://details?id=" + Params.Pkg,
        DEBUG && alert(dstLocation),
            dsAction.reportDSJSEvent(dsAction.actionJSDst, dstLocation),
            env.windowLocation(dstLocation)
    },
    gotoUrl = function(a) {
        env.windowChangeHistory(),
            dstLocation = a,
            dsAction.reportDSJSEvent(dsAction.actionJSDst, a),
            env.windowLocation(a)
    },
    gotoDivPlatformNotAvail = function(a) {
        var b = "";
        "ios" == a ? b = ResiOSNotAvailable: "android" == a && (b = ResAndroidNotAvailable),
            renderTemplete("platformNotAvailableTemplate", "platformNotAvailable", {
                bgImgUrl: ResPathImg + a + "_not_avail/notavail.png",
                resNotAvailable: b
            }),
            dstLocation = dsAction.destination.dstplatformNA.replace(/{Platform}/g, a),
            dsAction.reportDSJSEvent(dsAction.actionJSDst, dstLocation)
    },
    deeplinkLaunch = function(a, b, c) {
        deeplinkLocation = a,
        DEBUG && alert(deeplinkLocation),
            dsAction.reportDSJSEvent(dsAction.actionJSDeepLink, a),
            env.windowLocation(a);
        var d = setTimeout(function() {
                c()
            },
            b);
        clearTimeoutOnPageUnload(d)
    },
    chiosDeeplinkLaunch = function(a, b) {
        deeplinkLocation = a;
        var c = null;
        try {
            dsAction.reportDSJSEvent(dsAction.actionJSDeepLink, a),
                c = env.windowOpen(a),
            DEBUG && alert("pass"),
                env.windowChangeHistory()
        } catch(d) {
            DEBUG && alert("exception")
        }
        c ? env.windowClose() : b()
    },
    iframeDeeplinkLaunch = function(a, b, c) {
        var d = document.createElement("iframe");
        d.style.width = "1px",
            d.style.height = "1px",
            d.border = "none",
            d.style.display = "none",
            d.src = a,
            document.body.appendChild(d),
            deeplinkLocation = a,
            dsAction.reportDSJSEvent(dsAction.actionJSDeepLink, a);
        var e = setTimeout(function() {
                c()
            },
            b);
        clearTimeoutOnPageUnload(e)
    },
    isIosNotAvailable = function() {
        return Params.isIOS() && (void 0 === Params.BundleID || "" === Params.BundleID)
    },
    isAndroidNotAvailable = function() {
        return Params.isAndroid() && (void 0 === Params.Pkg || "" === Params.Pkg)
    },
    shouldGotoYYB = function() {
        return void 0 !== Params.YYB_url && "" !== Params.YYB_url && !Params.isIOS()
    };
window.onload = function() {
    start()
};