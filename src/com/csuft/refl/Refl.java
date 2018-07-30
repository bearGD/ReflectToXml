package com.csuft.refl;

import java.lang.reflect.Field;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Text;

import com.csuft.anno.Attribute;
import com.csuft.anno.Element;
import com.csuft.anno.ElementList;
import com.csuft.anno.Root;
import com.csuft.user.User;

public class Refl {

	public void toXml(Object obj) {
		Class clazz = obj.getClass();

		Document doc = null;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		} catch (ParserConfigurationException e2) {
			e2.printStackTrace();
		}

		Root r = (Root) clazz.getDeclaredAnnotation(Root.class);
		org.w3c.dom.Element root = doc.createElement(r.name());

		Field[] fd = clazz.getDeclaredFields();
		for (Field f : fd) {
			Element e = f.getDeclaredAnnotation(Element.class);
			Attribute attr = f.getDeclaredAnnotation(Attribute.class);
//			ElementList emList = f.getDeclaredAnnotation(ElementList.class);
			// 设置字段可访问
			f.setAccessible(true);

			// 获取到属性(成员变量/字段)的值
			try {
				Object o = f.get(obj);

				// 元素的情况
				if (e != null) {
					org.w3c.dom.Element em = doc.createElement(e.name());
					Text text = null;
					if (o == null) {
						text = doc.createTextNode("null");
					} else {
						text = doc.createTextNode(o.toString());
					}
					em.appendChild(text);
					root.appendChild(em);
				}
				// 属性的情况
				if (attr != null) {
					Attr at = doc.createAttribute(attr.name());
					if (o == null) {
						at.setValue("null");
					} else {
						at.setValue(o.toString());
					}
					root.setAttributeNode(at);
				}
				// 属性链表的情况
				// if(emList != null) {
				// org.w3c.dom.Element list = doc.createElement(emList.name());
				// org.w3c.dom.Element subList = doc.createElement(emList.entry());
				//
				// }

			} catch (IllegalArgumentException | IllegalAccessException e1) {
				e1.printStackTrace();
			}
		}

		doc.appendChild(root);

		// 序列化
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(new DOMSource(doc), new StreamResult("test.xml"));
		} catch (TransformerConfigurationException | TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}
}
