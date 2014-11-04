package soph.collab;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.ParseException;
import java.util.List;
import java.util.regex.Pattern;

import soph.collab.remoteservice.pubmed.EInfoService;
import soph.collab.remoteservice.pubmed.ESearchService;
import soph.collab.util.NameParser;
import soph.collab.util.StringUtils;

@RestController
public class MainController {

    @Autowired
    EInfoService eInfoservice;
    @Autowired
    ESearchService eSearchservice;

    @RequestMapping("/")
    public String index() {
        return "Greetings from SoPH Collab!";
    }

    @RequestMapping("/listdatabases")
    public String listDatabases() {
        StringBuilder buffer = new StringBuilder();
        for (String db : eInfoservice.listDatabases())
            buffer.append(db).append("\n");
        return "Databases:\n" + buffer.toString();
    }

    @RequestMapping("/listdatabaseinfo")
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
    public String searchPubmed() {
        StringBuilder buffer = new StringBuilder();
        for (String db : eSearchservice.searchForAuthor("Allison DB"))
            buffer.append(db).append("\n");
        return "Articles:\n" + buffer.toString();
    }

    @RequestMapping(method=RequestMethod.POST, value="/submitnamelist")
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
