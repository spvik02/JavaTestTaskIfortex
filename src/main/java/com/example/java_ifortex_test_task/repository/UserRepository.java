package com.example.java_ifortex_test_task.repository;

import com.example.java_ifortex_test_task.entity.DeviceType;
import com.example.java_ifortex_test_task.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = """
            SELECT u.*, s.last_mobile_session
            FROM users u
            JOIN (
                SELECT user_id, MAX(started_at_utc) AS last_mobile_session
                FROM sessions
                WHERE device_type = (:deviceType + 1)
                GROUP BY user_id
            ) s ON u.id = s.user_id
            ORDER BY s.last_mobile_session DESC;
            """, nativeQuery = true)
    List<User> getUsersWithAtLeastOneMobileSession(@Param("deviceType") DeviceType deviceType);

    @Query(value = """
            SELECT u.*
            FROM users u
            JOIN sessions s ON u.id = s.user_id
            GROUP BY u.id
            ORDER BY COUNT(s.id) DESC
            LIMIT 1
            """, nativeQuery = true)
    User getUserWithMostSessions();
}
