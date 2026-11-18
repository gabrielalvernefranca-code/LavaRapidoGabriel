package br.com.lavarapido.model;

public class Veiculo {
    private int id;
    private Cliente cliente;
    private String placa;
    private String marca;
    private String modelo;
    private String cor;

    public Veiculo(int id, Cliente cliente, String placa, String marca, String modelo, String cor) {
        this.id = id;
        this.cliente = cliente;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.cor = cor;
    }

    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public String getPlaca() { return placa; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public String getCor() { return cor; }
    @Override public String toString() { return placa + " - " + modelo; }
}
