package student.interceptor;

import net.sf.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录过滤拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public void afterCompletion(HttpServletRequest arg0,
                                HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {


    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
                           Object arg2, ModelAndView arg3) throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object arg2) throws Exception {
        String url = request.getRequestURI();
        //获取session里面的用户信息
        Object user = request.getSession().getAttribute("user");
        if (user == null) {
            //表示未登录或者登录状态失效
            System.out.println("未登录或登录失效，url = " + url);
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                //ajax请求
                Map<String, String> ret = new HashMap<>();
                ret.put("type", "error");
                ret.put("msg", "登录状态已失效，请重新去登录!");
                response.getWriter().write(JSONObject.fromObject(ret).toString());
                return false;
            }
            //拦截后重定向到登录界面
            response.sendRedirect(request.getContextPath() + "/system/login");
            return false;
        }
        return true;
    }

}
