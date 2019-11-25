package com.mercer.myblog.service.impl;

import com.mercer.myblog.dao.TypeRepository;
import com.mercer.myblog.exception.NotFoundException;
import com.mercer.myblog.po.Type;
import com.mercer.myblog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ Date:2019/8/21
 * Auther:Mercer
 * Auther:麻前程
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    TypeRepository typeRepository;
    @Transactional
    @Override
    public Type save(Type type) {
        return typeRepository.save(type);
    }
    @Transactional
    @Override
    public Type get(Long id) {
        return typeRepository.getOne(id);
    }

    @Transactional
    @Override
    public Type getByName(String name) {
        return typeRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Type> list(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> listTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return typeRepository.listTypeTop(pageable);
    }

    @Override
    public List<Type> list() {
        return typeRepository.findAll();
    }

    @Transactional
    @Override
    public Type update(Long id, Type type) {
        Type t = typeRepository.getOne(id);
        if (t==null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type, t);  //属性更新
        return typeRepository.save(t);  //id不变，会自动更新
    }
    @Transactional
    @Override
    public void delete(Long id) {
        typeRepository.deleteById(id);
    }
}
