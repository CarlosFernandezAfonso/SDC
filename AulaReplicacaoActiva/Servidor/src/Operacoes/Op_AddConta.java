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
public class Op_AddConta extends Operacao {
    public int saldo;

    public Op_AddConta(int saldo, SocketAddress origem) {
        super(origem);
        this.saldo = saldo;
    }
}
