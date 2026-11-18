package br.com.lavarapido.service;

import br.com.lavarapido.model.Veiculo;
import java.util.ArrayList;

public class VeiculoService {
    private ArrayList<Veiculo> veiculos = new ArrayList<>();
    private int proximoId = 1;

    public ArrayList<Veiculo> listar() { return veiculos; }

    public void salvar(Veiculo veiculo) {
        if (veiculo.getCliente() == null || veiculo.getPlaca().trim().isEmpty()
                || veiculo.getModelo().trim().isEmpty()) {
            throw new IllegalArgumentException("Informe cliente, placa e modelo.");
        }
        for (Veiculo v : veiculos) {
            if (v.getPlaca().equalsIgnoreCase(veiculo.getPlaca()) && v.getId() != veiculo.getId()) {
                throw new IllegalArgumentException("Esta placa já está cadastrada.");
            }
        }
        if (veiculo.getId() == 0) {
            veiculos.add(new Veiculo(proximoId++, veiculo.getCliente(), veiculo.getPlaca(),
                    veiculo.getMarca(), veiculo.getModelo(), veiculo.getCor()));
        } else {
            for (int i = 0; i < veiculos.size(); i++) {
                if (veiculos.get(i).getId() == veiculo.getId()) {
                    veiculos.set(i, veiculo);
                    return;
                }
            }
        }
    }

    public void excluir(int id) { veiculos.removeIf(v -> v.getId() == id); }
    public void excluirPorCliente(int id) { veiculos.removeIf(v -> v.getCliente().getId() == id); }
}
