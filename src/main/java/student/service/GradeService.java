package student.service;

import student.pojo.Grade;

import java.util.List;
import java.util.Map;

/**
 * ClassName: GradeService
 * Package: student.service
 * Date: 2020/2/11 9:21
 * Author; 吴华川
 */
public interface GradeService {
    int add(Grade grade);

    int edit(Grade grade);

    int delete(String ids);

    List<Grade> findList(Map<String, Object> queryMap);

    List<Grade> findAll();

    int getTotal(Map<String, Object> queryMap);
}
