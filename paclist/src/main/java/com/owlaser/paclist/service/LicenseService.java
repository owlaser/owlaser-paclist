package com.owlaser.paclist.service;


import com.owlaser.paclist.entity.CheckMessage;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LicenseService {

    public  void  licensecheck(ArrayList<String> license, CheckMessage checkMessage){

        HashMap<String,String> opensourcelicense = new HashMap<String, String>();  //开源
        opensourcelicense.put("Apache 2.0","false");
        opensourcelicense.put("Apache","false");
        opensourcelicense.put("GPL 3.0","true");
        opensourcelicense.put("GPL 2.0","true");
        opensourcelicense.put("GPL","true");
        opensourcelicense.put("MIT","false");
        opensourcelicense.put("BSD ","false");
        opensourcelicense.put("BSD 3-clause","false");
        opensourcelicense.put("BSD 2-clause","false");
        opensourcelicense.put("CC0","false");
        opensourcelicense.put("CC0 1.0","false");
        opensourcelicense.put("EPL","true");
        opensourcelicense.put("EPL 1.0","true");
        opensourcelicense.put("EPL 2.0","true");
        opensourcelicense.put("AGPL","true");
        opensourcelicense.put("AGPL 3.0","true");
        opensourcelicense.put("LGPL 3.0","true");
        opensourcelicense.put("LGPL 2.1","true");
        opensourcelicense.put("LGPL","true");
        opensourcelicense.put("MPL","false");
        opensourcelicense.put("MPL 2.0","true");

        Map<String,String> patentlicense = new HashMap<String, String>(); //专利
        patentlicense.put("Apache 2.0","true");
        patentlicense.put("Apache","true");
        patentlicense.put("GPL 3.0","true");
        patentlicense.put("GPL 2.0","true");
        patentlicense.put("GPL","true");
        patentlicense.put("MIT","true");
        patentlicense.put("BSD","true");
        patentlicense.put("BSD 2-Clause","true");
        patentlicense.put("BSD 3-Clause","true");
        patentlicense.put("CC0","false");
        patentlicense.put("CC0 1.0","false");
        patentlicense.put("EPL","true");
        patentlicense.put("EPL 1.0","true");
        patentlicense.put("EPL 2.0","true");
        patentlicense.put("AGPL","true");
        patentlicense.put("LGPL 2.1","true");
        patentlicense.put("LGPL 3.0","true");
        patentlicense.put("LGPL","true");
        patentlicense.put("MPL","true");
        patentlicense.put("MPL 2.0","true");


        Map<String,String> brandlicense = new HashMap<String, String>();//商标
        brandlicense.put("Apache 2.0","false");
        brandlicense.put("Apache","false");
        brandlicense.put("GPL 3.0","true");
        brandlicense.put("GPL 2.0","true");
        brandlicense.put("GPL","true");
        brandlicense.put("MIT","true");
        brandlicense.put("BSD","true");
        brandlicense.put("BSD 2-Clause","true");
        brandlicense.put("BSD 3-Clause","true");
        brandlicense.put("CC0 1.0","false");
        brandlicense.put("CC0","false");
        brandlicense.put("EPL","true");
        brandlicense.put("EPL 1.0","true");
        brandlicense.put("EPL 2.0","true");
        brandlicense.put("AGPL","true");
        brandlicense.put("LGPL 2.1","true");
        brandlicense.put("LGPL 3.0","true");
        brandlicense.put("LGPL 2.0","true");
        brandlicense.put("MPL","false");
        brandlicense.put("MPL 2.0","false");

        Map<String,String> statechangelicense = new HashMap<String, String>(); //声明变更
        statechangelicense.put("Apache 2.0","true");
        statechangelicense.put("Apache","true");
        statechangelicense.put("GPL 3.0","true");
        statechangelicense.put("GPL 2.0","true");
        statechangelicense.put("GPL","true");
        statechangelicense.put("MIT","false");
        statechangelicense.put("BSD","false");
        statechangelicense.put("BSD 2-Clause","false");
        statechangelicense.put("BSD 3-Clause","false");
        statechangelicense.put("CC0 1.0","false");
        statechangelicense.put("CC0","false");
        statechangelicense.put("EPL","false");
        statechangelicense.put("EPL 1.0","false");
        statechangelicense.put("EPL 2.0","false");
        statechangelicense.put("AGPL","true");
        statechangelicense.put("LGPL 2.1","true");
        statechangelicense.put("LGPL 3.0","true");
        statechangelicense.put("LGPL 2.0","true");
        statechangelicense.put("MPL","false");
        statechangelicense.put("MPL 2.0","false");


        Map<String,String> usesamelicense = new HashMap<String, String>(); //使用相同license
        usesamelicense.put("Apache 2.0","false");
        usesamelicense.put("Apache","false");
        usesamelicense.put("GPL 3.0","true");
        usesamelicense.put("GPL 2.0","true");
        usesamelicense.put("GPL","true");
        usesamelicense.put("MIT","false");
        usesamelicense.put("BSD","false");
        usesamelicense.put("BSD 2-Clause","false");
        usesamelicense.put("BSD 3-Clause","false");
        usesamelicense.put("CC0 1.0","false");
        usesamelicense.put("CC0","false");
        usesamelicense.put("EPL","true");
        usesamelicense.put("EPL 1.0","true");
        usesamelicense.put("EPL 2.0","true");
        usesamelicense.put("AGPL","true");
        usesamelicense.put("LGPL 2.1","true");
        usesamelicense.put("LGPL 3.0","true");
        usesamelicense.put("LGPL 2.0","true");
        usesamelicense.put("MPL","false");
        usesamelicense.put("MPL 2.0","true");



        /*****************************检测开源方面的license冲突*********************************/
        int opensourceflag = 0;
        String opensourceResult = null;
        for(int i = 0; i<license.size();i++) {
            for (int j = i+1; j < license.size(); j++) {
                if(opensourcelicense.get(license.get(i)) != opensourcelicense.get(license.get(j))){
                    opensourceflag =1;
                    break;
                }
            }
            break;
        }
        if(opensourceflag ==1){
            //System.out.println("在开源方面检测到license冲突!");
            opensourceResult = "在开源方面检测到要求不一致的license!\n";
            for(String string:license){
                if(opensourcelicense.get(string)== "true"){
                    //System.out.println(string+"强制要求衍生品开源");
                    opensourceResult = opensourceResult+string+"强制要求衍生品开源\n";
                }
            }
            for(String string:license){
                if(opensourcelicense.get(string)== "false"){
                   // System.out.println(string+"不强制要求衍生品开源");
                    opensourceResult = opensourceResult+string+"不强制要求衍生品开源\n";
                }
            }
        }
        else{ //System.out.println("在开源方面未检测到license冲突");
           opensourceResult = "在开源方面未检测到license冲突!\n";
        }
        checkMessage.setOpensourcelicense(opensourceResult);




        /*****************************检测专利方面的license冲突*********************************/
        int patentlicenseflag=0;
        String patentResult = null;
        for(int i = 0; i<license.size();i++) {
            for (int j = i+1; j < license.size(); j++) {
                if(patentlicense.get(license.get(i)) != patentlicense.get(license.get(j))){
                    patentlicenseflag = 1;
                    break;
                }
            }
            break;
        }
        if(patentlicenseflag ==1){
           // System.out.println("在专利授权方面检测到license冲突!");
            patentResult = "在专利授权方面检测到要求不一致的license!\n";
            for(String string:license){
                if(patentlicense.get(string)== "true"){
                   // System.out.println(string+"授予衍生品专利权");
                    patentResult = patentResult + string +"授予衍生品专利权\n";
                }
            }
            for(String string:license){
                if(patentlicense.get(string)== "false"){
                   // System.out.println(string+"不授予衍生品专利权");
                    patentResult = patentResult + string +"不授予衍生品专利权\n";
                }
            }
        }
        else{ //System.out.println("在专利授权方面未检测到license冲突");
            patentResult = "在专利授权方面未检测到license冲突\n";
        }
        checkMessage.setPatentlicense(patentResult);


        /*****************************检测商标方面的license冲突*********************************/
        int brandlicenseflag = 0;
        String brandResult = null;
        for(int i = 0; i<license.size();i++) {
            for (int j = i+1; j < license.size(); j++) {
                if(brandlicense.get(license.get(i)) != brandlicense.get(license.get(j))){
                    brandlicenseflag = 1;
                    break;
                }
            }
            break;
        }
        if(brandlicenseflag == 1){
            brandResult ="检测到要求不一致的license!\n";
            for(String string:license){
                if(brandlicense.get(string)== "true"){
                   // System.out.println(string+"授权商标使用");
                    brandResult = brandResult+string+"授权商标使用\n";
                }
            }

            for(String string:license){
                if(brandlicense.get(string)== "false"){
                   // System.out.println(string+"不授权商标使用");
                    brandResult = brandResult+string+"不授权商标使用\n";
                }
            }
        }
        else{
            //System.out.println("未检测到license冲突");
            brandResult = "未检测到license冲突\n";
        }
        checkMessage.setBrandlicense(brandResult);

        /*****************************检测声明更变方面的license冲突*********************************/
        int statechangelicenseflag = 0;
        String statechangeResult = null;
        for(int i = 0; i<license.size();i++) {
            for (int j = i+1; j < license.size(); j++) {
                if(statechangelicense.get(license.get(i)) != statechangelicense.get(license.get(j))){
                    statechangelicenseflag =1;
                    break;
                }
            }
            break;
        }

        if(statechangelicenseflag == 1){
            //System.out.println("检测到license冲突!");
            statechangeResult = "检测到要求不一致的license!\n";
            for(String string:license){
                if(statechangelicense.get(string)== "true"){
                    //System.out.println(string+"要求声明修改记录");
                    statechangeResult = statechangeResult+string +"要求声明修改记录\n";
                }
            }

            for(String string:license){
                if(statechangelicense.get(string)== "false"){
                    //System.out.println(string+"不要求声明修改记录");
                    statechangeResult = statechangeResult+string +"不要求声明修改记录\n";
                }
            }
        }
        else{
           //System.out.println("未检测到license冲突");
            statechangeResult = "未检测到license冲突\n";
        }
        checkMessage.setStatechangelicense(statechangeResult);


        /*****************************检测使用相同协议方面的license冲突*********************************/
        int usesamelicenseflag = 0;
        String usesameResult = null;
        for(int i = 0; i<license.size();i++) {
            for (int j = i+1; j < license.size(); j++) {
                if(usesamelicense.get(license.get(i)) != usesamelicense.get(license.get(j))){
                    usesamelicenseflag = 1;
                    break;
                }
            }
            break;
        }

        if(usesamelicenseflag == 1){
            //System.out.println("检测到license冲突!");
            usesameResult = "检测到要求不一致的license!\n";
            for(String string:license){
                if(usesamelicense.get(string)== "true"){
                    //System.out.println(string+"强制要求衍生品使用同一协议");
                    usesameResult = usesameResult+string+"强制要求衍生品使用同一协议\n";
                }
            }

            for(String string:license){
                if(usesamelicense.get(string)== "false"){
                   // System.out.println(string+"不强制要求衍生品使用同一协议");
                    usesameResult = usesameResult+string+"不强制要求衍生品使用同一协议\n";
                }
            }

        }
        else{
            //System.out.println("未检测到license冲突");
            usesameResult = "未检测到license冲突\n";
        }
        checkMessage.setUsesamelicense(usesameResult);




    }



}
