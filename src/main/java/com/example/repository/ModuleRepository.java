package com.example.repository;

import com.example.domain.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module,Integer>
{
}
