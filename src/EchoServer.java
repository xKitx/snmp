import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import org.snmp4j.smi.OID;

import Micran.SNMPManager;



public class EchoServer {
	

  public static void main(String[] args) throws IOException 
  {
    
	  generate_Integer();
	  read_Integer();
	  
	ServerSocket m_ServerSocket = new ServerSocket(3128);
    System.out.println("Starting server @ port "+ m_ServerSocket.getLocalPort());
    
    /*
    int tmp[] = Owen.EncodeString("Test!");

    for(int i=0; i< tmp.length; i++)
    	System.out.print(tmp[i] + "\t");

    System.out.println(Owen.DecodeInt(tmp));
    
    */
    
    int id = 0;
    
    while (true) 
    {
     
      Socket clientSocket = m_ServerSocket.accept();
     
      ClientServiceThread cliThread = new ClientServiceThread(clientSocket, id++);
      cliThread.start();
    }
  }

private static int[] read_Integer() throws FileNotFoundException {
	// TODO Auto-generated method stub
	
	int mass[] = null;
	
	String s = "";
	Scanner in = new Scanner(new File("d:\\file.txt"));
	while(in.hasNext())
	s += in.nextLine() + "\r\n";
	in.close();

	System.out.println(s.length());


	return mass;
}

private static void generate_Integer() {
	// TODO Auto-generated method stub
	
	Integer i;
	
		
		String  fileName = "d:\\file.txt";
        //Строка, которая будет записана в файл
        String data = "Some data to be written and read.\n";
        try{
        FileWriter fw = new FileWriter(fileName);
        BufferedWriter bw = new BufferedWriter(fw);
        String str=null;
        // Integer.MAX_VALUE
        for (i = 0; i<= 15500; i ++) {
    	str = i.toString() + "\r"; 
    	bw.write(str);
        }
        bw.close();
        System.out.println("Generate FILE OK");
        }
        
        catch(Exception e) {
            e.printStackTrace();
            }
        
}
}


class ClientServiceThread extends Thread 
{
  Socket clientSocket;
  int clientID = -1;
  boolean running = true;

  ClientServiceThread(Socket s, int i) 
  {
    clientSocket = s;
    clientID = i;
  }

  public void run() 
  {
    System.out.println("Accepted Client : ID - " + clientID +"\t"+ clientSocket.toString());
    
    try 
    {
    	
      BufferedReader   in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      
      // кодировка исходящих сообщений
      OutputStreamWriter osW= new OutputStreamWriter(clientSocket.getOutputStream(), "utf-8");
      
      PrintWriter client_writer = new PrintWriter(osW,true);
      
      while (running)
      {

    	  // для dotNet чтобы инициализировать прием-передачу нужно сначала что-то передать клиенту
    	  //client_writer.println();
    	  
    	  String Сommand = in.readLine();
 
    	  
/*
    	  // читаем до тех пор, пока не конец команды 
    	  // дождаться конца строки
    	  // нарубить на куски и обрабатывать
*/
    	  
    	  if (Сommand == null) break;
    	  System.out.println("Строка в Овене = " + Сommand);
    	  
    	  // главная кодировка
    	  int[] mass = Owen.Decode(Сommand);
    	  
		  char tmp[] = Сommand.toCharArray();
		  
		  
		  int len = tmp.length; 
		  char buf[] = { tmp[len-4], tmp[len-3], tmp[len-2], tmp[len-1]};
		  
		  String crc_in =  new String (buf);
			
		  System.out.println("Контрольная сумма полученная (отрезанная) присланная = " + crc_in);
	      client_writer.println("Контрольная сумма полученная (отрезанная) в Овене = " + crc_in);
	    	
	      // считаем контрольную сумму полученного массива
	      int lenMass = tmp.length - 4;
	      int crc_count  = Owen.CrcCalc(mass, lenMass);

	      int a = (crc_count >> 8) & 0x000000ff;
	      int b = (crc_count & 0x000000ff);

	      int crc_Mas[] = new int[2];
	      crc_Mas[0] = a;
	      crc_Mas[1] = b;
	    	  
	      // переводим контрольную сумму в Овен и запоминаем
	      String crc_owen = Owen.Encode(crc_Mas);
	    	  
	      System.out.println("Контрольная сумма по содержимому расчитанная в Овене = " + crc_owen);
	      client_writer.println("Контрольная сумма по содержимому расчитанная в Овене = " + crc_owen);
	    	  
	    	// если контрольные суммы совпадают  
	      if ( crc_in.equals(crc_owen) ) 
	      {
	    	  System.out.println("Суммы совпадают, команда правильная");
		      client_writer.println("Суммы совпадают, команда правильная");
		      //вызвать метод!!!
	      } 
	      
	      else 
	      {
	          System.out.println("Суммы НЕ совпадают, команда НЕ правильная");
		      client_writer.println("Суммы НЕ совпадают, команда НЕ правильная");
		      // ничего не делаем, дальше
	      }
	    	  
	      
	      /* синхронизация выполнения команд
	       * для приоритетных очередей добавлять поток и формировать очередь команд
	       * 
	       */
	      ConnectSNMP connectTecno = new ConnectSNMP("192.168.0.222");
	      client_writer.write(ConnectSNMP.get_sysContact());
	      client_writer.write(ConnectSNMP.get_sysUptime());
	      
	      
	     
		  
      }
 
     
    } 
    
    catch (Exception e) 
    {
      e.printStackTrace();
    }
  }
  


}
