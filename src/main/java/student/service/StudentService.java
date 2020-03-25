package student.service;

import org.springframework.stereotype.Service;
import student.pojo.Student;

import java.util.List;
import java.util.Map;

/**
 * ClassName: StudentService
 * Package: student.service
 * Date: 2020/2/9 21:48
 * Author; 吴华川
 */
public interface StudentService {
    public Student findByUserName(String username);

    public int add(Student student);

    public int edit(Student student);

    public int delete(String ids);


    public List<Student> findList(Map<String, Object> queryMap);

    public List<Student> findAll();

    public int getTotal(Map<String, Object> queryMap);
}
