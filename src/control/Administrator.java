package control;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.Map;

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