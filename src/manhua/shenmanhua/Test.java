package manhua.shenmanhua;

import java.net.URLDecoder;

public class Test {

	public static void main(String[] args) {
		String decode = URLDecoder.decode("http://mhpic.samanlehua.com/comic/A%2F%E9%98%BF%E8%A1%B0ONLINE%2F263%2F1.jpg");
		System.out.println(decode);
	}

}
