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
public class Resposta_AddConta extends Resposta {
    public int id;

    public Resposta_AddConta(int id, SocketAddress destino) {
        super(destino);
        this.id = id;
    }
    
}
