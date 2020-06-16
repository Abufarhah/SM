package com.example.sm.controller;

import com.example.sm.SoapCilent.BundleClient;
import com.example.sm.model.DtoBundle;
import com.example.sm.service.BundleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class BundleController {

    @Autowired
    BundleService bundleService;

    @Autowired
    BundleClient bundleClient;


    @RequestMapping(method = RequestMethod.GET, path = "/bundles")
    public ResponseEntity<List<DtoBundle>> getBundles() {
        try {
            List<DtoBundle> list=bundleService.getBundles();
            if (list.size() == 0) {
                return new ResponseEntity<List<DtoBundle>>(new ArrayList<DtoBundle>(), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<List<DtoBundle>>(list, HttpStatus.OK);

            }
        } catch (Exception e) {
            return new ResponseEntity<List<DtoBundle>>(new ArrayList<DtoBundle>(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/bundles/{id}")
    public ResponseEntity<DtoBundle> getBundle(@PathVariable int id) {
        if (bundleService.getBundle(id) != null) {
            return new ResponseEntity<DtoBundle>(bundleService.getBundle(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<DtoBundle>(bundleService.getBundle(id), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/bundles")
    public ResponseEntity<String> addBundle(@RequestBody DtoBundle dtoBundle) {
        int flag= bundleService.addBundle(dtoBundle);
        if (flag==1) {
            return new ResponseEntity<String>("Bundle with id: " + dtoBundle.getId() + " added successfully",
                    HttpStatus.OK);
        } else if(flag==-1) {
            return new ResponseEntity<String>("Bundle with id: " + dtoBundle.getId() + " error in adding",
                    HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<String>("Bundle with id: " + dtoBundle.getId() + " is already exist",
                    HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/bundles/{id}")
    public ResponseEntity<String> updateBundle(@RequestBody DtoBundle dtoBundle, @PathVariable int id) {
        int flag=bundleService.updateBundle(id, dtoBundle);
        if (flag==1) {
            return new ResponseEntity<String>("Bundle with id: " + id + " updated successfully", HttpStatus.OK);
        } else if(flag==0){
            return new ResponseEntity<String>("Bundle with id: " + id +" bundle not found!", HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<String>("Bundle with id: " + id +" error in updating!", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/bundles/{id}")
    public ResponseEntity<String> deleteBundle(@PathVariable int id) {
        int flag = bundleService.deleteBundle(id);
        if (flag==1) {
            return new ResponseEntity<String>("Bundle with id: " + id + " deleted successfully", HttpStatus.OK);
        } else if(flag==0){
            return new ResponseEntity<String>("Bundle with id: " + id +" bundle not found!", HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<String>("Bundle with id: " + id +" error in deleting!", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/bundles/{id}")
    public ResponseEntity<String> provideBundle(@PathVariable int id) {
        int flag = bundleService.provisionBundle(id);
        if (flag == 1) {
            return new ResponseEntity<String>("Bundle with id: " + id + " provisioned successfully", HttpStatus.OK);
        } else if (flag == 0) {
            return new ResponseEntity<String>("Bundle with id: " + id + " is already provisioned",
                    HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<String>("Bundle with id: " + id + " error in provision",
                    HttpStatus.NOT_FOUND);
        }
    }
}
