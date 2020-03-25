package student.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import student.interceptor.LoginInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(new LoginInterceptor());
        registration.addPathPatterns("/**");                      //所有路径都被拦截
        registration.excludePathPatterns(                         //添加不拦截路径
                "/system/login",       //登录
                "/system/get_cpacha", //验证码
                "/h-ui/**",      //静态资源(static静态资源的根目录)
                "/easyui/**"       //静态资源

        );
    }

    /**
     * 配置文件上传的虚拟路径
     *
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");//固定写法
//         addResourceHandler里面写页面访问的路径,一般就是图片的所在文件夹加**,addResourceLocations里面图片上传后的路径(路径前加file:)
        //windows下路径写法
//        registry.addResourceHandler("/upload/**").addResourceLocations("file:D:/ideaProject/student/src/main/resources/static/upload/");
        //linux下路径写法
        registry.addResourceHandler("/upload/**").addResourceLocations("file:/www/wwwroot/www.hellocoders.club/upload/");
    }
}
