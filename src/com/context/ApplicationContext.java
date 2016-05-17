package com.context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * 
 * @author freezyElf
 *
 */
public class ApplicationContext {
	
	//文件存放路径（默认的）
	private static final String rootResource = "resource/";
	
	private Map<String, Object> beanMap = new HashMap<String, Object>();

	public Object getBean(String beanId){
		//
		Object o = null;
		Class<?> clazz = null;
		try {
			String className = (String) beanMap.get(beanId);
			clazz = Class.forName(className);
			o = clazz.newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return o;
	}

	//无参数的构造方法
	public ApplicationContext(){
		
	}
	
	/**
	 * 参数为文件名的构造方法
	 * 1,只有一个文件名，需要指定默认路径
	 * 2,得到了io流，读取文件
	 * 3，选择dom读取文件内容
	 * @throws FileNotFoundException 
	 */
	public ApplicationContext(String fileName) throws FileNotFoundException{
		readXml(getIO(fileName));
	}
	
	//1.读取文件方法
	private InputStream getIO(String fileName) throws FileNotFoundException {
		return new FileInputStream(rootResource + fileName);
	}
	//2.读取xml
	private void readXml(InputStream is) {
		//1.解析器工厂类：DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		//2.解析器：DocumentBuilder
		DocumentBuilder db = null;
		Document doc = null;
		try {
			db = dbf.newDocumentBuilder();
			doc = db.parse(is);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		NodeList list =doc.getElementsByTagName("bean");
		Element ele = (Element) list.item(0);
		String id = ele.getAttribute("id");
		String className = ele.getAttribute("class");
		beanMap.put(id, className);
	}
	
	public Map<String, Object> getBeanMap() {
		return beanMap;
	}

	public void setBeanMap(Map<String, Object> beanMap) {
		this.beanMap = beanMap;
	}
}
