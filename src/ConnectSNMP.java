import java.io.IOException;

import org.snmp4j.smi.OID;

import Micran.SNMPManager;


public class ConnectSNMP {
	
	static SNMPManager client;
	
	ConnectSNMP(String adres_SNMP_Manager) throws IOException
	{
		adres_SNMP_Manager = "udp:" + "192.168.0.222" + "/161";
		
		client = new SNMPManager(adres_SNMP_Manager);
		  
		client.start();
	}

	
	static String  get_sysContact() throws IOException {
		  // sysContact RW
	  OID oid_sysContact = new OID(".1.3.6.1.2.1.1.4.0");
	  String sysContact = client.getAsString(oid_sysContact);
	  System.out.println("Контакт Сервера = " + sysContact); 
	  
	  return sysContact;
	}
	
	
	static String get_sysUptime() throws IOException {

	  // sysUptime
	  OID oid_sysUptime = new OID (".1.3.6.1.2.1.1.3.0");
	  String sysUptime = client.getAsString(oid_sysUptime);
	  System.out.println("Время Сервера = " + sysUptime); 

	  return sysUptime;
	}

}
