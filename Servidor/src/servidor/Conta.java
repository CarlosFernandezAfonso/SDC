/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servidor;

/**
 *
 * @author Carlos
 */
public class Conta {
    private int montante;

    public Conta(int saldo) {
        this.montante = saldo;
    }

    public void addBalance(int valor) {
        
        this.montante += valor;
        
    }

    public int balance() {
        return this.montante;
    }

    void removeBalance(int valor) {
        this.montante -= valor;
    }
}
