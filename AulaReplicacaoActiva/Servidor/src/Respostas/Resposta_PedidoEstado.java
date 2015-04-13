/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Respostas;

import java.net.SocketAddress;
import servidor.Banco;

/**
 *
 * @author Carlos
 */
public class Resposta_PedidoEstado extends Resposta {
    public Banco banco;
    
    public Resposta_PedidoEstado(Banco banco, SocketAddress destino){
        super(destino);
        this.banco = banco;
    }
}
