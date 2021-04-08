package com.dovendev.track.graphql.scalars;

import com.dovendev.track.graphql.utils.Utils;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import java.time.Duration;
import java.time.format.DateTimeParseException;

public class GraphQLDurationCoercing implements Coercing<Duration, String> {

  private Duration convert(Object object) {
    try {
      if (object instanceof Duration) {
        return (Duration) object;
      }
      return Duration.parse(object.toString());
    } catch (DateTimeParseException e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
    Duration result = convert(dataFetcherResult);
    if (result == null) {
      throw new CoercingSerializeException(
          "Expected type 'Duration' but was '" + Utils.typeName(dataFetcherResult) + "'");
    }
    return result.toString();
  }

  @Override
  public Duration parseValue(Object input) throws CoercingParseValueException {
    Duration result = convert(input);
    if (result == null) {
      throw new CoercingSerializeException(
          "Expected type 'Duration' but was '" + Utils.typeName(input) + "'");
    }
    return result;
  }

  @Override
  public Duration parseLiteral(Object input) throws CoercingParseLiteralException {
    if (input instanceof StringValue) {
      Duration result = convert(input);
      if (result == null) {
        throw new CoercingParseLiteralException(
            "Unable to turn AST input into a 'Duration': '" + input + "'");
      }
    }
    throw new CoercingParseLiteralException(
        "Expected AST type 'StringValue' but was '" + Utils.typeName(input) + "'.");
  }
}
