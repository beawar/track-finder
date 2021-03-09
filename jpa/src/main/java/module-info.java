module track.finder.jpa.main {
  exports com.dovendev.track.jpa.entities;
  exports com.dovendev.track.jpa.services;
  exports com.dovendev.track.jpa.repositories;
  requires java.persistence;
  requires java.sql;
  requires org.jetbrains.annotations;
  requires spring.data.jpa;
  requires spring.context;
}