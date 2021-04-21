package kr.co.uplus.cloud;

import java.util.Random;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Random rand = new Random();
		String numStr = ""; // 난수가 저장될 변수

		for (int i = 0; i < 5; i++) {
			// 0~9 까지 난수 생성
			String ran = Integer.toString(rand.nextInt(10));
			// 중복 허용시 numStr에 append
			numStr += ran;
		}
		System.out.println(numStr);
	}

}
