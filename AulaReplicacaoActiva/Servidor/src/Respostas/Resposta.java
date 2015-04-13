/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Respostas;

import Mensagem.Msg;
import java.io.Serializable;
import java.net.SocketAddress;

/**
 *
 * @author Carlos
 */
public abstract class Resposta extends Msg{
    public SocketAddress sa;

    public Resposta(SocketAddress sa) {
        this.sa = sa;
    }
    
    
}
