package com.example.sm.service;

import com.example.sm.converter.BundleConvertor;
import com.example.sm.model.DaoBundle;
import com.example.sm.model.DtoBundle;
import com.example.sm.repository.BundleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.sm.SoapCilent.BundleClient;
import com.example.sm.wsdl.AddBundleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BundleService {
    private static final Logger log = LoggerFactory.getLogger(BundleService.class);
    @Autowired
    BundleClient bundleClient;

    @Autowired
    BundleRepository bundleRepository;

    @Autowired
    BundleConvertor bundleConvertor;


    public List<DtoBundle> getBundles() {
        log.info("service to get list of bundles");
        List<DaoBundle> daoBundleList = new ArrayList<>();
        bundleRepository.findAll().forEach(daoBundleList::add);
        List<DtoBundle> dtoBundleList = new ArrayList<>();
        for(DaoBundle daoBundle:daoBundleList){
            dtoBundleList.add(bundleConvertor.daoToDtoBundle(daoBundle));
        }

        Collections.sort(dtoBundleList);
        return dtoBundleList;
    }

    public DtoBundle getBundle(int id) {
        log.info("service to get bundle: " + id);
        DaoBundle daoBundle =bundleRepository.findOne(id);
        DtoBundle dtoBundle=bundleConvertor.daoToDtoBundle(daoBundle);
        Optional<DtoBundle> returnedDtoBundle=Optional.of(dtoBundle);
        returnedDtoBundle=Optional.ofNullable(new DtoBundle(0,"NA",0));
        return dtoBundle;
    }

    public int addBundle(DtoBundle dtoBundle) {
        log.info("service to add bundle: " + dtoBundle.getId());
        DaoBundle daoBundle=bundleConvertor.dtoToDaoBundle(dtoBundle);
        Date creationDate=new Date(System.currentTimeMillis());
        daoBundle.setCreationDate(creationDate);
        daoBundle.setUpdatingDate(creationDate);

        try {
            if (bundleRepository.exists(daoBundle.getId())) {
                return 0;
            }
            bundleRepository.save(daoBundle);
            return 1;
        }catch (Exception e){
            return -1;
        }
    }

    public int updateBundle(int id, DtoBundle dtoBundle) {
        log.info("service to update bundle: " + id);
        DaoBundle retrieveddaoBundle =bundleRepository.findOne(id);
        String creationDate=retrieveddaoBundle.getCreationDate();
        DaoBundle daoBundle=bundleConvertor.dtoToDaoBundle(dtoBundle);
        Date updatingDate=new Date(System.currentTimeMillis());
        daoBundle.setUpdatingDate(updatingDate);
        daoBundle.setCreationDate(creationDate);
        try {
            if (!bundleRepository.exists(daoBundle.getId())) {
                return 0;
            }
            bundleRepository.save(daoBundle);
            return 1;
        }catch (Exception e){
            return -1;
        }    }

    public int deleteBundle(int id) {
        try {
            if (!bundleRepository.exists(id)) {
                return 0;
            }
            bundleRepository.delete(id);
            return 1;
        } catch (Exception e) {
            return -1;
        }
    }

    public int provisionBundle(int id) {
        log.info("service to provisions bundle: " + id);
        try{
            DaoBundle daoBundle =bundleRepository.findOne(id);
            AddBundleResponse addBundleResponse=bundleClient.addBundle(daoBundle.getId(), daoBundle.getName(), daoBundle.getPrice());
            if(addBundleResponse.getMessage().contains("exists"))return 0;
            return 1;
        } catch (Exception e) {
            return -1;
        }
    }
}
