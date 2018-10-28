//package sockets.chat.cliente;

import java.io.IOException;
import java.net.UnknownHostException;

public class RodaCliente {
	public static void main(String[] args) 
			throws UnknownHostException,	IOException {
		new Cliente("192.168.15.23", 45455).executa();
	}
}
