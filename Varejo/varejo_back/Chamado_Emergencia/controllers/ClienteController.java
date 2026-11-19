package Chamado_Emergencia.controllers;

import Chamado_Emergencia.models.Cliente;
import Chamado_Emergencia.repositories.ClienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clients")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    public Map<String, List<Cliente>> listarClientes() {
        return Map.of("clients", clienteRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> cadastrarCliente(@RequestBody Cliente cliente) {
        if (cliente.getName() == null || cliente.getName().isBlank()
                || cliente.getPhone() == null || cliente.getPhone().isBlank()
                || cliente.getEmail() == null || cliente.getEmail().isBlank()
                || cliente.getCpf() == null || cliente.getCpf().isBlank()
                || cliente.getAddress() == null || cliente.getAddress().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Preencha todos os campos obrigatórios."));
        }

        Cliente salvo = clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "Cliente cadastrado com sucesso!",
                "client", salvo
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> buscarClientePorId(@PathVariable Long id) {
        return clienteRepository.findById(id)
                .map(cliente -> ResponseEntity.ok(Map.<String, Object>of("client", cliente)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.<String, Object>of("error", "Cliente não encontrado.")));
    }
}
