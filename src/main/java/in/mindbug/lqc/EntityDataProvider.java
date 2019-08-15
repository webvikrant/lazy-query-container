package in.mindbug.lqc;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.stream.Stream;

import com.vaadin.data.provider.AbstractBackEndDataProvider;
import com.vaadin.data.provider.Query;

public class EntityDataProvider extends AbstractBackEndDataProvider<Entity, Void> {
	private static final long serialVersionUID = 1L;

	private Database database;
	private String dbQuery;
	private HashMap<String, Attribute> map;

	public EntityDataProvider() throws SQLException {
		// TODO Auto-generated constructor stub
		database = new Database();
	}

	public void setQueryAndAttributes(String dbQuery, HashMap<String, Attribute> map) {
		this.dbQuery = dbQuery;
		this.map = map;
	}

	@Override
	protected Stream<Entity> fetchFromBackEnd(Query<Entity, Void> query) {
		// TODO Auto-generated method stub
		return database.selectRows(dbQuery, map, query.getLimit(), query.getOffset()).stream();
	}

	@Override
	protected int sizeInBackEnd(Query<Entity, Void> query) {
		// TODO Auto-generated method stub
		return database.selectRowCount(dbQuery);
	}

	@Override
	public Object getId(Entity item) {
		// TODO Auto-generated method stub
		return item;
	}
}