package cn.gy.util;

/**
 * @desc:
 * @author: qingx
 * @create: 2021-04-08 14:17
 */
public final class StringMatcherUtil {
    private StringMatcherUtil() {
    }

    /**
     * 获取匹配串在源字符中的位置
     * @param src 源字符数组
     * @param pattern 待匹配数组
     * @param separator 分隔符
     * @return 如果匹配，返回匹配的index，否则返回0
     */
    public static int getPatternIndex(char[] src, char[] pattern, char separator){
        int fieldIndex = 0;
        int index = 0;
        int patternIndex = 0;
        boolean match = false;
        do {
            while (index < src.length && src[index] == separator){
                index++;
                fieldIndex++;
            }
            while (index < src.length && patternIndex < pattern.length && src[index] == pattern[patternIndex]){
                index++;
                patternIndex++;
            }
            if (patternIndex == pattern.length && (index == src.length || src[index] == separator)){
                match = true;
                break;
            }
            while (index < src.length && src[index++] != separator);
            patternIndex = 0;
            fieldIndex++;
        }while (index < src.length);
        if (match){
            return fieldIndex;
        }else {
            return -1;
        }
    }
}
