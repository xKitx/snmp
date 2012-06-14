package Micran;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.snmp4j.smi.OID;

public class Np2SNMP {

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException  {
		// TODO Auto-generated method stub
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		
		System.out.println("������� IP-����� ���������� NP2SNMP");
		
		//String addres =  "udp:" + input.readLine() + "/161";
		String addres =  "udp:" + args[0] + "/161";
		
		System.out.println(addres);
		
		SNMPManager client = new SNMPManager(addres);
		client.start();
		//System.out.println("����������� ����� � �����������");
		
		
		// sysContact RW
		OID oid_sysContact = new OID(".1.3.6.1.2.1.1.4.0");
		String sysContact = client.getAsString(oid_sysContact);
		
		client.set_String(oid_sysContact, "sidorov");
		
		
		System.out.println("sysContact\t" + client.getAsString(oid_sysContact));
		
		/*
		// �������� ������������ ����
		OID update_configuration_network = new OID("1.3.6.1.4.1.19707.8.1.2.3");
		client.set_Int(update_configuration_network, 1);
		*/
		
		//System.out.println("�������� ���������� ������������ ����");
		
		/*
		// �������� ���������� ���� ~ 1 ���
		OID oid_status = new OID("1.3.6.1.4.1.19707.8.3.2");

		/*  	1.3.6.1.4.1.19707.8.3.2 ������� ������
		*  		0 ���������� ��������;
		*		1 ���������� �������� ������������� ����;
		*		2 ���������� �������� ���������� ������������ ����;
		*
		* 		��������� ������� - ��������
		*/
/*
				int i=0;
		while (true)
		{
			System.out.println("Tik..\t" + (i++));
			
			String status_device = client.getAsString(sysContact);
			System.out.println(status_device);
			
			/*
			int status_device =  Integer.parseInt(tmp_status_device);
			
			if (status_device == 0) { System.out.println("���������� ������������ ���� ���������"); break; }
			if (status_device == 1) System.out.println("���������� �������� ������������� ����");
			if (status_device == 2) System.out.println("���������� �������� ���������� ������������ ����");
			
			if (i == 4) break;
			
		}

			System.out.println("����� �������������, �������� � FTP ����������");
			*/System.out.println("�����..");

	}

}
