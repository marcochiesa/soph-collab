package soph.collab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import soph.collab.remoteservice.pubmed.EInfoService;

@RestController
public class MainController {

    @Autowired
    EInfoService einfoservice;

    @RequestMapping("/")
    public String index() {
        return "Greetings from SoPH Collab!";
    }

    @RequestMapping("/listdatabases")
    public String listDatabases() {
        StringBuilder buffer = new StringBuilder();
        for (String db : einfoservice.listDatabases()) {
            if (buffer.length() > 0)
                buffer.append(", ");
            buffer.append(db);
        }
        return "Databases: " + buffer.toString();
    }
}
