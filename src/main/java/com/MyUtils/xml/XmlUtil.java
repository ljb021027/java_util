package com.MyUtils.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liujiabei
 * @since 2019/5/17
 */
public class XmlUtil {

    public static void main(String[] args){
        XmlUtil xmlUtil = new XmlUtil();
        List<Student> students = xmlUtil.listStudent();
        System.out.println(1);
    }
    /**
     * 读取xml学生列表信息
     * @return
     */
    public List<Student> listStudent(){
//创建SAXReader对象
        SAXReader reader=new SAXReader();
        Document document = null;
        try {
//通过read方法读取一个文件 转换成Document对象
            document = reader.read(new File("C:\\Users\\ljb\\Desktop\\mipush_user_reginfo.xml"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
//获取根节点元素对象
        Element node = document.getRootElement();
        return elementMethod(node);
    }
    /**
     * 获取节点中的信息
     * @param node
     * @return
     */
    private List<Student> elementMethod(Element node) {
        List<Student> list = new ArrayList<Student>();
//获取所有class 节点
        List<Element> elementClass = node.elements("class");
        for (Element aClass : elementClass) {
//获取所有student节点
            List<Element> elelmentStu = aClass.elements("student");
            for (Element element : elelmentStu) {
                System.out.println(element.getText());
//创建学生对象
                Student stu = new Student();
//class节点里name的值
//                stu.setSclazz(aClass.attribute("name").getValue());
////student节点里ID的值
//                stu.setSid(element.attribute("id").getValue());
////student节点里的String值
//                stu.setSname(element.getStringValue());
                list.add(stu);
            }
        }
        return list;
    }
}
