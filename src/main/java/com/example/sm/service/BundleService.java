package com.example.sm.service;

import com.example.sm.dto.BundleDto;
import com.example.sm.SoapCilent.BundleClient;
import com.example.sm.model.Bundle;
import com.example.sm.wsdl.AddBundleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BundleService {

    @Autowired
    BundleClient bundleClient;


    private BundleDto bundleDto = new BundleDto();

    public List<Bundle> getBundles() {
        return bundleDto.getBundleList();
    }

    public Bundle getBundle(int id) {
       return bundleDto.getBundleById(id);
    }

    public int addBundle(Bundle bundle) {
        return bundleDto.addBundle(bundle);
    }

    public int updateBundle(int id, Bundle bundle) {
       return bundleDto.updateBundle(id,bundle);
    }

    public int deleteBundle(int id) {
       return bundleDto.deleteBundle(id);
    }

    public int provisionBundle(int id) {
        try{
            Bundle bundle= bundleDto.getBundleById(id);
            AddBundleResponse addBundleResponse=bundleClient.addBundle(bundle.getId(), bundle.getName(),bundle.getPrice());
            if(addBundleResponse.getMessage().contains("exists"))return 0;
            return 1;
        } catch (Exception e) {
            return -1;
        }
    }

}
