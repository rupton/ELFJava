package org.elfjava.connection;
import org.elfjava.connection.SFDCSoapConnectionException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;
public class SoapLogin {

	private static String SOAPLOGIN = new String("https://login.salesforce.com/services/Soap/c/45.0");
	private static String SOAPNAMESPACE = new String("urn:enterprise.soap.sforce.com");
	private static String SOAPENV = new String("http://schemas.xmlsoap.org/soap/envelope/");
	private String user;
	private String password;
	
	public SoapLogin() {}
	
	public SFDCSoapLoginResponse soapConnect(String username, String password) throws SFDCSoapConnectionException, IOException {
		if("".equals(username) || "".equals(password)) {
			throw new SFDCSoapConnectionException("Username and Password must both be provided");
		}
		this.user = username;
		this.password = password;
		//build the XML file to call login SOAP
		StringBuilder loginXml = new StringBuilder();
		loginXml.append("<soapenv:Envelope xmlns:soapenv=\"" + SOAPENV+"\" xmlns:urn=\"" + SOAPNAMESPACE+"\">" );
		loginXml.append("<soapenv:Body>");
		loginXml.append("<urn:login>");
		loginXml.append("<urn:username>"+user+"</urn:username>");
		loginXml.append("<urn:password>"+password+"</urn:password>");
		loginXml.append("</urn:login>");
		loginXml.append("</soapenv:Body>");
		loginXml.append("</soapenv:Envelope>");
		System.out.println(loginXml.toString());
		URL url = new URL(SOAPLOGIN);
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("SOAPAction", "nothing");
		connection.setRequestProperty("Content-Type", "text/xml");
		connection.setRequestProperty("Accept-Encoding", "gzip,deflate");
		connection.setDoOutput(true);
		DataOutputStream body = new DataOutputStream(connection.getOutputStream());
		body.writeBytes(loginXml.toString());
		body.flush();
		body.close();
		int responseCode = connection.getResponseCode();
		System.out.println(responseCode);
		StringBuilder response = new StringBuilder();
		//need to wrap response in GZIPInputStream due to Accept-Encoding header value
		BufferedReader responseText = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getInputStream())));
		String inputLine;
		while((inputLine = responseText.readLine()) !=null) {
			response.append(inputLine);
		}
		responseText.close();
		System.out.println(response.toString());
		return new SFDCSoapLoginResponse();
	}
	
	/*
	 * <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:enterprise.soap.sforce.com">
		<soapenv:Body>
		      <urn:login>
		         <urn:username>ryan.upton@protonmail.com</urn:username>
		         <urn:password>Yacodsr10!_</urn:password>
		      </urn:login>
		   </soapenv:Body>
		</soapenv:Envelope>
	 */

	public static void main(String[] args) {
		try {
			new SoapLogin().soapConnect("ryan.upton@protonmail.com", "Yacodsr10!_");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
