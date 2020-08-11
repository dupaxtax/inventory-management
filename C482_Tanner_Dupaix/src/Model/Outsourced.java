package Model;

public class Outsourced extends Part
{
    private String companyName;

    public Outsourced(int id, String name, Double price, int stock, int min, int max, String companyName)
    {
        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
        setCompanyName(companyName);
    };

    public void setCompanyName(String name)
    {
        companyName = name;
    }

    public String getCompanyName()
    {
        return companyName;
    }

    public boolean isOutsourced()
    {
        return true;
    }
}
