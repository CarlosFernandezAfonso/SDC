/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Operacoes;

/**
 *
 * @author Carlos
 */
public class Op_Deposito_Levantamento extends Operacao  {
    public int id;
    public int saldo;

    public Op_Deposito_Levantamento(int id, int saldo) {
        this.id = id;
        this.saldo = saldo;
    }
    
}
