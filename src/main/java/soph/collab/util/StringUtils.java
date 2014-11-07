package soph.collab.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class StringUtils {

    public static final String ENCODING = "UTF-8";
    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final String COMMA = ",";
    public static final Pattern SPACE_PATTERN = Pattern.compile(SPACE);
    public static final Pattern COMMA_PATTERN = Pattern.compile(COMMA);
    public static final Pattern EOL_PATTERN = Pattern.compile("\n");

    public static final String VALID_NAME_CHAR = "[-a-zA-Z ,]";
    public static final String INVALID_NAME_CHAR = "[^" + VALID_NAME_CHAR.substring(1);
    public static final Pattern INVALID_NAME_CHAR_PATTERN = Pattern.compile(StringUtils.INVALID_NAME_CHAR);
    public static final Pattern LAST_NAME_FIRST_PATTERN
      = Pattern.compile("^(" + VALID_NAME_CHAR + "*), ?(" + VALID_NAME_CHAR + "*)$");

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

    public static List<String> spaceDelimitedList(String s) {
        return delimitedList(s, SPACE);
    }

    public static List<String> delimitedList(String s, String delimiter) {
        return Arrays.asList(org.springframework.util.StringUtils.delimitedListToStringArray(s, delimiter));
    }

    /**
     * Remove leading and trailing whitespace, then replace an internal
     * sequences of white space with a single space character.
     */
    public static String normalizeWhitespace(String s) {
        return org.apache.commons.lang.StringUtils.normalizeSpace(s);
    }

    public static List<String> parseName(String name) {
        if (name == null || !hasText(name))
            return Collections.emptyList();

        // remove leading and trailing whitespace and condense internal spaces
        name = normalizeWhitespace(name);

        List<String> nameParts = new ArrayList<>();
        String last = "";
        String rest = name;
        // Check if name fits 'last name, first name' pattern
        Matcher m = LAST_NAME_FIRST_PATTERN.matcher(name);
        if (m.matches()) {
            last = m.group(1).trim();
            rest = m.group(2).trim();
        }

        for (String part : StringUtils.SPACE_PATTERN.split(rest)) {
            if (part == null || !StringUtils.hasText(part))
                continue;
            nameParts.add(part);
        }
        if (StringUtils.hasText(last))
            nameParts.add(last);

        return nameParts;
    }
}
