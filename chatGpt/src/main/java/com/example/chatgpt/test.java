package com.example.chatgpt;


import java.util.*;
import java.util.stream.Collectors;

public class test {

    public static void main(String[] args) {
//        User user = new User();
//        user.setId(1l);
//        user.setName(null);
//
//        List<User> users = new ArrayList<>();
//        users.add(user);
//
//        Map<Long, String> collect = users.stream().collect(Collectors.toMap(x->x.getId(),x-> Objects.isNull(x.getName())?"":x.getName(),(a,b)->a));
//        System.out.println(collect.keySet());
//        System.out.println(111111);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY,23);
        calendar.add(Calendar.MINUTE,59);
        calendar.add(Calendar.SECOND,59);
        Date result = calendar.getTime();
        //System.out.println(new Defo);


    }
}
