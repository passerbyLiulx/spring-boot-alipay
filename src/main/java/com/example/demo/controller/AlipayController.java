package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.example.demo.service.AlipayService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AlipayController {

    private Logger logger = LoggerFactory.getLogger(AlipayController.class);

    @Autowired
    private AlipayService alipayService;

    /**
     * 统一收单交易支付接口
     * 收银员使用扫码设备读取用户手机支付宝“付款码”/声波获取设备（如麦克风）读取用户手机支付宝的声波信息后，
     * 将二维码或条码信息/声波信息通过本接口上送至支付宝发起支付。
     * @param outTradeNo 订单编号（唯一）
     * @return
     * @throws Exception
     */
    @GetMapping("pay")
    @ApiOperation(value = "统一收单交易支付接口", notes = "统一收单交易支付接口")
    public String pay(@RequestParam("outTradeNo") String outTradeNo, HttpServletRequest request) throws Exception {
        // 模仿数据库，从后台调数据
        // String outTradeNo = "19960310621211";
        Integer totalAmount = 1;
        String subject = "苹果28";

        String result = alipayService.pay(outTradeNo, totalAmount, subject);
        request.setAttribute("result", result);
        return "/result";
    }

    /**
     * 手机网站支付接口2.0
     * 外部商户创建订单并支付
     * @param outTradeNo 订单编号（唯一）
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("wapPay")
    @ApiOperation(value = "手机网站支付接口2.0", notes = "手机网站支付接口2.0")
    public String wapPay(@RequestParam("outTradeNo") String outTradeNo, HttpServletRequest request) throws Exception {
        // 模仿数据库，从后台调数据
        // String outTradeNo = "19960310621214";
        Integer totalAmount = 5;
        String subject = "苹果28";

        String submitFormData = alipayService.wapPay(outTradeNo, totalAmount, subject);
        logger.info(submitFormData);
        request.setAttribute("submitFormData", submitFormData);
        return "/submitFormData";
    }

    /**
     * app支付接口2.0
     * 外部商户APP唤起快捷SDK创建订单并支付
     * @param outTradeNo 订单编号（唯一）
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("appPay")
    @ApiOperation(value = "app支付接口2.0", notes = "app支付接口2.0")
    public String appPay(@RequestParam("outTradeNo") String outTradeNo, HttpServletRequest request) throws Exception {
        // 模仿数据库，从后台调数据
        // String outTradeNo = "19960310621214";
        Integer totalAmount = 5;
        String subject = "苹果28";

        String submitFormData = alipayService.appPay(outTradeNo, totalAmount, subject);
        logger.info(submitFormData);
        request.setAttribute("submitFormData", submitFormData);
        return "/submitFormData";
    }

    /**
     * 统一收单线下交易查询
     */
    @PostMapping("aipayQuery")
    @ResponseBody
    @ApiOperation(value = "统一收单线下交易查询", notes = "统一收单线下交易查询")
    public JSONObject alipayQuery(@RequestParam("outTradeNo") String outTradeNo) throws Exception{
        // String outTradeNo = "13123";

        String query = alipayService.query(outTradeNo);
        JSONObject jsonObject = JSONObject.parseObject(query);
        return jsonObject;
    }

    /**
     * 统一收单交易退款接口
     * @throws AlipayApiException
     */
    @GetMapping("alipayRefund")
    @ResponseBody
    @ApiOperation(value = "统一收单交易退款接口", notes = "统一收单交易退款接口")
    public JSONObject alipayRefund(@RequestParam("outTradeNo") String outTradeNo,
            @RequestParam(value = "outRequestNo", required = false) String outRequestNo,
            @RequestParam(value = "refundAmount", required = false) Integer refundAmount) throws AlipayApiException {
        // String outTradeNo = "15382028806591197";
        String refundReason = "用户不想购买";
        // refundAmount = 1;
        // outRequestNo = "22";

        String refund = alipayService.refund(outTradeNo, refundReason, refundAmount, outRequestNo);
        JSONObject jsonObject = JSONObject.parseObject(refund);
        return jsonObject;
    }

    /**
     * 统一收单交易退款查询
     * @throws AlipayApiException
     */
    @PostMapping("refundQuery")
    @ResponseBody
    @ApiOperation(value = "统一收单交易退款查询", notes = "统一收单交易退款查询")
    public JSONObject refundQuery(@RequestParam("outTradeNo") String outTradeNo,
                                  @RequestParam(value = "outRequestNo", required = false) String outRequestNo,
                                  HttpServletRequest request) throws AlipayApiException{
        // String outTradeNo = "13123";
        //String outRequestNo = "2";

        String refundQuery = alipayService.refundQuery(outTradeNo, outRequestNo);
        JSONObject jsonObject = JSONObject.parseObject(refundQuery);
        return jsonObject;
    }

    /**
     * 统一收单交易关闭接口
     * @throws AlipayApiException
     */
    @PostMapping("alipayclose")
    @ResponseBody
    @ApiOperation(value = "统一收单交易关闭接口", notes = "统一收单交易关闭接口")
    public JSONObject alipaycolse(@RequestParam("outTradeNo") String outTradeNo, HttpServletRequest request) throws AlipayApiException {
        // String outTradeNo = "13123";

        String close = alipayService.close(outTradeNo);
        JSONObject jsonObject = JSONObject.parseObject(close);
        return jsonObject;
    }
}
