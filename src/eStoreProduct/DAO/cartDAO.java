package eStoreProduct.DAO;

import java.util.List;

import eStoreProduct.model.cartModel;
import eStoreProduct.utility.ProductStockPrice;

public interface cartDAO {
	public int addToCart(int productId, int customerId);

	public int removeFromCart(int productId, int customerId);

	public List<ProductStockPrice> getCartProds(int cust_id);

	public int updateQty(cartModel cm);

	public int updateinsert(List<ProductStockPrice> products, int cust_id);
}