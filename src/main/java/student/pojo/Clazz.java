package student.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: Clazz
 * Package: student.pojo
 * Date: 2020/2/9 20:04
 * Author; 吴华川
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clazz {
    private Long id;
    private Long gradeId;//年级id
    private String name;
    private String remark;//备注
}
