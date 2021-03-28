package com.dovendev.track.jpa;

import com.dovendev.track.jpa.repositories.CustomRepositoryImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan
@EnableJpaRepositories(repositoryBaseClass = CustomRepositoryImpl.class)
public class JpaConfiguration {}
