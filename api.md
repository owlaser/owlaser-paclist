# 开源组件健康扫描api



## 包版本及license扫描

### 应用场景

```
用户上传pom.xml或jar包并扫描当前项目所用的所有直接依赖包，已经license约束信息
```



### 接口链接

```
POST http://localhost:8081/upload
```



### 请求参数

```
{
	"file": "pom.xml"
}
```



### 响应格式

```
{
    errno: xxx,
    errmsg: xxx,
    data: {}
}

请求成功
{
    errno: 0,
    errmsg: "成功",
    data: {}
}

请求失败
{
    errno: xxx,
    errmsg: xxx
}
```



### 响应内容

**请求成功**

```json
{
    "errno": 0,
    "data": {
        "dependenciesList": [
            {
                "artifact_id": "spring-boot-starter-jdbc",
                "version": "2.2.0.RELEASE",
                "group_id": "org.springframework.boot",
                "versionList": null,
                "usageList": null,
                "popular_version": "2.0.6.RELEASE",
                "stable_version": "2.2.2.RELEASE",
                "license": "Apache 2.0  ",
                "licenseList": null
            },
            {
                "artifact_id": "spring-boot-starter-thymeleaf",
                "version": "2.2.0.RELEASE",
                "group_id": "org.springframework.boot",
                "versionList": null,
                "usageList": null,
                "popular_version": "1.5.9.RELEASE",
                "stable_version": "2.2.2.RELEASE",
                "license": "Apache 2.0  ",
                "licenseList": null
            },
            {
                "artifact_id": "spring-boot-starter-web",
                "version": "2.2.0.RELEASE",
                "group_id": "org.springframework.boot",
                "versionList": null,
                "usageList": null,
                "popular_version": "2.1.3.RELEASE",
                "stable_version": "2.2.2.RELEASE",
                "license": "Apache 2.0  ",
                "licenseList": null
            },
            {
                "artifact_id": "mybatis-spring-boot-starter",
                "version": "2.0.1",
                "group_id": "org.mybatis.spring.boot",
                "versionList": null,
                "usageList": null,
                "popular_version": "1.3.2",
                "stable_version": "2.1.1",
                "license": "Apache 2.0",
                "licenseList": null
            },
            {
                "artifact_id": "mysql-connector-java",
                "version": "8.0.18",
                "group_id": "mysql",
                "versionList": null,
                "usageList": null,
                "popular_version": "5.1.38",
                "stable_version": "8.0.18",
                "license": "GPL 2.0",
                "licenseList": null
            },
            {
                "artifact_id": "spring-boot-starter-test",
                "version": "2.2.0.RELEASE",
                "group_id": "org.springframework.boot",
                "versionList": null,
                "usageList": null,
                "popular_version": "2.1.1.RELEASE",
                "stable_version": "2.2.2.RELEASE",
                "license": "Apache 2.0  ",
                "licenseList": null
            },
            {
                "artifact_id": "nekohtml",
                "version": "1.9.21",
                "group_id": "net.sourceforge.nekohtml",
                "versionList": null,
                "usageList": null,
                "popular_version": "1.9.12",
                "stable_version": "1.9.22",
                "license": "Apache 2.0  ",
                "licenseList": null
            }
        ],
        "checkMessage": {
            "notice": "强制要求衍生品开源   不授权商标使用   必须声明修改记录   必须声明修改记录   强制要求衍生品使用同一协议   ",
            "license_detail": [
                "强制要求衍生品开源:GPL 2.0  ",
                "不授权商标使用:Apache 2.0 ",
                "必须声明修改记录:Apache 2.0 GPL 2.0 ",
                "强制要求衍生品使用同一协议:GPL 2.0 "
            ]
        }
    },
    "errmsg": "成功"
}
```



未上传pom.xml文件或jar包异常

```json
{
    "errno": 401,
    "errmsg": "参数不对，请传入jar包或者pom.xml文件"
}
```



jar包中没有pom文件异常

```json
{
    "errno": 501,
    "errmsg": "jar包中没有检测到pom文件，请扫描其他jar包或直接上传pom.xml"
}
```





## 间接依赖查询

### 应用场景

```
用户上传pom.xml并扫描当前项目所用的所有直接依赖包后，查询所有直接依赖下的间接依赖包,返回两个id以及url
```



### 接口链接

```
GET http://localhost:8081/getdependency
```



### 请求参数

```
{
	"groupId": "org.apache.maven",
	"artifactId": "artifactId"
}
```



### 响应内容

```json
{
    "errno": 0,
    "data": {
        "list": [
            {
                "child_groupid": "org.apache.maven",
                "child_artifactid": "maven-settings",
                "url": "https://mvnrepository.com/artifact/org.apache.maven/maven-settings"
            },
            {
                "child_groupid": "org.apache.maven",
                "child_artifactid": "maven-profile",
                "url": "https://mvnrepository.com/artifact/org.apache.maven/maven-profile"
            },
            {
                "child_groupid": "org.apache.maven",
                "child_artifactid": "maven-artifact-manager",
                "url": "https://mvnrepository.com/artifact/org.apache.maven/maven-artifact-manager"
            },
            {
                "child_groupid": "org.apache.maven",
                "child_artifactid": "maven-plugin-registry",
                "url": "https://mvnrepository.com/artifact/org.apache.maven/maven-plugin-registry"
            },
            {
                "child_groupid": "org.codehaus.plexus",
                "child_artifactid": "plexus-interpolation",
                "url": "https://mvnrepository.com/artifact/org.codehaus.plexus/plexus-interpolation"
            },
            {
                "child_groupid": "org.codehaus.plexus",
                "child_artifactid": "plexus-container-default",
                "url": "https://mvnrepository.com/artifact/org.codehaus.plexus/plexus-container-default"
            }
        ]
    },
    "errmsg": "成功"
}
```



## 漏洞查询接口

### 应用场景

```
可以根据groupId和artifactId确定此包是否有报道过的漏洞信息（CVE和GHSA）
```



### 接口链接

```
GET http://localhost:8081/security
```



### 请求参数

```
{
	"groupId": "org.apache.hbase",
	"artifactId": "hbase"
	"version": "1.1.1"
}
```



### 响应内容

```json
{
    "errno": 0,
    "data": {
        "list": [
            {
                "severity": "MODERATE",
                "firstPatchedVersion": "2.1.4",
                "vulnerableVersionRange": ">= 2.1.0, < 2.1.4",
                "name": "org.apache.hbase:hbase",
                "ecosystem": "MAVEN",
                "cve_id": "CVE-2019-0212",
                "cve_url": "https://nvd.nist.gov/vuln/detail/CVE-2019-0212",
                "ghsa_id": "GHSA-535v-4x9q-446c",
                "ghsa_url": "https://github.com/advisories/GHSA-535v-4x9q-446c"
            },
            {
                "severity": "MODERATE",
                "firstPatchedVersion": "2.0.5",
                "vulnerableVersionRange": ">= 2.0.0, < 2.0.5",
                "name": "org.apache.hbase:hbase",
                "ecosystem": "MAVEN",
                "cve_id": "CVE-2019-0212",
                "cve_url": "https://nvd.nist.gov/vuln/detail/CVE-2019-0212",
                "ghsa_id": "GHSA-535v-4x9q-446c",
                "ghsa_url": "https://github.com/advisories/GHSA-535v-4x9q-446c"
            },
            {
                "severity": "HIGH",
                "firstPatchedVersion": "1.1.0.1",
                "vulnerableVersionRange": "= 1.1.0",
                "name": "org.apache.hbase:hbase",
                "ecosystem": "MAVEN",
                "cve_id": "CVE-2015-1836",
                "cve_url": "https://nvd.nist.gov/vuln/detail/CVE-2015-1836",
                "ghsa_id": "GHSA-p8xr-4v2c-rvgp",
                "ghsa_url": "https://github.com/advisories/GHSA-p8xr-4v2c-rvgp"
            },
            {
                "severity": "HIGH",
                "firstPatchedVersion": "1.0.1.1",
                "vulnerableVersionRange": ">= 1.0.0, < 1.0.1.1",
                "name": "org.apache.hbase:hbase",
                "ecosystem": "MAVEN",
                "cve_id": "CVE-2015-1836",
                "cve_url": "https://nvd.nist.gov/vuln/detail/CVE-2015-1836",
                "ghsa_id": "GHSA-p8xr-4v2c-rvgp",
                "ghsa_url": "https://github.com/advisories/GHSA-p8xr-4v2c-rvgp"
            },
            {
                "severity": "HIGH",
                "firstPatchedVersion": "0.98.12.1",
                "vulnerableVersionRange": ">= 0.98, < 0.98.12.1",
                "name": "org.apache.hbase:hbase",
                "ecosystem": "MAVEN",
                "cve_id": "CVE-2015-1836",
                "cve_url": "https://nvd.nist.gov/vuln/detail/CVE-2015-1836",
                "ghsa_id": "GHSA-p8xr-4v2c-rvgp",
                "ghsa_url": "https://github.com/advisories/GHSA-p8xr-4v2c-rvgp"
            }
        ],
      	"version": "1.1.1"
    },
    "errmsg": "成功"
}
```

