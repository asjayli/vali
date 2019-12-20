package com.kitsrc.watt.utils.guid.test;

import java.util.HashSet;
import java.util.Set;

import com.kitsrc.watt.utils.guid.Snowflake;
import org.junit.jupiter.api.Test;

class SnowflakeKitIdTests {

    @Test
    void nextStringId() {
        final Snowflake snowflakeKitId = new Snowflake(1L);
        Set<String> idSet = new HashSet<>();
        int n = 100;
        while (n > 0) {
            final String nextStringId = snowflakeKitId.nextStringId();
            idSet.add(nextStringId);
            System.out.println(nextStringId);
            n--;
        }
        System.out.println(idSet.size());
    }

    @Test
    void nextId() {
        final Snowflake snowflakeKitId = new Snowflake(1L);
        Set<Long> idSet = new HashSet<>();
        int n = 100;
        while (n > 0) {
            final long nextId = snowflakeKitId.nextId();
            idSet.add(nextId);
            System.out.println(nextId);
            n--;
        }
        System.out.println(idSet.size());

    }
}