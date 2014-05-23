package hu.vanio.spring.boot.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@ImportResource({ "/integration-context.xml", "/cxf-servlet.xml" })
public class ExampleIntegrationApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ExampleIntegrationApplication.class, args);
    }
    
    @Bean
    public ServletRegistrationBean cxfServlet() {
        org.apache.cxf.transport.servlet.CXFServlet cxfServlet = new org.apache.cxf.transport.servlet.CXFServlet();
        ServletRegistrationBean servletDef = new ServletRegistrationBean(cxfServlet, "/services/*");
        servletDef.setLoadOnStartup(1);
        return servletDef;
    }
    
//    @Bean
//    public WsdlDefinition contentStore() {
//        return new SimpleWsdl11Definition(new ClassPathResource("/contentStore.wsdl"));
//    }
    
//    @Bean
//    public Object marshaller() {
//        Jaxb2Marshaller marshaller = new Jaxb2Marshaller(); 
//        marshaller.setMtomEnabled(true);
//        marshaller.setContextPath("hu.vanio.springwsmtom.wstypes");
//        return marshaller;
//    }

}
