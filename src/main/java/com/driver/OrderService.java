package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public static void addOrder(Order order) {
        OrderRepository.addOrder(order);
    }

    public static void addPartner(String partnerId) {
        OrderRepository.addPartner(partnerId);
    }

    public static void addOrderPartnerPair(String orderId, String partnerId) {
        OrderRepository.addOrderPartnerPair(orderId,partnerId);
    }

    public static Order getOrderById(String orderId) {
         Order order= OrderRepository.getOrderById(orderId);
         return order;
    }

    public static DeliveryPartner getPartnerById(String partnerId) {
         DeliveryPartner partner=OrderRepository.getPartnerById(partnerId);
         return partner;
    }


    public static Integer getOrderCountByPartnerId(String partnerId) {
        return OrderRepository.getOrderCountByPartnerId(partnerId);
    }

    public static List<String> getOrdersByPartnerId(String partnerId) {
        return OrderRepository.getOrdersByPartnerId(partnerId);
    }

    public static List<String> getAllOrders() {
        return OrderRepository.getAllOrders();
    }

    public static Integer getCountOfUnassignedOrders() {
        return OrderRepository.getCountOfUnassignedOrders();
    }


    public static Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        String arr[] = time.split(":");
        int hh = Integer.parseInt(arr[0]);
        int mm = Integer.parseInt(arr[1]);

        int timeInt = (hh*60)+mm;

        int count = OrderRepository.getOrdersLeftAfterGivenTimeByPartnerId(timeInt,partnerId);
        return count;
    }

    public static String getLastDeliveryTimeByPartnerId(String partnerId) {
       int timeInt=OrderRepository.getLastDeliveryTimeByPartnerId(partnerId);
        int hh = timeInt/60;
        int mm = timeInt%60;
        String HH = String.valueOf(hh);
        if(HH.length()==1){
            HH = '0' + HH;
        }
        String MM = String.valueOf(mm);
        if(MM.length()==1){
            MM = '0'+MM;
        }

        String time = HH + ":" + MM;
        return time;
    }

    public static void deletePartnerById(String partnerId) {
        OrderRepository.deletePartnerById(partnerId);
    }


    public static void deleteOrderById(String orderId) {
        OrderRepository.deleteOrderById(orderId);
    }
}
