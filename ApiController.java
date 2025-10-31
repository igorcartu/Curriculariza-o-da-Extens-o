
package com.acapra.app.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import com.acapra.app.repo.*;
import com.acapra.app.model.*;
import java.util.*;
import java.time.LocalDateTime;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiController {
    @Autowired private AnimalRepo animalRepo;
    @Autowired private AdocaoRepo adocaoRepo;
    @Autowired private UsuarioRepo usuarioRepo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping(path="/animais", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Animal addAnimal(Animal a) { return animalRepo.save(a); }

    @GetMapping("/animais")
    public List<Animal> listAnimais() { return animalRepo.findAll(); }

    @PostMapping(path="/adocoes", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Adocao addAdocao(Adocao ad) { 
        ad.setCriadoEm(LocalDateTime.now());
        return adocaoRepo.save(ad); 
    }

    @GetMapping("/adocoes")
    public List<Adocao> listAdocoes() { return adocaoRepo.findAll(); }

    public static class LoginReq {
        public String usuario;
        public String senha;
    }
    public static class LoginResp {
        public boolean ok;
        public String message;
    }

    @PostMapping(path="/auth/login", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<LoginResp> login(LoginReq req) {
        LoginResp r = new LoginResp();
        Optional<Usuario> u = usuarioRepo.findByUsername(req.usuario);
        if (u.isPresent() && new BCryptPasswordEncoder().matches(req.senha, u.get().getPasswordHash())) {
            r.ok = true; r.message = "Login ok";
            return ResponseEntity.ok(r);
        } else {
            r.ok = false; r.message = "Usuário ou senha inválidos";
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(r);
        }
    }
}
