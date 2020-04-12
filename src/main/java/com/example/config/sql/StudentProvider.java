package com.example.config.sql;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class StudentProvider
{
    /**
     * 学生筛选器
     * @param map 筛选条件
     * @return sql语句
     */
    public String sqlStudent(Map<String,String> map)
    {
        SQL sql = new SQL();
        sql.SELECT("*").FROM("user");
        if (map.get("username") != null && !map.get("username").equals("")) {
            sql.WHERE(" username = " + map.get("username"));
        }
        if (map.get("name") != null && !map.get("name").equals("")) {
            String name = "'%" + map.get("name") + "%'";
            sql.WHERE(" name like " + name);
        }
        if (map.get("class") != null && !map.get("class").equals("")) {
            sql.WHERE(" class_id =" + map.get("class"));
        }
        if (map.get("school") != null && !map.get("school").equals("")) {
            String school = "'%" + map.get("school") + "%'";
            sql.WHERE(" school like" + school);
        }
        if (map.get("year") != null && !map.get("year").equals("")) {
            String year = "'%" + map.get("year") + "%'";
            sql.WHERE(" year like" + year);
        }
        Integer x = Integer.parseInt(map.get("rows")), y = Integer.parseInt(map.get("page"));
        sql.LIMIT(x);
        sql.OFFSET(x*(y-1));
        return sql.toString();
    }

    /**
     * 筛选记录数
     * @param map 筛选条件
     * @return sql语句
     */
    public String sqlStudentRecords(Map<String,String> map)
    {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("user");
        if (map.get("username") != null && !map.get("username").equals("")) {
            sql.WHERE(" username = " + map.get("username"));
        }
        if (map.get("name") != null && !map.get("name").equals("")) {
            String name = "'%" + map.get("name") + "%'";
            sql.WHERE(" name like " + name);
        }
        if (map.get("class") != null && !map.get("class").equals("")) {
            sql.WHERE(" class_id =" + map.get("class"));
        }
        if (map.get("school") != null && !map.get("school").equals("")) {
            String school = "'%" + map.get("school") + "%'";
            sql.WHERE(" school like" + school);
        }
        if (map.get("year") != null && !map.get("year").equals("")) {
            String year = "'%" + map.get("year") + "%'";
            sql.WHERE(" year like" + year);
        }
        return sql.toString();
    }
}
