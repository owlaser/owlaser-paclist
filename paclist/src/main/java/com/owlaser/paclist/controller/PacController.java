package com.owlaser.paclist.controller;
import com.owlaser.paclist.entity.CheckMessage;
import com.owlaser.paclist.entity.Dependency;
import com.owlaser.paclist.service.DependencyTreeService;
import com.owlaser.paclist.service.LicenseService;
import com.owlaser.paclist.service.PacService;
import fr.dutra.tools.maven.deptree.core.Node;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
    public List upload(@RequestParam("file") MultipartFile file) {
        ArrayList<Dependency> dependenciesList = new ArrayList<>();
        try {
            byte[] bytes = file.getBytes();
            String folderPath = "./repository/pom/";
            Path pomPath = Paths.get(folderPath + file.getOriginalFilename());
            Files.write(pomPath, bytes);
            String textPath = pacService.CreateDependencyText(folderPath, pomPath);
            Node root = DependencyTreeService.GetRoot(textPath);
            pacService.GetDependencies(root, dependenciesList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dependenciesList;
    }

    @GetMapping(value = "/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }




    ArrayList<Dependency> dependenciesList = new ArrayList<>();

    /************************************************************************************/
    /*******************************license冲突检测***************************************/
    /************************************************************************************/
    @ResponseBody
    @PostMapping(value = "/licensecheck")
    public CheckMessage licensecheck(@RequestParam("file") MultipartFile file){
        CheckMessage checkMessage = new CheckMessage();
        ArrayList<String> licenseAllList= new ArrayList<>();
        for(Dependency dependency:dependenciesList){
               String[] sqlit = dependency.getLicense().split("  ");
               for(int i=0; i<sqlit.length;i++) {
                   if (licenseAllList.contains(sqlit[i])) {
                       continue;
                   } else {
                       licenseAllList.add(sqlit[i]);
                   }
                   System.out.println(sqlit[i]);
               }
        }
        System.out.println(licenseAllList);
        System.out.println(dependenciesList);

        licenseService.licensecheck(licenseAllList,checkMessage);

        return checkMessage;
    }

}
