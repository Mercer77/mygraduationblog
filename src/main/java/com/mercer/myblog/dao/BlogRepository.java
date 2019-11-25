package com.mercer.myblog.dao;

import com.mercer.myblog.po.Blog;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ Date:2019/8/23
 * Auther:Mercer
 * Auther:麻前程
 */

public interface BlogRepository extends JpaRepository<Blog,Long>,JpaSpecificationExecutor<Blog> {

    @Override
    Page<Blog> findAll(Specification<Blog> specification, Pageable pageable);   //多条件组合查询


    @Query("select b from Blog b where b.recommend=true ")
    Page<Blog>  listRecommendBlogTop(Pageable pageable);

    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog>  listSearch(String query,Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Blog b set b.views=b.views+1 where b.id=?1")
    int updateViews(Long id);

    Blog findBlogById(Long id);

}
