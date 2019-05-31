package control;

public class Communication {
	private Map<Integer, Message> message;
    private Authority authority;
    private int id = 0;

    public contact() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Communication(Role role) throws IOException, ParseException {
        this.authority = new Authority(role);
        initMessage();
    }

    public Communication(Authority authority) throws IOException, ParseException {
        this.authority = authority;
        initMessage();
    }

    public boolean initMessage() throws IOException {
        Range rangeAuthority = authority.getMessage().get(Type.init);

        switch (rangeAuthority) {
        case all:
            this.message = Message.getAll();
            return true;
        case self:
            return false;
        default:
        case none:
            return false;
        }
    }

   public boolean deleteMessage(int id) throws IOException {
        Range rangeAuthority = authority.getMessage().get(Type.delete);

        switch (rangeAuthority) {
        case all:
            if (null == message.get(id))
                return false;
            else {
                this.message.remove(id);
                Message.delete(id);
                return true;
            }
        case self:
            if (null == message.get(id))
                return false;
            else if (this.message.get(id).getHost() != this.id)
                return false;
            else {
                this.message.remove(id);
                Message.delete(id);
                return true;
            }
        default:
        case none:
            return false;
        }
    }

     public Message getMessage(int id) {
        Range rangeAuthority = authority.getHouse().get(Type.get);

        switch (rangeAuthority) {
        case all:
            return this.message.get(id);
        case self:
            if (null == this.message.get(id))
                return null;
            else if (this.message.get(id).getHost() != this.id)
                return null;
            else
                return this.message.get(id);
        default:
        case none:
            return null;
        }
    }

    public boolean setMessage(Message message) throws IOException {
        Range rangeAuthority = authority.getHouse().get(Type.set);

        switch (rangeAuthority) {
        case all:
            this.message.put(message.getId(), message);
            message.set();
            return true;
        case self:
            if (message.getMessage() != this.id)
                return false;
            else {
                this.message.put(message.getId(), message);
                message.set();
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
