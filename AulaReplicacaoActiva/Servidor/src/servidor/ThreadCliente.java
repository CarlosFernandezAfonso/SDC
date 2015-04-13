/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servidor;

import Mensagem.Msg;
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
import java.util.ArrayList;
import net.sf.jgcs.ClosedSessionException;
import net.sf.jgcs.ControlSession;
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
    private ControlSession cs;
    private Banco bancoServer;
    private Msg mensagem;
    private SocketAddress m_senderAddress;
    private final IpService serv;
    private int estadoReplicacao;

   

    ThreadCliente(DataSession ds, ControlSession cs, Banco bancoServer) {
        this.ds = ds;
        this.cs = cs;
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
        ArrayList<Msg> operacaoQueue = new ArrayList<Msg>();
        try {
            while(true){
                synchronized(this){
                    while(mensagem == null){
                        wait();
                    }
                }
                
                switch(this.estadoReplicacao){
                    case 1:
                        if(m_senderAddress == cs.getLocalAddress()){
                            this.estadoReplicacao = 2;
                        }
                        break;
                    case 2:
                        if(mensagem.getClass().getSimpleName() == "Resposta_PedidoEstado"){
                            Resposta_PedidoEstado rpe = (Resposta_PedidoEstado) mensagem;
                            if(rpe.sa == cs.getLocalAddress()){
                                this.bancoServer = rpe.banco;
                                for(Msg msg : operacaoQueue){
                                    responder(msg);
                                }
                                this.estadoReplicacao = 3;
                            }
                        }
                        else{
                            if(mensagem instanceof Resposta){
                                operacaoQueue.add(mensagem);
                            }
                        }
                        break;
                    case 3:
                        responder(mensagem);
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
    
    public Msg toMsg(byte[] b) throws IOException, ClassNotFoundException{
        
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        ObjectInputStream ois = new ObjectInputStream(bais);
        
        
        return (Msg) ois.readObject();
        
    }
    
    @Override
    public Object onMessage(Message m){
        byte[] array = m.getPayload();

        try{
            mensagem = toMsg(array);
            m_senderAddress = m.getSenderAddress();
            notifyAll();
        } catch (ClassNotFoundException e){
            // nada
        } catch (IOException ex) {
            Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public void sendStateRequest(){
        
    }
    
    
    public void responder(Msg ms) throws IOException{
        Resposta resp = null;
        
        //Caso for do tipo resposta não queremos Responder
        if(!(ms instanceof Operacao)){
            return;
        }
        
        Operacao ope = (Operacao) ms;
        
        switch(ope.getClass().getSimpleName()){
            case "Op_AddConta":
                Op_AddConta a = (Op_AddConta) ope;
                resp = new Resposta_AddConta(this.bancoServer.addConta(a.saldo), ope.sa);
                writeMsgMultiCast(resp);  
                break;
            case "Op_Balance":
                Op_Balance b = (Op_Balance) ope;                 
                resp = new Resposta_Balance(this.bancoServer.getBalanceAccount(b.id), ope.sa);
                writeMsgMultiCast(resp);   
                break;
            case "Op_Deposito_Levantamento":
                Op_Deposito_Levantamento dl = (Op_Deposito_Levantamento) ope;
                resp = new Resposta_Deposito_Levantamento(this.bancoServer.addBalance(dl.id, dl.saldo), ope.sa);
                writeMsgMultiCast(resp);   
                break;
            case "Op_Movimento":
                Op_Movimento mv = (Op_Movimento) ope;
                resp = new Resposta_Movimento(this.bancoServer.movimento(mv.retiro, mv.destino, mv.valor), ope.sa);
                writeMsgMultiCast(resp);                       
                break;
            case "SOp_PedidoEstado":
                SOp_PedidoEstado pe = (SOp_PedidoEstado) ope;
                resp = new Resposta_PedidoEstado(this.bancoServer, ope.sa);
                writeMsgMultiCast(resp);
            default:
                break;

        }
    }
}
