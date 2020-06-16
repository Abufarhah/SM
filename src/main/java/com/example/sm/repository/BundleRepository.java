package com.example.sm.repository;

import com.example.sm.model.DaoBundle;
import org.springframework.data.aerospike.repository.AerospikeRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BundleRepository extends AerospikeRepository<DaoBundle,Integer> {

}
