package com.owlaser.paclist.controller;
import com.owlaser.paclist.entity.CheckMessage;
import com.owlaser.paclist.entity.ChildNode;
import com.owlaser.paclist.entity.Dependency;
import com.owlaser.paclist.entity.sum;
import com.owlaser.paclist.service.DependencyTreeService;
import com.owlaser.paclist.service.LicenseService;
import com.owlaser.paclist.service.PacService;
import com.owlaser.paclist.util.ResponseUtil;
import fr.dutra.tools.maven.deptree.core.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class PacController {

    @Autowired
    private PacService pacService;

    @Autowired
    private  LicenseService licenseService;

    @GetMapping(value = "/")
    public String show() {
        return "upload";
    }

    @ResponseBody
    @PostMapping(value = "/upload")
    public Object upload(@RequestParam("file") MultipartFile file) {
        if(!file.getOriginalFilename().equals("pom.xml") && !file.getOriginalFilename().matches(".*\\.jar")){
            return ResponseUtil.badArgument();
        }

        ArrayList<Dependency> dependenciesList = new ArrayList<>();
        try {
            byte[] bytes = file.getBytes();
            String folderPath = "./repository/pom/";
            Path filePath = Paths.get(folderPath + file.getOriginalFilename());
            Files.write(filePath, bytes);
            Pattern r = Pattern.compile("(pom.xml)$");
            Matcher m = r.matcher(file.getOriginalFilename());

            if(!m.find()){
                byte[] pomFile = pacService.JarRead(folderPath + file.getOriginalFilename());
                if(pomFile.length == 1) return ResponseUtil.noPom();
                Files.write(filePath, pomFile);
            }

            String textPath = pacService.CreateDependencyText(folderPath, filePath);
            Node root = DependencyTreeService.GetRoot(textPath);
            pacService.GetDependencies(root, dependenciesList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sum s = new sum(dependenciesList,licenseService.getConflic(dependenciesList));
        return s;
        //return ResponseUtil.okList(dependenciesList);
    }



//    ArrayList<Dependency> dependenciesList = new ArrayList<>();
//
//    /**
//     * license冲突检测
//     */
//    @ResponseBody
//    @PostMapping(value = "/licensecheck")
//    public CheckMessage licensecheck(@RequestParam("file") MultipartFile file){
//        CheckMessage checkMessage = new CheckMessage();
//        ArrayList<String> licenseAllList= new ArrayList<>();
//        for(Dependency dependency:dependenciesList){
//               String[] sqlit = dependency.getLicense().split("  ");
//               for(int i=0; i<sqlit.length;i++) {
//                   if (licenseAllList.contains(sqlit[i])) {
//                       continue;
//                   } else {
//                       licenseAllList.add(sqlit[i]);
//                   }
//                   System.out.println(sqlit[i]);
//               }
//        }
//        System.out.println(licenseAllList);
//        System.out.println(dependenciesList);
//
//       licenseService.licensecheck(licenseAllList,checkMessage);
//
//        return checkMessage;
//    }

    /**
     * 查询依赖信息接口
     */
    @ResponseBody
    @GetMapping(value = "/getdependency")
    public Object getDependency(String groupId, String artifactId){
        List<ChildNode> childNodes = pacService.getParentDependencies(groupId, artifactId);
        return ResponseUtil.okList(childNodes);
    }

}
