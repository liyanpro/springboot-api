package cloud.liyan.springbootapi.interceptor;

import cloud.liyan.springbootapi.annotation.LoginCheck;
import cloud.liyan.springbootapi.util.TokenUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author liyan
 * @description
 * @date 2020-05-19 17:26
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return false;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        LoginCheck loginCheck = handlerMethod.getMethodAnnotation(LoginCheck.class);
        if (loginCheck == null) {
            Class clazz = handlerMethod.getMethod().getDeclaringClass();
            loginCheck = (LoginCheck) clazz.getAnnotation(LoginCheck.class);
        }
        if (loginCheck != null && loginCheck.needLogin()) {
            String token = request.getHeader("Authorization");
            if (StringUtils.isEmpty(token)) {
                throw new RuntimeException("没有token，请重新登录");
            }
            Map<String, Object> claims = TokenUtil.validateTokenAndGetClaims(token);
            if (claims == null) {
                throw new RuntimeException("token已过期，请重新登录");
            }
            String loginName = claims.get("loginName").toString();
            //检查loginName是否存在
            if (!"liyan".equals(loginName)) {
                throw new RuntimeException("用户不存在，请检查后重新登录");
            }
            System.out.println(JSON.toJSONString(claims));
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
    }
}
