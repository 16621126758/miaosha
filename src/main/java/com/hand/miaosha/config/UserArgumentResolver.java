package com.hand.miaosha.config;

import com.hand.miaosha.access.UserContext;
import com.hand.miaosha.domain.MiaoshaUser;
import com.hand.miaosha.service.Impl.MiaoshaUserServiceImpl;
import com.hand.miaosha.service.MiaoshaUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @Class: UserArgumentResolver
 * @description: 在我们每次跳转页面的时候，我们都会先获取cookie这样的话会有很多重复的代码，所以用这个包的类来实现，
 * 每次进入controller的参数都需要写传的参数的对对象。
 * @Author: hongzhi.zhao
 * @Date: 2018-11-13 21:12
 */
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private MiaoshaUserService miaoshaUserService;
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        //获取参数的类型
        Class<?> clazz =  methodParameter.getParameterType();
        //如果遇到的类型是MiaoshaUser的就会执行下面的方法
        return clazz == MiaoshaUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
//        HttpServletRequest resquest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
//        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
//        String paramToken = resquest.getParameter(MiaoshaUserServiceImpl.COOKIE_NAME_TOKEN);
//        String cookieToken = getCookieValue(resquest,MiaoshaUserServiceImpl.COOKIE_NAME_TOKEN);
//        if (StringUtils.isEmpty(cookieToken)&&StringUtils.isEmpty(paramToken)){
//            return null;
//        }
//        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
//        return miaoshaUserService.getByToken(response,token);
        return UserContext.getUser();

//        if (StringUtils.isEmpty(cookieToken)&&StringUtils.isEmpty(paramToken)){
//            return "login";
//        }
//        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
//        MiaoshaUser user = userService.getByToken(response,token);
//        System.out.println(cookieToken);

    }
//    private String getCookieValue(HttpServletRequest request,String cookieName){
//        Cookie[] cookies= request.getCookies();
//        if (null == cookies||cookies.length<=0){
//            return null;
//        }
//       for (Cookie cookie:cookies){
//           if (cookie.getName().equals(cookieName)){
//               return cookie.getValue();
//           }
//       }
//       return null;
    //}
}
