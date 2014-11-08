package soph.collab.model;

import java.util.HashSet;
import java.util.Set;

public class AuthorGroup {

    private Set<Author> authors = new HashSet<>();
    private Set<String> commonalities = new HashSet<>();

    public Set<Author> getAuthors() {
        return this.authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<String> getCommonalities() {
        return this.commonalities;
    }
    public void setCommonalities(Set<String> commonalities) {
        this.commonalities = commonalities;
    }
}
