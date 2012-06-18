/*
 *  ласс дл€ работы с ќвеном
 * 
 * 
 * 
 */
public class Owen
{
	public static char BEG_MSG = '#';
	public static char END_MSG = '\r';
	
	private static String cowen = "GHIJKLMNOPQRSTUV";
	
	public static int[] Decode(String l2)
	{
		int[] res = new int[l2.length()/2];
		
		for (int i=0; i<res.length; i++)
		{
			char c1 = l2.charAt(i*2);
			char c2 = l2.charAt(i*2+1);
			int j1 = cowen.indexOf(c1);
			int j2 = cowen.indexOf(c2);
			res[i] = (j1*16 + j2);
		}
		
		return res;
	}
	
	public static String Encode(int[] buff)
	{
		String res = "";
		
		for (int i=0; i<buff.length; i++)
		{
			int b1 = buff[i];
			int j1 = (b1 & 0x0F);
			
			
			int j2 = (b1 & 0xf0) >> 4;
			//res += cowen.charAt(j1) + cowen.charAt(j2);
			char c = cowen.charAt(0);
			c = cowen.charAt(j2);
			res += c;
			c = cowen.charAt(j1);
			res += c;
			c = 0;
		}
		
		return res;
	}

	public boolean msgReceived(byte[] buffer, int len) 
	{
		try
		{
			String str = new String(buffer, "cp1251");
			if (len > 2)
			{
				boolean b = ((str.charAt(0) == Owen.BEG_MSG) && (str.charAt(len-1) == Owen.END_MSG));
				str = null;				
				return b;
			}
		}
		catch (Exception ex)
		{}
		return false;
	}
	
	public static int CrcCalc(int[] buff, int len)
	{
		int res = 0;
		
		for (int i=0; i<len; i++)
		{
			int b = buff[i];
			for (int j=0; j<8; j++)
			{
				/*res = ((res & 0x7FFF) * 2) & 0x0000FFFF;
				System.out.println(b + ":" + res);
				if (((b & 0x80) >> 7) != ((res & 0x8000) >> 15))
				{
					res = (res ^ 0x8F57) & 0x0000FFFF;
					System.out.println(b + "|" + ((b & 0x80) >> 7));
				}
				b = ((b & 0x7F) * 2) & 0x000000FF;*/
				if ((( b ^ (res>>8) ) & 0x80) > 0)
				{
					res = (res << 1) & 0x0000FFFF;  
					res = (res ^ 0x8F57) & 0x0000FFFF;
				}
				else 
					res = (res << 1) & 0x0000FFFF;  
				b = (b << 1) & 0x000000FF;
			}
		}
		
		return res;
	}
	
	
	public static void owen_test() 
	{

		System.out.println("Test");
		
		
		
		    int mas [] = {(int)'g', (int)'e', (int)'t', 16};
		    
		    String s_encode = Owen.Encode(mas);
		    
		    
		    System.out.println(" = "+ s_encode);
		    
		    String s_decode = s_encode;
		    
		    int[] mas_decode = Owen.Decode(s_decode);
		    System.out.print(s_decode +" = "+ mas_decode[0] + " " + mas_decode[1] + " " + mas_decode[2] + "\n");
		    
		    
		    char get = (char)mas_decode[0]; // +  (char)mas_decode[1] +  (char)mas_decode[2];
		    System.out.println(get);
		    
		    /*
		    System.out.println("A = " +(int)'A');
		    System.out.println("65 = " +(char)90);
*/
		
		    int crc = Owen.CrcCalc(mas, 4);
		    System.out.println("CRC = "+ crc);
		
		
	}
	
	
	
	public static int[] EncodeString (String str) {
		
		int str_int[] = new int[str.length()];
		
		char ch[] = str.toCharArray();
		
		for (int i=0; i< ch.length; i++) 
		{
			str_int[i] = (int) ch[i];
		}
		
		return str_int;
	}
	
	public static String DecodeInt (int d[]) {
		
		
		
		char ch[] = new char[d.length];
		
		for (int i=0; i< ch.length; i++) 
		{
			ch[i] = (char) d[i] ;
		}
		
		String str= new String (ch);
		return str;
	}
}
