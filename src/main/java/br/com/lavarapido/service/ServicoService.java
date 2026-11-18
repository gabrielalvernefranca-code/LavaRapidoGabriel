package br.com.lavarapido.service;

import br.com.lavarapido.model.Servico;
import java.util.ArrayList;

public class ServicoService {
    private ArrayList<Servico> servicos = new ArrayList<>();
    private int proximoId = 1;

    public ArrayList<Servico> listar() {
        return servicos;
    }

    public void salvar(Servico servico) {
        if (servico.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Digite o nome do serviço.");
        }
        if (servico.getPreco() < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }

        if (servico.getId() == 0) {
            servico = new Servico(proximoId++, servico.getNome(),
                    servico.getDescricao(), servico.getPreco());
            servicos.add(servico);
        } else {
            for (int i = 0; i < servicos.size(); i++) {
                if (servicos.get(i).getId() == servico.getId()) {
                    servicos.set(i, servico);
                    break;
                }
            }
        }
    }

    public void excluir(int id) {
        servicos.removeIf(s -> s.getId() == id);
    }
}
