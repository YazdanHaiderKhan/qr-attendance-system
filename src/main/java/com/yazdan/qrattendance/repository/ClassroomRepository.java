package com.yazdan.qrattendance.repository;

import com.yazdan.qrattendance.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
}
