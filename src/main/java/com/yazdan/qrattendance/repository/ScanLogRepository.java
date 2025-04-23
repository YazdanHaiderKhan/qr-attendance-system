package com.yazdan.qrattendance.repository;

import com.yazdan.qrattendance.entity.ScanLog;
import com.yazdan.qrattendance.entity.User;
import com.yazdan.qrattendance.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

public interface ScanLogRepository extends JpaRepository<ScanLog, Long> {
    Optional<ScanLog> findByUserAndClassroomAndScanDate(User user, Classroom classroom, LocalDate scanDate);
    List<ScanLog> findByClassroom_Id(Long classroomId);
}
