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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.SocketAddress;
import net.sf.jgcs.ClosedSessionException;
import net.sf.jgcs.DataSession;
import net.sf.jgcs.GroupException;
import net.sf.jgcs.Message;
import net.sf.jgcs.MessageListener;
import net.sf.jgcs.annotation.PointToPoint;
import net.sf.jgcs.ip.IpService;

/**
 *
 * @author Carlos
 */
class ThreadCliente extends Thread implements MessageListener {

    private DataSession ds;
    private Banco bancoServer;
    private Operacao operacao;
    private SocketAddress m_senderAddress;
    private final IpService serv;

   

    ThreadCliente(DataSession ds, Banco bancoServer) {
        this.ds = ds;
        try {
            this.ds.setMessageListener((MessageListener) this);
        } catch (ClosedSessionException ex) {
            System.out.println("DFSGH");
            Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.serv = new IpService();
    }


    public void  run() {
        System.out.println("Servidor Online");
        try {
            while(true){
                synchronized(this){
                    while(operacao == null){
                        wait();
                    }
                }
               
                   
               Resposta resp = null;
               switch(operacao.getClass().getSimpleName()){
                   case "Op_AddConta":
                       Op_AddConta a = (Op_AddConta) operacao;
                       resp = new Resposta_AddConta(this.bancoServer.addConta(a.saldo));
                       writeMsgMultiCast(resp);  
                       break;
                   case "Op_Balance":
                       Op_Balance b = (Op_Balance) operacao;                 
                       resp = new Resposta_Balance(this.bancoServer.getBalanceAccount(b.id));
                       writeMsgMultiCast(resp);   
                       break;
                   case "Op_Deposito_Levantamento":
                       Op_Deposito_Levantamento dl = (Op_Deposito_Levantamento) operacao;
                       resp = new Resposta_Deposito_Levantamento(this.bancoServer.addBalance(dl.id, dl.saldo));
                       writeMsgMultiCast(resp);   
                       break;
                   case "Op_Movimento":
                       Op_Movimento mv = (Op_Movimento) operacao;
                       resp = new Resposta_Movimento(this.bancoServer.movimento(mv.retiro, mv.destino, mv.valor));
                       writeMsgMultiCast(resp);                       
                       break;
                   default:
                       break;
                       
               }
               
            }
        } catch (Exception ex) {
            Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void writeMsgMultiCast(Resposta resp) throws GroupException, IOException{
        Message msg = ds.createMessage();
        msg.setPayload(toByte(resp));
        
        ds.multicast(msg, this.serv , null, new PointToPoint(this.m_senderAddress));
  
    }
    
    
    public byte[] toByte(Resposta o) throws IOException{
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        
        return baos.toByteArray();
        
    }
    
    public Operacao toOperacao(byte[] b) throws IOException, ClassNotFoundException{
        
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        ObjectInputStream ois = new ObjectInputStream(bais);
        
        
        return (Operacao) ois.readObject();
        
    }
    
    @Override
    public Object onMessage(Message m){
        byte[] array = m.getPayload();

        try{
            operacao = toOperacao(array);
            m_senderAddress = m.getSenderAddress();
            notifyAll();
        } catch (ClassNotFoundException e){
            // nada
        } catch (IOException ex) {
            Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
