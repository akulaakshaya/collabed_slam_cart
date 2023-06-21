package eStoreProduct.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import eStoreProduct.model.Category;

@Component
public class CategoryDAOImp implements CategoryDAO{
	private static final String JDBC_DRIVER = "org.postgresql.Driver";
	private static final String DB_URL = "jdbc:postgresql://LocalHost:5432/postgres";
	private static final String USERNAME = "postgres";
	private static final String PASSWORD = "2468";
	
	@PersistenceContext
	private EntityManager entityManager;
	private final JdbcTemplate jdbcTemplate;
	private final String SQL_INSERT_CATEGORY = "insert into slam_productCAtegories(prct_id,prct_title,prct_desc) values(?,?,?)";
	private final String SQL_GET_TOP_CATGID = "select prct_id from slam_productCAtegories order by prct_id desc limit 1";

	@Autowired
	public CategoryDAOImp(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public boolean addNewCategory(Category catg) {
		int c_id = jdbcTemplate.queryForObject(SQL_GET_TOP_CATGID, int.class);
		c_id = c_id + 1;
		System.out.println(c_id + "Category_id\n");

		return jdbcTemplate.update(SQL_INSERT_CATEGORY, c_id, catg.getPrct_title(), catg.getPrct_desc()) > 0;
	}
	
	public List<String> getAllCategories() {
		List<String> categories = new ArrayList<>();

		try {
			// Load the JDBC driver
			Class.forName(JDBC_DRIVER);

			// Establish the database connection
			Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

			// Prepare the SQL query
			String query = "SELECT prct_title FROM slam_productCategories";
			PreparedStatement statement = connection.prepareStatement(query);

			// Execute the query
			ResultSet resultSet = statement.executeQuery();

			// Process the result set
			while (resultSet.next()) {
				// Retrieve category information from the result set
				String category = resultSet.getString("prct_title");

				// Add the category to the list
				categories.add(category);
			}

			// Close the result set, statement, and connection
			resultSet.close();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return categories;
	}
}