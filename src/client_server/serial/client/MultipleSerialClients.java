package client_server.serial.client;

import client_server.wdi.data.WDIDAO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;

import static client_server.common.ConstantsKt.SERIAL_PORT;

/**
 * Class that launches in parallel a number of clients to the serial server
 * @author author
 *
 */
public class MultipleSerialClients {

	public static void main(String[] args) {
		WDIDAO.INSTANCE.getData();
		System.out.println("Data loaded");
		final int NUM_CLIENTS=5;
		
		for (int i=1; i<=NUM_CLIENTS; i++) {
			System.out.println("Number of Simultaneous Clients: "+i);
			Thread[] threads= new Thread[i];
			SerialClient client=new SerialClient();
			for (int j=0; j<i; j++) {
				threads[j] = new Thread(client);
				threads[j].start();
			}
			for (int j=0; j<i; j++) {
				try {
					threads[j].join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		try (Socket echoSocket = new Socket("localhost",
				SERIAL_PORT);
				PrintWriter out = new PrintWriter(
						echoSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(
						new InputStreamReader(echoSocket.getInputStream()));
				BufferedReader stdIn = new BufferedReader(
						new InputStreamReader(System.in))) {

			StringWriter writer = new StringWriter();
			writer.write("z");
			writer.write(";");

			String command = writer.toString();
			out.println(command);
			String output = in.readLine();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
