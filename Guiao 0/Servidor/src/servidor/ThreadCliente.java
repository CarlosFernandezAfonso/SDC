/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
class ThreadCliente implements Runnable{
    private PrintWriter out;
    private BufferedReader in;
    private int numero;
    private Banco bancoServer;
   

    ThreadCliente(PrintWriter out, BufferedReader in, int num, Banco banco) {
       this.out = out;
       this.in = in;
       this.numero = num;
       this.bancoServer = banco;
    }

    @Override
    public void run() {
        String entrada;
        System.out.println("Entrou um novo cliente numero: " + numero);
        try {
            while((entrada = in.readLine()) != null){
               String[] lString = entrada.split("_", 0);
               switch(lString[0]){
                   case "addConta":
                       int saldo = Integer.parseInt(lString[1]);
                       out.println(this.bancoServer.addConta(saldo));
                       out.flush();
                       break;
                   case "movimento":
                       int retiro = Integer.parseInt(lString[1]);
                       int destino = Integer.parseInt(lString[2]);
                       int montante = Integer.parseInt(lString[3]);
                       out.println(this.bancoServer.movimento(retiro, destino, montante));
                       out.flush();
                       break;
                   case "getBalanceAccount":
                       int idConta = Integer.parseInt(lString[1]);
                       this.bancoServer.getBalanceAccount(idConta);
                       out.println(this.bancoServer.getBalanceAccount(idConta));
                       out.flush();
                       break;
                   case "addBalance":
                       int id_Conta = Integer.parseInt(lString[1]);
                       int _montante = Integer.parseInt(lString[2]);
                       out.println(this.bancoServer.addBalance(id_Conta, _montante));
                       out.flush();
                       break;
               }
            }
        } catch (Exception ex) {
            //Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Saiu o cliente numero: " + numero);
    }
    
}
