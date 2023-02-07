package com.loggingwithaspect.aspect.repo;

import com.loggingwithaspect.aspect.model.CustomLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomLogRepository extends JpaRepository<CustomLog, Long> {
}
