package com.example.sm.SoapCilent;

import com.example.sm.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class BundleClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(BundleClient.class);

    public GetBundleResponse getBundle(int id) {
        GetBundleRequest request = new GetBundleRequest();
        request.setId(id);
        log.info("getting bundle: " + id);
        GetBundleResponse response = (GetBundleResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8083/SoapWS/bundles", request,
                        new SoapActionCallback(
                                "http://example.com/me/GetBundleRequest"));
        return response;
    }

    public AddBundleResponse addBundle(int id,String name,double price) {
        AddBundleRequest request = new AddBundleRequest();
        request.setId(id);
        request.setName(name);
        request.setPrice(price);
        log.info("adding bundle: " + id);
        AddBundleResponse response = (AddBundleResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8083/SoapWS/bundles", request,
                        new SoapActionCallback(
                                "http://example.com/me/GetBundleRequest"));
        return response;
    }

    public DeleteBundleResponse deleteBundle(int id) {
        DeleteBundleRequest request = new DeleteBundleRequest();
        request.setId(id);
        log.info("deleting bundle: " + id);
        DeleteBundleResponse response = (DeleteBundleResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8083/SoapWS/bundles", request,
                        new SoapActionCallback(
                                "http://example.com/me/GetBundleRequest"));
        return response;
    }

}
