package model;


public class AppProduct extends Products{
    private int id;


    public AppProduct(int id, String name, double price, int stock, int min, int max) {

        super(id,name,price,stock,min,max);
        this.id = id;
    }

    /**
     * @return the id of product
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }



}
