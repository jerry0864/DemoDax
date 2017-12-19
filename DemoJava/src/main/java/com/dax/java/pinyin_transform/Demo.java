package com.dax.java.pinyin_transform;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Desc:
 * Created by liuxiong on 2017/8/1.
 */

public class Demo {

    public static void main(String []args) throws BadHanyuPinyinOutputFormatCombination {
        //汉语转拼音
        transformChinese();
        //transformChinese1();
    }

    private static void transformChinese1() {
        //com.github.SilenceDut:jpinyin
        //String str = "你好世界";
//        System.out.println(PinyinHelper.convertToPinyinString(str, "", PinyinFormat.WITHOUT_TONE));
//        System.out.println(PinyinHelper.getShortPinyin(str));
    }

    private static void transformChinese() throws BadHanyuPinyinOutputFormatCombination {
        // pin4j
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        String string = "你好世界，男女搭配，干活不累";
        String result = getStringPinYin(string,format);
        System.out.println(result);
    }

    //转换单个字符
    public static String getCharacterPinYin(char c ,HanyuPinyinOutputFormat format) {
        String[] pinyin = null;
        try {
            pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }

        // 如果c不是汉字，toHanyuPinyinStringArray会返回null
        if (pinyin == null) return null;
        // 只取一个发音，如果是多音字，仅取第一个发音
        return pinyin[0];
    }

    //转换一个字符串
    public static String getStringPinYin(String str,HanyuPinyinOutputFormat format) {
        StringBuilder sb = new StringBuilder();
        String tempPinyin = null;
        for (int i = 0; i < str.length(); ++i) {
            tempPinyin = getCharacterPinYin(str.charAt(i),format);
            if (tempPinyin == null) {
                // 如果str.charAt(i)非汉字，则保持原样
                sb.append(str.charAt(i));
            } else {
                sb.append(tempPinyin);
            }
        }
        return sb.toString();
    }

}
