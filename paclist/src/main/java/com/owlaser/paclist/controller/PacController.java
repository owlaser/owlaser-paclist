package com.owlaser.paclist.controller;
import com.owlaser.paclist.entity.Dependency;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

@Controller
public class PacController {

    @GetMapping(value = "/")
    public String show(){
        return "upload";
    }

    @PostMapping(value = "/upload")
    public void upload(@RequestParam("file")MultipartFile file, Model model){
        if(file.isEmpty()){
            model.addAttribute("messages", "文件上传失败！文件为空");
            return;
        }
        try{
            byte[] bytes = file.getBytes();
            String path1= System.getProperty("user.dir") + "/repository" + "/pom/";
            Path path = Paths.get(path1 + file.getOriginalFilename());
            Files.write(path, bytes);
            model.addAttribute("messages", "文件上传成功！开始扫描");
            ArrayList<Dependency> dependenciesList = new ArrayList<>();
            ScanPac(path1 + file.getOriginalFilename(), dependenciesList);
            model.addAttribute("dependenciesList", dependenciesList);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return;
    }

    @GetMapping(value = "/uploadStatus")
    public String uploadStatus(){
        return "uploadStatus";
    }

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
