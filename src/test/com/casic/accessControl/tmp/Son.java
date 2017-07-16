package com.casic.accessControl.tmp;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import sun.misc.Unsafe;

/**
 * Created by lenovo on 2016/12/21.
 */
public class Son extends Father{
    @Override
    public void print(){
        System.out.println("this is son print");
    }

    public void test(){
        System.out.println("this is test method");
    }

    //直接编码方式获取beanFactory
   public static  BeanFactory bindViaCode(BeanDefinitionRegistry registry){
       AbstractBeanDefinition beanDefinition = new RootBeanDefinition(Father.class);
       registry.registerBeanDefinition("father",beanDefinition);
       return (BeanFactory) registry;
   }
    public static void main(String [] args){
//        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
//        BeanFactory container = bindViaCode(beanFactory);
//
//        container.getBean("father");

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(new FileSystemResource("E:\\wxl\\项目文档\\2016\\EMS\\beandefinition.xml"));
        Father father =(Father) beanFactory.getBean("father");
        father.print();
StringBuilder sb = new StringBuilder();
        sb.reverse();
        String s = "";
    }
}
