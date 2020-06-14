package com.example.sm.service;

import com.aerospike.client.*;
import com.aerospike.client.policy.ClientPolicy;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.ScanPolicy;
import com.aerospike.client.policy.WritePolicy;
import com.example.sm.model.Bundle;
import com.example.sm.soap.SoapClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BundleService {
    ClientPolicy cpolicy = new ClientPolicy();
    WritePolicy wpolicy = new WritePolicy();
    Policy policy=new Policy();
    private AerospikeClient client = new AerospikeClient(cpolicy, "172.28.128.3", 3000);

    public List<Bundle> getBundles() {
        ScanPolicy policy = new ScanPolicy();
        List<Bundle> bundleList = new ArrayList<Bundle>();

        client.scanAll(policy, "test", "smbundles", new ScanCallback() {

            @Override
            public void scanCallback(Key key, Record record) throws AerospikeException {
                Bundle temp = new Bundle();
                temp.setId(record.getInt("id"));
                temp.setName(record.getString("name"));
                temp.setPrice(record.getDouble("price"));
                bundleList.add(temp);
            }
        });
        Collections.sort(bundleList);
        return bundleList;
    }

    public Bundle getBundle(int id) {
        Bundle temp;
        try {
            Key key = new Key("test", "smbundles", "k" + id);
            ScanPolicy policy = new ScanPolicy();
            Record record = client.get(policy, key);
            temp = new Bundle();
            temp.setId(record.getInt("id"));
            temp.setName(record.getString("name"));
            temp.setPrice(record.getDouble("price"));
            return temp;
        } catch (Exception e) {
            return null;
        }
    }

    public int addBundle(Bundle bundle) {
        try {
            wpolicy.setTimeout(50);
            Key key = new Key("test", "smbundles", "k" + bundle.getId());
            if(client.exists(policy, key)) {
                return 0;
            }
            Bin bin1 = new Bin("id", bundle.getId());
            Bin bin2 = new Bin("name", bundle.getName());
            Bin bin3 = new Bin("price", bundle.getPrice());
            client.put(wpolicy, key, bin1, bin2, bin3);
            return 1;
        }catch (Exception e) {
            return -1;
        }
    }

    public boolean updateBundle(int id, Bundle bundle) {
        try {
            wpolicy.setTimeout(50);
            Key key = new Key("test", "smbundles", "k" + bundle.getId());
            Bin bin1 = new Bin("id", bundle.getId());
            Bin bin2 = new Bin("name", bundle.getName());
            Bin bin3 = new Bin("price", bundle.getPrice());

            client.put(wpolicy, key, bin1, bin2, bin3);
            return true;
        } catch (AerospikeException e) {
            return false;
        }
    }

    public boolean deleteBundle(int id) {
        try {
            wpolicy.setTimeout(50);
            Key key = new Key("test", "smbundles", "k" + id);
            client.delete(wpolicy, key);
            return true;
        } catch (AerospikeException e) {
            return false;
        }
    }

    public int provideBundle(int id) {
        try {
            Key key = new Key("test", "smbundles", "k" + id);
            ScanPolicy policy = new ScanPolicy();
            Record record = client.get(policy, key);
            return (SoapClient.provision(record.getInt("id"), record.getString("name"), record.getDouble("price")));
        } catch (Exception e) {
            return -1;
        }
    }

}
