package com.lostin.auth;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.*;

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
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
        System.out.println(uuid.toString());
    }
}
