package br.com.serratec.trabalhofinalapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.serratec.trabalhofinalapi.config.JwtService;
import br.com.serratec.trabalhofinalapi.dto.AutenticacaoRequestDTO;
import br.com.serratec.trabalhofinalapi.dto.AutenticacaoResponseDTO;
import br.com.serratec.trabalhofinalapi.dto.UsuarioRequestDTO;
import br.com.serratec.trabalhofinalapi.model.Usuario;
import br.com.serratec.trabalhofinalapi.service.UsuarioService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<AutenticacaoResponseDTO> login(@RequestBody AutenticacaoRequestDTO dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.username(), dto.password()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.gerarToken(userDetails);
        return ResponseEntity.ok(new AutenticacaoResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<AutenticacaoResponseDTO> registrar(@RequestBody UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario(dto.username(), dto.password(), dto.role() == null ? "USER" : dto.role());
        Usuario salvo = usuarioService.cadastrar(usuario);
        UserDetails userDetails = usuarioService.loadUserByUsername(salvo.getUsername());
        String token = jwtService.gerarToken(userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AutenticacaoResponseDTO(token));
    }
}
