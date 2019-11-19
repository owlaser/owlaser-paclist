package com.owlaser.paclist.service;

import com.owlaser.paclist.entity.Dependency;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import java.io.File;
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
                dependenciesList.add(dependency);
            }
        }
        return;
    }
}
