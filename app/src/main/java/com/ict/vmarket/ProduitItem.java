package com.ict.vmarket;


public class ProduitItem {

    private String product_libelle;
    private String product_cat;
    private String product_prix_n;
    private String product_prix_p;
    private String product_desc;
    private String product_image;
    private String product_nom_ent;
    private String product_date_deb;
    private String product_date_fin;


    public ProduitItem() {
    }

    public ProduitItem(String product_libelle, String product_cat, String product_prix_n, String product_prix_p, String product_desc, String product_image, String product_nom_ent, String product_date_deb, String product_date_fin) {
        this.product_libelle = product_libelle;
        this.product_cat = product_cat;
        this.product_prix_n = product_prix_n;
        this.product_prix_p = product_prix_p;
        this.product_desc = product_desc;
        this.product_image = product_image;
        this.product_nom_ent = product_nom_ent;
        this.product_date_deb = product_date_deb;
        this.product_date_fin = product_date_fin;
    }

    public String getProduct_libelle() {
        return product_libelle;
    }

    public void setProduct_libelle(String product_libelle) {
        this.product_libelle = product_libelle;
    }

    public String getProduct_cat() {
        return product_cat;
    }

    public void setProduct_cat(String product_cat) {
        this.product_cat = product_cat;
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

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_nom_ent() {
        return product_nom_ent;
    }

    public void setProduct_nom_ent(String product_nom_ent) {
        this.product_nom_ent = product_nom_ent;
    }

    public String getProduct_date_deb() {
        return product_date_deb;
    }

    public void setProduct_date_deb(String product_date_deb) {
        this.product_date_deb = product_date_deb;
    }

    public String getProduct_date_fin() {
        return product_date_fin;
    }

    public void setProduct_date_fin(String product_date_fin) {
        this.product_date_fin = product_date_fin;
    }
}


