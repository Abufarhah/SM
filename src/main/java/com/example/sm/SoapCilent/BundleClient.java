package com.example.sm.SoapCilent;

import com.example.sm.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import static com.example.sm.constants.Constants.*;

public class BundleClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(BundleClient.class);

    public GetBundleResponse getBundle(int id) {
        GetBundleRequest request = new GetBundleRequest();
        request.setId(id);
        log.info("soap request to get bundle: " + id);
        GetBundleResponse response = (GetBundleResponse) getWebServiceTemplate()
                .marshalSendAndReceive(BUNDLES_SOAP_URI, request,
                        new SoapActionCallback(
                                GET_BUNDLE_REQUEST_SOPA_ACTION));
        return response;
    }

    public AddBundleResponse addBundle(int id,String name,double price) {
        AddBundleRequest request = new AddBundleRequest();
        request.setId(id);
        request.setName(name);
        request.setPrice(price);
        log.info("soap request to add bundle: " + id);
        AddBundleResponse response = (AddBundleResponse) getWebServiceTemplate()
                .marshalSendAndReceive(BUNDLES_SOAP_URI, request,
                        new SoapActionCallback(
                                ADD_BUNDLE_REQUEST_SOPA_ACTION));
        return response;
    }

    public DeleteBundleResponse deleteBundle(int id) {
        DeleteBundleRequest request = new DeleteBundleRequest();
        request.setId(id);
        log.info("soap request to delete bundle: " + id);
        DeleteBundleResponse response = (DeleteBundleResponse) getWebServiceTemplate()
                .marshalSendAndReceive(BUNDLES_SOAP_URI, request,
                        new SoapActionCallback(
                                DELETE_BUNDLE_REQUEST_SOPA_ACTION));
        return response;
    }

}
