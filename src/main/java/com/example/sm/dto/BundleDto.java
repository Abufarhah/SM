package com.example.sm.dto;

import com.aerospike.client.*;
import com.aerospike.client.policy.ClientPolicy;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.ScanPolicy;
import com.aerospike.client.policy.WritePolicy;
import com.example.sm.model.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BundleDto {
    private static AerospikeClient aerospikeClient=new AerospikeClient(new ClientPolicy(), "172.28.128.3", 3000);
    private Policy  policy=new Policy();
    private WritePolicy writePolicy=new WritePolicy();
    private ScanPolicy scanPolicy=new ScanPolicy();

    public List<Bundle> getBundleList(){
        List<Bundle> bundleList = new ArrayList<Bundle>();
        aerospikeClient.scanAll(scanPolicy, "test", "smbundles", new ScanCallback() {
            @Override
            public void scanCallback(Key key, Record record) throws AerospikeException {
                Bundle bundle = new Bundle();
                bundle.setId(record.getInt("id"));
                bundle.setName(record.getString("name"));
                bundle.setPrice(record.getDouble("price"));
                bundleList.add(bundle);
            }
        });
        Collections.sort(bundleList);
        return bundleList;
    }

    public Bundle getBundleById(int id){
        Bundle bundle=new Bundle();
        Key key = new Key("test", "smbundles", "k" + id);
        Record record=aerospikeClient.get(scanPolicy,key);
        bundle.setId(record.getInt("id"));
        bundle.setName(record.getString("name"));
        bundle.setPrice(record.getDouble("price"));
        return bundle;
    }

    public int addBundle(Bundle bundle){
        try {
            Key key = new Key("test", "smbundles", "k" + bundle.getId());
            if (aerospikeClient.exists(policy,key)) {
                return 0;
            }
            Bin idBin = new Bin("id", bundle.getId());
            Bin nameBin = new Bin("name", bundle.getName());
            Bin priceBin = new Bin("price", bundle.getPrice());
            aerospikeClient.add(writePolicy, key, idBin, nameBin, priceBin);
            return 1;
        }catch (Exception e){
            return -1;
        }
    }

    public int updateBundle(int id,Bundle bundle){
        try {
            Key key = new Key("test", "smbundles", "k" + bundle.getId());
            if (!aerospikeClient.exists(policy,key)) {
                return 0;
            }
            Bin idBin = new Bin("id", bundle.getId());
            Bin nameBin = new Bin("name", bundle.getName());
            Bin priceBin = new Bin("price", bundle.getPrice());
            aerospikeClient.put(writePolicy, key, idBin, nameBin, priceBin);
            return 1;
        }catch (Exception e){
            return -1;
        }
    }

    public int deleteBundle(int id){
        try {
            Key key = new Key("test", "smbundles", "k" + id);
            if (!aerospikeClient.exists(policy,key)) {
                return 0;
            }
            aerospikeClient.delete(writePolicy, key);
            return 1;
        }catch (Exception e){
            return -1;
        }
    }

}
