package br.com.lavarapido.view;

import br.com.lavarapido.controller.*;
import br.com.lavarapido.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TelaPrincipal extends JFrame {
    private ClienteController clientes;
    private ServicoController servicos;
    private AgendaController agenda;
    private VeiculoController veiculos = new VeiculoController();
    private DefaultTableModel modeloVeiculos = new DefaultTableModel(
            new String[]{"ID", "Cliente", "Placa", "Marca", "Modelo", "Cor"}, 0);
    private JTable tabelaVeiculos = new JTable(modeloVeiculos);
    private JComboBox<String> filtroAgenda = new JComboBox<>(new String[]{"Todos", "Dia", "Semana"});
    private JTextField dataFiltro = new JTextField(java.time.LocalDate.now().format(
            DateTimeFormatter.ofPattern("dd/MM/yyyy")), 10);

    private DefaultTableModel modeloClientes = new DefaultTableModel(
            new String[]{"ID", "Nome", "CPF", "Telefone", "E-mail", "Placa"}, 0);
    private DefaultTableModel modeloServicos = new DefaultTableModel(
            new String[]{"ID", "Nome", "Descrição", "Preço"}, 0);
    private DefaultTableModel modeloAgenda = new DefaultTableModel(
            new String[]{"ID", "Cliente", "Serviço", "Data e hora", "Status"}, 0);

    private JTable tabelaClientes = new JTable(modeloClientes);
    private JTable tabelaServicos = new JTable(modeloServicos);
    private JTable tabelaAgenda = new JTable(modeloAgenda);

    private DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public TelaPrincipal(ClienteController c, ServicoController s, AgendaController a) {
        clientes = c;
        servicos = s;
        agenda = a;

        setTitle("Sistema do Lava-rápido");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Clientes", painelClientes());
        abas.addTab("Serviços", painelServicos());
        abas.addTab("Veículos", painelVeiculos());
        abas.addTab("Agenda", painelAgenda());
        add(abas);

        atualizarTabelas();
    }

    private JPanel painelClientes() {

        JButton adicionar = new JButton("Adicionar");
        JButton editar = new JButton("Editar");
        JButton excluir = new JButton("Excluir");

        adicionar.addActionListener(e -> formularioCliente(null));
        editar.addActionListener(e -> {
            int linha = tabelaClientes.getSelectedRow();
            if (linha == -1) {
                mensagem("Selecione um cliente.");
            } else {
                int id = (int) modeloClientes.getValueAt(tabelaClientes.convertRowIndexToModel(linha), 0);
                Cliente c = procurarCliente(id);
                formularioCliente(c);
            }
        });
        excluir.addActionListener(e -> {
            int linha = tabelaClientes.getSelectedRow();
            if (linha != -1) {
                int id = (int) modeloClientes.getValueAt(tabelaClientes.convertRowIndexToModel(linha), 0);
                veiculos.excluirPorCliente(id);
                clientes.excluir(id);
                atualizarTabelas();
            } else {
                mensagem("Selecione um cliente.");
            }
        });

        return new ClienteView(tabelaClientes, adicionar, editar, excluir);
    }

    private void formularioCliente(Cliente antigo) {
        JTextField nome = new JTextField(antigo == null ? "" : antigo.getNome());
        JTextField cpf = new JTextField(antigo == null ? "" : antigo.getCpf());
        JTextField telefone = new JTextField(antigo == null ? "" : antigo.getTelefone());
        JTextField email = new JTextField(antigo == null ? "" : antigo.getEmail());
        JTextField placa = new JTextField(antigo == null ? "" : antigo.getPlaca());

        JPanel p = new JPanel(new GridLayout(5, 2, 5, 5));
        p.add(new JLabel("Nome:")); p.add(nome);
        p.add(new JLabel("CPF:")); p.add(cpf);
        p.add(new JLabel("Telefone:")); p.add(telefone);
        p.add(new JLabel("E-mail:")); p.add(email);
        p.add(new JLabel("Placa:")); p.add(placa);

        int resposta = JOptionPane.showConfirmDialog(this, p, "Dados do cliente",
                JOptionPane.OK_CANCEL_OPTION);
        if (resposta == JOptionPane.OK_OPTION) {
            int id = antigo == null ? 0 : antigo.getId();
            try {
                clientes.salvar(new Cliente(id, nome.getText(), cpf.getText(),
                        telefone.getText(), email.getText(), placa.getText()));
                atualizarTabelas();
            } catch (Exception ex) {
                mensagem(ex.getMessage());
            }
        }
    }

    private JPanel painelServicos() {

        JButton adicionar = new JButton("Adicionar");
        JButton editar = new JButton("Editar");
        JButton excluir = new JButton("Excluir");

        adicionar.addActionListener(e -> formularioServico(null));
        editar.addActionListener(e -> {
            int linha = tabelaServicos.getSelectedRow();
            if (linha == -1) {
                mensagem("Selecione um serviço.");
            } else {
                int id = (int) modeloServicos.getValueAt(tabelaServicos.convertRowIndexToModel(linha), 0);
                formularioServico(procurarServico(id));
            }
        });
        excluir.addActionListener(e -> {
            int linha = tabelaServicos.getSelectedRow();
            if (linha != -1) {
                servicos.excluir((int) modeloServicos.getValueAt(tabelaServicos.convertRowIndexToModel(linha), 0));
                atualizarTabelas();
            } else {
                mensagem("Selecione um serviço.");
            }
        });

        return new ServicoView(tabelaServicos, adicionar, editar, excluir);
    }

    private void formularioServico(Servico antigo) {
        JTextField nome = new JTextField(antigo == null ? "" : antigo.getNome());
        JTextField descricao = new JTextField(antigo == null ? "" : antigo.getDescricao());
        JTextField preco = new JTextField(antigo == null ? "" : String.valueOf(antigo.getPreco()));

        JPanel p = new JPanel(new GridLayout(3, 2, 5, 5));
        p.add(new JLabel("Nome:")); p.add(nome);
        p.add(new JLabel("Descrição:")); p.add(descricao);
        p.add(new JLabel("Preço:")); p.add(preco);

        int resposta = JOptionPane.showConfirmDialog(this, p, "Dados do serviço",
                JOptionPane.OK_CANCEL_OPTION);
        if (resposta == JOptionPane.OK_OPTION) {
            try {
                int id = antigo == null ? 0 : antigo.getId();
                double valor = Double.parseDouble(preco.getText().replace(",", "."));
                servicos.salvar(new Servico(id, nome.getText(), descricao.getText(), valor));
                atualizarTabelas();
            } catch (Exception ex) {
                mensagem("Confira os dados e o preço digitado.");
            }
        }
    }

    private JPanel painelAgenda() {

        JButton novo = new JButton("Novo agendamento");
        JButton editar = new JButton("Editar agendamento");
        JButton concluir = new JButton("Concluir");
        JButton cancelar = new JButton("Cancelar");

        novo.addActionListener(e -> formularioAgendamento(null));
        editar.addActionListener(e -> {
            int linha = tabelaAgenda.getSelectedRow();
            if (linha == -1) { mensagem("Selecione um agendamento."); return; }
            int id = (int) modeloAgenda.getValueAt(tabelaAgenda.convertRowIndexToModel(linha), 0);
            for (Agendamento a : agenda.listar()) {
                if (a.getId() == id) { formularioAgendamento(a); return; }
            }
        });
        concluir.addActionListener(e -> mudarStatus("Concluído"));
        cancelar.addActionListener(e -> mudarStatus("Cancelado"));

        JPanel filtros = new JPanel();
        filtros.add(new JLabel("Exibir:"));
        filtros.add(filtroAgenda);
        filtros.add(new JLabel("Data (dd/MM/aaaa):"));
        filtros.add(dataFiltro);
        JButton filtrar = new JButton("Filtrar");
        filtrar.addActionListener(e -> atualizarTabelas());
        filtros.add(filtrar);
        return new AgendaView(tabelaAgenda, filtros, novo, editar, concluir, cancelar);
    }

    private void formularioAgendamento(Agendamento antigo) {
        if (clientes.listar().isEmpty() || servicos.listar().isEmpty()) {
            mensagem("Cadastre um cliente e um serviço primeiro.");
            return;
        }

        JComboBox<Cliente> comboCliente = new JComboBox<>(clientes.listar().toArray(new Cliente[0]));
        JComboBox<Servico> comboServico = new JComboBox<>(servicos.listar().toArray(new Servico[0]));
        if (antigo != null) {
            comboCliente.setSelectedItem(antigo.getCliente());
            comboServico.setSelectedItem(antigo.getServico());
        }
        JTextField data = new JTextField((antigo == null ? LocalDateTime.now().plusDays(1)
                : antigo.getDataHora()).format(formato));

        JPanel p = new JPanel(new GridLayout(3, 2, 5, 5));
        p.add(new JLabel("Cliente:")); p.add(comboCliente);
        p.add(new JLabel("Serviço:")); p.add(comboServico);
        p.add(new JLabel("Data e hora:")); p.add(data);

        int resposta = JOptionPane.showConfirmDialog(this, p, antigo == null ? "Novo agendamento" : "Editar agendamento",
                JOptionPane.OK_CANCEL_OPTION);
        if (resposta == JOptionPane.OK_OPTION) {
            try {
                Cliente cliente = (Cliente) comboCliente.getSelectedItem();
                Servico servico = (Servico) comboServico.getSelectedItem();
                LocalDateTime dataHora = LocalDateTime.parse(data.getText(), formato);
                if (antigo == null) agenda.agendar(cliente, servico, dataHora);
                else agenda.editar(antigo.getId(), cliente, servico, dataHora);
                atualizarTabelas();
            } catch (Exception ex) {
                mensagem(ex.getMessage() == null ? "Confira a data e hora." : ex.getMessage());
            }
        }
    }

    private void mudarStatus(String status) {
        int linha = tabelaAgenda.getSelectedRow();
        if (linha == -1) {
            mensagem("Selecione um agendamento.");
            return;
        }
        int id = (int) modeloAgenda.getValueAt(tabelaAgenda.convertRowIndexToModel(linha), 0);
        agenda.mudarStatus(id, status);
        atualizarTabelas();
    }

    private Cliente procurarCliente(int id) {
        for (Cliente c : clientes.listar()) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    private Servico procurarServico(int id) {
        for (Servico s : servicos.listar()) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    private void atualizarTabelas() {
        modeloClientes.setRowCount(0);
        for (Cliente c : clientes.listar()) {
            modeloClientes.addRow(new Object[]{c.getId(), c.getNome(), c.getCpf(),
                    c.getTelefone(), c.getEmail(), c.getPlaca()});
        }

        modeloServicos.setRowCount(0);
        for (Servico s : servicos.listar()) {
            modeloServicos.addRow(new Object[]{s.getId(), s.getNome(),
                    s.getDescricao(), s.getPreco()});
        }

        modeloVeiculos.setRowCount(0);
        for (Veiculo v : veiculos.listar()) {
            modeloVeiculos.addRow(new Object[]{v.getId(), v.getCliente().getNome(),
                    v.getPlaca(), v.getMarca(), v.getModelo(), v.getCor()});
        }

        java.util.ArrayList<Agendamento> exibidos = agenda.listar();
        if (filtroAgenda.getSelectedIndex() != 0) {
            try {
                java.time.LocalDate dia = java.time.LocalDate.parse(dataFiltro.getText(),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                exibidos = filtroAgenda.getSelectedIndex() == 1
                        ? agenda.listarDia(dia) : agenda.listarSemana(dia);
            } catch (Exception ex) {
                mensagem("Informe a data no formato dd/MM/aaaa.");
                return;
            }
        }
        modeloAgenda.setRowCount(0);
        for (Agendamento a : exibidos) {
            modeloAgenda.addRow(new Object[]{a.getId(), a.getCliente().getNome(),
                    a.getServico().getNome(), a.getDataHora().format(formato), a.getStatus()});
        }
    }


    private JPanel painelVeiculos() {
        JButton adicionar = new JButton("Adicionar");
        JButton editar = new JButton("Editar");
        JButton excluir = new JButton("Excluir");
        adicionar.addActionListener(e -> formularioVeiculo(null));
        editar.addActionListener(e -> {
            int linha = tabelaVeiculos.getSelectedRow();
            if (linha == -1) { mensagem("Selecione um veículo."); return; }
            int id = (int) modeloVeiculos.getValueAt(tabelaVeiculos.convertRowIndexToModel(linha), 0);
            for (Veiculo v : veiculos.listar()) {
                if (v.getId() == id) { formularioVeiculo(v); return; }
            }
        });
        excluir.addActionListener(e -> {
            int linha = tabelaVeiculos.getSelectedRow();
            if (linha == -1) { mensagem("Selecione um veículo."); return; }
            veiculos.excluir((int) modeloVeiculos.getValueAt(tabelaVeiculos.convertRowIndexToModel(linha), 0));
            atualizarTabelas();
        });
        return new VeiculoView(tabelaVeiculos, adicionar, editar, excluir);
    }

    private void formularioVeiculo(Veiculo antigo) {
        if (clientes.listar().isEmpty()) { mensagem("Cadastre um cliente primeiro."); return; }
        JComboBox<Cliente> cliente = new JComboBox<>(clientes.listar().toArray(new Cliente[0]));
        if (antigo != null) cliente.setSelectedItem(antigo.getCliente());
        JTextField placa = new JTextField(antigo == null ? "" : antigo.getPlaca());
        JTextField marca = new JTextField(antigo == null ? "" : antigo.getMarca());
        JTextField modelo = new JTextField(antigo == null ? "" : antigo.getModelo());
        JTextField cor = new JTextField(antigo == null ? "" : antigo.getCor());
        JPanel p = new JPanel(new GridLayout(5, 2, 5, 5));
        p.add(new JLabel("Cliente:")); p.add(cliente);
        p.add(new JLabel("Placa:")); p.add(placa);
        p.add(new JLabel("Marca:")); p.add(marca);
        p.add(new JLabel("Modelo:")); p.add(modelo);
        p.add(new JLabel("Cor:")); p.add(cor);
        if (JOptionPane.showConfirmDialog(this, p, "Dados do veículo", JOptionPane.OK_CANCEL_OPTION)
                == JOptionPane.OK_OPTION) {
            try {
                veiculos.salvar(new Veiculo(antigo == null ? 0 : antigo.getId(),
                        (Cliente) cliente.getSelectedItem(), placa.getText(),
                        marca.getText(), modelo.getText(), cor.getText()));
                atualizarTabelas();
            } catch (Exception ex) { mensagem(ex.getMessage()); }
        }
    }

    private void mensagem(String texto) {
        JOptionPane.showMessageDialog(this, texto);
    }
}
