package Chamado_Emergencia.controllers;

import Chamado_Emergencia.dtos.ChamadoCriacaoDTO;
import Chamado_Emergencia.dtos.ChamadoEscalonamentoDTO;
import Chamado_Emergencia.dtos.EncerramentoChamadoDTO;
import Chamado_Emergencia.models.Chamado;
import Chamado_Emergencia.services.ChamadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chamados")
@CrossOrigin(origins = "*")
public class ChamadoController {

    @Autowired
    private ChamadoService chamadoService;

    @GetMapping
    public ResponseEntity<List<Chamado>> listarTodos() {
        return ResponseEntity.ok(chamadoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Chamado chamado = chamadoService.buscarPorId(id);
            return ResponseEntity.ok(chamado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody ChamadoCriacaoDTO dto) {
        try {
            Chamado novoChamado = chamadoService.criarChamado(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoChamado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/escalar")
    public ResponseEntity<?> escalar(@PathVariable Long id, @RequestBody ChamadoEscalonamentoDTO dto) {
        try {
            Chamado chamadoEscalado = chamadoService.escalarChamado(id, dto);
            return ResponseEntity.ok(chamadoEscalado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/encerrar")
    public ResponseEntity<?> encerrar(@PathVariable Long id, @RequestBody EncerramentoChamadoDTO dto) {
        try {
            Chamado chamadoEncerrado = chamadoService.encerrarChamado(id, dto);
            return ResponseEntity.ok(chamadoEncerrado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
