package br.com.lavarapido.view;

import java.awt.BorderLayout;
import javax.swing.*;

// Painel da área de servicos.
public class ServicoView extends JPanel {
    public ServicoView(JTable tabela, JButton adicionar, JButton editar, JButton excluir) {
        super(new BorderLayout());
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        JPanel botoes = new JPanel();
        botoes.add(adicionar);
        botoes.add(editar);
        botoes.add(excluir);
        add(botoes, BorderLayout.SOUTH);
    }
}
