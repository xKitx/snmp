import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.snmp4j.smi.OID;

import Micran.SNMPManager;



public class EchoServer {
	

  public static void main(String[] args) throws IOException  {
    ServerSocket m_ServerSocket = new ServerSocket(3128);
    System.out.println("Starting server @ port "+ m_ServerSocket.getLocalPort());
    
    /*
    int tmp[] = Owen.EncodeString("Test!");

    for(int i=0; i< tmp.length; i++)
    	System.out.print(tmp[i] + "\t");

    System.out.println(Owen.DecodeInt(tmp));
    
    */
    
    int id = 0;
    while (true) {
      Socket clientSocket = m_ServerSocket.accept();
     
      ClientServiceThread cliThread = new ClientServiceThread(clientSocket, id++);
      cliThread.start();
    }
  }
}


class ClientServiceThread extends Thread {
  Socket clientSocket;
  int clientID = -1;
  boolean running = true;

  ClientServiceThread(Socket s, int i) {
    clientSocket = s;
    clientID = i;
  }

  public void run() {
    System.out.println("Accepted Client : ID - " + clientID +"\t"+ clientSocket.toString());
    try {
    	
    	
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
			char buf[] = { tmp[8], tmp[9], tmp[10], tmp[11]};
			String crc_poluchennoe =  new String (buf);
			
			System.out.println("����������� ����� ���������� (����������) ���������� = " + crc_poluchennoe);
	    	client_writer.println("����������� ����� ���������� (����������) � ����� = " + crc_poluchennoe);
	    	
	    	
	    	  // ������� ����������� ����� ����������� �������
	    	  int d  = Owen.CrcCalc(mass, 4);

	    	  int a = (d >> 8) & 0x000000ff;
	    	  int b = (d & 0x000000ff);

	    	  int crc_[] = new int[2];
	    	  crc_[0] = a;
	    	  crc_[1] = b;
	    	  
	    	  // ��������� ����������� ����� � ���� � ����������
	    	  String crc_owen = Owen.Encode(crc_);
	    	  
	    	  System.out.println("����������� ����� �� ����������� ����������� � ����� = " + crc_owen);
	    	  client_writer.println("����������� ����� �� ����������� ����������� � ����� = " + crc_owen);
	    	  
	    	  
	    	  if ( crc_poluchennoe.equals(crc_owen) ) {
	    		  System.out.println("����� ���������, ������� ����������");
		    	  client_writer.println("����� ���������, ������� ����������");
	    	  } else {
	    		  System.out.println("����� �� ���������, ������� �� ����������");
		    	  client_writer.println("����� �� ���������, ������� �� ����������");
		    	  break;
	    	  }
	    	  
	    	  
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
			
	    	
      }
 
     
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  

}
