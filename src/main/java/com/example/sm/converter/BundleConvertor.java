package com.example.sm.converter;

import com.example.sm.model.DaoBundle;
import com.example.sm.model.DtoBundle;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BundleConvertor {
    ModelMapper modelMapper = new ModelMapper();

    public DaoBundle dtoToDaoBundle(DtoBundle dtoBundle) {
        DaoBundle daoBundle = modelMapper.map(dtoBundle,DaoBundle.class);
        return daoBundle;
    }

    public DtoBundle daoToDtoBundle(DaoBundle daoBundle) {
        DtoBundle dtoBundle = modelMapper.map(daoBundle,DtoBundle.class);
        return dtoBundle;
    }
}
