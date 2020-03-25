package student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import student.pojo.Student;
import student.pojo.User;
import student.service.impl.StudentServiceImpl;
import student.service.impl.UserServiceImpl;
import student.util.CpachaUtil;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: index
 * Package: student.controller
 * Date: 2020/2/9 17:25
 * Author; 吴华川
 */


@Controller
@RequestMapping("/system")
public class SystemController {
    @Autowired
    public UserServiceImpl userService;
    @Autowired
    private StudentServiceImpl studentService;

    @GetMapping("/index")
    public String index() {

        return "index";
    }

    /**
     * 前往登录界面
     *
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "views/system/login";
    }

    /**
     * 登录界面的数据提交
     *
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public Map<String, String> login01(@RequestParam(value = "username") String username,
                                       @RequestParam(value = "password") String password,
                                       @RequestParam(value = "vcode") String vcode,
                                       @RequestParam(value = "type") int type,
                                       HttpServletRequest request) {
        Map<String, String> ret = new HashMap<>();
        if (StringUtils.isEmpty(username)) {
            ret.put("type", "error");
            ret.put("msg", "用户名不能为空!");
            return ret;
        }
        if (StringUtils.isEmpty(password)) {
            ret.put("type", "error");
            ret.put("msg", "密码不能为空!");
            return ret;
        }
        if (StringUtils.isEmpty(vcode)) {
            ret.put("type", "error");
            ret.put("msg", "验证码不能为空!");
            return ret;
        }
        //获取存在session里面的验证码
        String loginCpacha = (String) request.getSession().getAttribute("loginCpacha");
        if (StringUtils.isEmpty(loginCpacha)) { //时间太长,session里面的验证码会被清除
            ret.put("type", "error");
            ret.put("msg", "长时间未操作，会话已失效，请刷新后重试!");
            return ret;
        }
        if (!vcode.toUpperCase().equals(loginCpacha.toUpperCase())) {
            ret.put("type", "error");
            ret.put("msg", "验证码错误!");
            return ret;
        }
        //删除session里面的验证码(释放内存)
        request.getSession().setAttribute("loginCpacha", null);
        //把密码进行MD5加密后与数据库进行对比(密码的MD5加密可以在前端做)
//        String password = MD5Utils.md5(password01);
        //从数据库中去查找用户
        if (type == 1) {
            //管理员
            User user = userService.findByUserName(username);
            if (user == null) {
                ret.put("type", "error");
                ret.put("msg", "不存在该用户!");
                return ret;
            }
            if (!password.equals(user.getPassword())) {
                ret.put("type", "error");
                ret.put("msg", "密码错误!");
                return ret;
            }
            request.getSession().setAttribute("user", user);
        }
        if (type == 2) {
            //学生
            Student student = studentService.findByUserName(username);
            if (student == null) {
                ret.put("type", "error");
                ret.put("msg", "不存在该学生!");
                return ret;
            }
            if (!password.equals(student.getPassword())) {
                ret.put("type", "error");
                ret.put("msg", "密码错误!");
                return ret;
            }
            request.getSession().setAttribute("user", student);
        }
        request.getSession().setAttribute("userType", type);
        System.out.println(type);
        ret.put("type", "success");
        ret.put("msg", "登录成功!");
        return ret;
    }

    /**
     * 显示验证码
     *
     * @param request
     * @param vl
     * @param w
     * @param h
     * @param response
     */
    @GetMapping("get_cpacha")
    public void getCpacha(HttpServletRequest request,
                          @RequestParam(value = "vl", defaultValue = "4", required = false) Integer vl,
                          @RequestParam(value = "w", defaultValue = "98", required = false) Integer w,
                          @RequestParam(value = "h", defaultValue = "33", required = false) Integer h,
                          HttpServletResponse response) {
        //new一个验证码构造器
        CpachaUtil cpachaUtil = new CpachaUtil(vl, w, h);
        //获取验证码
        String generatorVCode = cpachaUtil.generatorVCode();
        //将验证码存入session(校验前端传回的验证码时需要)
        request.getSession().setAttribute("loginCpacha", generatorVCode);
        BufferedImage generatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVCode, true);
        try {
            ImageIO.write(generatorRotateVCodeImage, "gif", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 注销登录
     *
     * @param request
     * @return
     */
    @GetMapping("/login_out")
    public String loginOut(HttpServletRequest request) {
        //清除session的user信息
        request.getSession().setAttribute("user", null);
        //重定向到login的控制器
        return "redirect:login";
    }

}
