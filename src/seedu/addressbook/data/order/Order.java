package seedu.addressbook.data.order;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import seedu.addressbook.data.member.Member;
import seedu.addressbook.data.member.Points;
import seedu.addressbook.data.member.ReadOnlyMember;
import seedu.addressbook.data.menu.Menu;
import seedu.addressbook.data.menu.ReadOnlyMenus;

/**
 * Represents an Order in the ordering list.
 */

public class Order implements ReadOnlyOrder {

    private ReadOnlyMember customer;
    private Date date;
    private Points points;
    private double price;

    /**
     * Map with Dishes as keys and quantities as Integer values.
     *
     * Use {@code entrySet()} to create a Set for iteration.
     */
    private final Map<ReadOnlyMenus, Integer> dishItems = new HashMap<>();

    /**
     * Constructor used for drafting new order. Uses empty customer instead of null.
     */
    public Order() {
        this.customer = new Member();
        this.date = new Date();
        this.points = new Points();
        this.price = 0;
    }

    /**
     * Constructor for new order to be added to the order list.
     */
    public Order(ReadOnlyMember customer, Map<ReadOnlyMenus, Integer> dishItems, int pointsToRedeem) {
        this.customer = customer;
        this.dishItems.putAll(dishItems);
        this.price = calculatePrice(pointsToRedeem);
        this.points = new Points(pointsToRedeem);
        this.date = new Date();
    }

    /**
     * Constructor for edited order to keep the original ordered date.
     */
    public Order(ReadOnlyMember customer, Date date, Map<ReadOnlyMenus, Integer> dishItems, int pointsToRedeem) {
        this.customer = customer;
        this.dishItems.putAll(dishItems);
        this.price = calculatePrice(pointsToRedeem);
        this.points = new Points(pointsToRedeem);
        this.date = date;
    }

    /**
     * Full constructor.
     */
    public Order(ReadOnlyMember customer,
                 Date date,
                 double price,
                 Map<ReadOnlyMenus, Integer> dishItems,
                 int pointsToRedeem) {
        this.customer = customer;
        this.dishItems.putAll(dishItems);
        this.price = price;
        this.points = new Points(pointsToRedeem);
        this.date = date;
    }

    /**
     * Copy constructor.
     */
    public Order(ReadOnlyOrder source) {
        this(source.getCustomer(), source.getDate(), source.getPrice(), source.getDishItems(), source.getPoints());
    }

    @Override
    public ReadOnlyMember getCustomer() {
        return customer;
    }

    /**
     * Defensively returning the copy of the order's date
     */
    @Override
    public Date getDate() {
        return new Date(date.getTime());
    }

    @Override
    public double getPrice() {
        return price;
    }

    public void setPrice(double value) {
        price = value;
    }

    @Override
    public int getPoints() {
        return points.getPoints();
    }

    public int getPointsEarned() {
        if (customer.getName().equals(new Member().getName())) {
            return points.getPoints();
        }
        return (int) price / 10;
    }

    @Override
    public void setPoints(int value) {
        points.setPoints(value);
    }

    @Override
    public Map<ReadOnlyMenus, Integer> getDishItems() {
        return new HashMap<>(dishItems);
    }

    public void setCustomer(ReadOnlyMember customer) {
        this.customer = customer;
    }

    /**
     * Replaces the list of dish items with the dish items in {@code replacement}.
     */
    public void setDishItems(Map<ReadOnlyMenus, Integer> replacement, int pointsToRedeem) {
        dishItems.clear();
        dishItems.putAll(replacement);
        price = calculatePrice(pointsToRedeem);
    }

    /**
     * Calculate and return the total price of an order.
     */
    public double calculatePrice(int points) {
        double result = 0;
        for (Map.Entry<ReadOnlyMenus, Integer> m: getDishItems().entrySet()) {
            double dishPrice = m.getKey().getPrice().convertValueOfPricetoDouble();
            int dishQuantity = m.getValue();
            result += (dishPrice * dishQuantity);
        }
        result -= points; // 10 points = $1
        return result;
    }

    /**
     * Get the number of a certain dish item in an order.
     */
    public int getDishQuantity(ReadOnlyMenus dish) {
        if (dishItems.containsKey(dish)) {
            return dishItems.get(dish);
        } else {
            return 0;
        }
    }

    /**
     * Change the quantity of a dish in an order.
     * Used to add, remove and edit dishes in an order.
     */
    public void changeDishQuantity(ReadOnlyMenus readOnlyDish, int quantity) {
        ReadOnlyMenus dish = new Menu(readOnlyDish);
        if (quantity == 0) {
            dishItems.remove(dish);
        } else if (quantity > 0) {
            dishItems.put(dish, quantity);
        }
        price = calculatePrice(points.getPoints());
    }

    @Override
    public boolean hasCustomerField() {
        return !(customer.equals(new Member()));
    }

    @Override
    public boolean hasDishItems() {
        return !(dishItems.isEmpty());
    }

    @Override
    public boolean hasPointsField() {
        return !(points.equals(new Points()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyOrder // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyOrder) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(customer, date, price, dishItems);
    }

    @Override
    public String toString() {
        return getAsTextShowAll();
    }

}
