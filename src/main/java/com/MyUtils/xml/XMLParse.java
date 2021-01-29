package com.MyUtils.xml;



import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author liujiabei
 * @since 2019/5/17
 */
public class XMLParse {
    private String configName = "C:\\Users\\admin\\Desktop\\新建文件夹\\推送服务\\存量数据\\mipush_user_reginfo.xml";
    private SAXReader saxReader;
    private Document doc;
    private Element root;

    /**
     */
    public XMLParse() {
//      InputStream in = Thread.currentThread().getContextClassLoader()
//              .getResourceAsStream(configName);
        saxReader = new SAXReader();
        try {
            doc = saxReader.read(configName);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        root = doc.getRootElement();
    }

    /**
     * get Data Type
     *
     * @throws IOException
     */
    public void getModelElement(String attribute) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(attribute + ".txt");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List list = root.elements();
        Element model = null;
        List childList = null;
        Element modelEle = null;
        Element returnModel = null;
        String dataType = null;
        StringBuffer sb = new StringBuffer();
        int temp = 0;
        for (Iterator it = list.iterator(); it.hasNext();) {
            model = (Element) it.next();
            temp++;
            System.out.println("temp:"+temp);
            childList = model.elements();


            for (Iterator ite = childList.iterator(); ite.hasNext();) {
                modelEle = (Element) ite.next();
                if (attribute.equals(modelEle.getName())) {
                    dataType = modelEle.getText();
                    dataType = dataType;
                    if (sb.length() > 1) {
                        sb.append(",");
                    }
                    sb.append(dataType);
                }
            }
            dataType = sb.toString();
            if (!"".equals(dataType)) { // 没有值的话，跳过往txt中写值
                try {
                    fileWriter.write(dataType);
                    fileWriter.write("\r\n");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            sb.delete(0, sb.length());
            try {
                fileWriter.flush();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        try {
//              fileWriter.flush();
            fileWriter.close();
            System.out.println("xml解析成功，并成功写入到"+attribute+".txt 文件中!");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("list.size:"+list.size());
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String attribute = null;
        XMLParse parse = new XMLParse();
        attribute = "author";
        parse.getModelElement(attribute);
    }
}