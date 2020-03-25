package student.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import student.pojo.Clazz;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ClazzDao
 * Package: student.dao
 * Date: 2020/2/13 12:21
 * Author; 吴华川
 */
@Mapper
public interface ClazzDao {
    int add(Clazz clazz);

    int edit(Clazz clazz);

    int delete(String ids);

    List<Clazz> findList(Map<String, Object> queryMap);

    List<Clazz> findAll();

    int getTotal(Map<String, Object> queryMap);
}
