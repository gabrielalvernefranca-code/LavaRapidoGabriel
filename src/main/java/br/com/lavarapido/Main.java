package br.com.lavarapido;

import br.com.lavarapido.controller.ClienteController;
import br.com.lavarapido.controller.ServicoController;
import br.com.lavarapido.controller.AgendaController;
import br.com.lavarapido.view.TelaPrincipal;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClienteController clienteController = new ClienteController();
            ServicoController servicoController = new ServicoController();
            AgendaController agendaController = new AgendaController();

            TelaPrincipal tela = new TelaPrincipal(
                clienteController, servicoController, agendaController
            );
            tela.setVisible(true);
        });
    }
}
