package soph.collab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import soph.collab.remoteservice.pubmed.EInfoService;
import soph.collab.remoteservice.pubmed.ESearchService;

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
        for (String db : eInfoservice.listDatabases()) {
            if (buffer.length() > 0)
                buffer.append(", ");
            buffer.append(db);
        }
        return "Databases: " + buffer.toString();
    }

    @RequestMapping("/searchpubmed")
    public String searchpubmed() {
        StringBuilder buffer = new StringBuilder();
        for (String db : eSearchservice.searchForAuthor("Allison DB")) {
            if (buffer.length() > 0)
                buffer.append(", ");
            buffer.append(db);
        }
        return "Articles: " + buffer.toString();
    }
}
