
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
		public static void main(String[] args) throws IOException, InterruptedException {
				
				int port = 3128;
				ServerSocket servers = new ServerSocket(port);
				System.out.println("Starting server @ port "+ port);
				Socket       client = servers.accept();
				System.out.println("�lient connected @ "+ client.toString());

				//
				BufferedReader client_reader  = new BufferedReader(new InputStreamReader(client.getInputStream()));
					              
				PrintWriter client_writer = new PrintWriter(client.getOutputStream(),true);
						              
				
						              
						String message;
/*
						// ����������� ����
 						boolean done = true;					            				            
 						while ( done )
						  {
						            						            	
	*/					     
						            // � ����������� �� ������� ������ �������� ������� �����
						            client_writer.println("echo");
						            
						            
						            // ������ ������ ������� 
 									// ��������, ������ ����!
						     		message = client_reader.readLine();
						     		if (message == null ) ;
						     		
						     		/* ��� �������
						     		// ����� �� ����� exit
						     		if (message.equals("exit")) 
					            	{
					            		client_writer.println("Server is closed...");
					            		break; 
					            	}
						     		*/
 							
						     		
						            // ����� � �������, ��� ������� ������
						            System.out.println(message);
						            
						            
						            
						            
			
						            
						            
						            	System.out.println("Getting exit...");
						            	Thread.sleep(1000);
						            	client_writer.close();
						            	client_reader.close();
						           
						            client.close();
						            servers.close();
								
							

		}
	}