//package sockets.chat.servidor;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {

	private int porta;
	private List<Socket> clientes;
        
        private String perguntaQuiz;
        private String respostaQuiz;
        
        
        public void EnviarPergunta(String pergunta, String resposta)
        {
            perguntaQuiz = pergunta;
            respostaQuiz = resposta;
            this.distribuiMensagem(null, pergunta);
            
            
        
        }

	public Servidor(int porta) {
		this.porta = porta;
		this.clientes = new ArrayList<>();
	}

	public void executa() throws IOException  {
		try(ServerSocket servidor = new ServerSocket(this.porta)){
			System.out.println("Porta 4333 aberta!");
	
			while (true) {
				Socket cliente = servidor.accept();
				System.out.println("Nova conexão com o cliente " + 
						cliente.getInetAddress().getHostAddress());
	
				this.clientes.add(cliente);
	
				TratadorDeMensagemDoCliente tc = new TratadorDeMensagemDoCliente(cliente, this);
				new Thread(tc).start();
			}
		}
	}

	public void distribuiMensagem(Socket clienteQueEnviou, String msg) {
		for (Socket cliente : this.clientes) {
			if(!cliente.equals(clienteQueEnviou)){
				try {
					PrintStream ps = new PrintStream(cliente.getOutputStream());
					ps.println(msg);
                                       if(verificarResposta(ps,msg)){
                                           
                                           for (Socket clienteResp : this.clientes)
                                           {
                                               
                                               new PrintStream(clienteResp.getOutputStream()).println("Resposta Correta.\n Parabéns : " + clienteQueEnviou.getInetAddress().toString());
                                           }
                                           
                                       }
                                        
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
        
        public boolean verificarResposta(PrintStream ps, String msg)
        {
            
            if(!(respostaQuiz.equals(null) || respostaQuiz.equals("")))
            {
                boolean respostaCorreta = respostaQuiz.equals(msg);
                if(respostaCorreta)
                {
                    respostaQuiz = "";
                }
                
                return respostaCorreta;
            }
            
            return false;
        }
}