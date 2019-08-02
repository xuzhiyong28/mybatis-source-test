package xyz.coolblog.chapter1;

import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.junit.Test;

public class XuzyTest {
    @Test
    public void test1(){
        PropertyTokenizer prop = new PropertyTokenizer("jdbc.url");
        System.out.println(prop.hasNext());
    }


    @Test
    public void test2(){
        ThreadLocal<String> threadLocal = new ThreadLocal<String>();
        threadLocal.set("xuzy");
        threadLocal.set("xuzy2");
        System.out.println(threadLocal.get());
        System.out.println(threadLocal.get());
        threadLocal.remove();
        System.out.println(threadLocal.get());
    }
}
