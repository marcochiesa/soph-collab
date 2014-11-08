package soph.collab.web.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import soph.collab.web.model.AuthorGroupForm;
import soph.collab.util.StringUtils;

public class AuthorGroupFormValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return AuthorGroupForm.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "authorNames", "validation.blank");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "commonalities", "validation.blank");

        AuthorGroupForm form = (AuthorGroupForm)target;
        String authorNames = form.getAuthorNames();
        if (authorNames == null)
            return;
        for (String name : StringUtils.EOL_PATTERN.split(authorNames))
            validateName(name, errors);
    }

    private void validateName(String name, Errors errors) {
        if (name == null || !StringUtils.hasText(name))
            return;

        // Remove starting and trailing whitespace 
        name = name.trim();

        // Check for invalid characters
        Matcher m = StringUtils.INVALID_NAME_CHAR_PATTERN.matcher(name);
        if (m.find())
            errors.rejectValue("authorNames", "validation.invalidchar", new Object[]{name, m.group()}, null);

        // Check if multiple commas
        m = StringUtils.COMMA_PATTERN.matcher(name);
        if (m.find() && m.find(m.start() + 1))
            errors.rejectValue("authorNames", "validation.multiplecommas", new Object[]{name}, null);
    }
}
