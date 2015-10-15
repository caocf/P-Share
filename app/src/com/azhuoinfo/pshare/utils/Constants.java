package com.azhuoinfo.pshare.utils;

public class Constants {
	// 开发模式 显示log、生命周期等
	public static final boolean DEVMODE = true;

	public static final int DATABASE_VERSION = 1;

	public static final String DATABASE_NAME = "app";

	public static final String SHARED = "app";

	public static final String TAG = "app_";

	public static final String APP_DIR = "/pshare";

	public static final String APP_TEMP = APP_DIR + "/temp";

	public static final String APP_IMAGE = APP_DIR + "/image";


	public static final String APP_DB = "app.db";




    /********微信支付相关*********/
    // appid 请同时修改 androidmanifest.xml里面.PayActivityd里的属性<dataandroid:scheme="wxb4ba3c02aa476ea1"/>为新设置的appid
    public static final String APP_ID = "wxe0b0e843ec7f97f6";
    // 商户号
    public static final String MCH_ID = "1252078201";
    // API密钥，在商户平台设置
    public static final String API_KEY = "xianZhizewangluokejiyouxiangongs";




    /********支付宝钱包相关*********/
    //商户收款账号
    public static final String SELLER = "etg511@163.com";
    //商户PID
    public static final String PARTNERID = "2088911891298769";
    //商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMS2avtDTaSBgtrLw/nf7IKTKCTEDqMrKOaog+1CCM4rpr3cWUqRH8uHGFN0tMXfEY189Yg/oRuTaErvVfhePFJoxlQ6sCPYKwJg8ULXPdz+XXqBWGvqgboLuHUvI4KRlWSZnjsHxEHrjuMRAcq7b/Bn0z15rIIuP9xXeJREhDGLAgMBAAECgYEApjkLL6UQr+jsfRxgTv6kKTZWXrL5N3IaDsbpLAx3ylfif176aOM2/dH7gVIGC4pSl7+27tnttpKaN3AEsmpXB0ScW4YLG+ON8m+kO3mOPLLcY4M92O/ytZ6JOVQ0vvatawf5oXSDVldCC5+d+xyLDHmDo1w7IyN9B5rTxDOPXKECQQDoVAtGtA+RY7oUcd/le/NEXqK5MrZcrkOvUdxq2MDxeH7SXVdy/C1W4CWDDyr2iuikEKuHxgIj5ajFH0bVG+17AkEA2MFjSWP+NGY+MN6LR0A3tM3FdF5dM5MBa2p+ik38oKSJtNVnod6OfNZCqD0jdTn44bNgOn4qEJLe3urZB5onMQJBAOfSRNYuAYK/kgOohRLFlb3Y+GH3mkPslxkvg9MRqaTI6TMbdJEF1G/lhUi7L9GGVhCvmZkHGUecd8UKKnGnRlUCQEuNvUoLrMQfD3aBXBMN81z2jOkvK1Wa3FfQ2yLiZDiqocWCzYjCxvAEz19NUFrARlyF5fj9mTQFveH+AoB/30ECQQCzzespUgjJFADwQCWoKnVxaTqMydN3wOpVw6FI75IdvPpcpbHY+G6V3et6ZPzuamwGBdYBfo747wrvNko71sdm";
    //支付宝公钥
    public static final String RSA_PUBLIC = "";



    // 在商户后台回调地址
    public static final String NOTIFY_URL = "http://112.74.125.226/XPRO/appZhiNotice/findZhiNotice.do";
}
