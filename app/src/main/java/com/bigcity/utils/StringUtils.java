package com.bigcity.utils;

import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * * ==================================================
 * name:            StringUtils
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/8/8
 * description：   字符串辅助工具类
 * history：
 * * ==================================================
 */

public class StringUtils {
    private static final Gson gson = new Gson();

//    /**
//     * 返回未解密的json对象
//     */
//    private static GlobalBean firstParseJson(String json) {
//        return gson.fromJson(json, new TypeToken<GlobalBean>() {
//        }.getType());
//    }
//
//    /**
//     * @param json 第一次返回的json加密字符串
//     * @return MD5验证成功后返回解密后的json字符串, MD5验证失败返回null
//     */
//    public static String getDecodeString(String json) {
//        String temp;
//        GlobalBean bean = firstParseJson(json);
//        //MD5验证
//        if (MD5Utils.md5(CreateCode.getRECEIVE_SiGN_KEY() + bean.getDiyou() + CreateCode.getRECEIVE_SiGN_KEY()).equals(bean.getXmdy())) {
//            temp = CreateCode.s2pDiyou(bean.getDiyou());
//            return temp;
//        } else {
//            return null;
//        }
//    }
//
//    /**
//     * 将请求参数进行加密再组装
//     */
//    public static Map<String, String> getRequestParams(TreeMap<String, String> map) {
//        Map<String, String> params = new HashMap<>(2);
//        params.put("diyou", CreateCode.p2sDiyou(CreateCode.GetJson(map)));
//        params.put("xmdy", CreateCode.p2sXmdy(CreateCode.p2sDiyou(CreateCode.GetJson(map))));
//        return params;
//    }

    /**
     * 验证是否是手机号码
     *
     * @param str 手机号字符串
     * @return 是否是手机号格式
     */
    public static boolean isCellphone(String str) {
        Pattern pattern = Pattern.compile("1[0-9]{10}");
        Matcher matcher = pattern.matcher(str);
        return !str.equals("") && matcher.matches();
    }

    /**
     * 获取六位随机验证码
     */
    public static String getRandomCode() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            builder.append((int) (Math.random() * 10));
        }
        return builder.toString();
    }

    /**获取唯一id标识*/
    public static String  getSoleId(){
        return getRandomCode() +System.currentTimeMillis() +getRandomCode();
    }


    /**
     * 密码规则：必须是6-16位大小写字母及数字的组合
     * 是否包含
     *
     * @param str   检验的字符串
     * @return   符合--true,不符合--false
     */
    public static boolean conformPasswordRule(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLetter(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        return isDigit && isLetter && str.matches(regex);
    }




     /**
     *将字符串转化为int型数字
     * @param intString  数字型字符串
     * @return     字符串数字或0
     */
    public static int  string2Integer(String intString){
        try {
            return Integer.parseInt(intString);
        }catch (NumberFormatException e){
            return 0;
        }

    }

    /**
     *将字符串转化为int型数字
     * @param intString  数字型字符串
     * @return     字符串数字或0
     */
    public static long  string2Long(String intString){
        try {
            return Long.parseLong(intString);
        }catch (NumberFormatException e){
            return 0;
        }

    }

    /**
     *将字符串转化为float型数字
     * @param floatString  数字型字符串
     * @return     字符串数字或0
     */
    public static float  string2Float(String floatString){
        try {
            return Float.parseFloat(floatString);
        }catch (NumberFormatException e){
            return 0;
        }

    }

    /**将时间戳转为字符串
     *
     * @param cc_time   时间戳字符串,到秒
     * @return     yyyy-MM-dd
     */
    public static String getStrTime(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            long lcc_time = Long.valueOf(cc_time);
            re_StrTime = sdf.format(new Date(lcc_time*1000L));

        }catch (NumberFormatException e){
            return "****-**-**";
        }
        return re_StrTime;
    }

    /**
     * 判断字符串是否为null,或者""、{}、[]
     *
     * @param str
     * @return
     */
    public static boolean isEmpty2(String str) {
        return str == null || "".equals(str.trim()) || "{}".equals(str)
                || "[]".equals(str) || "null".equals(str);
    }

    /**补齐两位小数*/
    public static String  getTwoDecimalsStr(String  fStr){
        float  f=string2Float(fStr);
        DecimalFormat decimalFormat=new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return decimalFormat.format(f);//format 返回的是字符串
    }


}
