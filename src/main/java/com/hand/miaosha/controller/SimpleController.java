//package com.hand.miaosha.controller;
//
//import com.hand.miaosha.rabbitmq.MQSender;
//import com.hand.miaosha.result.Result;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * @Class: SimpleController
// * @description:
// * @Author: hongzhi.zhao
// * @Date: 2018-11-21 14:46
// */
//@Controller
//public class SimpleController {
//
//    @Autowired
//    private MQSender sender;
//    @RequestMapping("/mq")
//    @ResponseBody
//    public Result<String>  mq(){
//        sender.send("hello.zhaohongzhi");
//        return Result.success("hello, zhaohongzhi");
//    }
//
//    @RequestMapping("/mq/topic")
//    @ResponseBody
//    public Result<String>  mqtopic(){
//        sender.sendtopic("hello.topic");
//        return Result.success("hello, topic");
//    }
//
//    @RequestMapping("/mq/fanout")
//    @ResponseBody
//    public Result<String>  mqfanout(){
//        sender.sendFanout("hello.fanout");
//        return Result.success("hello, fanou");
//    }
//
//    @RequestMapping("/mq/headers")
//    @ResponseBody
//    public Result<String>  mqHeaders(){
//        sender.sendHeaders("hello.fanout");
//        return Result.success("hello, fanou");
//    }
//}
