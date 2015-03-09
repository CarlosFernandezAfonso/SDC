/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servidor;

import java.util.HashMap;

/**
 *
 * @author Carlos
 */
public class Banco {
    private HashMap<Integer, Conta> banco;
    private Integer numId=0;

    public Banco() {
        this.banco = new HashMap<Integer, Conta>();
    }
    
    public synchronized Integer addConta(int saldo){
                
        this.banco.put(numId, new Conta(saldo));
        numId++;
        
        return numId - 1;
    }
    
    public synchronized boolean movimento(Integer retiro, Integer destino, int valor){
        Conta cRetiro = this.banco.get(retiro);
        
        if(cRetiro.balance() >= valor){
            Conta cDestino = this.banco.get(destino); 
            cRetiro.removeBalance(valor);
            cDestino.addBalance(valor);
            return true;
        }
        else{
            return false;
        }
    }
    
    public synchronized int getBalanceAccount(Integer id){
        return this.banco.get(id).balance();
    }
    
    
    public synchronized boolean addBalance(Integer id, int montante){
        if(montante < 0){
            return false;
        }
        this.banco.get(id).addBalance(montante);
        return true;
    }
    
    
}
