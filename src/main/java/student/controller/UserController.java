package student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import student.page.Page;
import student.pojo.User;
import student.service.impl.UserServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: UserController
 * Package: student.controller
 * Date: 2020/2/10 9:16
 * Author; 吴华川
 */

/**
 * 管理员操作Controller
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    public UserServiceImpl userService;

    /**
     * 用户管理列表
     *
     * @return
     */
    @GetMapping("/list")
    public String list() {
        return "views/user/user_list";
    }

    /**
     * 获取用户列表
     *
     * @param username
     * @param page
     * @return
     */
    @PostMapping("/get_list")
    @ResponseBody
    public Map<String, Object> getList(
            @RequestParam(value = "username", required = false, defaultValue = "") String username,
            Page page
    ) {
        Map<String, Object> ret = new HashMap<>();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("username", "%" + username + "%");
        queryMap.put("offset", page.getOffset());
        queryMap.put("pageSize", page.getRows());
        ret.put("rows", userService.findList(queryMap));
        ret.put("total", userService.getTotal(queryMap));
        return ret;
    }

    /**
     * 添加用户(管理员)
     *
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public Map<String, Object> add(User user) {
        Map<String, Object> ret = new HashMap<>();
        if (user == null) {
            ret.put("type", "error");
            ret.put("msg", "数据绑定出错");
            return ret;
        }
        if (user.getUsername() == null) {
            ret.put("type", "error");
            ret.put("msg", "用户名为空");
            return ret;
        }
        if (user.getPassword() == null) {
            ret.put("type", "error");
            ret.put("msg", "密码为空");
            return ret;
        }
        if (userService.findByUserName(user.getUsername()) != null) {
            ret.put("type", "error");
            ret.put("msg", "该用户名已经存在!");
            return ret;
        }
        if (userService.add(user) <= 0) {
            ret.put("type", "error");
            ret.put("msg", "添加失败");
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "添加成功!");
        return ret;
    }

    /**
     * 删除用户
     *
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
            if (ids[i] == 1) {
                ret.put("type", "error");
                ret.put("msg", "删除失败,请不要删除admin");
                return ret;
            }
            allId = allId + ids[i];
            if (i < ids.length - 1) {
                allId = allId + ",";
            }
        }
        if (userService.delete(allId) <= 0) {
            ret.put("type", "error");
            ret.put("msg", "删除失败");
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "删除成功!");
        return ret;
    }

}
