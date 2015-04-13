/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jgcs.DataSession;
import net.sf.jgcs.Message;
import net.sf.jgcs.MessageListener;
import net.sf.jgcs.Protocol;
import net.sf.jgcs.ip.IpGroup;
import net.sf.jgcs.ip.IpProtocolFactory;

/**
 *
 * @author Carlos
 */
public class Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        BancoStub bs = new BancoStub();
               
        int saldo = 0;
        for(int i = 0; i < 100000; i++){
                
            if(bs.addBalance(0,10)){
                saldo += 10;
            }
                //b.movimento(1,0, 10);
        }
    }


}
