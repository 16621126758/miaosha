package com.hand.miaosha.controller;

import com.hand.miaosha.access.AccessLimit;
import com.hand.miaosha.domain.MiaoshaOrder;
import com.hand.miaosha.domain.MiaoshaUser;
import com.hand.miaosha.rabbitmq.MQSender;
import com.hand.miaosha.rabbitmq.MiaoshaMessage;
import com.hand.miaosha.redis.*;
import com.hand.miaosha.result.CodeMsg;
import com.hand.miaosha.result.Result;
import com.hand.miaosha.service.GoodsService;
import com.hand.miaosha.service.MiaoshaService;
import com.hand.miaosha.service.OrderService;
import com.hand.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Class: MiaoshaController
 * @description:
 * @Author: hongzhi.zhao
 * @Date: 2018-11-15 17:49
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MQSender mqSender;

    private Map<Long,Boolean> localOverMap = new HashMap <Long,Boolean>();
    public static final Logger log = LoggerFactory.getLogger(MiaoshaController.class);

    /**
     * 系统初始化用的
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsVoList = goodsService.listGoodsVo();
        if (null == goodsVoList||goodsVoList.size()==0){
            return ;
        }
//        goodsVoList.stream().forEach((e)-> {
//                redisService.set(GoodsKey.getMiaoshaGoodsStock,""+e.getId(),e.getStockCount());}
//        );
        for (GoodsVo goods:goodsVoList){
            redisService.set(GoodsKey.getMiaoshaGoodsStock,""+goods.getId(),goods.getStockCount());
            localOverMap.put(goods.getId(),false);
        }
    }


    @RequestMapping(value = "/verifyCode",method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> getMiaoshaVerifCode(HttpServletResponse response, MiaoshaUser user, @RequestParam("goodsId") long goodsId){
        if (null==user){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        BufferedImage image = miaoshaService.createMiaoshaVerifyCode(user,goodsId);
        try {
            OutputStream out = response.getOutputStream();
            ImageIO.write(image,"JPEG",out);
            out.flush();
            out.close();
        }catch (IOException e) {
            e.printStackTrace();
            return Result.error(CodeMsg.MIAOSHA_FAIL);
        }
        return null;
    }

    @RequestMapping(value="/reset", method=RequestMethod.GET)
    @ResponseBody
    public Result<Boolean> reset(Model model) {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        for(GoodsVo goods : goodsList) {
            goods.setStockCount(10);
            redisService.set(GoodsKey.getMiaoshaGoodsStock, ""+goods.getId(), 10);
            localOverMap.put(goods.getId(), false);
        }
        redisService.delete(OrderKey.getMiaoshaOrderByUidGid);
        redisService.delete(MiaoshaKey.isGoodOver);
        miaoshaService.reset(goodsList);
        return Result.success(true);
    }

    /**
     * orderId:代表成功
     * -1；库存不足
     * 0  排队中
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/result",method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> miaoshaResult(Model model, MiaoshaUser user, @RequestParam("goodsId") long goodsId){
        model.addAttribute("user",user);
        if (null==user){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long result = miaoshaService.getMiaoshaRestult(user.getId(),goodsId);
        return Result.success(result);
    }

    //get post 有什么区别
    //get 幂等 从服务端获取数据 调用多少次也不会对数据产生影响
    //post 像服务端提交数据
    @RequestMapping(value = "/{path}/do_miaosha",method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> list(Model model, MiaoshaUser user, @RequestParam("goodsId") long goodsId,
                                @PathVariable("path") String path){
        log.info("开始秒杀-------------------");
        model.addAttribute("user",user);
        if (user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //验证path
        boolean check = miaoshaService.checkPath(user,goodsId,path);
        if (!check){
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }
        //内存标记，减少redis访问
        boolean over = localOverMap.get(goodsId);
        if (over){
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //预减库存
        long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock,""+goodsId);
        if (stock < 0){
            localOverMap.put(goodsId,true);
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //判断是否已经秒杀过了
        MiaoshaOrder miaoshaOrder =  orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
        log.info("判断秒杀");
        if (null!=miaoshaOrder){
            return Result.error(CodeMsg.REPEATE_MIAOSHA);

//               model.addAttribute("errmsg",CodeMsg.REPEATE_MIAOSHA);
//               return "miaosha_fail";
        }
        //入队
        MiaoshaMessage mm = new MiaoshaMessage();
        mm.setMiaoshaUser(user);
        mm.setGoodsId(goodsId);
        mqSender.sendMiaoshaMessage(mm);
        return Result.success(0); //排队中

//        //判断库存
   //   GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
//        int stock = goods.getStockCount();
//        if (stock<=0){
//            //model.addAttribute("errmsg",CodeMsg.MIAO_SHA_OVER);
//            return Result.error(CodeMsg.MIAO_SHA_OVER);
//        }
        /*else{
            //判断是否已经秒杀过了
           MiaoshaOrder miaoshaOrder =  orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
           if (null!=miaoshaOrder){
               return Result.error(CodeMsg.REPEATE_MIAOSHA);

//               model.addAttribute("errmsg",CodeMsg.REPEATE_MIAOSHA);
//               return "miaosha_fail";
           }
           //减少库存，下订单，写入秒杀订单
            OrderInfo orderInfo = miaoshaService.miaosha(user,goods);
//            model.addAttribute("orderInfo",orderInfo);
//            System.out.println("orderinfo 的ID 为"+orderInfo.getId());
//            model.addAttribute("goods",goods);
//            return "order_detail";
            return Result.success(orderInfo);
             }*/

    }

    @AccessLimit(seconds = 5,maxCount = 5,needLogin = true)
    @RequestMapping(value = "/getMiaoshPath",method = RequestMethod.GET)
    @ResponseBody
    public Result<String> getMiaoshPath(HttpServletRequest request, MiaoshaUser user,
                                        @RequestParam("goodsId") long goodsId,
                                        @RequestParam(value = "verifyCode",defaultValue = "0")int verifyCode) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //查询访问的次数
        String url = request.getRequestURI();
        String key = url+"_"+user.getId();
        boolean check = miaoshaService.checkVerifyCode(user,goodsId,verifyCode);
        if (!check){
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }
        String path = miaoshaService.createMiaoshaPath(user,goodsId);

        return Result.success(path);
    }

//    public String createMiaoshaPath(MiaoshaUser user,long goodsId){
//        String str = MD5Util.md5(UUIDUtil.uuid()+"123456");
//        redisService.set(MiaoshaKey.getMiaoshaPath,""+user.getId()+"-"+goodsId,str);
//    }

}
