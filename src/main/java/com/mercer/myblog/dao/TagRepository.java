package com.mercer.myblog.dao;

import com.mercer.myblog.po.Tag;
import org.apache.ibatis.annotations.Select;
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
public interface TagRepository extends JpaRepository<Tag,Long> {
    Tag findByName(String name);
    @Query("select t from Tag t")
    List<Tag> listTagTop(Pageable pageable);
}
