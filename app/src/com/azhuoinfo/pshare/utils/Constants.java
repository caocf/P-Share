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


    public static final String KEY_USED_VERSION = "is_new_version";

    /********微信支付相关*********/
    // appid 请同时修改 androidmanifest.xml里面.PayActivityd里的属性<dataandroid:scheme="wx0112a93a0974d61b"/>为新设置的appid
    public static final String APP_ID = "wx0112a93a0974d61b";
    // 商户号
    public static final String MCH_ID = "1273348301";
    // API密钥，在商户平台设置
    public static final String API_KEY = "1234567890sukenxzkhcnsjdhydfnjag";




    /********支付宝钱包相关*********/
    //商户收款账号
    public static final String SELLER = "zhifu@forwell-parking.com";
    //商户PID
    public static final String PARTNERID = "2088021550883080";
    //商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKMLgUUYUTqwDOWaEg3ZqZ9A5UBjP8KO+xdpTmc1zv1c5EpMXVdXD7P6OuKHHNUAhu4gICEiB7+bLDSkro9gLcl99vyzblbTXBI1iSlPSq3mKfP8SVh1ZGZh1FDIMDX7KCju8jEKU1oUtiZkIJaGKlH+fYigQhPf+yPaOGEOm17vAgMBAAECgYBIZBFPRk66ifQP9WpSr/O5+6xN/EMQ9T7S1DS1apSutZG+000WPFeCh3Whom/Qut0t2SGq1FswXYsxDHVcv01UVYNrsmOf/bszI04cG3LVVoxPdF6g+oNMvNLBlYznpo4VLmMUDnN63YsgH1QRg8FIB01pU+KGa/knnUB1yEMEsQJBANSICl5IxpsspRb0xUYKDVQErfpeOK5dC0A1UddQvkhuNg7J5nWhuKjucGd3vYpzZFVt0lLZ69ScYhfu7rJluQ0CQQDEZGVpBKxy20Ig8bA6vaxLiX+lIrJ2fZ+T21z4PcCemWYkF5bPQahPPW3brvsn2u5b7TyQV50fNEjR7cgUhIDrAkEAndbq3FrwJQ5jDUl7uSh9/Yf8LZUMQ3KWiHkQ7vfoWaKAQztvDK2ulsd+c1laSxiny0pkiWOO4bfCokOwwo0JgQJBALX4IE6yWecCab+Esbl7zY0gFfm4sItB0v55HyeUcEmD8TQ39zCKsZzaWlRXSbegD4N1ycwkoh0roN2C6QS50YkCQBIFFvVQeEy9tUfvX6eG+d+xhjUIaUOY/E4Oo3vi4fy0Vsbtx2AAJlgk8RhWEyS2fsXZ8h/HD0yAIjs5EDDlLnc=";
    //支付宝公钥
    public static final String RSA_PUBLIC = "";



    // 在商户后台回调地址
    public static final String ALIPAY_NOTIFY_URL = "http://139.196.12.103/notify_url.jsp";
    public static final String ALIPAY_RENT_NOTIFY_URL = "http://139.196.12.103/payBackOrder.jsp";
    public static final String WECHAT_NOTIFY_URL = "http://139.196.12.103/{client_type}/{version}/customer/payOrder";
    public static final String WECHAT_RENT_NOTIFY_URL = "http://139.196.12.103/{client_type}/{version}/customer/payAmount";

}
