/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Operacoes;

import java.net.SocketAddress;

/**
 *
 * @author Carlos
 */
public class Op_Movimento extends Operacao  {
    public Integer retiro;
    public Integer destino;
    public int valor;

    public Op_Movimento(Integer retiro, Integer destino, int valor, SocketAddress sa) {
        super(sa);
        this.retiro = retiro;
        this.destino = destino;
        this.valor = valor;  
    }
    
}
