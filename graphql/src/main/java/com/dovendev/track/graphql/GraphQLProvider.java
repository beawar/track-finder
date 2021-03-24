package com.dovendev.track.graphql;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

import com.dovendev.track.graphql.datafetchers.TrackDataFetcher;
import com.dovendev.track.graphql.scalars.CustomScalars;
import graphql.GraphQL;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GraphQLProvider {
    private GraphQL graphQL;

    @Autowired
    private TrackDataFetcher trackDataFetchers;

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    @PostConstruct
    public void init() throws IOException {
        try (InputStream schema = getClass().getClassLoader().getResourceAsStream("schema.graphql")) {
            if (schema == null) {
                throw new IOException("Schema not found!");
            }
            String schemaStr = new String(schema.readAllBytes(), StandardCharsets.UTF_8);
            GraphQLSchema graphQLSchema = buildSchema(schemaStr);
            this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
        }
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .scalar(CustomScalars.Duration)
                .scalar(ExtendedScalars.DateTime)
                .type(newTypeWiring("Query")
                        .dataFetcher("getTrack", trackDataFetchers.getTrackDataFetcher()))
                .type(newTypeWiring("Query")
                    .dataFetcher("findAll", trackDataFetchers.findAllTrackDataFetcher()))
                .type(newTypeWiring("Query")
                    .dataFetcher("findByTitleDescription", trackDataFetchers.findByTitleDescriptionDataFetcher()))
                .type(newTypeWiring("Mutation")
                        .dataFetcher("createTrack", trackDataFetchers.createTrackDataFetcher()))
                .type(newTypeWiring("Mutation")
                        .dataFetcher("deleteTrack", trackDataFetchers.deleteTrackDataFetcher()))
                .type(newTypeWiring("Mutation")
                        .dataFetcher("updateTrack", trackDataFetchers.updateTrackDataFetcher()))
                .build();
    }
}
