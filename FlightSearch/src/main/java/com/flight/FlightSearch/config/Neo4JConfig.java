package com.flight.FlightSearch.config;

import org.neo4j.cypherdsl.core.renderer.Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Neo4JConfig {

    @Bean
    public org.neo4j.cypherdsl.core.renderer.Configuration neo4JDialect() {
//    specify Neo4J dialect for proper relationship creation with Neo4J JPA repository.
        return org.neo4j.cypherdsl.core.renderer.Configuration.newConfig().withDialect(Dialect.NEO4J_5).build();
    }
}
