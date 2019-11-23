package com.owlaser.paclist.service;

import com.owlaser.paclist.entity.Dependency;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Service
public class PacService {
    public static void ScanPac(String filepath, List<Dependency> dependenciesList) {
        SAXReader reader = new SAXReader();
        File pom = new File(filepath);
        try {
            // 通过reader对象的read方法加载xml文件 ，获取docuement对象
            Document document = reader.read(pom);
            // 通过document对象获取根节点root
            Element root = document.getRootElement();
            if(root.element("dependencyManagement") != null){
                Element dependencyManagement = root.element("dependencyManagement").element("dependencies");
                GetDependencies(dependencyManagement, dependenciesList);
            }
            Element dependencies = root.element("dependencies");
            GetDependencies(dependencies, dependenciesList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //得到pom文件里的依赖包信息
    public static void GetDependencies(Element parent, List<Dependency> dependenciesList){
        for(Iterator<Element> it = parent.elementIterator("dependency"); it.hasNext();)
        {
            Element element=it.next();
            Iterator itt = element.elementIterator();
            Dependency dependency = new Dependency();
            while(itt.hasNext()){
                Element value = (Element) itt.next();
                if(value.getName().equals("artifactId")){
                    dependency.setArtifactId(value.getStringValue());
                }
                else if (value.getName().equals("version") && !value.getStringValue().equals("${project.version}")){
                    dependency.setVersion(value.getStringValue());
                }
                else if(value.getName().equals("groupId")){
                    dependency.setGroupId(value.getStringValue());
                }
            }
            if(dependency.getArtifactId() != null && dependency.getVersion() != null){
                System.out.println(dependency.getGroupId());
                String url = "https://mvnrepository.com/artifact/" + dependency.getGroupId() + "/" + dependency.getArtifactId();
                getAll(url,dependency);
                dependenciesList.add(dependency);
            }
        }
        return;
    }

    public static void getAll(String url, Dependency dependency){
        String tmp="";
        ArrayList<String> versionList= new ArrayList<>();
        ArrayList<Integer> usageList = new ArrayList<>();
        ArrayList<String> licenseList = new ArrayList<>();
        try {
            org.jsoup.nodes.Document document = Jsoup.connect(url).header("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36").get();
            Elements latestversionValue = document.getElementsByTag("td");
            Elements versionnameValue = document.select(".vbtn");
            Elements usageValue = document.getElementsByTag("td");
            Elements licenseValue = document.select(".b").select(".lic");
            for(org.jsoup.nodes.Element element : latestversionValue) {
                if (element.text().matches("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}.*$")) {
                    tmp = element.text();
                    String ragex = "[^(a-zA-Z)]";
                    String stableSign = tmp.replaceAll(ragex, "");////提取版本号中的字母部分，以查看是否是稳定版本
                    if (stableSign.equals("Final")|| stableSign.equals("RELEASE") || stableSign.equals("") ) {
                        dependency.setLatestStableVersion(tmp);
                        break;
                    }
                }
            }
            for(org.jsoup.nodes.Element element:versionnameValue){
                org.jsoup.nodes.Document elementdoc = Jsoup.parse(element.toString());
                Elements versionName = elementdoc.select("a");
                versionList.add(versionName.text());

            }
            for(org.jsoup.nodes.Element element : usageValue){
                if(element.text().matches("\\d{1,3}(,\\d{3})*$")){ //取使用量，对于三位分割法去掉中间的逗号
                    String rawString = element.text();
                    String removeStr = ",";
                    rawString = rawString.replace(removeStr,"");
                    usageList.add(Integer.parseInt(rawString));
                }
            }
            for(org.jsoup.nodes.Element element:licenseValue){
                org.jsoup.nodes.Document elementdoc = Jsoup.parse(element.toString());
                Elements license = elementdoc.select("span");
                licenseList.add(license.text());
            }


        }
        catch (IOException e){
            e.printStackTrace();
        }
        // System.out.println(usageList.size() + " " + versionList.size());
        dependency.setVersionList(versionList);
        dependency.setusageList(usageList);
        dependency.setLicense(licenseList);
        String bestVersion = versionList.get(usageList.indexOf(Collections.max(usageList)));
        dependency.setPopurlarVersion(bestVersion);
    }


}
