
package com.acapra.app.config;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import com.acapra.app.repo.*;
import com.acapra.app.model.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired private AnimalRepo animalRepo;
    @Autowired private UsuarioRepo usuarioRepo;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepo.findByUsername("admin").isEmpty()) {
            Usuario u = new Usuario(null, "admin", new BCryptPasswordEncoder().encode("admin123"), "ADMIN");
            usuarioRepo.save(u);
        }
        if (animalRepo.count() == 0) {
            animalRepo.save(new Animal(null, "Max", "Cão", "Adulto", "Médio", "Macho", "Saudável", "Cão dócil e brincalhão", "https://via.placeholder.com/300"));
            animalRepo.save(new Animal(null, "Luna", "Gato", "Filhote", "Pequeno", "Fêmea", "Saudável", "Gatinha curiosa e carinhosa", "https://via.placeholder.com/300"));
        }
    }
}
