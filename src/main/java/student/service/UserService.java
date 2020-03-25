package student.service;

import student.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * ClassName: UserService
 * Package: student.service
 * Date: 2020/2/9 21:32
 * Author; 吴华川
 */
public interface UserService {
    User findByUserName(String username);

    int add(User user);

    int edit(User user);

    int delete(String ids);

    List<User> findList(Map<String, Object> queryMap);

    int getTotal(Map<String, Object> queryMap);
}
