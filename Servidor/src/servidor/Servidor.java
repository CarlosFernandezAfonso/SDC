/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servidor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jgcs.DataSession;
import net.sf.jgcs.GroupException;
import net.sf.jgcs.MessageListener;
import net.sf.jgcs.Protocol;
import net.sf.jgcs.ip.IpGroup;
import net.sf.jgcs.ip.IpProtocolFactory;


/**
 *
 * @author Carlos
 */
public class Servidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Servidor s = new Servidor();
        s.runProg();
    }
    
    public void runProg(){
        
        Banco bancoServer = new Banco();
        bancoServer.addConta(0);
        try{
            
            IpProtocolFactory pf = new IpProtocolFactory();
            IpGroup gc = new IpGroup("225.1.2.3:12345");
            Protocol p = pf.createProtocol();
            DataSession ds = p.openDataSession(gc);
           
            
            ThreadCliente tc = new ThreadCliente(ds, bancoServer);
            tc.start();
            
//            
//            ServerSocket serverSocket = new ServerSocket(4567);
//            int n = 0;
//            while(true){
//                Socket clientSocket = serverSocket.accept();
//                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
//                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
//                ThreadCliente tc = new ThreadCliente(out, in, n, bancoServer);
//                n = n +1;
//                Thread t = new Thread(tc);
//                t.start();
//            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GroupException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
}
