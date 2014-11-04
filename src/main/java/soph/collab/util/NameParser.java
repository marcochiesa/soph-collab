package soph.collab.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses a string containing a person's name. Expects the name to contain only
 * upper and lower case characters, spaces, hyphens, and possibly a single
 * comma. The comma is expected to signal a 'last name, first name' style. A
 * ParseException will be thrown if there is more than one comma or any invalid
 * characters.
 */
public class NameParser {

    private static final String VALID_NAME_CHAR = "[-a-zA-Z ,]";
    private static final String INVALID_NAME_CHAR = "[^" + VALID_NAME_CHAR.substring(1);
    private static final Pattern SPACES_PATTERN = Pattern.compile(StringUtils.SPACE + "+");
    private static final Pattern INVALID_NAME_CHAR_PATTERN = Pattern.compile(INVALID_NAME_CHAR);
    private static final Pattern LAST_NAME_FIRST_PATTERN
      = Pattern.compile("^(" + VALID_NAME_CHAR + "*), ?(" + VALID_NAME_CHAR + "*)$");

    private final List<String> nameParts = new ArrayList<>();

    public NameParser(String name) throws ParseException {
        // Remove starting and trailing whitespace 
        name = name.trim();

        // Check for invalid characters
        Matcher m = INVALID_NAME_CHAR_PATTERN.matcher(name);
        if (m.find())
            throw new ParseException("invalid name character '" + m.group() + "'", m.start());

        // Replace multiple spaces with a single space
        m = SPACES_PATTERN.matcher(name);
        name = m.replaceAll(StringUtils.SPACE);

        // Check if multiple commas
        m = StringUtils.COMMA_PATTERN.matcher(name);
        if (m.find() && m.find(m.start() + 1))
            throw new ParseException("multiple commas found in name", m.start());

        String last = "";
        String rest = name;
        // Check if name fits 'last name, first name' pattern
        m = LAST_NAME_FIRST_PATTERN.matcher(name);
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
    }

    public List<String> getNameParts() {
        return new ArrayList<>(nameParts);
    }

    public String getName() {
        return StringUtils.join(new ArrayList<>(nameParts), StringUtils.SPACE);
    }
}
