package filrouge.groupei.boredealsappandroid;

/**
 * Cette classe représente une classe utilitaire pour stocker des informations utilisateur.
 */
public class HelperClass {

    private String name, email, username;

    /**
     * Constructeur par défaut requis pour les appels à DataSnapshot.getValue(HelperClass.class).
     */
    public HelperClass() {
    }

    /**
     * Constructeur de la classe HelperClass.
     *
     * @param name     Le nom de l'utilisateur.
     * @param email    L'adresse e-mail de l'utilisateur.
     * @param username Le nom d'utilisateur de l'utilisateur.
     */
    public HelperClass(String name, String email, String username) {
        this.name = name;
        this.email = email;
        this.username = username;
    }

    // Getters and setters

    /**
     * Obtient le nom de l'utilisateur.
     *
     * @return Le nom de l'utilisateur.
     */
    public String getName() {
        return name;
    }

    /**
     * Définit le nom de l'utilisateur.
     *
     * @param name Le nom de l'utilisateur à définir.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtient l'adresse e-mail de l'utilisateur.
     *
     * @return L'adresse e-mail de l'utilisateur.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Définit l'adresse e-mail de l'utilisateur.
     *
     * @param email L'adresse e-mail de l'utilisateur à définir.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtient le nom d'utilisateur de l'utilisateur.
     *
     * @return Le nom d'utilisateur de l'utilisateur.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Définit le nom d'utilisateur de l'utilisateur.
     *
     * @param username Le nom d'utilisateur de l'utilisateur à définir.
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
