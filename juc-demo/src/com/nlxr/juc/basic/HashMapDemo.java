package com.nlxr.juc.basic;


import org.junit.Test;

/**
 * @Author labu
 * @Date 2021/2/24
 * @Description
 */
public class HashMapDemo {

    /**
     * hashmap 源码
     *
     * @param key
     * @return
     */
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    /**
     * Q:为什么hashmap的容量是2的幂次方？
     * A:
     * 1.hash%length == hash&(length-1),前提length必须是2的幂次方，&运算比%运算高效
     * 2.Hash算法最终得到的index结果，完全取决于Key的Hashcode值的最后几位，需要保证length-1是奇书才能减少index重复
     * 3.hash%length == hash&(length-1),前提length必须是2的幂次方，&运算比%运算高效
     */
    @Test
    public void test_capacity() {
        int h;
        Object key = "bbc";
        int length =16;
        int hash = hash(key);
        System.out.println("hashcode: "+hash);
        System.out.println("hashcode 2: "+Integer.toBinaryString(hash));
        int res = hash ^ (hash >>> 16);
//        int res = (h = hashcode) ^ (h >>> 16);
        System.out.println("源码hash值：" + res);

        int resss = hash & (length -1);
        System.out.println("源码hash值：" + resss);

        int ress = hash%length;
        System.out.println("取余值：" + res);

        int idx = (length - 1) & hash;
        System.out.println("index & : "+idx);
        idx = hash%length;
        System.out.println("index % : "+idx);
        length =32;
        idx = (length - 1) & hash;
        System.out.println("32 index & : "+idx);
        idx = hash%length;
        System.out.println("32 index % : "+idx);

    }
}
