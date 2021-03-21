package com.dovendev.track.graphql.scalars;

import graphql.PublicApi;
import graphql.schema.GraphQLScalarType;

@PublicApi
public class CustomScalars {

  public static final GraphQLScalarType Duration = GraphQLScalarType.newScalar()
      .name("Duration")
      .description("ISO_8601 compliant Duration Scalar")
      .coercing(new GraphQLDurationCoercing()).build();
}
