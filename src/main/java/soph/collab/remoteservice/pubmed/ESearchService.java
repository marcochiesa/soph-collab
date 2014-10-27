package soph.collab.remoteservice.pubmed;

import gov.nih.nlm.ncbi.www.soap.eutils.EUtilsServiceStub;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import soph.collab.util.StringUtils;

@Service
public class ESearchService {

    public List<String> searchForTerm(String term, String field) {
        try {
            EUtilsServiceStub service = new EUtilsServiceStub();
           
            // call NCBI ESearch utility
            // NOTE: search term should be URL encoded
            EUtilsServiceStub.ESearchRequest req = new EUtilsServiceStub.ESearchRequest();
            // req.setTool(<use configured tool name here>);
            // req.setEmail(<use configured tool email here>);
            req.setDb("pubmed");
            req.setTerm(StringUtils.urlEncode(term));
            if (field != null)
                req.setField(StringUtils.urlEncode(field));
            req.setRetMax("1000");
            String sort = "pub date";
            req.setSort(StringUtils.urlEncode(sort));
            EUtilsServiceStub.ESearchResult res = service.run_eSearch(req);
            // results output
            return Arrays.asList(res.getIdList().getId());
        } catch(Exception e) {
            return Collections.emptyList();
        }
    }

    public List<String> searchForAuthor(String author) {
        return this.searchForTerm(author, "author");
    }
}
