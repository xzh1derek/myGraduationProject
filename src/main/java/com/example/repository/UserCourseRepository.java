package com.example.repository;

import com.example.domain.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCourseRepository extends JpaRepository<UserCourse,Integer>
{
}
