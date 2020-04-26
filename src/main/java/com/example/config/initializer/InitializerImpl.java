package com.example.config.initializer;
import com.example.domain.Project;
import com.example.mapper.ProjectMapper;
import com.example.mapper.TeamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.util.List;

@Component
public class InitializerImpl implements ApplicationRunner
{
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private ProjectMapper projectMapper;

    /**
     * SpringBoot启动后自动执行脚本
     */
    @Override
    public void run(ApplicationArguments args)
    {
//        Jedis jedis = jedisPool.getResource();
//        List<Project> projects = projectMapper.queryAllProjects();
//        for(Project project : projects){
//            if(!project.getIs_fixed()){
//                Integer courseId=project.getCourse_id(),projectId=project.getId();
//                if(jedis.exists("course"+courseId+"project:"+projectId)) projectMapper.updateIsPublished(projectId,true);
//                else projectMapper.updateIsPublished(projectId,false);
//            }
//        }
//        jedis.close();
    }
}
