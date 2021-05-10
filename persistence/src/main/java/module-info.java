module track.finder.persistence {
  requires java.persistence;
  requires java.sql;
  requires spring.data.jpa;
  requires spring.context;
  requires spring.data.commons;
  requires querydsl.core;
  requires spring.core;
  requires spring.beans;

  exports com.dovendev.track.jpa.entities;
  exports com.dovendev.track.jpa.services;
  exports com.dovendev.track.jpa.repositories;
}