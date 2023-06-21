package eStoreProduct.DAO;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import eStoreProduct.model.Order;
import eStoreProduct.utility.ProductStockPrice;

@Component
public class orderDAOJDBCImp implements orderDAOJDBC {
	JdbcTemplate jdbcTemplate;
	private String ins = "INSERT INTO slam_orders (ordr_cust_id, ordr_billno, ordr_odate, ordr_total, ordr_gst, ordr_payreference, ordr_paymode, ordr_paystatus, ordr_saddress, order_shipment_status,ordr_shipment_date) VALUES (?, ?, CURRENT_TIMESTAMP,?, ?, ?, ?, ?, ?, ?, CURRENT_DATE + INTERVAL '5 days') RETURNING ordr_id";
	private String ins_ord_prd = "INSERT INTO slam_orderproducts (ordr_id, prod_id, orpr_qty, orpr_gst, orpr_price, orpr_shipment_status)\r\n"
			+ "VALUES (?, ?, ?, ?, ?, ?)";

	@Autowired
	public orderDAOJDBCImp(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);

	}

	@Override
	public void insertIntoOrders(Order or, List<ProductStockPrice> al) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(con -> {
			PreparedStatement ps = con.prepareStatement(ins, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, or.getCustomerId());
			ps.setString(2, or.getBillNumber());
			ps.setDouble(3, or.getTotal());
			ps.setDouble(4, or.getGst());
			ps.setString(5, or.getPaymentReference());
			ps.setString(6, "Online");
			ps.setString(7, or.getPaymentStatus());
			ps.setString(8, or.getShippingAddress());
			ps.setString(9, "Order_Placed");

			return ps;
		}, keyHolder);

		Number generatedOrderId = keyHolder.getKey();
		int ordrId = generatedOrderId != null ? generatedOrderId.intValue() : 0;

		for (ProductStockPrice product : al) {
			jdbcTemplate.update(ins_ord_prd, ordrId, product.getProd_id(), product.getQuantity(), product.getGst(),
					product.getPrice(), "Order_Placed");
		}
	}

}
