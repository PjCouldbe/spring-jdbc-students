package db.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.model.Order;

public class OrderRepository extends AbstractDAO{
    public OrderRepository() {
//    	super();
//    	doOperation(new Operation() {
//			@Override
//			public Object execute(Statement stmt) {
//				String creator = "CREATE TABLE ORDERS ("
//						+ "orderId INTEGER not NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
//						+ "customerId INTEGER not NULL,"
//						+ "salesPersonId INTEGER not NULL,"
//						+ "totalAmount INTEGER not NULL,"
//						+ "PRIMARY KEY(orderId))";
//				try {
//					stmt.execute(creator);	
//				} catch (SQLException ex) {
//					if (!ex.getSQLState().equals("X0Y32")) {
//						ex.printStackTrace();
//					}
//				}
//				return null;
//			}
//		});
	}
	
	public void addOrder(final Order order) {
		doOperation(new Operation() {
			@Override
			public Object execute(Statement stmt) {
				try {	
					String sql = "INSERT INTO ORDERS (customerId, salesPersonId, totalAmount) VALUES ("
							+ order.getCustomerId() + ", "
							+ order.getSalesPersonId() + ", "
							+ order.getTotalAmount() + ")";
					stmt.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}
	
	public boolean update(final Order order) {
		return (boolean)doOperation(new Operation() {
			@Override
			public Boolean execute(Statement stmt) {
				try {		
					String sql = "SELECT * FROM ORDERS";
					ResultSet res = stmt.executeQuery(sql);
					res.last();
					res.previous();
					int i = res.getInt(1);
					
					sql = "UPDATE ORDERS "
							+ "SET customerId=" + order.getCustomerId()
							+ ", salesPersonId=" + order.getSalesPersonId()
							+ ", totalAmount=" + order.getTotalAmount()
							+ " WHERE orderId=" + i;
					stmt.executeUpdate(sql);
					return true;
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return false;
			}
		});
	}
	
	public Boolean delete() {
		return (Boolean)doOperation(new Operation() {
			@Override
			public Boolean execute(Statement stmt) {
				int i = getLastId();
				try {
					String sql = "DELETE FROM ORDERS WHERE orderId=" + i;
					stmt.executeUpdate(sql);
					return true;
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return false;
			}
		});
	}
	
	public int getLastId() {
		return (Integer)doOperation(new Operation() {
			@Override
			public Integer execute(Statement stmt) {
				try {
					String sql = "SELECT * FROM ORDERS";
					ResultSet res = stmt.executeQuery(sql);
					res.last();
					return res.getInt(1);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return 0;
			}
		});	
	}
	
	public String selectUser() {	
		return (String)doOperation(new Operation() {
			@Override
			public String execute(Statement stmt) {
				try {
					String sql = "SELECT * FROM ORDERS";
					ResultSet res = stmt.executeQuery(sql);
					StringBuilder s = new StringBuilder();
					for (int i = 1; i <= res.getMetaData().getColumnCount(); i++) {
						s.append(res.getString(i) + " ");
					}
					s.append("\n");
					return s.toString();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}
	
	public String showAllOrders() {
		return (String)doOperation(new Operation() {
			@Override
			public String execute(Statement stmt) {
				try {
					String sql = "SELECT * FROM ORDERS";
					ResultSet res = stmt.executeQuery(sql);
					StringBuilder s = new StringBuilder();
					while (res.next()) {
						for (int i = 1; i <= res.getMetaData().getColumnCount(); i++) {
							s.append(res.getString(i) + " ");
						}
						s.append("\n");
					}
					return s.toString();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return null;
			}
		});		
	}
	
	public int getCount() {
		return (Integer)doOperation(new Operation() {
			@Override
			public Integer execute(Statement stmt) {
				try {
					String sql = "SELECT COUNT(*) FROM ORDERS";
					ResultSet res = stmt.executeQuery(sql);
					res.next();
					return res.getInt(1);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
	}
}