package eStoreProduct.BLL;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eStoreProduct.DAO.ProductDAO;
import eStoreProduct.DAO.cartDAO;
import eStoreProduct.utility.ProductStockPrice;

@Component
public class BLL {
	ProductDAO pdaoimp;
	cartDAO cartimp;

	@Autowired
	public BLL(ProductDAO productdao, cartDAO ca) {
		pdaoimp = productdao;
		cartimp = ca;
	}

	public double getCartCost(int id) {
		double cartcost = 0.0;
		List<ProductStockPrice> cproducts = cartimp.getCartProds(id);
		for (ProductStockPrice p : cproducts) {
			cartcost += p.getPrice() * p.getQuantity();
		}
		return (cartcost*100);
	}

	public double getCartCost(List<ProductStockPrice> al) {
		double cost = 0.0;
		for (ProductStockPrice p : al) {
			cost += p.getPrice() * p.getQuantity();
		}
		return cost;
	}

	public double getOrderGST(List<ProductStockPrice> al) {
		double gst = 0.0;
		for (ProductStockPrice ps : al) {
			gst += (ps.getGst() * ps.getPrice()) / 100;
		}
		return gst;
	}
}
