package in.mindbug.lqc;

import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of an HTML page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

	private static final long serialVersionUID = 1L;

	private final Grid<Entity> grid = new Grid<>();

	@Override
	public void init(VaadinRequest vaadinRequest) {
		final VerticalLayout rootLayout = new VerticalLayout();
		setContent(rootLayout);
		rootLayout.addComponent(grid);

		String dbQuery = "select id, name from employee where name like '%VIK%'";

		HashMap<String, Attribute> map = new HashMap<>();

		map.put("id", new Attribute("id", Integer.class, null, "Emp Id"));
		map.put("name", new Attribute("name", String.class, null, "Emp Name"));

		for (String colName : map.keySet()) {
			Attribute attribute = map.get(colName);
			grid.addColumn(entity -> {
				Class<?> dataType = attribute.getDataType();
				Attribute attributeWithValue = entity.getMap().get(colName);
				if (dataType == String.class) {
					return (String) attributeWithValue.getValue();
				} else if (dataType == Integer.class) {
					return (Integer) attributeWithValue.getValue();
				}
				return null;
			}).setCaption(attribute.getCaption());
		}
		
		try {
			EntityDataProvider entityDataProvider = new EntityDataProvider();
			entityDataProvider.setQueryAndAttributes(dbQuery, map);
			grid.setDataProvider(entityDataProvider);
			entityDataProvider.refreshAll();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Notification.show("Error", e.getMessage(), Type.ERROR_MESSAGE);
		}

	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
		private static final long serialVersionUID = 1L;
	}
}
