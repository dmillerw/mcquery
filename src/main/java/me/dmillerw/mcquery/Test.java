package me.dmillerw.mcquery;

import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        String string = "/";
        String string2 = "test";
        String string3 = "/test/a";

        System.out.println(Arrays.toString(string.split("/")));
        System.out.println(Arrays.toString(string2.split("/")));
        System.out.println(Arrays.toString(string3.split("/")));
    }
}
