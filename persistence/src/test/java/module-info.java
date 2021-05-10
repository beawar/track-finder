open module track.finder.persistence {
  requires spring.data.jpa;
  requires spring.context;

  requires org.junit.jupiter.api;
  requires spring.boot.test;
  requires spring.beans;
  requires spring.boot.test.autoconfigure;
  requires spring.boot;
  requires spring.boot.autoconfigure;
  requires org.mockito;
  requires spring.data.commons;
  requires querydsl.core;
  requires spring.test;
}