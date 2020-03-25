package student.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import student.pojo.User;

@Mapper
public interface UserDao {

    User findByUserName(String username);

    int add(User user);

    int edit(User user);

    int delete(String ids);

    List<User> findList(Map<String, Object> queryMap);

    int getTotal(Map<String, Object> queryMap);
}
