package db.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;

import db.config.DBConfiguration;

public abstract class AbstractDAO {
	@Autowired(required=true)
    private DBConfiguration config;

    public DBConfiguration getConfig() {
        return config;
    }

    public void setConfig(DBConfiguration config) {
        this.config = config;
    }
    
    public AbstractDAO() {
		
	}
    
    public Object doOperation(Operation op) {
    	try (Connection conn = DriverManager.getConnection(config.getDbUrl())){	
    		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
												ResultSet.CONCUR_READ_ONLY);
    		return op.execute(stmt);
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    	return null;
    }
}
