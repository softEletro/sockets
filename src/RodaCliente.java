//package sockets.chat.cliente;

import java.io.IOException;
import java.net.UnknownHostException;

public class RodaCliente {
	public static void main(String[] args) 
			throws UnknownHostException,	IOException {
		new Cliente("10.180.199.204", 4333).executa();
	}
}
