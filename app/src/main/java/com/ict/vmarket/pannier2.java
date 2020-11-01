package com.ict.vmarket;


public class pannier2 {

    private String product_libelle;
    private String product_prix_n;
    private String product_prix_p;
    private String ent;
    private String qte;
    private String product_image;
    private String p_id;

    public pannier2() {
    }

    public pannier2(String ent, String product_image, String product_libelle, String product_prix_n, String product_prix_p, String qte, String id) {
        this.product_libelle = product_libelle;
        this.product_prix_n = product_prix_n;
        this.product_prix_p = product_prix_p;
        this.ent = ent;
        this.qte = qte;
        this.product_image = product_image;
        this.p_id = id;
    }

    public String getEnt() {
        return ent;
    }

    public void setEnt(String ent) {
        this.ent = ent;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
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

    public String getProduct_prix_p() {
        return product_prix_p;
    }

    public void setProduct_prix_p(String product_prix_p) {
        this.product_prix_p = product_prix_p;
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
