package com.owlaser.paclist.dao;

import com.owlaser.paclist.entity.Dependency;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
}
