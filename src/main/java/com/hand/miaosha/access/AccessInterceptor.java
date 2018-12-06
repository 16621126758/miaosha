package com.hand.miaosha.access;

import com.alibaba.fastjson.JSON;
import com.hand.miaosha.domain.MiaoshaUser;
import com.hand.miaosha.redis.AccessKey;
import com.hand.miaosha.redis.RedisService;
import com.hand.miaosha.result.CodeMsg;
import com.hand.miaosha.result.Result;
import com.hand.miaosha.service.Impl.MiaoshaUserServiceImpl;
import com.hand.miaosha.service.MiaoshaUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @Class: AccessInterceptor
 * @description: 拦截器
 * @Author: hongzhi.zhao
 * @Date: 2018-11-26 10:19
 */
@Component
public class AccessInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private MiaoshaUserService miaoshaUserService;

    @Autowired
    private RedisService redisService;


    @Override
    //方法执行前做一个拦截
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod){
            MiaoshaUser miaoshaUser = getUser(request,response);
            UserContext.setUser(miaoshaUser);

            HandlerMethod hm = (HandlerMethod) handler;
            //拿到注解
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            //如果没有获取到注解，那么就和森么也不做
            if (null == accessLimit){
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount =  accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();
            String key = request.getRequestURI();
            if(needLogin){
                if (miaoshaUser == null){
                    render(response,CodeMsg.SESSION_ERROR);
                    return false;
                }
                key+="_"+miaoshaUser.getId();
            }else {
                //do nothing
            }
             Integer count = redisService.get(AccessKey.access,key,Integer.class);
            AccessKey ak = AccessKey.withExpire(seconds);
            if (null==redisService.get(ak,key,Integer.class)){
                redisService.set(ak,key,1);
            }else if ( count<maxCount){
                redisService.incr(ak,key);
            }else{
                render(response,CodeMsg.ACCESS_LIMIT_REACHED);
                return false;
            }

        }
        return super.preHandle(request, response, handler);
    }

    /**
     *
     * @param response
     * @param
     */
    private void render(HttpServletResponse response, CodeMsg cm) throws Exception{
        response.setContentType("application/json;charset=UTF-8");
        OutputStream outPutStream = response.getOutputStream();
        String json = JSON.toJSONString(Result.error(cm));
        outPutStream.write(json.getBytes("UTF-8"));
        outPutStream.flush();
        outPutStream.close();
    }

    private MiaoshaUser getUser(HttpServletRequest resquest, HttpServletResponse response){
        String paramToken = resquest.getParameter(MiaoshaUserServiceImpl.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(resquest,MiaoshaUserServiceImpl.COOKIE_NAME_TOKEN);
        if (StringUtils.isEmpty(cookieToken)&&StringUtils.isEmpty(paramToken)){
            return null;
        }
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        return miaoshaUserService.getByToken(response,token);
    }

    private String getCookieValue(HttpServletRequest request,String cookieName){
        Cookie[] cookies= request.getCookies();
        if (null == cookies||cookies.length<=0){
            return null;
        }
        for (Cookie cookie:cookies){
            if (cookie.getName().equals(cookieName)){
                return cookie.getValue();
            }
        }
        return null;
    }

}
