/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import Operacoes.*;
import Respostas.*;

/**
 *
 * @author Carlos
 */
class ThreadCliente1 implements Runnable{

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private int numero;
    private Banco bancoServer;
   

    ThreadCliente1(ObjectOutputStream out, ObjectInputStream in, int num, Banco banco) {
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
            while(true){
               Operacao op =(Operacao) in.readObject();
               if(op == null){
                   System.out.println("Saida de Cliente.");
                   return;
               }
               
               
               switch(op.getClass().getSimpleName()){
                   case "Op_AddConta":
                       Op_AddConta a = (Op_AddConta) op;
                       out.writeObject(new Resposta_AddConta(this.bancoServer.addConta(a.saldo)));
                       break;
                   case "Op_Balance":
                       Op_Balance b = (Op_Balance) op;                 
                       out.writeObject(new Resposta_Balance(this.bancoServer.getBalanceAccount(b.id)));
                       break;
                   case "Op_Deposito_Levantamento":
                       Op_Deposito_Levantamento dl = (Op_Deposito_Levantamento) op;
                       out.writeObject(new Resposta_Deposito_Levantamento(this.bancoServer.addBalance(dl.id, dl.saldo)));
                       break;
                   case "Op_Movimento":
                       Op_Movimento mv = (Op_Movimento) op;
                       out.writeObject(new Resposta_Movimento(this.bancoServer.movimento(mv.retiro, mv.destino, mv.valor)));
                       break;
                   default:
                       break;
                       
               }
               
            }
        } catch (Exception ex) {
            //Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Saiu o cliente numero: " + numero);
    }
    
}
