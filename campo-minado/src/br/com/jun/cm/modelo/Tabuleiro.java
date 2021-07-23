package br.com.jun.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.jun.cm.excecao.ExplosaoException;

public class Tabuleiro {
    
    private int linhas;
    private int colunas;
    private int quantidadeMinas;

    private final List<Campo> campos = new ArrayList<>();

    public Tabuleiro(int linhas, int colunas, int quantidadeMinas) {

        this.linhas = linhas;
        this.colunas = colunas;
        this.quantidadeMinas = quantidadeMinas;

        gerarCampos();
        associarVizinhos();
        sortearMinas();
    }
    
    private void gerarCampos() {
     
        for (int linha = 0; linha < linhas; linha++) {
           for (int coluna = 0; coluna < colunas; coluna++) {
                
                campos.add(new Campo(linha, coluna));
            }
        }

    }

    private void associarVizinhos() {
        
        for (Campo campo1 : campos) {
            for (Campo campo2 : campos) {
            
                campo1.adicionarVizinho(campo2);
            }
        }

    }

    private void sortearMinas() {
        long minasArmadas = 0;
        Predicate<Campo> minado = campo -> campo.isMinado();

        while (minasArmadas < this.quantidadeMinas){

            int aleatorio = (int) (Math.random() * campos.size());
            campos.get(aleatorio).minar();
            minasArmadas = campos.stream().filter(minado).count();
        }
    }

    public boolean objetivoAlcancado() {

        return campos.stream().allMatch(campo -> campo.objetivoAlcancado());
    }

    public void reiniciar(){

        campos.stream().forEach(campo -> campo.reiniciar());
        sortearMinas();
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append(" ");
        sb.append(" ");
        for (int coluna = 0; coluna < colunas; coluna++) {

            sb.append(" ");
            sb.append(coluna);
            sb.append(" ");
        }
        sb.append("\n");

        int i = 0;
        for (int linha = 0; linha < linhas; linha++) {

            sb.append(linha);
            sb.append(" ");
            for (int coluna = 0; coluna < colunas; coluna++) {
             
                sb.append(" ");
                sb.append(campos.get(i));
                sb.append(" ");
                i++;
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public void abrir(int linha, int coluna){

        try {
            
            campos.parallelStream().filter(campo -> campo.getLinha() == linha && campo.getColuna() == coluna)
            .findFirst()
            .ifPresent(c -> c.abrir());
        } catch (ExplosaoException e) {
            
            campos.forEach(campo -> campo.setAberto(true));
            throw e;
        }
    }

    public void marcar(int linha, int coluna){

        campos.parallelStream().filter(campo -> campo.getLinha() == linha && campo.getColuna() == coluna)
        .findFirst()
        .ifPresent(c -> c.alternarMarcacao());
    }

    //Getters and Setters
    public List<Campo> getCampos() {

        return campos;
    }

    public int getQuantidadeMinas() {

        return quantidadeMinas;
    }
    public int getLinhas() {

        return linhas;
    }

    public int getColunas() {
        
        return colunas;
    }
}
