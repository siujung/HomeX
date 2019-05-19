package control;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.Map;
import java.util.Set;

import bean.House;
import bean.Order;
import bean.User;

public class Administrator {
    private Map<Integer, House> house;
    private Map<Integer, Order> order;
    private Map<Integer, User> user;
    private Set<Authority> authority;

    // A ordinary user can be regarded as an administrator with none authorities
    // who can only use his id in setUser, all ids in getUser and so on
    // in this case the profile.java can be omitted
    public enum Authority {
        house, order, user, message, none
    }

    public Administrator() {
    }

    public Administrator(Set<Authority> authority) throws MalformedURLException, IOException, ParseException {
        this.authority = authority;
        initHouse();
        initOrder();
        initUser();
    }

    public void initHouse() throws MalformedURLException, IOException {
        if (authority.contains(Authority.house))
            house = House.getAll();
    }

    public void initOrder() throws MalformedURLException, IOException, ParseException {
        if (authority.contains(Authority.order))
            order = Order.getAll();
    }

    public void initUser() throws MalformedURLException, IOException, ParseException {
        if (authority.contains(Authority.user))
            user = User.getAll();
    }

    public boolean deleteHouse(int id) {
    }

    public boolean deleteOrder(int id) {
    }

    public boolean deleteUser(int id) {
    }

    public House getHouse(int id) {
        if (authority.contains(Authority.house))
            return house.get(id);
        else
            return null;
    }

    public Order getOrder(int id) {
        if (authority.contains(Authority.order))
            return order.get(id);
        else
            return null;
    }

    public User getUser(int id) {
        if (authority.contains(Authority.user))
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