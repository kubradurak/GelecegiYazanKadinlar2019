package mobil.csystem.org.hafta5.model;

public class ListViewModelClass {

    private int city_picture;
    private String city_name;
    private String city_about;


    public ListViewModelClass(int city_picture, String city_name, String city_about) {
        this.city_picture = city_picture;
        this.city_name = city_name;
        this.city_about = city_about;
    }

    public int getCity_picture() {
        return city_picture;
    }

    public void setCity_picture(int city_picture) {
        this.city_picture = city_picture;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCity_about() {
        return city_about;
    }

    public void setCity_about(String city_about) {
        this.city_about = city_about;
    }
}
