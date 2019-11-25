package com.mercer.myblog.service;

import com.mercer.myblog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @ Date:2019/8/21
 * Auther:Mercer
 * Auther:麻前程
 */
public interface TypeService {
    Type save(Type type);
    Type get(Long id);
    Type getByName(String name);
    Page<Type> list(Pageable pageable);
    List<Type> listTop(Integer size);
    List<Type> list();
    Type update(Long id,Type type);
    void delete(Long id);
}
