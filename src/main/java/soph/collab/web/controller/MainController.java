package soph.collab;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

import javax.validation.Valid;

import soph.collab.model.Author;
import soph.collab.model.AuthorGroup;
import soph.collab.remoteservice.pubmed.EInfoService;
import soph.collab.remoteservice.pubmed.ESearchService;
import soph.collab.util.NameParser;
import soph.collab.util.StringUtils;
import soph.collab.web.model.AuthorGroupForm;
import soph.collab.web.validation.AuthorGroupFormValidator;

@Controller
public class MainController {

    @Autowired
    EInfoService eInfoservice;
    @Autowired
    ESearchService eSearchservice;

    @InitBinder("authorGroupForm")
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(new AuthorGroupFormValidator());
    }

    @RequestMapping("/")
    @ResponseBody
    public String index() {
        return "Greetings from SoPH Collab!";
    }

    @RequestMapping("/listdatabases")
    @ResponseBody
    public String listDatabases() {
        StringBuilder buffer = new StringBuilder();
        for (String db : eInfoservice.listDatabases())
            buffer.append(db).append("\n");
        return "Databases:\n" + buffer.toString();
    }

    @RequestMapping("/listdatabaseinfo")
    @ResponseBody
    public String listDatabaseInfo() {
        StringBuilder buffer = new StringBuilder();
        for (Map.Entry<String, Object> entry : eInfoservice.listDatabaseInfo("pubmed").entrySet()) {
            buffer.append(entry.getKey() + ":");
            Object ob = entry.getValue();
            if (ob instanceof String) {
                buffer.append(" " + ob + "\n");
            } else if (ob instanceof Map) {
                buffer.append("\n");
                for (Object key : ((Map)ob).keySet()) {
                    buffer.append("    ").append(key.toString()).append(": ");
                    buffer.append(((Map)ob).get(key).toString()).append("\n");
                }
            } else {
                throw new RuntimeException("impossible");
            }
        }
        return "Database:\n" + buffer.toString();
    }

    @RequestMapping("/searchpubmed")
    @ResponseBody
    public String searchPubmed() {
        StringBuilder buffer = new StringBuilder();
        for (String db : eSearchservice.searchForAuthor("Allison DB"))
            buffer.append(db).append("\n");
        return "Articles:\n" + buffer.toString();
    }

    @RequestMapping(method=RequestMethod.GET, value="/authorGroupForm")
    public String getAuthorGroupForm(Model model) {
        model.addAttribute("authorGroupForm", new AuthorGroupForm());
        return "loadAuthorGroup";
    }

    @RequestMapping(method=RequestMethod.POST, value="/authorGroupForm")
    public String submitAuthorGroupForm(Model model, @Valid AuthorGroupForm form, BindingResult result) {
        System.out.println("**authorNames:" + form.getAuthorNames());
        System.out.println("**commonalities:" + form.getCommonalities());
        if (result.hasErrors())
            return "loadAuthorGroup";

        AuthorGroup group = new AuthorGroup();
        group.setCommonalities(new HashSet(StringUtils.spaceDelimitedList(form.getCommonalities())));
        for (String name : StringUtils.EOL_PATTERN.split(form.getAuthorNames())) {
            List<String> nameParts = StringUtils.parseName(name);
            if (nameParts.isEmpty())
                continue;

            Author author = new Author();
            author.setNameParts(nameParts);
            group.getAuthors().add(author);
        }
        
        model.addAttribute("authorGroup", group);
        model.addAttribute("authorCount", group.getAuthors().size());

        return "loadedAuthorGroup";
    }

    @RequestMapping(method=RequestMethod.POST, value="/submitNameList")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void submitNameList(@RequestParam(value = "names") String nameList) {
        if (!StringUtils.hasText(nameList))
            return;

        List<String> nameParts = null;
        String parsedName = null;
        for (String name : StringUtils.EOL_PATTERN.split(nameList)) {
            if (name == null || !StringUtils.hasText(name))
                continue;

            try {
                parsedName = (new NameParser(name)).getName();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Name:" + parsedName);
        }
    }
}
