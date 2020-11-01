package com.ict.vmarket;


public class pannier {

    private String product_libelle;
    private String product_prix_n;
    private String product_ent;
    private String qte;
    private String product_image;
    private String product_id;

    public pannier() {
    }

    public pannier(String product_ent, String product_image, String product_libelle, String product_prix_n, String qte, String product_id) {
        this.product_libelle = product_libelle;
        this.product_prix_n = product_prix_n;
        this.product_ent = product_ent;
        this.qte = qte;
        this.product_image = product_image;
        this.product_id = product_id;
    }

    public String getProduct_ent() {
        return product_ent;
    }

    public void setProduct_ent(String product_ent) {
        this.product_ent = product_ent;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_libelle() {
        return product_libelle;
    }

    public void setProduct_libelle(String product_libelle) {
        this.product_libelle = product_libelle;
    }

    public String getProduct_prix_n() {
        return product_prix_n;
    }

    public void setProduct_prix_n(String product_prix_n) {
        this.product_prix_n = product_prix_n;
    }


    public String getQte() {
        return qte;
    }

    public void setQte(String qte) {
        this.qte = qte;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

}
