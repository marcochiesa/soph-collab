package soph.collab.remoteservice.pubmed;

import gov.nih.nlm.ncbi.www.soap.eutils.EUtilsServiceStub;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class EInfoService {

    public List<String> listDatabases() {
        try {
            EUtilsServiceStub service = new EUtilsServiceStub();
           
            // call NCBI EInfo utility
            EUtilsServiceStub.EInfoRequest req = new EUtilsServiceStub.EInfoRequest();
            EUtilsServiceStub.EInfoResult res = service.run_eInfo(req);
            // results output
            return Arrays.asList(res.getDbList().getDbName());
        } catch(Exception e) {
            return Collections.emptyList();
        }
    }
}
