package control;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import bean.House;
import bean.Order;
import bean.User;
import control.Authority.*;

public class Administrator {
    private Map<Integer, House> house;
    private Map<Integer, Order> order;
    private Map<Integer, User> user;
    private Authority authority;

    public Administrator() {
    }

    public Administrator(Authority authority) throws MalformedURLException, IOException, ParseException {
        this.authority = authority;
        initHouse();
        initOrder();
        initUser();
    }

    public void initHouse() throws MalformedURLException, IOException {
        if (authority.getHouse().get(Type.init).equals(Range.all))
            house = House.getAll();
    }

    public void initOrder() throws MalformedURLException, IOException, ParseException {
        if (authority.getOrder().get(Type.init).equals(Range.all))
            order = Order.getAll();
    }

    public void initUser() throws MalformedURLException, IOException, ParseException {
        if (authority.getUser().get(Type.init).equals(Range.all))
            user = User.getAll();
    }

    public boolean deleteHouse(int id) {
    }

    public boolean deleteOrder(int id) {
    }

    public boolean deleteUser(int id) {
    }

    public House getHouse(int id) {
        if (authority.getHouse().get(Type.get).equals(Range.all))
            return house.get(id);
        else
            return null;
    }

    // Default pattern
    // All fields not specified
    // isAvailable = false;
    // host = 0;
    // id = 0;
    // tenant = 0;
    // constraint = null;
    // service = null;
    // address = null;
    // title = null;
    public Set<House> getHouse(House pattern) {
        Set<House> match = new HashSet<>();

        if (authority.getHouse().get(Type.get).equals(Range.all)) {
            if (pattern.getId() != 0) {
                match.add(getHouse(pattern.getId()));

                return match;
            }

            match.addAll(house.values());
            for (House search : house.values()) {
                Set<House.Constraint> constraint = pattern.getConstraint().keySet();
                Set<House.Service> service = pattern.getService().keySet();

                if (pattern.isAvailable() == false || pattern.isAvailable() == search.isAvailable()) {
                    match.add(search);
                } else {
                    match.remove(search);
                    continue;
                }
                if (pattern.getAddress() == null || pattern.getAddress().contains(search.getAddress())) {
                    match.add(search);
                } else {
                    match.remove(search);
                    continue;
                }
                if (constraint == null || constraint.containsAll(search.getConstraint().keySet())) {
                    match.add(search);
                } else {
                    match.remove(search);
                    continue;
                }
                if (service == null || service.containsAll(search.getService().keySet())) {
                    match.add(search);
                } else {
                    match.remove(search);
                    continue;
                }
                if (pattern.getHost() == 0 || pattern.getHost() == search.getHost()) {
                    match.add(search);
                } else {
                    match.remove(search);
                    continue;
                }
                if (pattern.getTenant() == 0 || pattern.getTenant() == search.getTenant()) {
                    match.add(search);
                } else {
                    match.remove(search);
                    continue;
                }
                if (pattern.getTitle() == null || search.getTitle().contains(pattern.getTitle())) {
                    match.add(search);
                } else {
                    match.remove(search);
                    continue;
                }
            }

            return match;
        } else {
            match = null;

            return match;
        }
    }

    public Order getOrder(int id) {
        if (authority.getOrder().get(Type.get).equals(Range.all))
            return order.get(id);
        else
            return null;
    }

    public User getUser(int id) {
        if (authority.getUser().get(Type.get).equals(Range.all))
            return user.get(id);
        else
            return null;
    }

    public boolean setHouse(int id, House house) {
    }

    public boolean setOrder(int id, Order order) {
    }

    public boolean setUser(int id, User user) {
    }
}