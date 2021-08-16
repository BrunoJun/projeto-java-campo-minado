package br.com.jun.cm.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CampoTeste {
    
    private Campo campo;

    @BeforeEach
    void iniciarCampo(){

        campo = new Campo(3, 3);
    }

    @Test
    void testeVizinhoRealLateral(){
        Campo vizinho = new Campo(3, 2);

        boolean resultado = campo.adicionarVizinho(vizinho);

        assertTrue(resultado);
    }

    @Test
    void testeVizinhoRealDiagonal(){
        Campo vizinho = new Campo(2, 2);

        boolean resultado = campo.adicionarVizinho(vizinho);

        assertTrue(resultado);
    }

    @Test
    void testeVizinhoFalsoLateral(){
        Campo vizinho = new Campo(3, 1);

        boolean resultado = campo.adicionarVizinho(vizinho);

        assertFalse(resultado);
    }

    @Test
    void testeVizinhoFalsoDiagonal(){
        Campo vizinho = new Campo(5, 5);

        boolean resultado = campo.adicionarVizinho(vizinho);

        assertFalse(resultado);
    }

    @Test
    void testeAlternarMarcacaoComValorPadrao(){

        assertFalse(campo.isMarcado());
    }

    @Test
    void testeAlternarMarcacaoComValorTrocado(){

        campo.alternarMarcacao();
        assertTrue(campo.isMarcado());
    }

    @Test
    void testeAlternarMarcacaoDuasChamadas(){

        campo.alternarMarcacao();
        campo.alternarMarcacao();
        assertFalse(campo.isMarcado());
    }

    @Test
    void testeAbrirNaoMinadoENaoMarcado(){

        assertTrue(campo.abrir());
    }

    @Test
    void testeAbrirNaoMinadoEMarcado(){

        campo.alternarMarcacao();
        assertFalse(campo.abrir());
    }

    @Test
    void testeAbrirMinadoEMarcado(){

        campo.alternarMarcacao();
        campo.minar();
        assertFalse(campo.abrir());
    }

    @Test
    void testeAbrirComVizinhos(){
        Campo vizinho1 = new Campo(2, 2);
        Campo vizinhoDovizinho1 = new Campo(1, 1);
        
        campo.adicionarVizinho(vizinho1);
        vizinho1.adicionarVizinho(vizinhoDovizinho1);

        campo.abrir();
        
        assertTrue(vizinhoDovizinho1.isAberto() && vizinho1.isAberto());
    }

    @Test
    void testeAbrirComVizinhos2(){
        Campo vizinho1 = new Campo(2, 2);
        Campo vizinhoDovizinho1 = new Campo(1, 1);
        Campo vizinho2Dovizinho1 = new Campo(1, 1);
        
        campo.adicionarVizinho(vizinho1);
        vizinho1.adicionarVizinho(vizinhoDovizinho1);
        vizinho1.adicionarVizinho(vizinho2Dovizinho1);

        vizinhoDovizinho1.minar();
        campo.abrir();
        
        assertFalse(vizinho2Dovizinho1.isAberto());
    }

    @Test
    void testeMinasNaVizinhanca(){
        Campo vizinho1 = new Campo(2, 2);
        Campo vizinho2 = new Campo(1, 1);

        vizinho1.adicionarVizinho(campo);
        vizinho1.adicionarVizinho(vizinho2);

        vizinho2.minar();
        campo.minar();

        assertEquals(2, vizinho1.minasNaVizinhanca());
    }

    @Test
    void testeToStringMarcado(){

        campo.alternarMarcacao();

        assertEquals("x", campo.toString());
    }

    @Test
    void testeToStringExplodido(){

        campo.minar();

        try{

            campo.abrir();
        } catch (Exception e){
        }

        assertEquals("*", campo.toString());
    }

    @Test
    void testeToStringNumeroMinasVizinhas(){

        Campo vizinho1 = new Campo(2, 2);
        Campo vizinho2 = new Campo(2, 2);
        vizinho1.minar();
        vizinho2.minar();

        campo.adicionarVizinho(vizinho1);
        campo.adicionarVizinho(vizinho2);
        campo.abrir();

        assertEquals("2", campo.toString());
    }

    @Test
    void testeToStringAberto(){

        campo.abrir();

        assertEquals(" ", campo.toString());
    }

    @Test
    void testeToStringFechado(){

        assertEquals("?", campo.toString());
    }
}
