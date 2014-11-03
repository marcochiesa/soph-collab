package soph.collab.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.regex.Pattern;

public final class StringUtils {

    public static final String ENCODING = "UTF-8";
    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final String COMMA = ",";
    public static final Pattern SPACE_PATTERN = Pattern.compile(SPACE);
    public static final Pattern COMMA_PATTERN = Pattern.compile(COMMA);
    public static final Pattern EOL_PATTERN = Pattern.compile("\n");

    /**
     * Escape the given as HTML.
     * Newlines are converted to <code>&lt;br/&gt;</code>.
     * Ampersands are converted to <code>&amp;amp;</code>.
     * Double quotes are converted to <code>&amp;quot;</code>.
     * Less than signs are converted to <code>&amp;lt;</code>.
     * Greater than signs are converted to <code>&amp;gt;</code>.
     *
     * @param s text to escape
     * @return empty string if given string is null, otherwise the converted
     *         version if the given string.
     */
    public static String htmlEncode(String s) {
        if (s == null)
            return "";

        StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            switch (c) {
                case '"':
                    sb.append("\"");
                    break;
                case '&':
                    sb.append("&");
                    break;
                case '<':
                    sb.append("<");
                    break;
                case '>':
                    sb.append(">");
                    break;
                case '\n':
                    sb.append("<br/>");
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        return sb.toString();
    }

    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, ENCODING);
        } catch (Exception e) {
            return s;
        }
    }

    public static String urlDecode(String url) {
        try {
            return URLDecoder.decode(url, ENCODING);
        } catch (Exception e) {
            return url;
        }
    }

    public static boolean hasText(String s) {
        return org.springframework.util.StringUtils.hasText(s);
    }

    public static String join(Collection<String> collection) {
        return join(collection, COMMA);
    }
    public static String join(Collection<String> collection, String delimiter) {
        return org.springframework.util.StringUtils.collectionToDelimitedString(collection, delimiter);
    }
}
