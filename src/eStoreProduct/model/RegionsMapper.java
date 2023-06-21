package eStoreProduct.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class RegionsMapper implements RowMapper<Regions> {

	@Override
	public Regions mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Regions rg = new Regions(rs.getInt("region_id"), rs.getString("region_name"), rs.getInt("region_pin_from"),
				rs.getInt("region_pin_to"), rs.getDouble("region_surcharge"), rs.getDouble("region_pricewaiver"));
		return rg;
	}

}