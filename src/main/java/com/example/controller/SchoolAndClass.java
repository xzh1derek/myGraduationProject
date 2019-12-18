package com.example.controller;

import com.example.domain.Classes;
import com.example.domain.School;
import com.example.mapper.SchoolAndClassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SchoolAndClass
{
    @Autowired
    private SchoolAndClassMapper mapper;

    @GetMapping("school")
    public List<School> showSchool()
    {
        return mapper.querySchool();
    }

    @PutMapping("school")
    public String newSchool(String name)
    {
        mapper.newSchool(name);
        return "0";
    }

    @PostMapping("school")
    public String updateSchool(Integer id,String name)
    {
        mapper.updateSchool(id,name);
        return "0";
    }

    @DeleteMapping("school")
    public String deleteSchool(Integer id)
    {
        mapper.deleteSchool(id);
        return "0";
    }

    @GetMapping("class")
    public List<Classes> showClasses()
    {
        return mapper.queryAllClasses();
    }

    @GetMapping("selectClass")
    public List<Classes> selectClasses(Integer school)
    {
        return mapper.queryClassesBySchool(school);
    }

    @PutMapping("class")
    public String newClass(Integer classId, Integer schoolId)
    {
        mapper.newClass(classId,schoolId);
        return "0";
    }

    @DeleteMapping("class")
    public String deleteClass(Integer classId)
    {
        mapper.deleteClass(classId);
        return "0";
    }

    @GetMapping("schools")
    public List<School> querySchoolWithClasses()
    {
        return mapper.querySchoolWithClasses();
    }
}
