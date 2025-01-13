package produit.demo.model;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users = new ArrayList<>();

    // Méthode utilitaire pour ajouter un utilisateur
    public void addUser(User user) {
        users.add(user);
        user.getRoles().add(this);
    }

    // Méthode utilitaire pour supprimer un utilisateur
    public void removeUser(User user) {
        users.remove(user);
        user.getRoles().remove(this);
    }

    @Override
    public String getAuthority() {
        return name;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }
}