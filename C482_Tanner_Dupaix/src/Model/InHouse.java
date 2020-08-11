package Model;

public class InHouse extends Part
{
    private int machineId;

    public InHouse(int id, String name, Double price, int stock, int min, int max, int machineId)
    {
        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
        setMachineId(machineId);
    };

    public void setMachineId(int machine)
    {
         machineId = machine;
    }

    public int getMachineId()
    {
        return machineId;
    }
}
