package control;

import java.util.HashMap;
import java.util.Map;

// A ordinary user can be regarded as an administrator with none authorities
// who can only use his id in setUser, all ids in getUser and so on
// in this case the profile.java can be omitted
public class Authority {
    private Map<Type, Range> House;
    private Map<Type, Range> Order;
    private Map<Type, Range> User;
    private Map<Type, Range> Message;

    public static enum Type {
        init, delete, get, set
    }

    public static enum Range {
        none, self, friend, all
    }

    public static enum Role {
        none, visitor, user, administrator
    }

    public Authority() {
        this(Role.none);
    }

    public Authority(Role role) {
        House = new HashMap<>();
        Order = new HashMap<>();
        User = new HashMap<>();
        Message = new HashMap<>();

        House.put(Type.init, Range.none);
        House.put(Type.delete, Range.none);
        House.put(Type.get, Range.none);
        House.put(Type.set, Range.none);
        Order.put(Type.init, Range.none);
        Order.put(Type.delete, Range.none);
        Order.put(Type.get, Range.none);
        Order.put(Type.set, Range.none);
        User.put(Type.init, Range.none);
        User.put(Type.delete, Range.none);
        User.put(Type.get, Range.none);
        User.put(Type.set, Range.none);
        Message.put(Type.init, Range.none);
        Message.put(Type.delete, Range.none);
        Message.put(Type.get, Range.none);
        Message.put(Type.set, Range.none);

        switch (role) {
        case none:
            break;
        case visitor:
            House.put(Type.init, Range.all);
            House.put(Type.get, Range.all);
            User.put(Type.init, Range.all);
            User.put(Type.get, Range.all);
            break;
        case user:
            House.put(Type.init, Range.all);
            House.put(Type.delete, Range.self);
            House.put(Type.get, Range.all);
            House.put(Type.set, Range.self);
            Order.put(Type.init, Range.self);
            Order.put(Type.delete, Range.self);
            Order.put(Type.get, Range.self);
            Order.put(Type.set, Range.self);
            User.put(Type.init, Range.all);
            User.put(Type.delete, Range.self);
            User.put(Type.get, Range.all);
            User.put(Type.set, Range.self);
            Message.put(Type.init, Range.self);
            Message.put(Type.delete, Range.self);
            Message.put(Type.get, Range.self);
            Message.put(Type.set, Range.self);
            break;
        case administrator:
            House.put(Type.init, Range.all);
            House.put(Type.delete, Range.all);
            House.put(Type.get, Range.all);
            House.put(Type.set, Range.all);
            Order.put(Type.init, Range.all);
            Order.put(Type.delete, Range.all);
            Order.put(Type.get, Range.all);
            Order.put(Type.set, Range.all);
            User.put(Type.init, Range.all);
            User.put(Type.delete, Range.all);
            User.put(Type.get, Range.all);
            User.put(Type.set, Range.all);
            Message.put(Type.init, Range.all);
            Message.put(Type.delete, Range.all);
            Message.put(Type.get, Range.all);
            Message.put(Type.set, Range.all);
            break;
        }
    }

    public Map<Type, Range> getHouse() {
        return House;
    }

    public Map<Type, Range> getOrder() {
        return Order;
    }

    public Map<Type, Range> getUser() {
        return User;
    }

    public Map<Type, Range> getMessage() {
        return Message;
    }
}
