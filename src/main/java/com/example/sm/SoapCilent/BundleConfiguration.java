package com.example.sm.SoapCilent;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class BundleConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.example.sm.wsdl");
        return marshaller;
    }

    @Bean
    public BundleClient bundleClient(Jaxb2Marshaller marshaller) {
        BundleClient client = new BundleClient();
        client.setDefaultUri("http://localhost:8083/SoapWS");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}
