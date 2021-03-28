module track.finder.persistence {
  requires java.persistence;
  requires java.sql;
  requires spring.data.jpa;
  requires spring.context;
  requires spring.data.commons;
  requires querydsl.core;
  requires spring.core;

  exports com.dovendev.track.jpa.entities;
  exports com.dovendev.track.jpa.services;
}