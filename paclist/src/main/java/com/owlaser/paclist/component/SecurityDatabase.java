package com.owlaser.paclist.component;

import com.owlaser.paclist.dao.PacDao;
import org.mountcloud.graphql.GraphqlClient;
import org.mountcloud.graphql.request.query.DefaultGraphqlQuery;
import org.mountcloud.graphql.request.query.GraphqlQuery;
import org.mountcloud.graphql.request.result.ResultAttributtes;
import org.mountcloud.graphql.response.GraphqlResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 更新数据库类
 * 如果需要更新漏洞数据库则把@Component前面的注释去掉
 */
//@Component
public class SecurityDatabase implements CommandLineRunner{
    @Autowired
    private PacDao pacDao;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("漏洞数据库更新中...");

        //graphql服务器地址
        String serverUrl = "https://api.github.com/graphql";
        //build一个新的graphqlclient
        GraphqlClient graphqlClient = GraphqlClient.buildGraphqlClient(serverUrl);

        //如果说graphql需要健全信息我们可以用map来进行传递
        Map<String,String> httpHeaders = new HashMap<>();


        //更改为自己的token
        httpHeaders.put("Authorization","token XXXXXX");
        httpHeaders.put("User-Agent", "Mozilla/5.0");
        httpHeaders.put("Content-Type", "application/graphql");
        //设置http请求的头
        graphqlClient.setHttpHeaders(httpHeaders);

        GraphqlQuery query = new DefaultGraphqlQuery("securityVulnerabilities");
        query.addParameter("first", 100);
        query.addResultAttributes("totalCount");

        ResultAttributtes nodesAttr = new ResultAttributtes("nodes");

        ResultAttributtes advisoryAttr = new ResultAttributtes("advisory");
        ResultAttributtes referencesAttr = new ResultAttributtes("references");
        referencesAttr.addResultAttributes("url");
        ResultAttributtes identifiersAttr = new ResultAttributtes("identifiers");
        identifiersAttr.addResultAttributes("type", "value");
        advisoryAttr.addResultAttributes(referencesAttr);
        advisoryAttr.addResultAttributes(identifiersAttr);
        advisoryAttr.addResultAttributes("severity");

        ResultAttributtes firstPatchedVersionAttr = new ResultAttributtes("firstPatchedVersion");
        firstPatchedVersionAttr.addResultAttributes("identifier");

        ResultAttributtes packageAttr = new ResultAttributtes("package");
        packageAttr.addResultAttributes("name");
        packageAttr.addResultAttributes("ecosystem");

        nodesAttr.addResultAttributes(advisoryAttr);
        nodesAttr.addResultAttributes(firstPatchedVersionAttr);
        nodesAttr.addResultAttributes("vulnerableVersionRange");
        nodesAttr.addResultAttributes(packageAttr);

        ResultAttributtes pageInfoAttr = new ResultAttributtes("pageInfo");
        pageInfoAttr.addResultAttributes("endCursor", "hasNextPage");

        query.addResultAttributes(nodesAttr);
        query.addResultAttributes(pageInfoAttr);

        try{
            GraphqlResponse response = graphqlClient.doQuery(query);
            Map result = response.getData();
            Map data = (Map)result.get("data");
            Map securityVulnerabilities = (Map)data.get("securityVulnerabilities");
            List<Map> nodesList = (List<Map>)securityVulnerabilities.get("nodes");
            update(nodesList);
            int totalCount = (int)securityVulnerabilities.get("totalCount");
            int count = 100;
            Map pageInfo = (Map)securityVulnerabilities.get("pageInfo");
            System.out.println("更新中...      已完成100" + "/" + "漏洞总数" + totalCount);
            while(pageInfo.get("hasNextPage").toString().equals("true")){
                query.addParameter("after", pageInfo.get("endCursor").toString());
                GraphqlResponse responseTemp = graphqlClient.doQuery(query);
                result = responseTemp.getData();
                data = (Map)result.get("data");
                securityVulnerabilities = (Map)data.get("securityVulnerabilities");
                nodesList = (List<Map>)securityVulnerabilities.get("nodes");
                update(nodesList);
                pageInfo = (Map)securityVulnerabilities.get("pageInfo");
                if(pageInfo.get("hasNextPage").toString().equals("false")){
                    System.out.println("更新已完成!      已完成" + totalCount + "/漏洞总数" + totalCount);
                    break;
                }
                count += 100;
                System.out.println("更新中...      已完成" + count + "/" + "漏洞总数" + totalCount);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update(List<Map> nodesList){
        String severity = null, CVE = null, CVEurl = null, GHSA = null, GHSAurl = null,
                firstPatchedVersion = null, vulnerableVersionRange = null, name = null, ecosystem = null;
        for(Map nodes : nodesList){
            Map advisory = (Map)nodes.get("advisory");
            severity = advisory.get("severity").toString();
            List<Map> identifiersList = (List<Map>)advisory.get("identifiers");
            for(Map identifier : identifiersList){
                if(identifier.get("type").toString().equals("GHSA")){
                    GHSA = identifier.get("value").toString();
                    GHSAurl = "https://github.com/advisories/" + GHSA;
                }
                else if(identifier.get("type").toString().equals("CVE")){
                    CVE = identifier.get("value").toString();
                    CVEurl = "https://nvd.nist.gov/vuln/detail/" + CVE;
                }
            }
            if(nodes.get("firstPatchedVersion") != null){
                Map firstPatchedVersionIdentifier = (Map)nodes.get("firstPatchedVersion");
                firstPatchedVersion = firstPatchedVersionIdentifier.get("identifier").toString();
            }

            vulnerableVersionRange = nodes.get("vulnerableVersionRange").toString();
            Map nodePackage = (Map)nodes.get("package");
            name = nodePackage.get("name").toString();
            ecosystem = nodePackage.get("ecosystem").toString();
            String tempName = pacDao.getSecurityName(name, vulnerableVersionRange);
            if(tempName == null){
                pacDao.insertSecurity(name, ecosystem, severity, GHSA, GHSAurl, CVE, CVEurl, vulnerableVersionRange, firstPatchedVersion);
            }
        }
    }
}
