package br.com.lavarapido.service;

import br.com.lavarapido.model.Agendamento;
import br.com.lavarapido.model.Cliente;
import br.com.lavarapido.model.Servico;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AgendaService {
    private ArrayList<Agendamento> agendamentos = new ArrayList<>();
    private int proximoId = 1;

    public ArrayList<Agendamento> listar() {
        return agendamentos;
    }

    // Validação usada tanto ao criar quanto ao editar.
    private void validar(Cliente cliente, Servico servico, LocalDateTime dataHora, int ignorarId) {
        if (cliente == null || servico == null || dataHora == null) {
            throw new IllegalArgumentException("Escolha cliente, serviço e data.");
        }
        if (!dataHora.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Escolha uma data futura.");
        }
        for (Agendamento a : agendamentos) {
            if (a.getId() != ignorarId && a.getDataHora().equals(dataHora)
                    && a.getStatus().equals("Agendado")) {
                throw new IllegalArgumentException("Esse horário já está ocupado.");
            }
        }
    }

    public void agendar(Cliente cliente, Servico servico, LocalDateTime dataHora) {
        validar(cliente, servico, dataHora, -1);
        agendamentos.add(new Agendamento(proximoId++, cliente, servico, dataHora));
    }

    public void editar(int id, Cliente cliente, Servico servico, LocalDateTime dataHora) {
        for (Agendamento a : agendamentos) {
            if (a.getId() == id) {
                if (!a.getStatus().equals("Agendado")) {
                    throw new IllegalArgumentException("Só é possível editar agendamentos ativos.");
                }
                validar(cliente, servico, dataHora, id);
                a.setCliente(cliente);
                a.setServico(servico);
                a.setDataHora(dataHora);
                return;
            }
        }
        throw new IllegalArgumentException("Agendamento não encontrado.");
    }

    public ArrayList<Agendamento> listarDia(java.time.LocalDate dia) {
        ArrayList<Agendamento> resultado = new ArrayList<>();
        for (Agendamento a : agendamentos) {
            if (a.getDataHora().toLocalDate().equals(dia)) resultado.add(a);
        }
        return resultado;
    }

    public ArrayList<Agendamento> listarSemana(java.time.LocalDate dia) {
        java.time.LocalDate inicio = dia.with(java.time.DayOfWeek.MONDAY);
        java.time.LocalDate fim = inicio.plusDays(6);
        ArrayList<Agendamento> resultado = new ArrayList<>();
        for (Agendamento a : agendamentos) {
            java.time.LocalDate data = a.getDataHora().toLocalDate();
            if (!data.isBefore(inicio) && !data.isAfter(fim)) resultado.add(a);
        }
        return resultado;
    }

    public void mudarStatus(int id, String status) {
        for (Agendamento a : agendamentos) {
            if (a.getId() == id) {
                a.setStatus(status);
            }
        }
    }
}
