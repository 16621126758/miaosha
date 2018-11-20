package com.hand.miaosha.service.Impl;

import com.hand.miaosha.dao.MiaoshaUserDao;
import com.hand.miaosha.domain.MiaoshaUser;
import com.hand.miaosha.exception.GlobalException;
import com.hand.miaosha.redis.MiaoshaUserKey;
import com.hand.miaosha.redis.RedisService;
import com.hand.miaosha.result.CodeMsg;
import com.hand.miaosha.service.MiaoshaUserService;
import com.hand.miaosha.util.MD5Util;
import com.hand.miaosha.util.UUIDUtil;
import com.hand.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UnknownFormatConversionException;

/**
 * @Class: MiaoshaUserService
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-13 09:38
 */
@Service
public class MiaoshaUserServiceImpl implements MiaoshaUserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    private MiaoshaUserDao miaoshaUserDao;

    @Autowired
    private RedisService redisService;

    public MiaoshaUser getById(long id){
        System.out.println("在service中执行getById方法--------------------------");
        //取缓存
        MiaoshaUser miaoshaUser = redisService.get(MiaoshaUserKey.getById,""+id,MiaoshaUser.class);
        if (miaoshaUser!=null){
            return miaoshaUser;
        }

        //取数据库
        miaoshaUser = miaoshaUserDao.getById(id);
        redisService.set(MiaoshaUserKey.getById,""+id,miaoshaUser);
        return miaoshaUser;
    }

    public boolean updatePassword(String token,long id,String formPass){
        //取user
        MiaoshaUser miaoshauser = getById(id);
        if (null==miaoshauser){
            throw new GlobalException(CodeMsg.NOT_EXIST);
        }
        //更新数据库
        MiaoshaUser toBeUpdate = new MiaoshaUser();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.formPassToDbPass(formPass,miaoshauser.getSalt()));
        miaoshaUserDao.update(toBeUpdate);
        //清理缓存
        redisService.delete(MiaoshaUserKey.getById,""+id);
        miaoshauser.setPassword(toBeUpdate.getPassword());
        redisService.set(MiaoshaUserKey.token,token,miaoshauser);

        return true;



    }

    //public CodeMsg login(LoginVo loginVo) {
    public String  login(HttpServletResponse response,LoginVo loginVo) {
        if (null==loginVo){
            System.out.println("loginvo  wei   null");
            //return CodeMsg.SERVER_ERROR;
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPassword = loginVo.getPassword();
        //判断手机号是否存在
        MiaoshaUser user = getById(Long.parseLong(mobile));
        if (null==user){
           // return CodeMsg.NOT_EXIST;
            throw new GlobalException(CodeMsg.NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String dbSalt = user.getSalt();
        String calcPass = MD5Util.formPassToDbPass(formPassword,dbSalt );
        if (!calcPass.equals(dbPass)){
           // return CodeMsg.PASSWORD_ERROR;
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //return CodeMsg.SUCCESS;
        //生成cookie
        String token=UUIDUtil.uuid();
        addCookie(user, token,response);
        return token;


    }

    @Override
    public MiaoshaUser getByToken(HttpServletResponse response,String token) {
        if (StringUtils.isEmpty(token)){
            return null;
        }else {
          MiaoshaUser user =   redisService.get(MiaoshaUserKey.token,token,MiaoshaUser.class);
          //延长有效期
            if (null!=user) {
                addCookie(user, token,response);
            }
            return user;
        }
    }


    private void addCookie(MiaoshaUser user,String token,HttpServletResponse response){
        redisService.set(MiaoshaUserKey.token,token,user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN,token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);

    }

}
