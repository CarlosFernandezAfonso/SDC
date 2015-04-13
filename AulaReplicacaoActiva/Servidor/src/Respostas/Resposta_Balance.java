/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Respostas;

import java.net.SocketAddress;

/**
 *
 * @author Carlos
 */
public class Resposta_Balance extends Resposta {
    public int balance;

    public Resposta_Balance(int balance, SocketAddress sa) {
        super(sa);
        this.balance = balance;
    }
    
}
