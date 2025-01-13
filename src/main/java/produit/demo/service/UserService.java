package produit.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import produit.demo.model.User;
import produit.demo.model.Role;
import produit.demo.repository.UserRepository;
import produit.demo.repository.RoleRepository;
import produit.demo.repository.ProductRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    @Autowired

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createAdminUser() {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        admin.setRoles(Collections.singletonList(adminRole));
        return userRepository.save(admin);
    }

    public User createUser() {
        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user123"));
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(Collections.singletonList(userRole));
        return userRepository.save(user);
    }



}
