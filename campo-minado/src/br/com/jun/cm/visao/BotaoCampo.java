package br.com.jun.cm.visao;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;


import java.awt.Color;
import java.awt.event.*;

import br.com.jun.cm.modelo.Campo;
import br.com.jun.cm.modelo.CampoEvento;
import br.com.jun.cm.modelo.CampoObservador;

public class BotaoCampo extends JButton implements CampoObservador, MouseListener{

    private final Color BG_PADRAO = new Color(184, 184, 184);
    private final Color BG_MARCADO = new Color(8, 179, 247);
    private final Color BG_EXPLODIR = new Color(189, 66, 68);
    private final Color TEXTO_VERDE = new Color(0, 100, 0);

    private final Icon iconBomba = new ImageIcon("campo-minado\\src\\br\\com\\jun\\cm\\visao\\img\\bomb.png");
    private final Icon iconMarca = new ImageIcon("campo-minado\\src\\br\\com\\jun\\cm\\visao\\img\\flag.png");

    private Campo campo;
    
    public BotaoCampo(Campo campo) {
        
        this.campo = campo;
        setBackground(BG_PADRAO);
        setBorder(BorderFactory.createBevelBorder(0));
        campo.registrarObservador(this);
        addMouseListener(this);
    }

    @Override
    public void eventoOcorreu(Campo c, CampoEvento e) {
        
        switch (e) {
            case ABRIR:
                
                aplicarEstiloAbrir();
                break;
                            
            case MARCAR:
                
                aplicarEstiloMarcar();
                break;
            case EXPLODIR:
                
                aplicarEstiloExplodir();
                break;
            default:

                aplicarEstiloPadrao();
        }
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        if (e.getButton() == 1){

            campo.abrir();
        } else {

            campo.alternarMarcacao();
        }
    }

    public void mouseReleased(java.awt.event.MouseEvent e) {}
    public void mouseEntered(java.awt.event.MouseEvent e){}
    public void mouseExited(java.awt.event.MouseEvent e){}
    public void mouseClicked(java.awt.event.MouseEvent e){}

    private void aplicarEstiloPadrao() {

        setBackground(BG_PADRAO);
        setBorder(BorderFactory.createBevelBorder(0));
        setText("");
        setIcon(null);
    }

    private void aplicarEstiloExplodir() {

        setBackground(BG_EXPLODIR);
        setIcon(iconBomba);
    }

    private void aplicarEstiloMarcar() {

        setBackground(BG_MARCADO);
        setIcon(iconMarca);
    }

    private void aplicarEstiloAbrir() {
        
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        if (campo.isMinado()) {

            setBackground(BG_EXPLODIR);
            return;
        }

        setBackground(BG_PADRAO);

        switch (campo.minasNaVizinhanca()) {
            case 1:
                
                setForeground(TEXTO_VERDE);
                break;
            case 2:

                setForeground(Color.BLUE);
                break;

            case 3:

                setForeground(Color.YELLOW);
                break;

            case 4:
            case 5:
            case 6:

                setForeground(Color.RED);
                break;
            default:

                setForeground(Color.PINK);
                break;
        }

        String valor = !campo.vizinhancaSegura() ? campo.minasNaVizinhanca() + "" : "";

        setText(valor);
    }
}
