package com.dovendev.track.graphql.connection;
import graphql.relay.ConnectionCursor;
import graphql.relay.Edge;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CursorUtil {

  public static <T> ConnectionCursor getFirstCursorFrom(List<Edge<T>> edges){
    return edges.isEmpty() ? null : edges.get(0).getCursor();
  }

  public static <T> ConnectionCursor getLastCursorFrom(List<Edge<T>> edges){
    return edges.isEmpty() ? null : edges.get(edges.size() - 1).getCursor();
  }

}
