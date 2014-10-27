package soph.collab.remoteservice.pubmed;

import gov.nih.nlm.ncbi.www.soap.eutils.EUtilsServiceStub;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

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

    public Map<String, Object> listDatabaseInfo(String db) {
        /*
        This method doesn't work at the moment. It appears that the EInfo SOAP
        web service response schema has changed and the eutils jar doesn't
        refect this, yet.

        Exception: Unexpected subelement {http://www.ncbi.nlm.nih.gov/soap/eutils/einfo}DbBuild

        Example response:
        <eInfoResult>
            <DbInfo>
            <DbName>pubmed</DbName>
            <MenuName>PubMed</MenuName>
            <Description>PubMed bibliographic record</Description>
            <DbBuild>Build141026-2207m.6</DbBuild>
            <Count>24311724</Count>
            ...
        </eInfoResult>
        */
        try {
            EUtilsServiceStub service = new EUtilsServiceStub();
           
            // call NCBI EInfo utility
            EUtilsServiceStub.EInfoRequest req = new EUtilsServiceStub.EInfoRequest();
            req.setDb(db);
            EUtilsServiceStub.EInfoResult res = service.run_eInfo(req);
            // results output
            Map<String, Object> result = new LinkedHashMap<String, Object>();
            result.put("Name", res.getDbInfo().getDbName());
            result.put("Description", res.getDbInfo().getDescription());
            result.put("Count", res.getDbInfo().getCount());
            result.put("Last Update", res.getDbInfo().getLastUpdate());
            Map<String, String> fields = new LinkedHashMap<String, String>();
            for (EUtilsServiceStub.FieldType field : res.getDbInfo().getFieldList().getField()) {
                fields.put("Name", field.getName());
                fields.put("Full Name", field.getFullName());
                fields.put("Description", field.getDescription());
                fields.put("Term Count", field.getTermCount());
            }
            result.put("Fields", fields);
            return result;
        } catch(Exception e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }
}
