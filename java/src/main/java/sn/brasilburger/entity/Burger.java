package sn.brasilburger.entity;

public class Burger {

    private int id;
    private String nom;
    private double prix;
    private String description;
    private String image; // URL Cloudinary
    private String imageUrl; // URL Cloudinary
    private boolean actif;

    public Burger() {
        this.actif = true;
    }

    public Burger(String nom, double prix, String image) {
        this.nom = nom;
        this.prix = prix;
        this.image = image;
        this.actif = true;
    }

    public Burger(int id, String nom, double prix, String image, boolean actif) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.image = image;
        this.actif = actif;
    }

    // ===== GETTERS / SETTERS =====

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    @Override
    public String toString() {
        return "Burger{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", image='" + image + '\'' +
                ", actif=" + actif +
                '}';
    }
}
