package com.dovendev.track.jpa.repositories;

import com.dovendev.track.jpa.entities.Activity;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ActivityRepository extends CustomRepository<Activity, Long>,
    QuerydslPredicateExecutor<Activity> {

}
