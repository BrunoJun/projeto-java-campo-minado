package br.com.jun.cm.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TabuleiroTeste {

    private Tabuleiro tabuleiro;
    
    @BeforeEach
    void iniciarCampo(){

        tabuleiro = new Tabuleiro(4, 4, 5);
    }

    @Test
    void TesteGerarCampos(){

        assertEquals(16, tabuleiro.getCampos().size());
    }

    @Test
    void TesteAssociarCamposVizinhosVerdadeiros(){

        Campo campoComparacao = tabuleiro.getCampos().get(6);
        Campo campoVizinho1 = tabuleiro.getCampos().get(5);
        Campo campoVizinho2 = tabuleiro.getCampos().get(7);
        Campo campoVizinho3 = tabuleiro.getCampos().get(2);
        Campo campoVizinho4 = tabuleiro.getCampos().get(11);

        assertEquals(campoVizinho1, campoComparacao.getVizinhos().get(3));
        assertEquals(campoVizinho2, campoComparacao.getVizinhos().get(4));
        assertEquals(campoVizinho3, campoComparacao.getVizinhos().get(1));
        assertEquals(campoVizinho4, campoComparacao.getVizinhos().get(7));
    }

    @Test
    void TesteAssociarCamposVizinhosFalsos(){

        Campo campoComparacao = tabuleiro.getCampos().get(6);
        Campo campoVizinho1 = tabuleiro.getCampos().get(0);
        Campo campoVizinho2 = tabuleiro.getCampos().get(4);
        Campo campoVizinho3 = tabuleiro.getCampos().get(12);
        Campo campoVizinho4 = tabuleiro.getCampos().get(13);

        assertNotEquals(campoVizinho1, campoComparacao.getVizinhos().get(3));
        assertNotEquals(campoVizinho2, campoComparacao.getVizinhos().get(4));
        assertNotEquals(campoVizinho3, campoComparacao.getVizinhos().get(1));
        assertNotEquals(campoVizinho4, campoComparacao.getVizinhos().get(7));
    }

    @Test
    void TesteSortearMinas(){
        int quantidadeMinados = 0;

        for (Campo campo : tabuleiro.getCampos()) {
            
            if (campo.isMinado()){

                quantidadeMinados++;
            }
        }

        assertEquals(tabuleiro.getQuantidadeMinas(), quantidadeMinados);
    }

    @Test
    void TesteToString(){
        String comparacao = "";

        for (int i = 0; i < tabuleiro.getLinhas(); i++) {
            for (int j = 0; j < tabuleiro.getColunas(); j++) {
                
                comparacao += " ";
                comparacao += "?";
                comparacao += " ";
            }
            comparacao += "\n";
        }

        assertEquals(comparacao, tabuleiro.toString());
    }

    @Test
    void TesteMarcar(){
        int campoSemMina = 0;
        int campoSemMinaLinha = 0;
        int campoSemMinaColuna = 0;
        int tamanho = tabuleiro.getCampos().size();

        for (int i = 0; i < tamanho; i++) {
            
            if (tabuleiro.getCampos().get(i).isMinado()){

                campoSemMina = i;
                campoSemMinaColuna =  tabuleiro.getCampos().get(i).getColuna();
                campoSemMinaLinha = tabuleiro.getCampos().get(i).getLinha();
                break;
            }
        }

        tabuleiro.marcar(campoSemMinaLinha, campoSemMinaColuna);
        String stringAtual = tabuleiro.getCampos().get(campoSemMina).toString();

        assertEquals("x", stringAtual);
    }
}

