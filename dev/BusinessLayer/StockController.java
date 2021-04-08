package BusinessLayer;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class StockController {
    private List<Category> categories;
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String RESET = "\033[0m";  // Text Reset


    public StockController() {
        categories = new LinkedList<>();
    }

    private Category getCategory(String name) {
        for (Category c : categories) {
            Category curr = c.getCategory(name);
            if (curr != null && curr.getCategory(name) != null)
                return curr;
        }
        return null;
    }

    public boolean addSubCategory(String name, String superName) {
        Category superCategory = getCategory(superName);
        if (superCategory == null || getCategory(name) != null)
            return false;
        return superCategory.addSubCategory(new Category(name));
    }

    public boolean addCategory(String name) {
        if (categories.contains(name))
            return false;
        return categories.add(new Category(name));
    }

    public Item getItem(int id) {
        for (Category c : categories) {
            Item i = c.getItem(id);
            if (i != null)
                return i;
        }
        return null;
    }

    public boolean addItem(int id, String name, double price, double cost, int shelfNum, String manufacturer, int shelfQuantity, int storageQuantity, int minAlert, String catName) {
        Category c = getCategory(catName);
        if (c == null)
            return false;
        if (getItem(id) != null)
            return false;
        return c.addItem(id, name, price, cost, shelfNum, manufacturer, shelfQuantity, storageQuantity, minAlert);
    }

    public boolean removeItem(int id) {
        Category c = getCategory(id);
        if (c == null)
            return false;
        return c.removeItem(id);
    }

    public boolean removeFromShelf(int id, int amount) {
        Category c = getCategory(id);
        if (c == null)
            return false;
        return c.removeFromShelf(id, amount);
    }

    public boolean removeFromStorage(int id, int amount) {
        Category c = getCategory(id);
        if (c == null)
            return false;
        return c.removeFromStorage(id, amount);
    }

    public Category getCategory(int id) {
        for (Category c : categories) {
            Category output = c.getCategory(id);
            if (output != null)
                return output;
        }
        return null;
    }

    public boolean addToStorage(int id, int amount) {
        Category c = getCategory(id);
        if (c == null)
            return false;
        return c.addToStorage(id, amount);
    }

    public boolean moveToShelf(int id, int amount) {
        Category c = getCategory(id);
        if (c == null)
            return false;
        return c.moveToShelf(id, amount);
    }

    public boolean changeShelf(int id, int shelf) {
        Category c = getCategory(id);
        if (c == null)
            return false;
        return c.changeShelf(id, shelf);
    }


    public boolean addItemDiscount(LocalDate start, LocalDate end, int discountPr, int id) {
        Category c = getCategory(id);
        if (c == null)
            return false;
        c.addItemDiscount(start, end, discountPr, id);
        return true;
    }

    public boolean addCategoryDiscount(LocalDate start, LocalDate end, int discountPr, String catName) {
        Category c = getCategory(catName);
        if (c == null)
            return false;
        c.addCategoryDiscount(start, end, discountPr);
        return true;
    }

    public boolean addManuDiscount(LocalDate start, LocalDate end, int discountPr, int id) {
        Category c = getCategory(id);
        if (c == null)
            return false;
        c.addManuDiscount(start, end, discountPr, id);
        return true;
    }

    public String stkReport() {
        StringBuilder sb = new StringBuilder("\n");
        for (Category c : categories) {
            sb.append(c.toString());
        }
        return sb.toString();
    }

    public String catReport(List<String> catNames) {
        StringBuilder sb = new StringBuilder("\nReport of categories: "+catNames.toString()+"\n");
        for (String cat : catNames) {
            Category c = getCategory(cat);
            if (c == null)
                sb.append("Could not found category: "+RED_BOLD+cat+RESET+"\n");
            else
                sb.append(c.toString()+"\n");
        }
        return sb.toString();
    }
}