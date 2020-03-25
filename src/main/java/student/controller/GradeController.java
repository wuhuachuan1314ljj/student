package student.controller;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import student.page.Page;
import student.pojo.Grade;
import student.service.impl.GradeServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: GradeController
 * Package: student.controller
 * Date: 2020/2/11 9:08
 * Author; 吴华川
 */

/**
 * 年级Controller
 */
@RequestMapping("/grade")
@Controller
public class GradeController {
    @Autowired
    private GradeServiceImpl gradeService;

    @GetMapping("/list")
    public String list() {
        return "views/grade/grade_list";
    }

    @PostMapping("/get_list")
    @ResponseBody
    public Map<String, Object> get_list(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                                        Page page) {
        Map<String, Object> ret = new HashMap<>();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("name", "%" + name + "%");
        queryMap.put("offset", page.getOffset());
        queryMap.put("pageSize", page.getRows());
        ret.put("rows", gradeService.findList(queryMap));
        ret.put("total", gradeService.getTotal(queryMap));
        return ret;
    }

    /**
     * 添加年级信息
     *
     * @param grade
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public Map<String, Object> add(Grade grade) {
        Map<String, Object> ret = new HashMap<>();
        if (grade == null) {
            ret.put("type", "error");
            ret.put("msg", "数据绑定出错");
            return ret;
        }
        if (grade.getName() == null) {
            ret.put("type", "error");
            ret.put("msg", "年级名为空");
            return ret;
        }
        if (grade.getRemark() == null) {
            ret.put("type", "error");
            ret.put("msg", "备注为空");
            return ret;
        }

        if (gradeService.add(grade) <= 0) {
            ret.put("type", "error");
            ret.put("msg", "添加失败");
            return ret;

        }
        ret.put("type", "success");
        ret.put("msg", "添加成功");
        return ret;
    }

    /**
     * 修改年级信息
     *
     * @param grade
     * @return
     */
    @PostMapping("/edit")
    @ResponseBody
    public Map<String, Object> edit(Grade grade) {
        Map<String, Object> ret = new HashMap<>();
        if (grade.getName() == null) {
            ret.put("type", "error");
            ret.put("msg", "年级名为空");
            return ret;
        }

        if (gradeService.edit(grade) <= 0) {
            ret.put("type", "error");
            ret.put("msg", "修改失败");
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "修改成功");
        return ret;
    }

    /**
     * 删除年级
     *
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestParam("ids[]") long[] ids) {  //获取前端传过来的数组(json格式)
        Map<String, Object> ret = new HashMap<>();
        if (ids == null || ids.length == 0) {
            ret.put("type", "error");
            ret.put("msg", "请选择要删除的数据!");
            return ret;
        }
        //将数组里的数据放入字符串,因为数据库操作用的是in进行删除
        String allId = "";
        for (int i = 0; i < ids.length; i++) {
            allId = allId + ids[i];
            if (i < ids.length - 1) {
                allId = allId + ",";
            }
        }
        try {  //当删除有班级的年级是会抛异常
            if (gradeService.delete(allId) <= 0) {
                ret.put("type", "error");
                ret.put("msg", "删除失败!");
                return ret;
            }
        } catch (Exception e) {
            ret.put("type", "error");
            ret.put("msg", "删除失败,年级下有班级,不能删除");
            return ret;

        }
        ret.put("type", "success");
        ret.put("msg", "删除成功");
        return ret;
    }

}
