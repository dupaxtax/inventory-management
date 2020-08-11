package Model;

public abstract class Part
{
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Part()
    {
        this(0, "", 0, 0.00, 0, 0);
    }

    public Part(int id, String name, int stock, double price, int min, int max)
    {
        setId(id);
        setName(name);
        setStock(stock);
        setPrice(price);
        setMin(min);
        setMax(max);
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public void setStock(int stock)
    {
        this.stock = stock;
    }

    public void setMin(int min)
    {
        this.min = min;
    }

    public void setMax(int max)
    {
        this.max = max;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public double getPrice()
    {
        return price;
    }

    public int getStock()
    {
        return stock;
    }

    public int getMin()
    {
        return min;
    }

    public int getMax()
    {
        return max;
    }

}
