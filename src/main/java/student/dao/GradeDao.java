package student.dao;

import org.apache.ibatis.annotations.Mapper;
import student.pojo.Grade;

import java.util.List;
import java.util.Map;

/**
 * ClassName: GradeDao
 * Package: student.dao
 * Date: 2020/2/11 9:23
 * Author; 吴华川
 */
@Mapper
public interface GradeDao {
     int add(Grade grade);

     int edit(Grade grade);

     int delete(String ids);

     List<Grade> findList(Map<String, Object> queryMap);

     List<Grade> findAll();

     int getTotal(Map<String, Object> queryMap);
}
