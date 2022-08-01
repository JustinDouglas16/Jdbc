package sr.unasat.jdbc.crud.entities;

public class Auto {
    private Integer id;
    private String model;
    private Persoon persoon;

    public Auto(Integer id, String model, Persoon persoon) {
        this.id = id;
        this.model = model;
        this.persoon = persoon;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(Persoon persoon) {
        this.persoon = persoon;
    }

    @Override
    public String toString() {
        return "Auto{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", persoon=" + persoon.getId() +
                '}';
    }
}
