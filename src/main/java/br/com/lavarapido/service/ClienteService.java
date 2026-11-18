package br.com.lavarapido.service;

import br.com.lavarapido.model.Cliente;
import java.util.ArrayList;

public class ClienteService {
    private ArrayList<Cliente> clientes = new ArrayList<>();
    private int proximoId = 1;

    public ArrayList<Cliente> listar() {
        return clientes;
    }

    public void salvar(Cliente cliente) {
        if (cliente.getNome().trim().isEmpty() || cliente.getCpf().trim().isEmpty()
                || cliente.getTelefone().trim().isEmpty() || cliente.getPlaca().trim().isEmpty()) {
            throw new IllegalArgumentException("Preencha nome, CPF, telefone e placa.");
        }

        if (cliente.getId() == 0) {
            cliente = new Cliente(proximoId++, cliente.getNome(), cliente.getCpf(),
                    cliente.getTelefone(), cliente.getEmail(), cliente.getPlaca());
            clientes.add(cliente);
        } else {
            for (int i = 0; i < clientes.size(); i++) {
                if (clientes.get(i).getId() == cliente.getId()) {
                    clientes.set(i, cliente);
                    break;
                }
            }
        }
    }

    public void excluir(int id) {
        clientes.removeIf(c -> c.getId() == id);
    }
}
