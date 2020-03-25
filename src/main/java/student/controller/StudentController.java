package student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import student.page.Page;
import student.pojo.Clazz;
import student.pojo.Student;
import student.service.impl.ClazzServiceImpl;
import student.service.impl.StudentServiceImpl;
import student.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: StudentController
 * Package: student.controller
 * Date: 2020/2/13 21:46
 * Author; 吴华川
 */
@RequestMapping("/student")
@Controller
public class StudentController {
    @Autowired
    private StudentServiceImpl studentService;
    @Autowired
    private ClazzServiceImpl clazzService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Clazz> all = clazzService.findAll();
        model.addAttribute("clazzList", all);
        return "views/student/student_list";
    }

    /**
     * 获取学生列表
     *
     * @param name
     * @param page
     * @return
     */
    @PostMapping("/get_list")
    @ResponseBody
    public Map<String, Object> getList(
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "clazzId", required = false) Long clazzId,
            HttpServletRequest request,
            Page page
    ) {
        Map<String, Object> ret = new HashMap<>();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("username", "%" + name + "%");
        Object attribute = request.getSession().getAttribute("userType");
        if ("2".equals(attribute.toString())) {
            //说明是学生,就查询该学生的信息(直接覆盖前面的username即可)
            Student loginedStudent = (Student) request.getSession().getAttribute("user");
            queryMap.put("username", "%" + loginedStudent.getUsername() + "%");
        }
        if (clazzId != null) {//不用判断也可以,sql里面的动态语句也会判断
            queryMap.put("clazzId", clazzId);
        }
        queryMap.put("offset", page.getOffset());
        queryMap.put("pageSize", page.getRows());
        ret.put("rows", studentService.findList(queryMap));
        ret.put("total", studentService.getTotal(queryMap));
        return ret;
    }

    /**
     * 添加学生信息
     *
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    public Map<String, Object> add(Student student) {
        Map<String, Object> ret = new HashMap<>();
        if (StringUtils.isEmpty(student.getUsername())) {
            ret.put("type", "error");
            ret.put("msg", "学生姓名不能为空！");
            return ret;
        }
        if (StringUtils.isEmpty(student.getPassword())) {
            ret.put("type", "error");
            ret.put("msg", "学生登录密码不能为空！");
            return ret;
        }
        if (student.getClazzId() == null) {
            ret.put("type", "error");
            ret.put("msg", "请选择所属班级！");
            return ret;
        }
        if (studentService.findByUserName(student.getUsername()) != null) {
            ret.put("type", "error");
            ret.put("msg", "该姓名已存在！");
            return ret;
        }
        //添加学生的时候,设置学号
        student.setSn(StringUtil.generateSn("S", ""));
        if (studentService.add(student) <= 0) {
            ret.put("type", "error");
            ret.put("msg", "学生添加失败！");
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "学生添加成功！");
        return ret;
    }

    /**
     * 修改学生信息
     *
     * @param student
     * @return
     */
    @PostMapping("/edit")
    @ResponseBody
    public Map<String, Object> edit(Student student) {
        Map<String, Object> ret = new HashMap<>();
        if (StringUtils.isEmpty(student.getUsername())) {
            ret.put("type", "error");
            ret.put("msg", "学生姓名不能为空！");
            return ret;
        }
        if (StringUtils.isEmpty(student.getPassword())) {
            ret.put("type", "error");
            ret.put("msg", "学生登录密码不能为空！");
            return ret;
        }
        if (student.getClazzId() == null) {
            ret.put("type", "error");
            ret.put("msg", "请选择所属班级！");
            return ret;
        }

        if (studentService.findByUserName(student.getUsername()) != null && studentService.findByUserName(student.getUsername()).getId() != student.getId()) {
            ret.put("type", "error");
            ret.put("msg", "该姓名已存在！");
            return ret;
        }
        if (studentService.edit(student) <= 0) {
            ret.put("type", "error");
            ret.put("msg", "学生修改失败！");
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "学生修改成功！");
        return ret;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestParam("ids[]") long[] ids) {
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
            if (studentService.delete(allId) <= 0) {
                ret.put("type", "error");
                ret.put("msg", "删除失败");
                return ret;
            }
        } catch (Exception e) {
            ret.put("type", "error");
            ret.put("msg", "学生下有其他信息,不能删除");
            return ret;

        }
        ret.put("type", "success");
        ret.put("msg", "删除成功");
        return ret;
    }

    /**
     * 上传用户头像图片
     *
     * @param photo
     * @return
     * @throws IOException
     */
    @PostMapping("/upload_photo")
    @ResponseBody
    public Map<String, String> uploadPhoto(MultipartFile photo
    ) throws Exception {
        Map<String, String> ret = new HashMap<>();
        System.out.println(photo);
        if (photo == null) {
            //文件没有选择
            ret.put("type", "error");
            ret.put("msg", "请选择文件！");
            return ret;
        }
        if (photo.getSize() > 10485760) {
            //文件太大
            ret.put("type", "error");
            ret.put("msg", "文件大小超过10M，请上传小于10M的图片！");
            return ret;
        }
        //获取文件的后缀(类似.jpg)
        String suffix = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf("."));
        System.out.println(suffix.toLowerCase());
        if (!".jpg .png .gif .jpeg".contains(suffix.toLowerCase())) {
            ret.put("type", "error");
            ret.put("msg", "文件格式不正确，请上传jpg,png,gif,jpeg格式的图片！");
            return ret;
        }
        //文件在磁盘上的存储路径
        //windows下路径的写法
//        String savePath = "D:\\ideaProject\\student\\src\\main\\resources\\static\\upload\\";
        //linux下路径写法
        String savePath = "/www/wwwroot/www.hellocoders.club/upload/";

        //进入指定文件夹
        File savePathFile = new File(savePath);
        if (!savePathFile.exists()) {
            savePathFile.mkdir();//如果不存在，则创建一个文件夹upload
        }
        String filename = new Date().getTime() + suffix;
        //将数据放到指定文件夹
        photo.transferTo(new File(savePath + filename));
        ret.put("type", "success");
        ret.put("msg", "图片上传成功！");
        //将图片路径返回,方便前台预览图片(一般是项目里面文件在的文件夹名字加文件名)
        ret.put("src", "/upload/" + filename);
        return ret;
    }


}
