package com.owlaser.paclist.service;

import fr.dutra.tools.maven.deptree.core.*;

import java.io.*;

/**
 * 从txt文件获得依赖树信息
 * 返回树的根root
 */

public class DependencyTreeService {

    public static Node GetRoot(String filepath) {
        Node root = null;
        try {
            File file = new File(filepath);
            FileInputStream fis = new FileInputStream(file);
            Reader reader = new BufferedReader(new InputStreamReader(fis));
            InputType type = InputType.TEXT;
            Parser parser = type.newParser();
            root = parser.parse(reader);
            return root;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }

}