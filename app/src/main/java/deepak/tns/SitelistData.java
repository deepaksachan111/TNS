package deepak.tns;

/**
 * Created by intex on 8/30/2016.
 */
public class SitelistData {
    private int image[];
    private String name[];

    public SitelistData() {

    }

    public SitelistData(int[] image, String[] name) {
        this.image = image;
        this.name = name;
    }

    public String[] getName() {
        return name;
    }

    public void setName(String[] name) {
        this.name = name;
    }
}
