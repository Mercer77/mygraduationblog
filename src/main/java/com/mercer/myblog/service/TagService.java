package com.mercer.myblog.service;

import com.mercer.myblog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @ Date:2019/8/21
 * Auther:Mercer
 * Auther:麻前程
 */
public interface TagService {
    Tag save(Tag tag);
    Tag get(Long id);
    Tag getByName(String name);
    Page<Tag> list(Pageable pageable);
    List<Tag> listTop(Integer size);
    List<Tag> list(String tagsId);
    List<Tag> list();
    Tag update(Long id, Tag tag);
    void delete(Long id);
}
