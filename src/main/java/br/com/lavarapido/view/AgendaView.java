package br.com.lavarapido.view;

import java.awt.BorderLayout;
import javax.swing.*;

// Painel da agenda com filtros e ações.
public class AgendaView extends JPanel {
    public AgendaView(JTable tabela, JPanel filtros, JButton novo, JButton editar,
                      JButton concluir, JButton cancelar) {
        super(new BorderLayout());
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(filtros, BorderLayout.NORTH);
        JPanel botoes = new JPanel();
        botoes.add(novo);
        botoes.add(editar);
        botoes.add(concluir);
        botoes.add(cancelar);
        add(botoes, BorderLayout.SOUTH);
    }
}
