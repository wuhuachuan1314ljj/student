package student.service;

import student.pojo.Clazz;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ClazzService
 * Package: student.service
 * Date: 2020/2/13 12:23
 * Author; 吴华川
 */
public interface ClazzService {
    int add(Clazz clazz);

    int edit(Clazz clazz);

    int delete(String ids);

    List<Clazz> findList(Map<String, Object> queryMap);

    List<Clazz> findAll();

    int getTotal(Map<String, Object> queryMap);
}
