package br.com.lavarapido.model;

import java.time.LocalDateTime;

public class Agendamento {
    private int id;
    private Cliente cliente;
    private Servico servico;
    private LocalDateTime dataHora;
    private String status;

    public Agendamento(int id, Cliente cliente, Servico servico, LocalDateTime dataHora) {
        this.id = id;
        this.cliente = cliente;
        this.servico = servico;
        this.dataHora = dataHora;
        this.status = "Agendado";
    }

    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Servico getServico() { return servico; }
    public LocalDateTime getDataHora() { return dataHora; }
    public String getStatus() { return status; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public void setServico(Servico servico) { this.servico = servico; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public void setStatus(String status) { this.status = status; }
}
