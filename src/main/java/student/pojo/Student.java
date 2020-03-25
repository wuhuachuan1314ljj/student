package student.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: Student
 * Package: student.pojo
 * Date: 2020/2/9 20:02
 * Author; 吴华川
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private Long id;
    private String sn;//学号
    private Long clazzId;//班级id
    private String username;
    private String password;
    private String sex;
    private String photo;//头像
    private String remark;//备注
}
