/**
 * Copyright (C), 2022-2022,
 * FileName: ParamUtil
 * Author:   cyl
 * Date:     2022/12/12 21:28
 * Description: 常用工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.danson.utils;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类功能描述: 常用工具类
 *
 * 转换类方法
 * 1.转换字符串(去前后空格) toStr(Object ov)
 * 需要不抛出异常用toStr(Object ov,Boolean flag)
 * 2.转换整型: toInt(Object ov)
 * 需要不抛出异常用toInt(Object ov,Boolean flag)
 * 3.转换为double(注意:不能转小数,有小数用toBigDecimal) toDouble(Object ov)
 * 需要不抛出异常用toDouble(Object ov,Boolean flag)
 * 4.转换为Long(注意:不能转小数,有小数用toBigDecimal) toLong(Object ov)
 * 需要不抛出异常用toLong(Object ov,Boolean flag)
 * 5.转换为BigDecimal toBigDecimal(Object ov)
 * 需要不抛出异常用toBigDecimal(Object ov,Boolean flag)
 *
 * 判断类方法
 * 1.判断是空List isNullList(List listOb)
 * 2.判断是空Map isNullMap(Map mapOb)
 * 3.是否等于0 isZero(Object ov) 转换为BigDecimal类型比较
 * 4.是否符合BigDecimal类型转换 isBigDecimal(Object str)
 * 5.整数校验 isInteger(Object number)
 * 6.判断是邮件地址 judgeEmail(Object email)
 * 7.判断多种类型是否是0或者空
 * (方法描述: 自动判断对象类型,做不同的空或0判断
 * 支持 String,Integer,Long,BigDecimal,List,Map,String[])
 *
 * 功能类方法
 * 1.去除List的重复值 listValueUniq(List list)
 * 2.map根据key值排序 sortMapByKey(Map<String, String> map
 * @version 1.0
 * @author W1028 YJD
 * @createDate 2016-7-20 下午1:17:55
 */
public class ParamUtil {


    /**
     * 通用正则
     */
    private static Pattern positivePattern = Pattern.compile("[0-9]+");
    private static Pattern integerPattern = Pattern.compile("^[+-]?[0-9]\\d*$");
    private static Pattern longPattern = Pattern.compile("^[+-]?[0-9]*");
    private static Pattern doublePattern = Pattern.compile("^[+-]?[0-9]+(\\.\\d{1,100})");
    private static Pattern IS_DATE = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
    private static Pattern IS_NUMBER = Pattern.compile("^[1-9]\\d*$");
    private static Pattern IS_NUMBER2 = Pattern.compile("^(0|[1-9]\\d*)$");
    private static Pattern IS_NUMBER3 = Pattern.compile("^(0|[1-9]\\d{0,9})(\\.(0|00))?$");


    public static String toStr(Object ov) {
        return null == ov ? "" : "null".equals(ov.toString().trim()) ? "" : ov.toString().trim();
    }

    public static String toStr(Object ov, Boolean flag) {
        try {
            return ov == null ? "" : "null".equals(ov.toString().trim()) ? "" : ov.toString().trim();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 验证手机号码
     * @param mobiles
     * @return
     */
    public static boolean isMobile(String mobiles){
        boolean flag = false;
        try{
            Pattern p = Pattern.compile("^[1](([3][0-9])|([4][5,7,9])|([5][0-9])|([6][6])|([7][3,5,6,7,8])|([8][0-9])|([9][5,8,9]))[0-9]{8}$");
            Matcher m = p.matcher(mobiles);
            flag = m.matches();
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }

    /**
     * 验证邮箱
     * @param email
     * @return
     */
    public static boolean isEmail(String email){
        boolean flag = false;
        try{
            Pattern p = Pattern.compile("^\\s*?(.+)@(.+?)\\s*$");
            Matcher m = p.matcher(email);
            flag = m.matches();
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }

    /**
     *
     * 方法描述: 自动判断对象类型,做不同的空或0判断
     * 支持 String,Integer,Long,BigDecimal,List,Map,String[]
     *
     * @param ov
     * @return
     * @throws Exception
     * @author yjd
     * @createDate 2017-6-16 上午10:44:14
     */
    public static Boolean isEmptyOrZero(Object ov) throws Exception {
        try {
            if(ov == null){
                return true;
            }else if (ov instanceof String) {
                return StringUtils.isEmpty(toStr(ov)) ? true : false;
            } else if (ov instanceof Integer) {
                return isZero(ov) ? true : false;
            } else if (ov instanceof Long) {
                return isZero(ov) ? true : false;
            } else if (ov instanceof BigDecimal) {
                return isZero(ov) ? true : false;
            } else if (ov instanceof List) {
                return isNullList((List)ov) ? true : false;
            } else if (ov instanceof Map) {
                return isNullMap((Map)ov) ? true : false;
            } else if (ov instanceof String[]) {
                return isNullArray((String[])ov) ? true : false;
            }
            return false;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public static int toInt(Object ov) {
        return ov == null ? 0 : Integer.parseInt(toStr(ov), 10);
    }
    public static int toInt(Object ov, Boolean flag) {
        try {
            return ov == null ? 0 : Integer.parseInt(toStr(ov), 10);
        } catch (Exception e) {
            return 0;
        }
    }

    public static long toLong(Object ov) {
        return ov == null ? 0L : Long.parseLong("".equals(toStr(ov)) ? "0"
                : toStr(ov), 10);
    }

    public static long toLong(Object ov, Boolean flag) {
        try {
            return ov == null ? 0L : Long.parseLong(toStr(ov), 10);
        } catch (Exception e) {
            return 0;
        }
    }

    public static double toDouble(Object ov) {
        return ov == null ? 0.00 : Double.parseDouble(toStr(ov));
    }

    public static double toDouble(Object ov, Boolean flag) {
        try {
            return ov == null ? 0.0 : Double.parseDouble(toStr(ov));
        } catch (Exception e) {
            return 0.0;
        }
    }

    /**
     * 转换BigDecimal 为空时返回null
     * @param ov
     * @return
     */
    public static BigDecimal toBigDecimalByNull(Object ov) {
        return isBigDecimal(ov) ? new BigDecimal(toStr(ov)) : null;
    }

    /**
     * 转换BigDecimal 为空时默认0
     * @param ov
     * @return
     */
    public static BigDecimal toBigDecimal(Object ov) {
        return isBigDecimal(ov) ? new BigDecimal(toStr(ov)) : BigDecimal.ZERO;
    }

    /**
     * 转换BigDecimal  为空时默认无限大 （慎用）
     * @param ov
     * @return
     */
    public static BigDecimal toBigDecimalByUnlimited(Object ov) {
        return isBigDecimal(ov) ? new BigDecimal(toStr(ov)) : new BigDecimal("100000000");
    }

    public static BigDecimal toBigDecimal(Object ov, Boolean flag) {
        try {
            return (ov == null ? BigDecimal.ZERO : new BigDecimal(toStr(ov)));
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    public static Boolean isZero(Object ov) {
        try {
            return BigDecimal.ZERO.compareTo(toBigDecimal(ov)) == 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean noNullList(List listOb) {
        if (null == listOb || listOb.isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean isNullList(List listOb) {
        if (null == listOb || listOb.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isList(List listOb) {
        if (null == listOb || listOb.isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean isNullMap(Map mapOb) {
        if (null == mapOb || mapOb.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isNullArray(String[] arrayOb) {
        if (null == arrayOb || arrayOb.length<=0) {
            return true;
        }
        return false;
    }

    public static boolean isNullArrayNew(Object[] arrayOb) {
        if (null == arrayOb || arrayOb.length<=0) {
            return true;
        }
        return false;
    }

    /**
     * 整数
     * @param number
     * @return
     */
    public static boolean isInteger(Object number) {
        try {
            Matcher matcher = integerPattern.matcher(toStr(number));
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 0或者正整数
     * @param number
     * @return
     */
    public static boolean isNumber2(Object number) {
        try {
            Matcher matcher = IS_NUMBER2.matcher(toStr(number));
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 自定义数字格式匹配
     * @param number
     * @return
     */
    public static boolean isNumber(Object number,Pattern p) {
        try {
            Matcher matcher = p.matcher(toStr(number));
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 0或者正整数(可以包含.0或者.00)
     * @param number
     * @return
     */
    public static boolean isNumber3(Object number) {
        try {
            Matcher matcher = IS_NUMBER3.matcher(toStr(number));
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 正整数
     * @param number
     * @return
     */
    public static boolean isNumber(Object number) {
        try {
            Matcher matcher = IS_NUMBER.matcher(toStr(number));
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isPositiveInteger(Object number) {
        try {
            Matcher matcher = positivePattern.matcher(toStr(number));
            if (matcher.matches()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isMapContainsKey(Map map,Object key) {
        try {
            if(StringUtils.isNotBlank(key+"") && map.containsKey(key)){
                if(StringUtils.isNotBlank(toStr(map.get(key)))){
                    return true;
                }
                return false;
            }else{
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    //标准金额校验,无负数,最多小数点后2位
    public static boolean isMoney(Object str) {
        try {
            if (StringUtils.isBlank(toStr(str))){
                return false;
            }
            java.util.regex.Pattern pattern = java.util.regex.Pattern
                    .compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
            java.util.regex.Matcher match = pattern.matcher(toStr(str));
            if (match.matches()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    //可以选择是否支持负数,支持小数点后几位
    //支持几位数,支持负数
    public static boolean isMoney(Object str,int decimal,int symbol) {
        try {
            String mark = symbol==1?"[+-]?":"";
            if (StringUtils.isBlank(toStr(str))){
                return false;
            }
            java.util.regex.Pattern pattern = java.util.regex.Pattern
                    .compile("^"+mark+"(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,"+decimal+"})?$"); // 判断小数点后位的数字的正则表达式
            java.util.regex.Matcher match = pattern.matcher(toStr(str));
            if (match.matches()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    //小数点前位数校验 支持负数校验 w1028
    public static boolean checkDigits(Object str,int digits) {
        try {
            if (isBigDecimal(str)) {
                BigDecimal bc = toBigDecimal(str);
                String d = "";
                while(digits>0){
                    d += "9";
                    digits--;
                }
                if(bc.abs().compareTo(new BigDecimal(d)) > 0){
                    return false;
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isBigDecimal(Object str) {
        try {
            if (StringUtils.isBlank(toStr(str))){
                return false;
            }
            Matcher match = null;
            if (isInteger(str)) {
                match = longPattern.matcher(toStr(str));
            } else {
                if (toStr(str).indexOf(".") == -1) {
                    match = longPattern.matcher(toStr(str));
                } else {
                    match = doublePattern.matcher(toStr(str));
                }
            }
            return match.matches();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean judgeEmail(Object email) throws Exception {
        try {
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(toStr(email));
            boolean isMatched = matcher.matches();
            if (isMatched) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }


    public static boolean isLogisticsNum(Object code) throws Exception {
        try {
            String check = "[-0-9a-zA-Z]+";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(toStr(code));
            boolean isMatched = matcher.matches();
            if (isMatched) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isBlankArray(String[] arr) {
        if(arr==null||arr.length<1){
            return true;
        }
        return false;
    }

    public static boolean isContain(String[] arr, String targetValue) {
        if(targetValue==null||StringUtils.isBlank(targetValue)){
            return false;
        }
        for(String s: arr){
            if(s.equals(targetValue)){
                return true;
            }
        }
        return false;
    }

    public static List listValueUniq(List list) {
		/*注意，如果是asList转换的List实际为抽象List没有clear方法会报错
		所以利用ArrayList转换一次LIST保险*/
        try {
            if(isNullList(list)){
                return list;
            }
            list = new ArrayList<String>(list);
            HashSet h = new HashSet(list);
            list.clear();
            list.addAll(h);
            return list;
        } catch (Exception e) {
            return list;
        }
    }

    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        try {
            if (isNullMap(map)){
                return map;
            }
            Map<String, String> sortMap = new TreeMap<String, String>();
            sortMap.putAll(map);
            return sortMap;
        } catch (Exception e) {
            return map;
        }
    }

    /**
     *
     * 方法描述: 通过value取map中的key
     *
     * @param map
     * @param value
     * @return
     * @author w1025-test10
     * @createDate 2016-9-19 下午4:34:52
     */
    public static String getKeyByValue(Map map, Object value) {
        String keys="";
        if(isNullMap(map)){
            return null;
        }
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Entry) it.next();
            Object obj = entry.getValue();
            if (obj != null && obj.equals(value)) {
                keys=(String) entry.getKey();
            }
        }
        return keys;
    }

    public static String getValueByKey(Map map, Object key) {
        try {
            return toStr(map.get(key));
        } catch (Exception e) {
            return "";
        }
    }

    public static String getNumberValueByKey(Map map, Object key) {
        try {

            return StringUtils.defaultIfBlank(toStr(map.get(key)),"0");
        } catch (Exception e) {
            return "0";
        }
    }

    /**
     * 截取字符串方法,不会取空值,自动转换为List,保证返回对象,不反回Null
     */
    public static List<String> split(String o,String symbol){
        try {
            if(StringUtils.isEmpty(o)||StringUtils.isEmpty(symbol)||"null".equals(o)){return new ArrayList<String>();}

            if(o.startsWith(symbol)){
                o = o.substring(1);
            }
            if(o.endsWith(symbol)){
                o = o.substring(0,o.lastIndexOf(symbol));
            }
            if(StringUtils.isEmpty(o)){
                return new ArrayList<String>();
            }
            String[] array = o.split("[\\\\"+symbol+"]+");

            List<String> list = Arrays.asList(array);

            return new ArrayList<String>(list);
        } catch (Exception e) {
            return new ArrayList<String>();
        }
    }

    /**
     *
     * 功能描述：按长度截取字符串，可能会出现最后字符乱码
     * @return
     * @author 12624
     * @version DEALER 5.0
     * @since 2019年7月26日
     *
     */
    public static String subStringByMaxLen(String source,int maxLen) {
        if(source==null) {
            return null;
        }else {
            byte[] b = source.getBytes();
            if(b.length > maxLen) {
                return new String(b, 0, maxLen);
            }else {
                return  source;
            }
        }
    }

    //转换格式为\/Date(1476701958640+0800)\/的json格式时间
    public static Date convertJsonDate(String jsonDate){
        try {
            String str = jsonDate.substring(jsonDate.indexOf("(")+1, jsonDate.indexOf(")"));
            String time = str.substring(0,str.length()-5);
            Date date = new Date(Long.parseLong(time));
            return date;
        } catch (Exception e) {
            return new Date(System.currentTimeMillis());
        }
    }

    /**
     *
     * 方法描述: 截取保留几位小数,按哪种方式
     *
     * @param price
     * @return
     * @author DELL
     * @createDate 2016-11-29 下午2:41:59
     */
    public static Object keepXXDigits(Object price,int digits,int bigDecimalFormat){
        BigDecimal priceB = ParamUtil.toBigDecimal(price);
        priceB = priceB.setScale(digits,bigDecimalFormat);
        if(price instanceof String){
            price = ParamUtil.toStr(priceB);
            return price;
        }else if(price instanceof BigDecimal){
            return priceB;
        }else{
            return "";
        }
    }

    /**
     *
     * 方法描述: 将对象用字符串形式表示
     *
     * @param obj
     * @return
     * @author w1025-test10
     * @createDate 2016-12-13 下午5:25:49
     */
    public static String ObjToString(Object obj){
        return ToStringBuilder.reflectionToString(obj, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     *
     * 方法描述: 校验一系列参数，如果有空则返回 说明 如果都不为空则返回true， 改方法目前只校验String类型的
     *
     * @param params 可变参数 如果第一个为String[]数组 则表示第一个参数为参数说明
     * @return
     * @author w1025-test10
     * @createDate 2016-12-30 上午11:34:05
     */
    public static String stringsIsNotEmpty(Object... params){
        if(params!=null && params.length>0){
            String[] names = null;
            if(params[0] instanceof String[]){//如果为String[]则表明为 参数说明数组 需校验的参数从第二个开始
                names = (String[]) params[0];
            }
            if(names!=null && names.length>0){//从第二个开始校验
                if(names.length==params.length-1){//参数说明和需校验参数长度一致
                    for(int i=1; i<params.length; i++){
                        if(StringUtils.isEmpty(toStr(params[i]))){
                            return names[i-1]+"不能为空";//使用参数说明
                        }
                    }
                }else{
                    for(int i=1; i<params.length; i++){
                        if(StringUtils.isEmpty(toStr(params[i]))){
                            return "第"+(i+1)+"个参数不能为空";
                        }
                    }
                }
            }else{
                for(int i=1; i<=params.length; i++){
                    if(StringUtils.isEmpty(toStr(params[i-1]))){
                        return "第"+i+"个参数不能为空";
                    }
                }
            }
        }else{
            return "请至少传入一个参数";
        }
        return "true";
    }

    public static boolean areNotEmpty(String... values) {
        boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        } else {
            for (String value : values) {
                result &= !StringUtils.isEmpty(value);
            }
        }
        return result;
    }

    public static String[] arrayRemoveEmpty(String[] th) {
        List<String> tmp = new ArrayList<String>();
        for(String str:th){
            if(str!=null && str.length()!=0){
                tmp.add(str);
            }
        }
        th = tmp.toArray(new String[0]);
        return th;
    }

    /**
     * 校验request.getParameterMap的值非空
     * @param param   参数
     * @param message 提示信息标识
     * @return  trim后的值
     * @throws Exception
     */
    public static String checkRequestParam(String[] param, String message) throws Exception {
        if (param == null || StringUtils.isBlank(param[0].trim())) {
            throw new RuntimeException(message + "未填写或未选!");
        }
        return param[0].trim();
    }

    /**
     * 校验request.getParameterMap的值非空,为空返回null
     * @param param   参数
     * @return  trim后的值
     * @throws Exception
     */
    public static String checkRequestParam(String[] param) throws Exception {
        if (param == null || StringUtils.isBlank(ParamUtil.toStr(param[0]).trim())) {
            return null;
        }
        return param[0].trim();
    }

    /**
     * 校验request.getParameterMap的值非空,为空返回null
     * @param param   参数
     * @return  trim后的值
     * @throws Exception
     */
    public static String checkRequestParamReturnString(String[] param) throws Exception {
        if (param == null || StringUtils.isBlank(ParamUtil.toStr(param[0]).trim())) {
            return "";
        }
        return param[0].trim();
    }

    /**
     * 校验request.getParameterMap的值非空,且满足长度限制
     * @param param  参数
     * @param length  长度
     * @param message  标识
     * @return  trim后的值
     * @throws Exception
     */
    public static String checkRequestParam(String[] param, int length, String message) throws Exception {
        return checkRequestParam(param,length,message,true);
    }

    /**
     * 校验request.getParameterMap的值若存在，满足长度限制
     * @param param  参数
     * @param length  长度
     * @param message  标识
     * @param checkNull  是否校验非空
     * @return  trim后的值
     * @throws Exception
     */
    public static String checkRequestParam(String[] param, int length, String message,boolean checkNull) throws Exception {
        if(checkNull) {
            checkRequestParam(param, message);
        }
        if (checkNull || (param != null && StringUtils.isNotBlank(param[0].trim()))) {
            if(param[0].trim().length() > length){
                throw new Exception(message + "超长!");
            }
            return param[0].trim();
        }
        return null;
    }

    /**
     * 校验request.getParameterMap的值非空,且满足正则限制
     * @param param   参数
     * @param matchRex  正则
     * @param message  标识
     * @return  trim后的值
     * @throws Exception
     */
    public static String checkRequestParam(String[] param,String matchRex, String message) throws Exception {
        return checkRequestParam(param,matchRex,message,true);
    }

    /**
     * 校验request.getParameterMap的值若存在,满足正则限制
     * @param param  参数
     * @param matchRex  正则
     * @param message  标识
     * @param checkNull  是否校验非空
     * @return  trim后的值
     * @throws Exception
     */
    public static String checkRequestParam(String[] param,String matchRex, String message,boolean checkNull) throws Exception {
        if(checkNull) {
            checkRequestParam(param, message);
        }
        if (checkNull || (param != null && StringUtils.isNotBlank(param[0].trim()))) {
            if(!param[0].matches(matchRex)){
                throw new Exception(message + "格式不正确!");
            }
            return param[0].trim();
        }
        return null;
    }

    public static String getStr(Map map,String key){
        return toStr(map.get(key),true);
    }
    public static BigDecimal getPrice(Map map,String key){
        return toBigDecimal(map.get(key),true);
    }
    public static int getInt(Map map,String key){
        return toInt(map.get(key),true);
    }
    public static long getLong(Map map,String key){
        return toLong(map.get(key),true);
    }

    public static boolean isDate(String str) {
        /**
         * 修改正则
         */
        Matcher match = IS_DATE.matcher(str.trim());
        return match.matches();
    }

    /**
     *
     * @param length
     *            每个分段的长度，
     * @param paramsList
     *            待拆分的参数列表
     * @return 拆分后的分段列表
     * @author cyl
     */
    public static <T> List<List<T>> splitInParams(int length, List<T> paramsList) {
        if (length<1 || paramsList == null || paramsList.size() == 0){
            return null;
        }
        int size = paramsList.size();
        List<List<T>> list = new ArrayList<List<T>>();
        int d = (int) Math.ceil(size / (length+0.0));
        for (int i = 0; i < d; i++) {
            int fromIndex = length * i;
            int toIndex = Math.min(fromIndex + length, size);
            list.add(paramsList.subList(fromIndex, toIndex));
        }
        return list;
    }

    /**
     * 字符串转换
     * @param str 目前字符串
     * @param source 需转化的字符
     * @param target 转化后的字符
     * @return
     */
    public static String strChange(String str, String source, String target){
        return str.replace(source,target);
    }

}
