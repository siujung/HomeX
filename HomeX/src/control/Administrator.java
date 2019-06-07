package control;

import java.io.IOException;
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
	private int id = 0;

	public Administrator() throws IOException, ParseException {
		this(Role.visitor);
	}

	public Administrator(Role role) throws IOException, ParseException {
		this.authority = new Authority(role);
		initHouse();
		initOrder();
		initUser();
	}

	public Administrator(Authority authority) throws IOException, ParseException {
		this.authority = authority;
		initHouse();
		initOrder();
		initUser();
	}

	public boolean initHouse() throws IOException {
		Range rangeAuthority = authority.getHouse().get(Type.init);

		switch (rangeAuthority) {
		case all:
			this.house = House.getAll();
			return true;
		case self:
			return false;
		default:
		case none:
			return false;
		}
	}

	public boolean initOrder() throws IOException, ParseException {
		Range rangeAuthority = authority.getOrder().get(Type.init);

		switch (rangeAuthority) {
		case all:
			this.order = Order.getAll();
			return true;
		case self:
			return false;
		default:
		case none:
			return false;
		}
	}

	public boolean initUser() throws IOException, ParseException {
		Range rangeAuthority = authority.getUser().get(Type.init);

		switch (rangeAuthority) {
		case all:
			this.user = User.getAll();
			return true;
		case self:
			return false;
		default:
		case none:
			return false;
		}
	}

	public boolean deleteHouse(int id) throws IOException {
		Range rangeAuthority = authority.getHouse().get(Type.delete);

		switch (rangeAuthority) {
		case all:
			if (null == house.get(id))
				return false;
			else {
				this.house.remove(id);
				House.delete(id);
				return true;
			}
		case self:
			if (null == house.get(id))
				return false;
			else if (this.house.get(id).getHost() != this.id)
				return false;
			else {
				this.house.remove(id);
				House.delete(id);
				return true;
			}
		default:
		case none:
			return false;
		}
	}

	public boolean deleteOrder(int id) throws IOException {
		Range rangeAuthority = authority.getOrder().get(Type.delete);

		switch (rangeAuthority) {
		case all:
			if (null == order.get(id))
				return false;
			else {
				this.order.remove(id);
				Order.delete(id);
				return true;
			}
		case self:
			if (null == order.get(id))
				return false;
			else if (this.order.get(id).getHost() != this.id && this.order.get(id).getTenant() != this.id)
				return false;
			else {
				this.order.remove(id);
				Order.delete(id);
				return true;
			}
		default:
		case none:
			return false;
		}
	}

	public boolean deleteUser(int id) throws IOException {
		Range rangeAuthority = authority.getUser().get(Type.delete);

		switch (rangeAuthority) {
		case all:
			if (null == this.user.get(id))
				return false;
			else {
				this.user.remove(id);
				User.delete(id);
				return true;
			}
		case self:
			if (null == this.user.get(id))
				return false;
			else if (id != this.id)
				return false;
			else {
				this.user.remove(id);
				User.delete(id);
				return true;
			}
		default:
		case none:
			return false;
		}
	}

	public House getHouse(int id) {
		Range rangeAuthority = authority.getHouse().get(Type.get);

		switch (rangeAuthority) {
		case all:
			return this.house.get(id);
		case self:
			if (null == this.house.get(id))
				return null;
			else if (this.house.get(id).getHost() != this.id)
				return null;
			else
				return this.house.get(id);
		default:
		case none:
			return null;
		}
	}

	public Set<House> getHouse(House pattern) {
		Range rangeAuthority = authority.getHouse().get(Type.get);

		switch (rangeAuthority) {
		case all:
			return matchHouse(pattern);
		case self:
			if (null == this.house.get(id))
				return null;
			else if (this.house.get(id).getHost() != this.id)
				return null;
			else
				return matchHouse(pattern);
		default:
		case none:
			return null;
		}
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
	private Set<House> matchHouse(House pattern) {
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
				if (search.getAddress() == null && pattern.getAddress() != null) {
					match.remove(search);
					continue;
				} else if (pattern.getAddress() == null
						|| search.getAddress().toLowerCase().contains(pattern.getAddress().toLowerCase())) {
					match.add(search);
				} else {
					match.remove(search);
					continue;
				}
				if (search.getConstraint() == null && constraint != null && !constraint.isEmpty()) {
					match.remove(search);
					continue;
				} else if (constraint == null || search.getConstraint().keySet().containsAll(constraint)) {
					match.add(search);
				} else {
					match.remove(search);
					continue;
				}
				if (search.getService() == null && service != null && !service.isEmpty()) {
					match.remove(search);
					continue;
				} else if (service == null || search.getService().keySet().containsAll(service)) {
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
				if (search.getTitle() == null && pattern.getTitle() != null) {
					match.remove(search);
					continue;
				} else if (pattern.getTitle() == null
						|| search.getTitle().toLowerCase().contains(pattern.getTitle().toLowerCase())) {
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
		Range rangeAuthority = authority.getOrder().get(Type.get);

		switch (rangeAuthority) {
		case all:
			return this.order.get(id);
		case self:
			if (null == this.order.get(id))
				return null;
			else if (this.order.get(id).getHost() != this.id && this.order.get(id).getTenant() != this.id)
				return null;
			else
				return this.order.get(id);
		default:
		case none:
			return null;
		}
	}

	public User getUser(int id) {
		Range rangeAuthority = authority.getUser().get(Type.delete);

		switch (rangeAuthority) {
		case all:
			return user.get(id);
		case self:
			if (id != this.id)
				return null;
			else
				return user.get(id);
		default:
		case none:
			return null;
		}
	}

	public boolean setHouse(House house) throws IOException {
		Range rangeAuthority = authority.getHouse().get(Type.set);

		switch (rangeAuthority) {
		case all:
			this.house.put(house.getId(), house);
			house.set();
			return true;
		case self:
			if (house.getHost() != this.id)
				return false;
			else {
				this.house.put(house.getId(), house);
				house.set();
				return true;
			}
		default:
		case none:
			return false;
		}
	}

	public boolean setOrder(Order order) throws IOException {
		Range rangeAuthority = authority.getOrder().get(Type.set);

		switch (rangeAuthority) {
		case all:
			this.order.put(order.getId(), order);
			order.set();
			return true;
		case self:
			if (order.getHost() != this.id && order.getTenant() != this.id)
				return false;
			else {
				this.order.put(order.getId(), order);
				order.set();
				return true;
			}
		default:
		case none:
			return false;
		}
	}

	public boolean setUser(User user) throws IOException {
		Range rangeAuthority = authority.getUser().get(Type.set);

		switch (rangeAuthority) {
		case all:
			this.user.put(user.getId(), user);
			user.set();
			return true;
		case self:
			if (user.getId() != this.id)
				return false;
			else {
				this.user.put(user.getId(), user);
				user.set();
				return true;
			}
		default:
		case none:
			return false;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}