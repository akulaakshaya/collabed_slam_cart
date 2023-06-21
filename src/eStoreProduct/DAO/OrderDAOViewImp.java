package eStoreProduct.DAO;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import eStoreProduct.model.OrdersViewModel;

@Component
public class OrderDAOViewImp implements OrderDAOView {

	private final JdbcTemplate jdbcTemplate;
	private final RowMapper<OrdersViewModel> ordersMapper;

	public OrderDAOViewImp(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.ordersMapper = new OrdersMapper();
	}

	public List<OrdersViewModel> getorderProds(int cust_id) {
		String query = "SELECT sp.prod_id, sp.prod_title, " + "sp.image_url, sp.prod_desc, "
				+ "sps.prod_price, o.ordr_id, op.orpr_shipment_status " + "FROM slam_orders o "
				+ "JOIN slam_OrderProducts op ON o.ordr_id = op.ordr_id "
				+ "JOIN slam_products sp ON op.prod_id = sp.prod_id "
				+ "JOIN slam_productstock sps ON sp.prod_id = sps.prod_id " + "WHERE o.ordr_cust_id = ?";

		return jdbcTemplate.query(query, new Object[] { cust_id }, ordersMapper);
	}

	public OrdersViewModel OrdProductById(int cust_id, Integer productId) {
		String query = "SELECT sp.prod_id, sp.prod_title, sp.image_url, sp.prod_desc, "
				+ "sps.prod_price, o.ordr_id, op.orpr_shipment_status " + "FROM slam_orders o "
				+ "JOIN slam_OrderProducts op ON o.ordr_id = op.ordr_id "
				+ "JOIN slam_products sp ON op.prod_id = sp.prod_id "
				+ "JOIN slam_productstock sps ON sp.prod_id = sps.prod_id "
				+ "WHERE o.ordr_cust_id = ? AND sp.prod_id = ? order by prod_id limit 1";

		return jdbcTemplate.queryForObject(query, new Object[] { cust_id, productId }, ordersMapper);
	}

	public void cancelorderbyId(Integer productId) {
		String updateQuery = "UPDATE slam_OrderProducts SET orpr_shipment_status = 'cancelled' WHERE prod_id = ?";
		jdbcTemplate.update(updateQuery, productId);

	}

	@Override
	public String getShipmentStatus(int prodId) {

		String sql = "SELECT orpr_shipment_status FROM slam_orderproducts WHERE prod_id = ?";

		try {
			return jdbcTemplate.queryForObject(sql, new Object[] { prodId }, String.class);
		} catch (EmptyResultDataAccessException e) {
			return null; // Handle the case when shipment status is not found
		}

	}

	@Override
	public List<OrdersViewModel> getProductsSortedByPrice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrdersViewModel> getProductsSortedByShippingStatus() {
		// TODO Auto-generated method stub
		return null;
	}
}
