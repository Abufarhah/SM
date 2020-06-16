package com.example.sm.service;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLFault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.sm.dto.BundleDto;
import com.example.sm.SoapCilent.BundleClient;
import com.example.sm.model.Bundle;
import com.example.sm.wsdl.AddBundleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BundleService {
    private static final Logger log = LoggerFactory.getLogger(BundleService.class);

    @Autowired
    BundleClient bundleClient;

    @Autowired
    private BundleDto bundleDto;

    public List<Bundle> getBundles() {
        log.info("service to get list of bundles");
        return bundleDto.getBundleList();
    }

    public Bundle getBundle(int id) {
        log.info("service to get bundle: " + id);
        return bundleDto.getBundle(id);
    }

    public int addBundle(Bundle bundle) {
        log.info("service to add bundle: " + bundle.getId());
        return bundleDto.addBundle(bundle);
    }

    public int updateBundle(int id, Bundle bundle) {
        log.info("service to update bundle: " + id);
        return bundleDto.updateBundle(id,bundle);
    }

    public int deleteBundle(int id) {
        log.info("service to delete bundle: " + id);
        return bundleDto.deleteBundle(id);
    }

    public int provisionBundle(int id) {
        log.info("service to provisions bundle: " + id);
        try{
            Bundle bundle= bundleDto.getBundle(id);
            AddBundleResponse addBundleResponse=bundleClient.addBundle(bundle.getId(), bundle.getName(),bundle.getPrice());
            if(addBundleResponse.getMessage().contains("exists"))return 0;
            return 1;
        } catch (Exception e) {
            return -1;
        }
    }

}
