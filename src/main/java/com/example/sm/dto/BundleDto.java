package com.example.sm.dto;

import com.aerospike.client.*;
import com.aerospike.client.policy.ClientPolicy;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.ScanPolicy;
import com.aerospike.client.policy.WritePolicy;
import com.example.sm.model.Bundle;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.sm.constants.Constants.*;

@Component
public class BundleDto {
    private static AerospikeClient aerospikeClient=new AerospikeClient(new ClientPolicy(), AEROSPIKE_SERVER_HOST_NAME, AEROSPIKE_SERVER_PORT);
    private Policy  policy=new Policy();
    private WritePolicy writePolicy=new WritePolicy();
    private ScanPolicy scanPolicy=new ScanPolicy();

    public List<Bundle> getBundleList(){
        List<Bundle> bundleList = new ArrayList<>();
        aerospikeClient.scanAll(scanPolicy, AEROSPIKE_SERVER_NAMESPACE, AEROSPIKE_SERVER_SM_SETNAME, new ScanCallback() {
            @Override
            public void scanCallback(Key key, Record record) throws AerospikeException {
                Bundle bundle = new Bundle();
                bundle.setId(record.getInt(AEROSPIKE_SERVER_BIN_ID));
                bundle.setName(record.getString(AEROSPIKE_SERVER_BIN_NAME));
                bundle.setPrice(record.getDouble(AEROSPIKE_SERVER_BIN_PRICE));
                bundleList.add(bundle);
            }
        });
        Collections.sort(bundleList);
        return bundleList;
    }

    public Bundle getBundle(int id){
        Bundle bundle=new Bundle();
        Key key = new Key(AEROSPIKE_SERVER_NAMESPACE, AEROSPIKE_SERVER_SM_SETNAME, "k" + id);
        Record record=aerospikeClient.get(scanPolicy,key);
        bundle.setId(record.getInt(AEROSPIKE_SERVER_BIN_ID));
        bundle.setName(record.getString(AEROSPIKE_SERVER_BIN_NAME));
        bundle.setPrice(record.getDouble(AEROSPIKE_SERVER_BIN_PRICE));
        return bundle;
    }

    public int addBundle(Bundle bundle){
        try {
            Key key = new Key(AEROSPIKE_SERVER_NAMESPACE, AEROSPIKE_SERVER_SM_SETNAME, "k" + bundle.getId());
            if (aerospikeClient.exists(policy,key)) {
                return 0;
            }
            Bin idBin = new Bin(AEROSPIKE_SERVER_BIN_ID, bundle.getId());
            Bin nameBin = new Bin(AEROSPIKE_SERVER_BIN_NAME, bundle.getName());
            Bin priceBin = new Bin(AEROSPIKE_SERVER_BIN_PRICE, bundle.getPrice());
            aerospikeClient.add(writePolicy, key, idBin, nameBin, priceBin);
            return 1;
        }catch (Exception e){
            return -1;
        }
    }

    public int updateBundle(int id,Bundle bundle){
        try {
            Key key = new Key(AEROSPIKE_SERVER_NAMESPACE, AEROSPIKE_SERVER_SM_SETNAME, "k" + bundle.getId());
            if (!aerospikeClient.exists(policy,key)) {
                return 0;
            }
            Bin idBin = new Bin(AEROSPIKE_SERVER_BIN_ID, bundle.getId());
            Bin nameBin = new Bin(AEROSPIKE_SERVER_BIN_NAME, bundle.getName());
            Bin priceBin = new Bin(AEROSPIKE_SERVER_BIN_PRICE, bundle.getPrice());
            aerospikeClient.put(writePolicy, key, idBin, nameBin, priceBin);
            return 1;
        }catch (Exception e){
            return -1;
        }
    }

    public int deleteBundle(int id){
        try {
            Key key = new Key(AEROSPIKE_SERVER_NAMESPACE, AEROSPIKE_SERVER_SM_SETNAME, "k" + id);
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
