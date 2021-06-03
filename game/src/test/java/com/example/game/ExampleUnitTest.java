package com.example.game;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {

        assertEquals(4, 2 + 2);
        HashMap map =new HashMap();
        map.put(null,null);
        System.out.println(map.get(null));
        System.out.println(("abcd")instanceof Object);

    }
}