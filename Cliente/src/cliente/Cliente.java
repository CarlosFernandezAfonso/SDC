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

/**
 *
 * @author Carlos
 */
public class Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 4567);
            
            BancoStub b = new BancoStub(s);
            
            int saldo = 0;
            for(int i = 0; i < 100000; i++){
                
                if(b.addBalance(0,10)){
                    saldo += 10;
                }
                //b.movimento(1,0, 10);
            }
            System.out.println("Saldo Real: " + b.getBalanceAccount(0) + "\nSaldo local: " + saldo);
            
        } catch (Exception ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
