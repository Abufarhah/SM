package com.example.sm.controller;

import com.example.sm.model.Bundle;
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

    @RequestMapping(method = RequestMethod.GET, path = "/bundles")
    public ResponseEntity<List<Bundle>> getBundles() {
        try {
            List<Bundle> list=bundleService.getBundles();
            if (list.size() == 0) {
                return new ResponseEntity<List<Bundle>>(new ArrayList<Bundle>(), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<List<Bundle>>(list, HttpStatus.OK);

            }
        } catch (Exception e) {
            return new ResponseEntity<List<Bundle>>(new ArrayList<Bundle>(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/bundles/{id}")
    public ResponseEntity<Bundle> getBundle(@PathVariable int id) {
        if (bundleService.getBundle(id) != null) {
            return new ResponseEntity<Bundle>(bundleService.getBundle(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<Bundle>(bundleService.getBundle(id), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/bundles")
    public ResponseEntity<String> addBundle(@RequestBody Bundle bundle) {
        int flag= bundleService.addBundle(bundle);
        if (flag==1) {
            return new ResponseEntity<String>("Bundle with id: " + bundle.getId() + " added successfully",
                    HttpStatus.OK);
        } else if(flag==-1) {
            return new ResponseEntity<String>("Bundle with id: " + bundle.getId() + " error in adding",
                    HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<String>("Bundle with id: " + bundle.getId() + " is already exist",
                    HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/bundles/{id}")
    public ResponseEntity<String> updateBundle(@RequestBody Bundle bundle, @PathVariable int id) {
        if (bundleService.updateBundle(id, bundle)) {
            return new ResponseEntity<String>("updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Bundle not found!", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/bundles/{id}")
    public ResponseEntity<String> deleteBundle(@PathVariable int id) {
        if (bundleService.deleteBundle(id)) {
            return new ResponseEntity<String>("deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Bundle not found!", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/bundles/{id}")
    public ResponseEntity<String> provideBundle(@PathVariable int id) {
        int flag = bundleService.provideBundle(id);
        if (flag == 1) {
            return new ResponseEntity<String>("Bundle with id: " + id + " provided successfully", HttpStatus.OK);
        } else if (flag == 0) {
            return new ResponseEntity<String>("Bundle with id: " + id + " is already provided",
                    HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<String>("Bundle with id: " + id + " error in provision",
                    HttpStatus.NOT_FOUND);
        }
    }
}
