package com.example.movies;

public class FavItem {

    private String item_title;
    private String item_date;
    private String item_descricao;
    private String key_id;
    private String item_image;

    public FavItem() {
    }

    public FavItem(String item_title, String key_id, String item_date, String item_descricao, String item_image) {
        this.item_title = item_title;
        this.key_id = key_id;
        this.item_date = item_date;
        this.item_descricao = item_descricao;
        this.item_image = item_image;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }

    public String getItem_descricao() {
        return item_descricao;
    }

    public void setItem_descricao(String item_descricao) {
        this.item_descricao = item_descricao;
    }

    public String getItem_date() {
        return item_date;
    }

    public void setItem_date(String item_date) {
        this.item_date = item_date;
    }
}
