package Chamado_Emergencia.controllers;

import Chamado_Emergencia.dtos.LoginDTO;
import Chamado_Emergencia.dtos.UsuarioCriacaoDTO;
import Chamado_Emergencia.dtos.UsuarioRespostaDTO;
import Chamado_Emergencia.models.Unidade;
import Chamado_Emergencia.models.Usuario;
import Chamado_Emergencia.repositories.UnidadeRepository;
import Chamado_Emergencia.repositories.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final UnidadeRepository unidadeRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            UsuarioRepository usuarioRepository,
            UnidadeRepository unidadeRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.unidadeRepository = unidadeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto, HttpSession session) {
        if (dto == null || dto.getEmail() == null || dto.getEmail().isBlank()
                || dto.getSenha() == null || dto.getSenha().isBlank()) {
            return ResponseEntity.badRequest().body("E-mail e senha são obrigatórios.");
        }

        String email = dto.getEmail().trim().toLowerCase(Locale.ROOT);
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("E-mail ou senha inválidos.");
        }

        Usuario usuario = usuarioOpt.get();

        if (!passwordEncoder.matches(dto.getSenha(), usuario.getSenha())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("E-mail ou senha inválidos.");
        }

        if (!Boolean.TRUE.equals(usuario.getAtivo())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Utilizador inativo no sistema.");
        }

        session.setAttribute("usuarioId", usuario.getId());
        session.setAttribute("usuarioNome", usuario.getNome());
        return ResponseEntity.ok(toResposta(usuario));
    }

    @GetMapping("/unidades")
    public List<Map<String, Object>> listarUnidadesAtivas() {
        return unidadeRepository.findAll().stream()
                .filter(unidade -> Boolean.TRUE.equals(unidade.getAtivo()))
                .map(unidade -> Map.<String, Object>of(
                        "id", unidade.getId(),
                        "nome", unidade.getNome()
                ))
                .toList();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UsuarioCriacaoDTO dto, HttpSession session) {
        if (dto == null || isBlank(dto.getNome()) || isBlank(dto.getEmail())
                || isBlank(dto.getSenha()) || dto.getPerfil() == null || dto.getUnidadeId() == null) {
            return ResponseEntity.badRequest().body("Preencha todos os campos obrigatórios.");
        }
        if (dto.getSenha().length() < 8) {
            return ResponseEntity.badRequest().body("A senha deve ter pelo menos 8 caracteres.");
        }

        String email = dto.getEmail().trim().toLowerCase(Locale.ROOT);
        if (usuarioRepository.existsByEmail(email)) {
            return ResponseEntity.badRequest().body("E-mail já registado no sistema.");
        }

        Optional<Unidade> unidadeOpt = unidadeRepository.findById(dto.getUnidadeId());
        if (unidadeOpt.isEmpty() || !Boolean.TRUE.equals(unidadeOpt.get().getAtivo())) {
            return ResponseEntity.badRequest().body("Unidade informada não foi encontrada ou está inativa.");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(dto.getNome().trim());
        novoUsuario.setEmail(email);
        novoUsuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        novoUsuario.setPerfil(dto.getPerfil());
        novoUsuario.setUnidade(unidadeOpt.get());
        novoUsuario.setAtivo(true);

        try {
            Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);
            session.setAttribute("usuarioId", usuarioSalvo.getId());
            session.setAttribute("usuarioNome", usuarioSalvo.getNome());
            return ResponseEntity.status(HttpStatus.CREATED).body(toResposta(usuarioSalvo));
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.badRequest().body("E-mail já registado no sistema.");
        }
    }

    @GetMapping("/session")
    public ResponseEntity<Map<String, Object>> obterSessao(HttpSession session) {
        Object nome = session.getAttribute("usuarioNome");
        if (nome == null) {
            return ResponseEntity.ok(Map.of("authenticated", false));
        }
        return ResponseEntity.ok(Map.of("authenticated", true, "name", nome));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of("message", "Sessão encerrada com sucesso."));
    }

    private static UsuarioRespostaDTO toResposta(Usuario usuario) {
        return new UsuarioRespostaDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfil(),
                usuario.getUnidade().getNome()
        );
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
