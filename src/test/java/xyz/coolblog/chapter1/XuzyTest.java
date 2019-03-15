package xyz.coolblog.chapter1;

import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.junit.Test;

public class XuzyTest {
    @Test
    public void test1(){
        PropertyTokenizer prop = new PropertyTokenizer("jdbc.url");
        System.out.println(prop.hasNext());
    }
}
