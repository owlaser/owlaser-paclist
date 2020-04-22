package com.owlaser.paclist.dao;

import com.owlaser.paclist.entity.ChildNode;
import com.owlaser.paclist.entity.Dependency;
import com.owlaser.paclist.entity.SecurityAdvise;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PacDao {
    //查找数据库是否有此包
    @Select("SELECT artifact_id FROM maven_dependency WHERE artifact_id = #{artifact_id} AND group_id = #{group_id}")
    public String getArtifactId(@Param("artifact_id")String artifact_id, @Param("group_id")String group_id);

    //插入数据
    @Insert("INSERT INTO maven_dependency(group_id, artifact_id, popular_version, stable_version, license)  VALUES(#{group_id}, #{artifact_id}, #{popular_version}, #{stable_version}, #{license})")
    public void insert(Dependency dependency);

    //得到包数据元祖
    @Select("SELECT * FROM maven_dependency WHERE artifact_id = #{artifact_id} AND group_id = #{group_id}")
    public Dependency getSqlDependency(@Param("artifact_id")String artifact_id, @Param("group_id")String group_id);

    //查找数据库是否有此包的依赖信息
    @Select("SELECT DISTINCT parent_artifactid FROM maven_childNodes WHERE parent_artifactid = #{artifact_id} AND parent_groupid = #{group_id}")
    public String getParentArtifactId(@Param("artifact_id")String artifact_id, @Param("group_id")String group_id);

    //插入依赖树数据
    @Insert("INSERT INTO maven_childNodes(parent_groupid, parent_artifactid, child_groupid, child_artifactid, url) VALUES(#{parent_groupid}, #{parent_artifactid}, #{child_groupid}, #{child_artifactid}, #{url})")
    public void insertChild(@Param("parent_groupid")String parent_groupid, @Param("parent_artifactid")String parent_artifactid, @Param("child_groupid")String child_groupid, @Param("child_artifactid")String child_artifactid, @Param("url")String url);

    //得到包数据的所有依赖包信息元祖
    @Select("SELECT * FROM maven_childNodes WHERE parent_artifactid = #{artifact_id} AND parent_groupid = #{group_id}")
    public List<ChildNode> getParentDependencies(@Param("artifact_id")String artifact_id, @Param("group_id")String group_id);

    //查找数据库是否有此包的漏洞信息
    @Select("SELECT DISTINCT name FROM security_vulnerabilities WHERE name = #{name} AND vulnerableVersionRange = #{vulnerableVersionRange}")
    public String getSecurityName(@Param("name")String name, @Param("vulnerableVersionRange")String vulnerableVersionRange);

    @Insert("INSERT INTO security_vulnerabilities(name, ecosystem, severity, GHSA_id, GHSA_url, CVE_id, CVE_url, vulnerableVersionRange, firstPatchedVersion) " +
            "VALUE(#{name}, #{ecosystem}, #{severity}, #{GHSA_id}, #{GHSA_url}, #{CVE_id}, #{CVE_url}, #{vulnerableVersionRange}, #{firstPatchedVersion})")
    public void insertSecurity(@Param("name")String name, @Param("ecosystem")String ecosystem, @Param("severity")String severity,
                               @Param("GHSA_id")String GHSA_id, @Param("GHSA_url")String GHSA_url, @Param("CVE_id")String CVE_id,
                               @Param("CVE_url")String CVE_url, @Param("vulnerableVersionRange")String vulnerableVersionRange,
                               @Param("firstPatchedVersion")String firstPatchedVersion);

    @Select("SELECT * FROM security_vulnerabilities WHERE name = #{name}")
    public List<SecurityAdvise> getSecurityMessage(@Param("name")String name);
}
