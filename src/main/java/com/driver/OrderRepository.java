package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {

    static Map<String,Order> orderdb=new HashMap<>();
    static Map<String,DeliveryPartner> partnerdb=new HashMap<>();
    static Map<String, List<String>> pairdb=new HashMap<>();
    static HashSet<String> isOrderAssigned = new HashSet<>();
    public static void addOrder(Order order) {
         orderdb.put(order.getId(), order);
        isOrderAssigned.add(order.getId());
    }

    public static void addPartner(String partnerId) {
        DeliveryPartner deliveryPartner=new DeliveryPartner(partnerId);
        partnerdb.put(partnerId,deliveryPartner);
    }

    public static void addOrderPartnerPair(String orderId, String partnerId) {
        List<String>ans=pairdb.getOrDefault(partnerId,new ArrayList<>());
        ans.add(orderId);

        DeliveryPartner partner = partnerdb.get(partnerId);
        int noOfOrders = partner.getNumberOfOrders();
        partner.setNumberOfOrders(noOfOrders+1);

        pairdb.put(partnerId,ans);
        isOrderAssigned.remove(orderId);
    }

    public static Order getOrderById(String orderId) {
        Order order=orderdb.get(orderId);
        return order;
    }

    public static DeliveryPartner getPartnerById(String partnerId) {
        return partnerdb.get(partnerId);
    }

    public static Integer getOrderCountByPartnerId(String partnerId) {
        List<String>ans=pairdb.getOrDefault(partnerId,new ArrayList<>());
        return ans.size();
    }

    public static List<String> getOrdersByPartnerId(String partnerId) {
        List<String>ans=pairdb.getOrDefault(partnerId,new ArrayList<>());
        return ans;
    }

    public static List<String> getAllOrders() {
        List<String>list=new ArrayList<>();

        for(String id:orderdb.keySet()){
            list.add(id);
        }
        return list;
    }

    public static Integer getCountOfUnassignedOrders() {
//        int sum=0;
//        for(String str:pairdb.keySet()) {
//            List<String>list=pairdb.get(str);
//            sum+=list.size();
//        }
//        int ansum=orderdb.size();
//
//        return sum-ansum;

        return isOrderAssigned.size();
        }

    public static int getOrdersLeftAfterGivenTimeByPartnerId(int time, String partnerId) {
        List<String>list=pairdb.getOrDefault(partnerId,new ArrayList<>());
        int count=0;

        for(String id:list){
            Order order=orderdb.get(id);
            int t=order.getDeliveryTime();
            if(t>time){
                count++;
            }

        }
        return count;
    }


    public static int getLastDeliveryTimeByPartnerId(String partnerId) {
        List<String>list=pairdb.getOrDefault(partnerId,new ArrayList<>());
        int time=0;

        for(String id:list){
            Order order=orderdb.get(id);
            if(order.getDeliveryTime()>time){
                time=order.getDeliveryTime();

            }

        }
        return time;
    }

    public static void deletePartnerById(String partnerId) {
        if(!pairdb.isEmpty()){
            isOrderAssigned.addAll(pairdb.get(partnerId));
        }
        pairdb.remove(partnerId);
        partnerdb.remove(partnerId);

    }


    public static void deleteOrderById(String orderId) {
        if(orderdb.containsKey(orderId)){
            if(isOrderAssigned.contains(orderId)){
                isOrderAssigned.remove(orderId);
            }
            else{
                for(String partnerId : pairdb.keySet()){
                    List<String> list = pairdb.get(partnerId);
                    if(list.contains(orderId)){
                        list.remove(orderId);
                    }
                }
            }
        }
    }
}

