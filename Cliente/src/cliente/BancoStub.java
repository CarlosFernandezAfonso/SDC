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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import net.sf.jgcs.DataSession;
import net.sf.jgcs.Message;

/**
 *
 * @author Carlos
 */
public class BancoStub {

    DataSession ds;
    Resposta resposta = null;
    
    
    public BancoStub(DataSession s){
        this.ds = ds;
    }
    
    public Integer addConta(int saldo) throws IOException, ClassNotFoundException, InterruptedException{
        Op_AddConta op = new Op_AddConta(saldo);
        Message msg = ds.createMessage();
        msg.setPayload(toByte(op));
        ds.multicast(msg, null, null);

        while(resposta == null){
            wait();
        }

        return ((Resposta_AddConta) resposta).id;
    }
    
    public boolean movimento(Integer retiro, Integer destino, int valor) throws IOException, ClassNotFoundException, InterruptedException{ 
        Op_Movimento op = new Op_Movimento(retiro, destino, valor);
        
        Message msg = ds.createMessage();
        msg.setPayload(toByte(op));
        ds.multicast(msg, null, null);

        while(resposta == null){
            wait();
        }
 
        Resposta_Movimento res = (Resposta_Movimento) resposta;
        return res.res;
    }
        
    public Integer getBalanceAccount(int id) throws IOException, ClassNotFoundException, InterruptedException{       
        Op_Balance op = new Op_Balance(id);
        Message msg = ds.createMessage();
        msg.setPayload(toByte(op));
        ds.multicast(msg, null, null);

        while(resposta == null){
            wait();
        }
 
        Resposta_Balance res = (Resposta_Balance) resposta;
        
        return res.balance;
    }
    
    public boolean addBalance(int id, int saldo) throws IOException, ClassNotFoundException, InterruptedException{       
        Op_Deposito_Levantamento op = new Op_Deposito_Levantamento(id, saldo);
        Message msg = ds.createMessage();
        msg.setPayload(toByte(op));
        ds.multicast(msg, null, null);

        while(resposta == null){
            wait();
        }
 
        Resposta_Deposito_Levantamento res = (Resposta_Deposito_Levantamento) resposta;
        
        return res.res;
    }
    
    
    
    public byte[] toByte(Operacao o) throws IOException{
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        
        return baos.toByteArray();
        
    }
    
    public Resposta toResposta(byte[] b) throws IOException, ClassNotFoundException{
        
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        ObjectInputStream ois = new ObjectInputStream(bais);
        
        
        return (Resposta) ois.readObject();
        
    }
    
    public Resposta onMessage(Message m) throws IOException, ClassNotFoundException{
        byte[] array = m.getPayload();
        
        
        return toResposta(array);
    }
}
