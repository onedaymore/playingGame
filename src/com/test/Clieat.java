package com.test;

import java.io.FileNotFoundException;

import com.context.ApplicationContext;

public class Clieat {
	

	public static void main(String[] args) throws FileNotFoundException {
		ApplicationContext app = new ApplicationContext("beans.xml");
		Mybean myBean =(Mybean) app.getBean("myBean");
		System.out.println(myBean.id);
	}
}
