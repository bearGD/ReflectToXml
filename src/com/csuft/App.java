package com.csuft;

import com.csuft.refl.Refl;
import com.csuft.user.User;

public class App {

	public static void main(String[] args) {
		
		Refl re = new Refl();
		User user = new User(1, "bob", "150");
		re.toXml(user);
		System.out.println("结束");
		
	}
}
