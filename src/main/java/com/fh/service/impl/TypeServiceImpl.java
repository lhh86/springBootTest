package com.fh.service.impl;

import com.fh.mapper.TypeMapper;
import com.fh.model.Type;
import com.fh.service.TypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class TypeServiceImpl implements TypeService {

    @Resource
    private TypeMapper typeMapper;

    @Override
    public List<Type> queryTypeAll() {
        return typeMapper.selectList(null);
    }
}
