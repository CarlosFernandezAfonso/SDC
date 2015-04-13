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
public class Resposta_Movimento extends Resposta{
    public boolean res;

    public Resposta_Movimento(boolean res, SocketAddress destino) {
        super(destino);
        this.res = res;
    }
    
    
}
