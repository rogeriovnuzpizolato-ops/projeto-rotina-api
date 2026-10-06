package com.example.projetorotinaapi.controller;

import com.example.projetorotinaapi.dto.AuthRequest;
import com.example.projetorotinaapi.dto.AuthResponse;
import com.example.projetorotinaapi.model.Usuario;
import com.example.projetorotinaapi.repository.UsuarioRepository;
import com.example.projetorotinaapi.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/cadastro")
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrar(@RequestBody AuthRequest request) {
        if (usuarioRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado: " + request.email());
        }

        Usuario usuario = new Usuario(request.email(), passwordEncoder.encode(request.senha()));
        usuarioRepository.save(usuario);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.senha())
        );

        UserDetails usuario = usuarioRepository.findByEmail(request.email()).orElseThrow();

        String token = jwtService.gerarToken(usuario);
        return new AuthResponse(token);
    }
}