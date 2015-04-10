package db.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import db.model.User;

public class UserRepository extends AbstractDAO{
	public UserRepository() {
//    	super();
//    	doOperation(new Operation() {
//			@Override
//			public Object execute(Statement stmt) {
//				String creator = "CREATE TABLE USERS ("
//						+ "id INTEGER not NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
//						+ "lastname VARCHAR(256),"
//						+ "firstname VARCHAR(256),"
//						+ "middlename VARCHAR(256),"
//						+ "age INTEGER not NULL,"
//						+ "PRIMARY KEY(id))";
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
	
	public void addUser(final User user) {
		doOperation(new Operation() {
			@Override
			public String execute(Statement stmt) {
				String sql = "INSERT INTO USERS (lastname, firstname, middlename, age) VALUES ("
						+ "\'" + user.getLastname() + "\',"
						+ "\'" + user.getFirstname() + "\',"
						+ "\'" + user.getMiddlename() + "\',"
						+ user.getAge() + ")";
				try {
					stmt.executeUpdate(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}
	
	public Boolean update(final User user) {
		return (Boolean)doOperation(new Operation() {
			@Override
			public Boolean execute(Statement stmt) {
				try {
					String sql = "SELECT * FROM USERS";
					ResultSet res = stmt.executeQuery(sql);
					res.last();
					res.previous();
					int i = res.getInt(1);
					sql = "UPDATE USERS "
							+ "SET lastname=" + "\'" + user.getLastname() + "\', "
							+ "firstname=" + "\'" + user.getFirstname() + "\', "
							+ "middlename=" + "\'" + user.getMiddlename() + "\', "
							+ "age=" + user.getAge()
							+ " WHERE id=" + i;
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
					String sql = "DELETE FROM USERS WHERE id=" + i;
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
					String sql = "SELECT * FROM USERS";
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
					String sql = "SELECT * FROM USERS";
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
	
	public String showAllUsers() {
		return (String)doOperation(new Operation() {
			@Override
			public String execute(Statement stmt) {
				try {
					String sql = "SELECT * FROM USERS";
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
					String sql = "SELECT COUNT(*) FROM USERS";
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