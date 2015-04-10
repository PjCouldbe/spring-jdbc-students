package db.repository;

import java.sql.Statement;

public interface Operation {
	Object execute(Statement stmt);
}
