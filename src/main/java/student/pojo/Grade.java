package student.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: Grade
 * Package: student.pojo
 * Date: 2020/2/9 20:03
 * Author; 吴华川
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grade {
    private Long id;
    private String name;
    private String remark;//备注
}
