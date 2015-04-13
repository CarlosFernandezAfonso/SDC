/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Operacoes;

import Mensagem.Msg;
import java.io.Serializable;
import java.net.SocketAddress;

/**
 *
 * @author Carlos
 */
public abstract class Operacao extends Msg{
    public SocketAddress sa;
    
    public Operacao(SocketAddress sa){
        this.sa = sa;
    }
}
