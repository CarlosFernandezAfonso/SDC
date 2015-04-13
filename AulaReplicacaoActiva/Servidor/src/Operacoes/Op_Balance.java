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
public class Op_Balance extends Operacao  {
    public int id;

    public Op_Balance(int id, SocketAddress origem) {
        super(origem);
        this.id = id;
    }
}
