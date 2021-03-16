module track.finder.persistence {
  requires java.persistence;
  requires java.sql;
  requires org.jetbrains.annotations;
  requires spring.data.jpa;
  requires spring.context;

  exports com.dovendev.track.jpa.entities;
  exports com.dovendev.track.jpa.services;

}