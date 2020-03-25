package student.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import student.pojo.Student;

@Mapper
public interface StudentDao {
    Student findByUserName(String username);

    int add(Student student);

    int edit(Student student);

    int delete(String ids);

    List<Student> findList(Map<String, Object> queryMap);

    List<Student> findAll();

    int getTotal(Map<String, Object> queryMap);
}
