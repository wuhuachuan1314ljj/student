package student.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: User
 * Package: student
 * Date: 2020/2/9 17:58
 * Author; 吴华川
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;//用户id，主键、自增
    private String username;//用户名
    private String password;//密码
}
