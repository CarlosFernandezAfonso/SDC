/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Operacoes.*;
import Respostas.*;

/**
 *
 * @author Carlos
 */
public class BancoStub {
    ObjectOutputStream out;
    ObjectInputStream in;
    
    
    public BancoStub(Socket s){
        try {
            out = new ObjectOutputStream(s.getOutputStream());
            in = new ObjectInputStream(s.getInputStream());
            
        } catch (Exception ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Integer addConta(int saldo) throws IOException, ClassNotFoundException{
        Op_AddConta op = new Op_AddConta(saldo);
        this.out.writeObject(op);
        Resposta_AddConta res = (Resposta_AddConta) in.readObject();
        return res.id;
    }
    
    public boolean movimento(Integer retiro, Integer destino, int valor) throws IOException, ClassNotFoundException{ 
        Op_Movimento op = new Op_Movimento(retiro, destino, valor);
        this.out.writeObject(op);
        Resposta_Movimento res = (Resposta_Movimento) in.readObject();
        return res.res;
    }
        
    public Integer getBalanceAccount(int id) throws IOException, ClassNotFoundException{       
        Op_Balance op = new Op_Balance(id);
        this.out.writeObject(op);
        Resposta_Balance res = (Resposta_Balance) in.readObject();
        
        return res.balance;
    }
    
    public boolean addBalance(int id, int saldo) throws IOException, ClassNotFoundException{       
        Op_Deposito_Levantamento op = new Op_Deposito_Levantamento(id, saldo);
        this.out.writeObject(op);
        Resposta_Deposito_Levantamento res = (Resposta_Deposito_Levantamento) in.readObject();
        
        return res.res;
    }
    
}
