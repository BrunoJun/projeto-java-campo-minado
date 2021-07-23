package br.com.jun.cm;

import br.com.jun.cm.modelo.Tabuleiro;
import br.com.jun.cm.visao.TabuleiroTerminal;

public class Aplicacao {
    
    public static void main(String[] args) {
        
        Tabuleiro tabuleiro = new Tabuleiro(6, 6, 6);

        new TabuleiroTerminal(tabuleiro);
    }
}
