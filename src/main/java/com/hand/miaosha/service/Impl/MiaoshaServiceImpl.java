package com.hand.miaosha.service.Impl;
import com.hand.miaosha.domain.MiaoshaOrder;
import com.hand.miaosha.domain.MiaoshaUser;
import com.hand.miaosha.domain.OrderInfo;
import com.hand.miaosha.redis.MiaoshaKey;
import com.hand.miaosha.redis.RedisService;
import com.hand.miaosha.service.GoodsService;
import com.hand.miaosha.service.MiaoshaService;
import com.hand.miaosha.service.OrderService;
import com.hand.miaosha.util.MD5Util;
import com.hand.miaosha.util.UUIDUtil;
import com.hand.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

/**
 * @Class: MiaoshaServiceImpl
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-16 09:27
 */
@Service
public class MiaoshaServiceImpl implements MiaoshaService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        //减少库存  下订单  写入秒杀订单
        boolean success = goodsService.reduceStock(goods);
        if (success){
            //生成订单
            return orderService.createOrder(user,goods);
        }else {
           setGoodsOver(goods.getId());
            return null;
        }

    }

    @Override
    public long getMiaoshaRestult(Long userId, long goodsId) {
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(userId,goodsId);
        if (order!=null){
            return order.getOrderId();
        }else {
            boolean isOver = getGoodsOver(goodsId);
            if (isOver){
                return -1;
            }else {
                return 0;
            }
        }
    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(MiaoshaKey.isGoodOver,""+goodsId,true);

    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(MiaoshaKey.isGoodOver,""+goodsId);
    }

    public void reset(List<GoodsVo> goodsList) {
        goodsService.resetStock(goodsList);
        orderService.deleteOrders();
    }

    @Override
    public boolean checkPath(MiaoshaUser user, long goodsId, String path) {
        if (user == null&&path==null){
            return false;
        }
        String pathgood = redisService.get(MiaoshaKey.getMiaoshaPath,""+user.getId()+"-"+goodsId,String.class);
        return pathgood.equals(path);
    }

    @Override
    public String createMiaoshaPath(MiaoshaUser user,long goodsId) {
        if (user == null&&goodsId<=0){
            return null;
        }
        String str = MD5Util.md5(UUIDUtil.uuid()+"123456");
        redisService.set(MiaoshaKey.getMiaoshaPath,""+user.getId()+"-"+goodsId,str);
        return str;
    }

    @Override
    public BufferedImage createMiaoshaVerifyCode(MiaoshaUser user, long goodsId) {
        if (user == null&&goodsId<=0){
            return null;
        }
        int width = 80;
        int height = 32;
        //create the image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // set the background color
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);
        // draw the border
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);
        // create a random instance to generate the codes
        Random rdm = new Random();
        // make some confusion
        for (int i = 0; i < 50; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        // generate a random code
        String verifyCode = generateVerifyCode(rdm);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(verifyCode, 8, 24);
        g.dispose();
        //把验证码存到redis中
        int rnd = calc(verifyCode);
        redisService.set(MiaoshaKey.getMiaoshaVerifyCode, user.getId()+","+goodsId, rnd);
        //输出图片
        return image;
    }

    @Override
    public boolean checkVerifyCode(MiaoshaUser user, long goodsId, int verifyCode) {
        if (user == null&&goodsId<=0){
            return false;
        }
        Integer codeOld = redisService.get(MiaoshaKey.getMiaoshaVerifyCode, user.getId()+","+goodsId, Integer.class);
        if (null==codeOld||codeOld-verifyCode!=0){
            return false;
        }else {
            redisService.delete(MiaoshaKey.getMiaoshaVerifyCode, user.getId()+","+goodsId);
            return true;
        }

    }

    private static int calc(String exp) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer)engine.eval(exp);
        }catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static char[] ops = new char[] {'+', '-', '*'};
    /**
     * + - *
     * */
    private String generateVerifyCode(Random rdm) {
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        char op1 = ops[rdm.nextInt(3)];
        char op2 = ops[rdm.nextInt(3)];
        String exp = ""+ num1 + op1 + num2 + op2 + num3;
        return exp;
    }
}
