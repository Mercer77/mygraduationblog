package com.mercer.myblog.dao;

import com.mercer.myblog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ Date:2019/8/21
 * Auther:Mercer
 * Auther:麻前程
 */
public interface TypeRepository extends JpaRepository<Type,Long> {
    Type findByName(String name);
    @Query("select t from Type t")
    List<Type> listTypeTop(Pageable pageable);  //分类前x列表
}
