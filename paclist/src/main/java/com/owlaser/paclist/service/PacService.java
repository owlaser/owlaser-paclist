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
                String stableLatestVersionName = getStableLatestVersionName(url, dependency);
                dependency.setLatestStableVersion(stableLatestVersionName);
                ArrayList<String> VersionName = getAllVersionName(url);
                ArrayList<Integer> usages = getAllUsages(url);
                ArrayList<String> License = getLicense(url);  //获取license
                     dependency.setLicense(License);
                    String bestVersion = VersionName.get(usages.indexOf(Collections.max(usages)));
                    dependency.setPopurlarVersion(bestVersion);
                    dependenciesList.add(dependency);
            }
        }
        return;
    }

    //得到该组件的最新稳定版本
    public static String getStableLatestVersionName(String url, Dependency dependency){
        String res = "";
        try {
            org.jsoup.nodes.Document document = Jsoup.connect(url).header("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36").get();
            Elements value = document.getElementsByTag("td");
            for(org.jsoup.nodes.Element element : value) {
                if (element.text().matches("^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}.*$")) {
                    res = element.text();
                    String ragex = "[^(a-zA-Z)]";
                    String stableSign = res.replaceAll(ragex, "");//提取版本号中的字母部分，以查看是否是稳定版本
                    if (stableSign.equals("Final")|| stableSign.equals("RELEASE") || stableSign.equals("") ) {
                        return res;
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return "未找到最新稳定版本!";//未找到最新稳定版本
    }

    public static ArrayList getAllVersionName(String url){
        ArrayList<String> list = new ArrayList<>();
        try {
            org.jsoup.nodes.Document document = Jsoup.connect(url).header("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36").get();
            Elements value = document.select(".vbtn").select(".release");
            for(org.jsoup.nodes.Element element:value){
                org.jsoup.nodes.Document elementdoc = Jsoup.parse(element.toString());
                Elements license = elementdoc.select("a");
                list.add(license.text());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList getAllUsages(String url){
        ArrayList<Integer> list = new ArrayList<>();
        try {
            org.jsoup.nodes.Document document = Jsoup.connect(url).header("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36").get();
            Elements value = document.getElementsByTag("td");
            for(org.jsoup.nodes.Element element : value){
                if(element.text().matches("^\\d+")){
                    list.add(Integer.parseInt(element.text()));
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }
    
    public static ArrayList getLicense(String url){
        ArrayList<String> list = new ArrayList<>();
        try {
            org.jsoup.nodes.Document document = Jsoup.connect(url).header("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36").get();
//            Elements value = document.getElementsByClass("b lic");
//            System.out.println(value);
            Elements value = document.select(".b").select(".lic");


            for(org.jsoup.nodes.Element element:value){
                org.jsoup.nodes.Document elementdoc = Jsoup.parse(element.toString());
                Elements license = elementdoc.select("span");
                list.add(license.text());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }
}
