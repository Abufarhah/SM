package com.example.sm.SoapCilent;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import static com.example.sm.constants.Constants.*;

@Configuration
public class BundleConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath(WSDL_CONTEXT_PATH);
        return marshaller;
    }

    @Bean
    public BundleClient bundleClient(Jaxb2Marshaller marshaller) {
        BundleClient client = new BundleClient();
        client.setDefaultUri(SOAP_URI);
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}
