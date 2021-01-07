package edu.tamu.aser.tests.mutual_exclusion;


import static org.junit.Assert.*;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import log.RecordTimeInfo;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnit4MCRRunner.class)
public class Peterson {
	
	final static int COUNT = 1;
	public static int flag1;
	public static int flag2;
	public static int turn;
	public static int x;
	
	public static void main(String[] args) {
		
		flag1 = 0;
		flag2 = 0;
		turn = 0;
		x = 0;
		
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int count = 0;
				flag1 = 1;
				turn = 2;
				while(flag2 == 1 && turn ==2){
					if(count++ > COUNT)break;
				}
				
				//cs
				x = 1;
				flag1 = 0;
				
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int count = 0;
				flag2 = 1;
				turn = 1;
				while(flag1 == 1 && turn ==1){
					if(count++ > COUNT)break;
				}
				
				//cs
				x = 2;
				
				flag2 = 0;
			}
		});
		
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@Test
	public void test() throws InterruptedException {
//		RecordTimeInfo.recordInfo("Peterson", "记录原始测试用例生成和执行的时间:",true);
		for (int i = 0; i < 1; i++) {
			long start = System.currentTimeMillis();
			try {
				Peterson.main(null);
			} catch (Exception e) {
				System.out.println("here");
				fail();
			}
			long end = System.currentTimeMillis();
			String timeInfo = "执行原始测试用例的时间为:" + (end - start);
			if (i != 29){
				RecordTimeInfo.recordInfo("Peterson", timeInfo, true);
			}else {
				RecordTimeInfo.recordInfo("Peterson", timeInfo, true);
			}
		}

	}
}
