package in.mindbug.lqc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.h2.jdbcx.JdbcConnectionPool;

public class Database {

	private JdbcConnectionPool connPool;

	Database() {
		connPool = JdbcConnectionPool.create("jdbc:h2:tcp://localhost/e:/lqc", "admin", "password");
		System.out.print("Connected to H2...");
	}

	ArrayList<Entity> selectRows(String dbQuery, HashMap<String, Attribute> map, int limit, int offset) {

		ArrayList<Entity> entities = new ArrayList<>();

		try {
			Connection conn = connPool.getConnection();
			PreparedStatement stmt = conn.prepareStatement(dbQuery);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Entity entity = new Entity();
				entities.add(entity);

				for (String colName : map.keySet()) {
					Attribute attribute = map.get(colName);
					Object value = null;
					Class<?> dataType = attribute.getDataType();
					String caption = attribute.getCaption();

					if (dataType == String.class) {
						value = rs.getString(colName);
					} else if (dataType == Integer.class) {
						value = rs.getInt(colName);
					}

					Attribute attributeWithValue = new Attribute(colName, dataType, value, caption);

					entity.getMap().put(colName, attributeWithValue);
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

		return entities;
	}

	int selectRowCount(String dbQuery) {
		int count = 0;

		try {
			Connection conn = connPool.getConnection();
			PreparedStatement stmt = conn.prepareStatement(dbQuery);
			ResultSet rs = stmt.executeQuery();
			if (rs != null) {
				rs.beforeFirst();
				rs.last();
				count = rs.getRow();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

		return count;
	}

}