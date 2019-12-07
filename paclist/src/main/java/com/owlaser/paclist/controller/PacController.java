package com.owlaser.paclist.controller;
import com.owlaser.paclist.entity.Dependency;
import com.owlaser.paclist.service.PacService;
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
            Pattern r = Pattern.compile("(pom.xml)$");
            Matcher m = r.matcher(file.getOriginalFilename());
            if(m.find()){
                pacService.ScanPacPom(path1 + file.getOriginalFilename(),dependenciesList);
            }
            else {
                InputStream input = pacService.JarRead(path1 + file.getOriginalFilename());
                pacService.ScanPacJar(input, file.getOriginalFilename(), dependenciesList);
            }
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

}
