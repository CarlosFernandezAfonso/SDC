/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cliente;

/**
 *
 * @author Carlos
 */
public class Mensagem {
    public enum Tipo {CREATE,BALANCE,MOVIMENTO,DEPOSITO_LEVANTAMENTO};
    
    final Tipo tipo;
    final Object param ;

    public Mensagem(Tipo tipo, Object param) {
        this.tipo = tipo;
        this.param = param;
    }
    
    
    
}
