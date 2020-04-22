package com.owlaser.paclist.service;


import com.owlaser.paclist.entity.CheckMessage;
import com.owlaser.paclist.entity.Dependency;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LicenseService {
    public CheckMessage  getConflic(ArrayList<Dependency> dependenciesList){
        CheckMessage license_warn = new CheckMessage();
        Map<String,String> result_map = new HashMap<>();
        ArrayList<String> licenseAllList= new ArrayList<>();
        for(Dependency dependency:dependenciesList){
            String[] sqlit = dependency.getLicense().split("  ");
            for(int i=0; i<sqlit.length;i++) {
                if (licenseAllList.contains(sqlit[i])) {
                    continue;
                } else {
                    licenseAllList.add(sqlit[i]);
                }
                //System.out.println(sqlit[i]);
            }
        }

        return  licensecheck(licenseAllList,license_warn);


    }


    public  CheckMessage  licensecheck(ArrayList<String> license, CheckMessage license_warn){
        ArrayList<Map<String,String>> sum_judgeMap_List = new ArrayList<>();
        HashMap<String,String> opensourcelicense = new HashMap<String, String>();  //开源
        StringBuffer notice = new StringBuffer();
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
        sum_judgeMap_List.add(opensourcelicense);

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
        sum_judgeMap_List.add(patentlicense);

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
        sum_judgeMap_List.add(brandlicense);

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
        sum_judgeMap_List.add(statechangelicense);

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
        sum_judgeMap_List.add(usesamelicense);

        ArrayList<String> license_detail = new ArrayList<>();
        StringBuffer tool_sb = new StringBuffer();
          boolean flag = false;
        /*****************************检测开源方面*********************************/
        for(int i =0;i<license.size();i++ ){
            if(sum_judgeMap_List.get(0).containsKey(license.get(i))
                    &&sum_judgeMap_List.get(0).get(license.get(i)).equals("true")) {
                flag =true;
                notice.append("强制要求衍生品开源   ");
                tool_sb.append(license.get(i)+"  ");
            }
        }
        System.out.println("1");
        if(flag==true) {license_detail.add("强制要求衍生品开源:"+tool_sb.toString());
        System.out.println("zxc");
        }
        tool_sb.setLength(0); flag=false;


        /*****************************检测专利方面*********************************/

        for(int i =0;i<license.size();i++ ){
            if(sum_judgeMap_List.get(1).containsKey(license.get(i))
                    &&sum_judgeMap_List.get(1).get(license.get(i)).equals("false")) {
                flag =true;
                notice.append("不授予衍生品专利权   ");
                tool_sb.append(license.get(i)+" ");
            }
        }
        if(flag==true) license_detail.add("不授予衍生品专利权:"+tool_sb.toString());
        tool_sb.setLength(0); flag=false;


        /*****************************检测商标方面*********************************/

        for(int i =0;i<license.size();i++ ){
            if(sum_judgeMap_List.get(2).containsKey(license.get(i))
                    &&sum_judgeMap_List.get(2).get(license.get(i)).equals("false")) {
                flag =true;
                notice.append("不授权商标使用   ");
                tool_sb.append(license.get(i)+" ");
            }
        }
        if(flag==true) license_detail.add("不授权商标使用:"+tool_sb.toString());
        tool_sb.setLength(0); flag=false;


        /*****************************检测声明更变 *********************************/

        for(int i =0;i<license.size();i++ ){
            if(sum_judgeMap_List.get(3).containsKey(license.get(i))
                    &&sum_judgeMap_List.get(3).get(license.get(i)).equals("true")) {
                flag =true;
                notice.append("必须声明修改记录   ");
                tool_sb.append(license.get(i)+" ");
            }
        }
        if(flag==true) license_detail.add("必须声明修改记录:"+tool_sb.toString());
        tool_sb.setLength(0); flag=false;


        /*****************************检测使用相同协议 *********************************/

        for(int i =0;i<license.size();i++ ){
            if(sum_judgeMap_List.get(4).containsKey(license.get(i))
                    &&sum_judgeMap_List.get(4).get(license.get(i)).equals("true")) {
                flag =true;
                notice.append("强制要求衍生品使用同一协议   ");
                tool_sb.append(license.get(i)+" ");
            }
        }
        if(flag==true) license_detail.add("强制要求衍生品使用同一协议:"+tool_sb.toString());
        tool_sb.setLength(0); flag=false;

        license_warn.setNotice(notice.toString());
        license_warn.setLicense_detail(license_detail);
        return license_warn;
    }



}
