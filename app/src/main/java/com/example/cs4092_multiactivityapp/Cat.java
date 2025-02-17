package com.example.cs4092_multiactivityapp;

import java.io.Serializable;
import java.util.List;


public class Cat implements Serializable {

    private String id;
    private String url;
    private int width;
    private int height;
    private List<Breed> breeds;
    private boolean starred;


    public Cat() {}


    public Cat(String id, String url, int width, int height, List<Breed> breeds) {
        this.id = id;
        this.url = url;
        this.width = width;
        this.height = height;
        this.breeds = breeds;
    }

    // Add getter and setter for isStarred
    public boolean isStarred() {
        return this.starred;
    }

    public void setStarred(boolean isStarred) {
        this.starred = isStarred;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<Breed> getBreeds() {
        return breeds;
    }

    public void setBreeds(List<Breed> breeds) {
        this.breeds = breeds;
    }

    //  Inside class breeds
    public static class Breed implements Serializable {
        private String name;
        private String vetstreet_url;
        private String description;
        private String life_span;
        private String origin;
        private String temperament;


        public Breed() {}


        public Breed(String name, String life_span,String vetstreet_url, String origin,String description,String temperament) {
            this.name = name;
            this.life_span = life_span;
            this.origin = origin;
            this.vetstreet_url = vetstreet_url;
            this.description = description;
            this.temperament = temperament;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVetstreet_url() {
            return vetstreet_url;
        }

        public void setVetstreet_url(String vetstreet_url) {
            this.vetstreet_url = vetstreet_url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLife_span() {
            return life_span;
        }

        public void setLife_span(String life_span) {
            this.life_span = life_span;
        }
        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getTemperament() {
            return temperament;
        }

        public void setTemperament(String temperament) {
            this.temperament = temperament;
        }
    }
}
