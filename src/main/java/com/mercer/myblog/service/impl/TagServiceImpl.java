package com.mercer.myblog.service.impl;

import com.mercer.myblog.dao.TagRepository;
import com.mercer.myblog.exception.NotFoundException;
import com.mercer.myblog.po.Tag;
import com.mercer.myblog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Date:2019/8/21
 * Auther:Mercer
 * Auther:麻前程
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagRepository tagRepository;
    @Transactional
    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }
    @Transactional
    @Override
    public Tag get(Long id) {
        return tagRepository.getOne(id);
    }

    @Transactional
    @Override
    public Tag getByName(String name) {
        return tagRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Tag> list(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return tagRepository.listTagTop(pageable);
    }

    @Transactional
    @Override
    public List<Tag> list(String tagsId) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(tagsId) && tagsId != null) {
            String[] tags = tagsId.split(",");
            for (int i = 0; i < tags.length; i++) {
                list.add(new Long(tags[i]));
            }
        }
        return tagRepository.findAllById(list);
    }

    @Override
    public List<Tag> list() {
        return tagRepository.findAll();
    }

    @Transactional
    @Override
    public Tag update(Long id, Tag type) {
        Tag t = tagRepository.getOne(id);
        if (t==null){
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(type, t);  //属性更新
        return tagRepository.save(t);  //id不变，会自动更新
    }
    @Transactional
    @Override
    public void delete(Long id) {
        tagRepository.deleteById(id);
    }
}
