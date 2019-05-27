package com.rui.concurrency.example.singleton;

/**
 * 懒汉模式，单例使用时仅第一次获取时创建
 */
public class SingletonExample1 {

    //私有的，单例模型不能外部通过new来创建对象
    private SingletonExample1() {}

    //单例对象
    private static SingletonExample1 singletonExample1 = null;

    public static SingletonExample1 getInstance() {
        if (singletonExample1 == null) {
            singletonExample1 = new SingletonExample1();
        }
        return singletonExample1;
    }
}
