package br.com.lavarapido.view;

import java.awt.BorderLayout;
import javax.swing.*;

// Painel da área de clientes.
public class ClienteView extends JPanel {
    public ClienteView(JTable tabela, JButton adicionar, JButton editar, JButton excluir) {
        super(new BorderLayout());
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        JPanel botoes = new JPanel();
        botoes.add(adicionar);
        botoes.add(editar);
        botoes.add(excluir);
        add(botoes, BorderLayout.SOUTH);
    }
}
