package soph.collab.util;

import java.net.URLEncoder;

public final class StringUtils {

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
            return URLEncoder.encode(s, "UTF-8");
        } catch (Exception e) {
            return s;
        }
    }
}
