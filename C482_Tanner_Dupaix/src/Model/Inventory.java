package Model;

import com.apple.eawt.AppEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory
{
    private static ObservableList<Part> allParts = FXCollections.observableArrayList(
            new Outsourced(1, "Part 1", 5.00, 5, 5, 300, "Blah"),
            new InHouse(2, "Part 2", 10.00, 10, 5, 300, 123),
            new Outsourced(3, "Part 3", 15.00, 12, 5, 300, "Good"),
            new InHouse(4, "Part 4", 25.00, 15, 5, 300, 123)
    );

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList(
            new Product(1, "Product 1", 100.00, 25, 5, 50),
            new Product(2, "Product 2", 150.00, 50, 5,50),
            new Product(3, "Product 3", 250, 20, 5, 45)
    );

    private static int nextId = 0;

    public static void addPart(Part newPart)
    {
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct)
    {
        allProducts.add(newProduct);
    }
    public static Part lookupPart(int partId)
    {
        for(int i = 0; i < allParts.size(); ++i)
        {
            if(allParts.get(i) instanceof InHouse)
            {
                InHouse part = (InHouse) allParts.get(i);
                if(part.getId() == partId)
                {
                    return part;
                }
            } else if (allParts.get(i) instanceof Outsourced)
            {
                Outsourced part = (Outsourced) allParts.get(i);
                if(part.getId() == partId)
                {
                    return part;
                }
            }
        }
        return null;
    }

    public static Product lookupProduct(int productId)
    {
        for(Product product : allProducts)
        {
            if(product.getId() == productId)
            {
                return product;
            }
        }
        return null;
    }

    public static ObservableList<Part> lookupPart(String partName)
    {
        ObservableList<Part> partList = FXCollections.observableArrayList();

        for(int i = 0; i < allParts.size(); ++i)
        {
            if(allParts.get(i) instanceof InHouse)
            {
                InHouse part = (InHouse) allParts.get(i);
                if(part.getName().toLowerCase().contains(partName.toLowerCase()))
                {
                    partList.add(part);
                }
            } else
            {
                Outsourced part = (Outsourced) allParts.get(i);
                if(part.getName().toLowerCase().contains(partName.toLowerCase()))
                {
                    partList.add(part);
                }
            }
        }

        return partList;
    }

    public static ObservableList<Product> lookupProduct(String productName)
    {
        ObservableList<Product> productList = FXCollections.observableArrayList();
        for(Product product : allProducts)
        {
            if(product.getName().toLowerCase().contains(productName.toLowerCase()))
            {
                productList.add(product);
            }
        }
        return productList;
    }

    public static void updatePart(int index, Part selectedPart)
    {
        allParts.set(index, selectedPart);
    }

    public static void updateProduct(int index, Product selectedProduct)
    {
        allProducts.set(index, selectedProduct);
    }

    public static boolean deletePart(Part selectedPart)
    {
        allParts.remove(selectedPart);

        return true;
    }

    public static boolean deleteProduct(Product selectedProduct)
    {
        allProducts.remove(selectedProduct);

        return true;
    }

    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }

    public static int getNextParttId()
    {
        int size = allParts.size();

        nextId = allParts.get(size - 1).getId();

        nextId = nextId + 1;

        return nextId;
    }

    public static int getNextProductId()
    {
        int size = allProducts.size();

        nextId = allProducts.get(size - 1).getId();

        nextId = nextId + 1;

        return nextId;
    }
}
