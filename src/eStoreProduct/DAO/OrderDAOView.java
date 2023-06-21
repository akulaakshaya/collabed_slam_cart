package eStoreProduct.DAO;

import java.util.List;

import eStoreProduct.model.OrdersViewModel;

public interface OrderDAOView {
	public List<OrdersViewModel> getorderProds(int cust_id);

	public OrdersViewModel OrdProductById(int cust_id, Integer productId);

	public void cancelorderbyId(Integer productId);

	public String getShipmentStatus(int productId);

	public List<OrdersViewModel> getProductsSortedByPrice();

	public List<OrdersViewModel> getProductsSortedByShippingStatus();
}
