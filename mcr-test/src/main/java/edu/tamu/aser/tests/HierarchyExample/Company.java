package edu.tamu.aser.tests.HierarchyExample;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import edu.tamu.aser.tests.garageManager.GarageManager;
import log.RecordTimeInfo;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RunWith(JUnit4MCRRunner.class)
public class Company {

	String name;
	boolean active = true;
	Company parent;
	List children = new ArrayList();
	
//	public Company(String name) {
//		this.name = name;
//	}

	public void setName(String name){
		this.name = name;
	}
	
	public String toString() {
		return name 
			//+ " " + (active?"A":"I")
			//+ " (" + parent + ")"
			;
	}
	
	public synchronized boolean isActive() {
		return active;
	}
	
	public synchronized boolean setParent(Company newParent) {
		if(newParent == null) {
			this.parent = null;
			return true;
		} else if(newParent.isActive() 
				|| !this.isActive()) {
			newParent.children.add(this);
			if(parent!=null) {
				parent.children.remove(this);
			}			
			parent = newParent;
			return true;
		} else {
			return false;		
    	}
	}
	
	public synchronized boolean setActive() {
		if(parent == null || parent.isActive()) {
			active = false;
			return true;
		} else {
			return false;
		}			
	}
	synchronized boolean isChildrenInactive() {
		for(Iterator i=children.iterator(); i.hasNext();) {
			Company child = (Company)i.next();
			if(child.isActive())
				return false;
		}
		return true;
	}
	
	public synchronized boolean setInactive() {
		if(isChildrenInactive()) {
			active = false;
			return true;			
		} else {
			return false;
		}
	}
	
	static Company clients[] = new Company[] {
		new Company(), new Company()
	};
	
	public static void main(String[] args) {
		clients[0].setName("Rosneft");
		clients[1].setName("Gazprom");

		Thread t1 =  new Thread() {
			public void run() {
				clients[0].setParent(clients[1]);
			}
		};
		Thread t2 =  new Thread() {
			public void run() {
				clients[1].setInactive();
			}
		};
		
		t2.start();
		t1.start();
		
		/*
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}*/
		
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//System.out.println(clients[0] + "\n" + clients[1]);
		
		boolean b = clients[0].parent == null 
			|| clients[1].isActive() 
			|| !clients[0].isActive();
		System.out.println(b);
		assert b; 
	}

	@Test
	public void test(){
//		RecordTimeInfo.recordInfo("Company", "记录原始测试用例生成和执行的时间:",true);
		for (int i = 0; i < 1; i++) {
			long start = System.currentTimeMillis();
			Company.main(null);
			long end = System.currentTimeMillis();
			String timeInfo = "执行原始测试用例的时间为:" + (end - start);
			if (i != 29){
				RecordTimeInfo.recordInfo("Company", timeInfo, true);
			}else {
				RecordTimeInfo.recordInfo("Company", timeInfo, true);
			}
		}

	}
}
