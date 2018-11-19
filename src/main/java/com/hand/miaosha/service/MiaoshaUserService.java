package com.hand.miaosha.service;

import com.hand.miaosha.domain.MiaoshaUser;
import com.hand.miaosha.result.CodeMsg;
import com.hand.miaosha.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;

/**
 * @Class: MiaoshaService
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-13 11:33
 */
public interface MiaoshaUserService {
    String login(HttpServletResponse response,LoginVo loginVo);

    MiaoshaUser getByToken(HttpServletResponse response,String token);
}
