package soph.collab.model;

import java.util.ArrayList;
import java.util.List;

import soph.collab.util.StringUtils;

public class Author {

    private List<String> nameParts = new ArrayList<>();

    public List<String> getNameParts() {
        return this.nameParts;
    }

    public void setNameParts(List<String> nameParts) {
        this.nameParts = nameParts;
    }

    public String getName() {
        return StringUtils.join(new ArrayList<>(this.nameParts), StringUtils.SPACE);
    }
}
