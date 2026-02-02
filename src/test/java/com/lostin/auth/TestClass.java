package com.lostin.auth;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TestClass {

    String smth = null;

    @Test
    void test(){
        Map<Integer, String> map = Map.of();
        List<String> strings = new ArrayList<>(map.values().stream().toList());
        strings.add(smth);
        System.out.println(strings);
    }
    @Test
    void test2(){
        System.out.println(Instant.now());
        System.out.println(new Date());
    }
}
