package com.example.config.sql;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class UserCourseProvider
{
    public String sqlUserCourse(Map<String,String> map)
    {
        SQL sql = new SQL();
        sql.SELECT("*").FROM("user_course");
        sql.WHERE("course_id = "+map.get("courseId"));
        if(map.get("username")!=null&&!map.get("username").equals("")){
            sql.WHERE("username = "+map.get("username"));
        }
        if(map.get("name")!=null&&!map.get("name").equals("")){
            sql.WHERE("username in (select username from user where name like '%"+map.get("name")+"%')");
        }
        if(map.get("classId")!=null&&!map.get("classId").equals("")){
            sql.WHERE("username in (select username from user where class_id = "+map.get("classId")+")");
        }
        if(map.get("school")!=null&&!map.get("school").equals("")){
            sql.WHERE("username in(select username from user where school like '%"+map.get("school")+"%')");
        }
        if(map.get("teamId")!=null&&!map.get("teamId").equals("")){
            sql.WHERE("team_id ="+map.get("teamId"));
        }
        if(map.get("marked").equals("1")){
            sql.WHERE("score is not null");
        }
        if(map.get("order")!=null&&!map.get("order").equals("")){
            String order = map.get("order");
            switch (order) {
                case "1":
                    sql.ORDER_BY("score DESC");
                    break;
                case "2":
                    sql.ORDER_BY("score");
                    break;
                case "3":
                    sql.ORDER_BY("username");
                    break;
            }
        }
        int x = Integer.parseInt(map.get("rows")), y = Integer.parseInt(map.get("page"));
        sql.LIMIT(x);
        sql.OFFSET(x*(y-1));
        return sql.toString();
    }

    public String sqlUserCourseRecords(Map<String,String> map)
    {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("user_course");
        sql.WHERE("course_id = "+map.get("courseId"));
        if(map.get("username")!=null&&!map.get("username").equals("")){
            sql.WHERE("username = "+map.get("username"));
        }
        if(map.get("name")!=null&&!map.get("name").equals("")){
            sql.WHERE("username in (select username from user where name like '%"+map.get("name")+"%')");
        }
        if(map.get("classId")!=null&&!map.get("classId").equals("")){
            sql.WHERE("username in (select username from user where class_id = "+map.get("classId")+")");
        }
        if(map.get("school")!=null&&!map.get("school").equals("")){
            sql.WHERE("username in(select username from user where school like '%"+map.get("school")+"%')");
        }
        if(map.get("teamId")!=null&&!map.get("teamId").equals("")){
            sql.WHERE("team_id ="+map.get("teamId"));
        }
        if(map.get("marked").equals("1")){
            sql.WHERE("score is not null");
        }
        return sql.toString();
    }
}
