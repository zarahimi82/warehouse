package com.krieger.warehouse.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                description = "Warehouse Resources",
                version = "V1.0.0",
                title = "Warehouse Resource API",
                contact = @Contact(
                        name = "Zahra Rahimi",
                        email = "za.rahimi82@gmail.com",
                        url = ""
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        servers = {@Server(url = "")}
)
public interface ApiDocumentationConfig {

}