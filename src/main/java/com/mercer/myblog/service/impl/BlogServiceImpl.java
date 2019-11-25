package com.mercer.myblog.service.impl;

import com.mercer.myblog.dao.BlogRepository;
import com.mercer.myblog.dao.TagRepository;
import com.mercer.myblog.exception.NotFoundException;
import com.mercer.myblog.po.Blog;
import com.mercer.myblog.po.Tag;
import com.mercer.myblog.po.Type;
import com.mercer.myblog.service.BlogService;
import com.mercer.myblog.util.MarkdownUtils;
import com.mercer.myblog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @ Date:2019/8/23
 * Auther:Mercer
 * Auther:麻前程
 */
@Service
@CacheConfig(cacheNames = "blog")
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogRepository blogRepository;
    @Autowired
    TagRepository tagRepository;

    @Transactional
    @Override
    public Blog get(Long id) {
        return blogRepository.getOne(id);
    }

    @Transactional
    @Override
    @Cacheable(key = "#id")
    public Blog getDetail(Long id) {
        blogRepository.updateViews(id);
        Blog blog = blogRepository.getOne(id);
        if (blog==null){
            throw new NotFoundException("该博客不存在！");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));  //将markdown格式转换为html格式
        return b;
    }

    @Transactional
    @Override
    public Page<Blog> list(Pageable pageable, BlogQuery blog) {
        return  blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (blog.getTitle()!=null && !"".equals(blog.getTitle()) ){
                    predicates.add(cb.like(root.get("title"), "%"+blog.getTitle()+"%"));
                }
                if (blog.getTypeId()!=null){
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()){
                    predicates.add(cb.equal(root.get("recommend"), blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);

    }

    @Transactional
    @Override
    public Page<Blog> list(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listByTagId(Pageable pageable, Long tagId) {
       return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"), tagId);
            }
        }, pageable);
    }

    @Override
    public Page<Blog> listSearch(Pageable pageable, String query) {

        return blogRepository.listSearch(query, pageable);
    }

    @Transactional
    @Override
    public Page<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepository.listRecommendBlogTop(pageable);
    }

    @Transactional
    @Override
    public Blog save(Blog blog) {

        if (blog.getId()==null){
            //新增
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
//            this.TagsInsert(blog.getTags());
            return blogRepository.save(blog);
        }
        else {
            //更新
            blog.setUpdateTime(new Date());
//            this.TagsInsert(blog.getTags());
            return this.update(blog.getId(), blog);
        }
    }

    // 添加手动添加的标签到数据库 todo 暂时有问题，tagsId的值中有数字与文字同时传来，不好分别
    @Transactional
    public List<Tag> TagsInsert(List<Tag> tags){
        List<Tag> tagList = tagRepository.findAll();
        Collection<Tag> exists = new ArrayList<Tag>(tags);
        exists.removeAll(tagList);
        if (exists !=null) {
            return tagRepository.saveAll(new ArrayList<>(exists));
        }
        else
            return null;

    }

    @Transactional
    @Override
    public Blog update(Long id, Blog blog) {
        Blog b = blogRepository.getOne(id);
        blog.setCreateTime(b.getCreateTime());  //创建时间不变
        blog.setViews(b.getViews());            //浏览次数不变
        if (b==null){
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog, b);
        return blogRepository.save(b);
    }
    @Transactional
    @Override
    public void delete(Long id) {
        blogRepository.deleteById(id);
    }
}
