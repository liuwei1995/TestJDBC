package test;

public class TestJiaMi {
	private static String [] code = new String[]{"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
	
	public static void main(String[] args) {
		String data = TestJiaMi.class.getSimpleName();
		data = "liuwei1281659061";
		StringBuilder sb = new StringBuilder();
		
		byte []b = data.getBytes();
		for (int i = 0; i < b.length; i++) {
			System.out.println(b[i]);
		}
		byte[] b2 = new byte[b.length];
		for (int i = 0; i < b.length; i++) {
			b2[i] = b[i];
		}
		System.out.println("==========>>1");
		for (int i = 0; i < b2.length; i++) {
			int d = 0;
			do {
				b2[i] = (byte) (d = b2[i]>>16);
				
			} while (d >= 26);
			
			sb.append(code[d]);
			System.out.println(b[i]+"==>>1=="+d);
		}
		System.out.println(sb.toString().toUpperCase());
		
		
		int n1 = 14;
		//十进制转成十六进制：
		String hexString = Integer.toHexString(n1);
		System.out.println(hexString);
		//十进制转成八进制
		String octalString = Integer.toOctalString(n1);
		System.out.println(octalString);
		//十进制转成二进制
		String binaryString = Integer.toBinaryString(n1);
		System.out.println(binaryString);

		//十六进制转成十进制
		String string2 = Integer.valueOf(n1+"",16).toString();
		
		System.out.println(string2);
		//十六进制转成二进制
		String binaryString3 = Integer.toBinaryString(Integer.valueOf(n1+"",16));
		System.out.println(binaryString3);
		//十六进制转成八进制
		String octalString2 = Integer.toOctalString(Integer.valueOf(n1+"",16));
		System.out.println(octalString2);

		//八进制转成十进制
		String string = Integer.valueOf(n1+"",8).toString();
		System.out.println(string);
		//八进制转成二进制
		String binaryString2 = Integer.toBinaryString(Integer.valueOf(n1+"",8));
		System.out.println(binaryString2);
		//八进制转成十六进制
		String hexString2 = Integer.toHexString(Integer.valueOf("23",8));
		System.out.println(hexString2);


		//二进制转十进制
		String string3 = Integer.valueOf("0101",2).toString();
		
		System.out.println(string3);
		//二进制转八进制
		String octalString3 = Integer.toOctalString(Integer.parseInt("0101", 2));
		System.out.println(octalString3);
		//二进制转十六进制
		String hexString3 = Integer.toHexString(Integer.parseInt("0101", 2));
		System.out.println(hexString3);
		
		
		
		
//		System.out.println("==========>>>1");
//		for (int i = 0; i < b2.length; i++) {
//			int d = b2[i]>>>1;
//		System.out.println(b[i]+"==>>>1=="+d);
//		}
//		System.out.println("==========<<1");
//		for (int i = 0; i < b2.length; i++) {
//			int d = b2[i]<<1;
//			System.out.println(b[i]+"==<<1=="+d);
//		}
//		System.out.println("==========|1");
//		for (int i = 0; i < b2.length; i++) {
//			int d = b2[i]|1;
//			System.out.println(b[i]+"==|1=="+d);
//		}
//		System.out.println("==========&1");
//		for (int i = 0; i < b2.length; i++) {
//			int d = b2[i]&1;
//			System.out.println(b[i]+"==&1=="+d);
//		}
//		System.out.println("==========^1");
//		for (int i = 0; i < b2.length; i++) {
//			int d = b2[i]^1;
//			System.out.println(b[i]+"==^1=="+d);
//		}
	}
}
