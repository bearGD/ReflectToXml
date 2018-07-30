package com.csuft.user;

import com.csuft.anno.Attribute;
import com.csuft.anno.Element;
import com.csuft.anno.Root;

@Root(name = "User")
public class User {

	@Attribute(name = "id")
	int id;
	@Element(name = "name")
	String name;
	@Element(name = "phone")
	String phone;
	@Element(name = "age")
	Integer age;
	// @ElementList(name="address-list",entry="address")
	// String[] address;

	// 反射使用
	public User() {
	}

	public User(int id, String name, String phone) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", phone=" + phone + "]";
	}

}
