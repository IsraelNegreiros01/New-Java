package Chamado_Emergencia.controllers;

import Chamado_Emergencia.models.ArtigoBaseConhecimento;
import Chamado_Emergencia.repositories.ArtigoBaseConhecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/base-conhecimento")
@CrossOrigin(origins = "*")
public class BaseConhecimentoController {

    @Autowired
    private ArtigoBaseConhecimentoRepository artigoRepository;

    @GetMapping
    public ResponseEntity<List<ArtigoBaseConhecimento>> listarTodos() {
        return ResponseEntity.ok(artigoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtigoBaseConhecimento> buscarPorId(@PathVariable Long id) {
        return artigoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ArtigoBaseConhecimento>> buscarPorCategoria(@PathVariable Long categoriaId) {
        return ResponseEntity.ok(artigoRepository.findByCategoriaId(categoriaId));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ArtigoBaseConhecimento>> buscarPorTermo(@RequestParam String termo) {
        return ResponseEntity.ok(artigoRepository.findByTituloContainingIgnoreCaseOrConteudoContainingIgnoreCase(termo, termo));
    }

    @PostMapping
    public ResponseEntity<ArtigoBaseConhecimento> criar(@RequestBody ArtigoBaseConhecimento artigo) {
        ArtigoBaseConhecimento novoArtigo = artigoRepository.save(artigo);
        return ResponseEntity.ok(novoArtigo);
    }
}
