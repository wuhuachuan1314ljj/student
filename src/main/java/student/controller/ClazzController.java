package student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import student.page.Page;
import student.pojo.Clazz;
import student.pojo.Grade;
import student.service.impl.ClazzServiceImpl;
import student.service.impl.GradeServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: ClazzController
 * Package: student.controller
 * Date: 2020/2/13 12:28
 * Author; 吴华川
 */
@RequestMapping("/clazz")
@Controller
public class ClazzController {
    @Autowired
    private GradeServiceImpl gradeService;
    @Autowired
    private ClazzServiceImpl clazzService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Grade> all = gradeService.findAll();
        model.addAttribute("gradeList", all);
        return "views/clazz/clazz_list";

    }

    /**
     * 班级列表页
     *
     * @param name
     * @param gradeId
     * @param page
     * @return
     */
    @PostMapping("/get_list")
    @ResponseBody
    public Map<String, Object> getList(
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "gradeId", required = false) Long gradeId,
            Page page
    ) {
        Map<String, Object> ret = new HashMap<>();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("name", "%" + name + "%");
        if (gradeId != null) {
            queryMap.put("gradeId", gradeId);
        }
        queryMap.put("offset", page.getOffset());
        queryMap.put("pageSize", page.getRows());
        ret.put("rows", clazzService.findList(queryMap));
        ret.put("total", clazzService.getTotal(queryMap));
        return ret;
    }

    /**
     * 班级添加
     *
     * @param clazz
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public Map<String, Object> add(Clazz clazz) {
        Map<String, Object> ret = new HashMap<>();
        if (clazz.getGradeId() == null) {
            ret.put("type", "error");
            ret.put("msg", "年级名为空");
            return ret;
        }
        if (clazz.getName() == null) {
            ret.put("type", "error");
            ret.put("msg", "班级名为空");
            return ret;
        }
        if (clazzService.add(clazz) <= 0) {
            ret.put("type", "error");
            ret.put("msg", "添加失败");
            return ret;
        }

        ret.put("type", "success");
        ret.put("msg", "添加成功");
        return ret;
    }

    @PostMapping("/edit")
    @ResponseBody
    public Map<String, Object> edit(Clazz clazz) {
        Map<String, Object> ret = new HashMap<>();
        if (clazz.getGradeId() == null) {
            ret.put("type", "error");
            ret.put("msg", "年级名为空");
            return ret;
        }
        if (clazz.getName() == null) {
            ret.put("type", "error");
            ret.put("msg", "班级名为空");
            return ret;
        }
        if (clazzService.edit(clazz) <= 0) {
            ret.put("type", "error");
            ret.put("msg", "修改失败");
            return ret;
        }

        ret.put("type", "success");
        ret.put("msg", "修改成功");
        return ret;
    }

    /**
     * 删除班级信息
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
            ret.put("msg", "删除失败,请选择数据");
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
        try {
            if (clazzService.delete(allId) <= 0) {
                ret.put("type", "error");
                ret.put("msg", "删除失败");
                return ret;
            }
        } catch (Exception e) {
            ret.put("type", "error");
            ret.put("msg", "班级下有学生信息,不能删除");
            return ret;

        }
        ret.put("type", "success");
        ret.put("msg", "删除成功");
        return ret;
    }
}
