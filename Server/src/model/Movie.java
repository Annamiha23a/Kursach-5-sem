package model;
import java.io.Serializable;
public class Movie implements Serializable {
    private int id;
    private String name;
    private String genre;
    private String country;
    private int year;
    private int duration;
    private int ageLimit;
    private String producer;
    private String[] places=new String[15];

    public Movie(){
        super();
        this.id=-1;
        this.name="";
        this.genre="";
        this.country="";
        this.year=-1;
        this.duration=-1;
        this.ageLimit=-1;
        this.producer="";
        for(int i=0; i<15;i++){
            this.places[i]="";
        }
    }

    public Movie(String name, String genre, String country, int year, int duration, int ageLimit, String producer, String[] places){
        setName(name);
        setGenre(genre);
        setCountry(country);
        setYear(year);
        setDuration(duration);
        setAgeLimit(ageLimit);
        setProducer(producer);
        setPlaces(places);
    }
    public int getId(){
        return id;
    }

    public void setId(int id){
    this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String[] getPlaces() {
        return places;
    }

    public void setPlaces(String[] places) {
        this.places = places;
    }
}