package com.xu.design.service.proxy.impl;

import com.xu.design.service.proxy.Subject;

public class RealSubjectProxy implements Subject {

    private RealSubject subject;

    public RealSubjectProxy() {
        try {
            this.subject = (RealSubject) this.getClass()
                    .getClassLoader()
                    .loadClass("com.xu.design.service.proxy.impl.RealSubject")
                    .newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connect(){
        System.out.println("建立连接");
    }

    public void log(){
        System.out.println("日志记录");
    }

    @Override
    public void doWork() {
        connect();
        subject.doWork();
        log();
    }
}
