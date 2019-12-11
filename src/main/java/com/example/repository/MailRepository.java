package com.example.repository;

import com.example.domain.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailRepository extends JpaRepository<Mail,Integer>
{

}
