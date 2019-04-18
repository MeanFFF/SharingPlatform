package com.platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.platform.common.ServerResponse;
import com.platform.dao.OrderItemMapper;
import com.platform.dao.OrderMapper;
import com.platform.pojo.Order;
import com.platform.pojo.OrderItem;
import com.platform.service.IOrderService;
import com.platform.vo.OrderItemVo;
import com.platform.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    /**
     *  管理员获取订单列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ServerResponse<PageInfo> ManageList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectList();
        List<OrderVo> orderListVoList = assembleOrderVoList(orderList);
        PageInfo pageResult = new PageInfo(orderList);
        pageResult.setList(orderListVoList);
        return ServerResponse.createBySuccess(pageResult);
    }

    /**
     *  获取订单详情页
     * @param orderNo
     * @return
     */
    public ServerResponse OrderDetail(Integer orderNo){
        if(orderNo == null){
            return ServerResponse.createByErrorMessage("订单号为空");
        }
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order == null){
            return ServerResponse.createByErrorMessage("没有此订单");
        }
        List<OrderItem> orderItemList = orderItemMapper.getByOrderNo(order.getOrderNo());
        OrderVo orderVo = assembleOrderVo(order, orderItemList);
        return ServerResponse.createBySuccess(orderVo);
    }

    /**
     * 包装OrderVoList
     *
     * @param orderList
     * @return
     */
    private List<OrderVo> assembleOrderVoList(List<Order> orderList){
        List<OrderVo> orderVoList = Lists.newArrayList();
        for(Order order:orderList){
            List<OrderItem> orderItemList = orderItemMapper.getByOrderNo(order.getOrderNo());
            OrderVo orderVo = assembleOrderVo(order, orderItemList);
            orderVoList.add(orderVo);
        }
        return orderVoList;
    }

    /**
     * 包装Order
     *
     * @param order
     * @param orderItemList
     * @return
     */
    private OrderVo assembleOrderVo(Order order, List<OrderItem> orderItemList) {
        OrderVo orderVo = new OrderVo();
        orderVo.setUserId(order.getUserId());
        orderVo.setOrderNo(order.getOrderNo());
        orderVo.setPayment(order.getPayment());
        orderVo.setPaymentType(order.getPaymentType());
        orderVo.setStatus(order.getStatus());
        orderVo.setEndTime(order.getEndTime());
        orderVo.setCreateTime(order.getCreateTime());

        List<OrderItemVo> orderItemVoList = Lists.newArrayList();

        for(OrderItem orderItem : orderItemList){
            OrderItemVo orderItemVo = assembleOrderItemVo(orderItem);
            orderItemVoList.add(orderItemVo);
        }

        orderVo.setOrderItemVoList(orderItemVoList);

        return orderVo;
    }

    /**
     * 包装OrderItem
     *
     * @param orderItem
     * @return
     */
    private OrderItemVo assembleOrderItemVo(OrderItem orderItem){
        OrderItemVo orderItemVo = new OrderItemVo();
        orderItemVo.setOrderNo(orderItem.getOrderNo());
        orderItemVo.setFileId(orderItem.getFileId());
        orderItemVo.setFileName(orderItem.getFileName());
        orderItemVo.setPrice(orderItem.getPrice());
        orderItemVo.setCreateTime(orderItem.getCreateTime());
        return orderItemVo;
    }
}
