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
public class BancoStub {
    PrintWriter out;
    BufferedReader in;
    
    
    public BancoStub(Socket s){
        try {
            out = new PrintWriter(s.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            
        } catch (Exception ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Integer addConta(int saldo) throws IOException{       
        this.out.println("addConta_" + saldo);
        return Integer.parseInt(in.readLine());
    }
    
    public boolean movimento(Integer retiro, Integer destino, int valor) throws IOException{       
        this.out.println("movimento_" + retiro + "_" + destino + "_" + valor);
        return Boolean.parseBoolean(in.readLine());
    }
        
    public Integer getBalanceAccount(int id) throws IOException{       
        this.out.println("getBalanceAccount_" + id);
        return Integer.parseInt(in.readLine());
    }
    
    public boolean addBalance(int id, int saldo) throws IOException{       
        this.out.println("addBalance_" + id + "_" + saldo);
        return Boolean.parseBoolean(in.readLine());
    }
    
}
