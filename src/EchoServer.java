import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.smartcardio.CommandAPDU;

import org.snmp4j.smi.OID;

import Micran.SNMPManager;



public class EchoServer {
	

  public static void main(String[] args) throws IOException 
  {
    
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
      
      OutputStreamWriter osW= new OutputStreamWriter(clientSocket.getOutputStream(), "utf-8");
      
      PrintWriter client_writer = new PrintWriter(osW,true);
      
      while (running)
      {

    	  // ��� dotNet ����� ���������������� �����-�������� ����� ������� ���-�� �������� �������
    	  //client_writer.println();
    	  
    	  String �ommand = in.readLine();
    	  if (�ommand == null) break;
    	  System.out.println("������ � ����� = " + �ommand);
    	  
    	  // ������� ���������
    	  int[] mass = Owen.Decode(�ommand);
    	  
		  char tmp[] = �ommand.toCharArray();
		  
		  
		  int len = tmp.length; 
		  char buf[] = { tmp[len-4], tmp[len-3], tmp[len-2], tmp[len-1]};
		  
		  String crc_in =  new String (buf);
			
		  System.out.println("����������� ����� ���������� (����������) ���������� = " + crc_in);
	      client_writer.println("����������� ����� ���������� (����������) � ����� = " + crc_in);
	    	
	      // ������� ����������� ����� ����������� �������
	      int lenMass = tmp.length - 4;
	      int crc_count  = Owen.CrcCalc(mass, lenMass);

	      int a = (crc_count >> 8) & 0x000000ff;
	      int b = (crc_count & 0x000000ff);

	      int crc_Mas[] = new int[2];
	      crc_Mas[0] = a;
	      crc_Mas[1] = b;
	    	  
	      // ��������� ����������� ����� � ���� � ����������
	      String crc_owen = Owen.Encode(crc_Mas);
	    	  
	      System.out.println("����������� ����� �� ����������� ����������� � ����� = " + crc_owen);
	      client_writer.println("����������� ����� �� ����������� ����������� � ����� = " + crc_owen);
	    	  
	    	  
	      if ( crc_in.equals(crc_owen) ) 
	      {
	    	  System.out.println("����� ���������, ������� ����������");
		      client_writer.println("����� ���������, ������� ����������");
		      //������� �����!!!
	      } 
	      
	      else 
	      {
	          System.out.println("����� �� ���������, ������� �� ����������");
		      client_writer.println("����� �� ���������, ������� �� ����������");
		      break;//���������� �� break - �����
	      }
	    	  
	      //*******� ������ ������********* 
	  	  String addres =  "udp:" + "192.168.0.222" + "/161";
			
		  System.out.println(addres);
			
		  SNMPManager client = new SNMPManager(addres);
		  
		  client.start();
		
		  // sysContact RW
		  OID oid_sysContact = new OID(".1.3.6.1.2.1.1.4.0");
		  String sysContact = client.getAsString(oid_sysContact);
		  System.out.println("������� ������� = " + sysContact); 
		  client_writer.println("������� ������� = " + sysContact); 
			
		  // sysUptime
		  OID oid_sysUptime = new OID (".1.3.6.1.2.1.1.3.0");
		  String sysUptime = client.getAsString(oid_sysUptime);
		  System.out.println("����� ������� = " + sysUptime); 
		  client_writer.println("����� ������� = " + sysUptime); 
		  //*****************
		  
      }
 
     
    } 
    
    catch (Exception e) 
    {
      e.printStackTrace();
    }
  }
  

}
