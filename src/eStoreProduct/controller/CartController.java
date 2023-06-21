package eStoreProduct.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// import eStoreProduct.BLL.BLLClass;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eStoreProduct.BLL.BLL;
import eStoreProduct.DAO.ProductDAO;
import eStoreProduct.DAO.ServicableRegionDAO;
import eStoreProduct.DAO.cartDAO;
import eStoreProduct.DAO.customerDAO;
import eStoreProduct.model.cartModel;
import eStoreProduct.model.custCredModel;
import eStoreProduct.utility.ProductStockPrice;

@Controller
public class CartController {
	ServicableRegionDAO sdao;
	static boolean flag = false;
	cartDAO cartimp;
	private final ProductDAO pdaoimp;
	List<ProductStockPrice> alist = new ArrayList<>();
	customerDAO cdao;
	// ______________
	BLL BLL;

	// BLLClass obj = new BLLClass();
	@Autowired
	public CartController(cartDAO cartdao, ProductDAO productdao, customerDAO cdao, BLL b, ServicableRegionDAO sdao) {
		cartimp = cartdao;
		pdaoimp = productdao;
		this.cdao = cdao;
		this.sdao = sdao;
		BLL = b;
	}

	@GetMapping("/addToCart")
	@ResponseBody
	public String addToCart(@RequestParam(value = "productId", required = true) int productId, Model model,
			HttpSession session) throws NumberFormatException, SQLException {
		custCredModel cust1 = (custCredModel) session.getAttribute("customer");
		if (cust1 != null) {
			return cartimp.addToCart(productId, cust1.getCustId()) + " Added to cart";
		} else {
			ProductStockPrice product = pdaoimp.getProductById(productId);
			product.setQuantity(1);
			alist.add(product);
			model.addAttribute("alist", alist);
			return "added to cart";

		}
	}

	@RequestMapping(value = "/cartDisplay", method = RequestMethod.GET)
	public String getSignUpPage(Model model, HttpSession session) {
		double cartt = 0;
		// ProductDAO pdao = new ProductDAO();
		custCredModel cust1 = (custCredModel) session.getAttribute("customer");
		if (cust1 != null) {
			List<ProductStockPrice> products = cartimp.getCartProds(cust1.getCustId());
			model.addAttribute("products", products);
			double cartcost = BLL.getCartCost(cust1.getCustId());
			model.addAttribute("cartcost", cartcost);

			// Forward to the cart.jsp view
			return "cart";
		} else {
			// Set the products attribute in the model
			double cartcost = BLL.getCartCost(alist);
			model.addAttribute("cartcost", cartcost);
			model.addAttribute("alist", alist);
			return "cart";

		}
	}

	@RequestMapping(value = "/signOk", method = RequestMethod.POST)
	public String getHomeFinal(@Validated custCredModel ccm, Model model, HttpSession session) {
		// Retrieve the products ArrayList from the model
		custCredModel cust = cdao.getCustomer(ccm.getCustEmail(), ccm.getCustPassword());
		session.setAttribute("customer", cust);
		if (cust != null) {
			flag = true;
			System.out.println("customer there");
		}
		System.out.println("not validCustomer ");
		if (alist != null && !alist.isEmpty()) {
			System.out.println(alist + " in signOk");
			System.out.println(alist.isEmpty() + " in signOk");
			try {
				cdao.updatelastlogin(cust.getCustId());
			} catch (Exception e) {
				System.out.println(e);
			}

			if (cust != null) {
				flag = true;
				if (alist != null) {
					cartimp.updateinsert(alist, cust.getCustId());
					List<ProductStockPrice> products = cartimp.getCartProds(cust.getCustId());
					model.addAttribute("products", products);
					return "home";
				}
			}

			// custCredModel cust1 = (custCredModel) session.getAttribute("customer");
			// model.addAttribute("cust1", cust1);
			model.addAttribute("fl", flag);
		}

		return "home";
	}

	@GetMapping("/removeFromCart")
	@ResponseBody
	public String removeFromCart(@RequestParam(value = "productId", required = true) int productId, Model model,
			HttpSession session) throws NumberFormatException, SQLException {
		custCredModel cust1 = (custCredModel) session.getAttribute("customer");
		if (cust1 != null) {
			System.out.println("remove from cart login");
			return cartimp.removeFromCart(productId, cust1.getCustId()) + " remove from cart";
		} else {

			for (ProductStockPrice p : alist) {
				if (p.getProd_id() == productId)

					alist.remove(p);
			}

			return "remove from cart";
		}

	}

	@PostMapping("/updateQuantity")
	public int updateQuantity(@RequestParam(value = "productId", required = true) int productId,
			@RequestParam(value = "quantity", required = true) int quantity, Model model, HttpSession session)
			throws NumberFormatException, SQLException {
		custCredModel cust1 = (custCredModel) session.getAttribute("customer");
		if (cust1 != null) {
			cartModel cart = new cartModel(cust1.getCustId(), productId, quantity);
			return cartimp.updateQty(cart);
		} else {
			for (ProductStockPrice product : alist) {
				if (product.getProd_id() == productId) {
					product.setQuantity(quantity);
				}
			}
		}
		return quantity;
	}

	@PostMapping("/checkPincodeValidity")
	@ResponseBody
	public boolean checkPincodeValidity(@RequestParam(value = "pincode", required = true) String pincode, Model model,
			HttpSession session) throws NumberFormatException, SQLException {
		return sdao.getValidityOfPincode(Integer.parseInt(pincode));

	}

}
