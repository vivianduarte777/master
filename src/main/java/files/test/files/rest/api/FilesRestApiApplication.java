package files.test.files.rest.api;


import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication


public class FilesRestApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(FilesRestApiApplication.class, args);
    }


    @Bean
    public ServletWebServerFactory servletContainer(){
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory(){
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();

                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection col = new SecurityCollection();
                col.addPattern("/*");
                securityConstraint.addCollection(col);
                context.addConstraint(securityConstraint);
            }
        };

        //Add HTTP redirect
        tomcat.addAdditionalTomcatConnectors(httToHttpsRedirectConnector());
        return tomcat;
    }

    private Connector httToHttpsRedirectConnector() {
        Connector con = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        con.setScheme("http");
        con.setPort(8082);
        con.setSecure(false);
        con.setRedirectPort(8443);
        return con;
    }
}
