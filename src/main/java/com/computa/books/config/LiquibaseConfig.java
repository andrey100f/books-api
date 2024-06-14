package com.computa.books.config;

import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.exception.LiquibaseException;
import liquibase.ext.mongodb.database.MongoLiquibaseDatabase;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LiquibaseConfig {

    @Value("${spring.data.mongodb.uri}")
    private String url;

    @Bean
    public Liquibase liquibase() throws LiquibaseException {
        MongoLiquibaseDatabase database = (MongoLiquibaseDatabase) DatabaseFactory.getInstance()
                .openDatabase(url, null, null, null, null);
        database.setSupportsValidator(false);
        database.setAdjustTrackingTablesOnStartup(false);

        String LIQUIBASE_CHANGELOG = "db/main_changelog.xml";
        Liquibase liquibase = new Liquibase(LIQUIBASE_CHANGELOG, new ClassLoaderResourceAccessor(), database);

        liquibase.update("");

        return liquibase;
    }

}
