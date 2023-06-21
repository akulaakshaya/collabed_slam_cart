package eStoreProduct.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eStoreProduct.BLL.BLL;
import eStoreProduct.BLL.BLLClass2;
import eStoreProduct.DAO.ProductDAO;
import eStoreProduct.DAO.cartDAO;
import eStoreProduct.DAO.customerDAO;
import eStoreProduct.DAO.orderDAOJDBC;
import eStoreProduct.model.Order;
import eStoreProduct.model.custCredModel;
import eStoreProduct.utility.ProductStockPrice;

@Controller
public class CustomerController {
	customerDAO cdao;
	// BLLClass obj;
	cartDAO cartimp;

	BLL BLL;
	BLLClass2 bl2;
	// BLLClass obj;
	ProductDAO pdaoimp;
	orderDAOJDBC odao;

	@Autowired
	public CustomerController(cartDAO cartdao, customerDAO customerdao, BLLClass2 bl2, BLL bl1, ProductDAO productdao,
			orderDAOJDBC odao) {
		cdao = customerdao;
		cartimp = cartdao;
		this.bl2 = bl2;
		this.BLL = bl1;
		pdaoimp = productdao;
		this.odao = odao;
		// cartdao1 = cartdao;
	}

	@RequestMapping(value = "/profilePage")
	public String sendProfilePage(Model model, HttpSession session) {
		custCredModel cust = (custCredModel) session.getAttribute("customer");
		// System.out.println(cust.getCustId());
		model.addAttribute("cust", cust);
		return "profile";
	}

	// on clicking update Profile in profile page
	@RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
	public String userupdate(@ModelAttribute("Customer") custCredModel cust, Model model, HttpSession session) {
		cdao.updatecustomer(cust);
		custCredModel custt = cdao.getCustomerById(cust.getCustId());
		if (custt != null) {
			model.addAttribute("cust", custt);
		}
		return "profile";
	}

	/*
	 * @RequestMapping(value = "/payment", method = RequestMethod.POST) public String showPaymentOptions(Model model,
	 * HttpSession session) { return "payment"; }
	 */

	@GetMapping("/buycartitems")
	public String confirmbuycart(Model model, HttpSession session) {
		custCredModel cust = (custCredModel) session.getAttribute("customer");
		System.out.println("buycartitems");
		if (cust == null) {
			return "signIn";
		}
		// model.addAttribute("cust", cust);
		List<ProductStockPrice> products = cartimp.getCartProds(cust.getCustId());
		model.addAttribute("products", products);
		System.out.println("in buycartitems");
		double cartcost = BLL.getCartCost(cust.getCustId());
		model.addAttribute("cartcost", cartcost);
		return "paymentcart";

	}

	@GetMapping("/done")
	public String orderCreation(Model model) {
		return "redirect:/OrderCreation";
	}

	@RequestMapping(value = "/invoice", method = RequestMethod.POST)
	public String showPaymentOptions(Model model, HttpSession session, @Validated Order od) {
		custCredModel cust1 = (custCredModel) session.getAttribute("customer");
		List<ProductStockPrice> products = cartimp.getCartProds(cust1.getCustId());
		od.setGst(BLL.getOrderGST(products));
		model.addAttribute("products", products);
		model.addAttribute("payment_id", od.getPaymentReference());
		odao.insertIntoOrders(od, products);
		return "invoice";
	}

	@PostMapping("/confirmShipmentAddress")
	@ResponseBody
	public String confirm(@RequestParam(value = "mobile") String mobile,
			@RequestParam(value = "custsaddress") String custsaddress,
			@RequestParam(value = "spincode") int spincode, Model model, HttpSession session) {
		// System.out.println("confirm shipmentaddress");
		custCredModel cust1 = (custCredModel) session.getAttribute("customer");
		custCredModel cust2 = new custCredModel();
		cust2.setCustMobile(mobile);
		cust2.setCustSAddress(custsaddress);
		cust2.setCustPincode(spincode);
		session.setAttribute("cust2", cust2);
		// cdao.updateshipmentaddress(cust1.getCustId(), shaddr);
		return "OK";

	}

	@GetMapping("/paymentoptions")
	public String orderCreate(Model model, HttpSession session) {
		// String orderId = bl2.createRazorpayOrder(Double.parseDouble((String) session.getAttribute("qtycost")));
		custCredModel cust = (custCredModel) session.getAttribute("customer");
		double cartcost = BLL.getCartCost(cust.getCustId());
		// model.addAttribute("cartcost", cartcost);
		System.out.println(cartcost + ":tcost");
		System.out.println("amount in controller before razor pay  " + cartcost);
		String orderId = bl2.createRazorpayOrder(cartcost);
		model.addAttribute("orderId", orderId);
		model.addAttribute("amt", (int) cartcost * 100);
		return "payment-options";
	}

	@PostMapping("/updateshipment")
	@ResponseBody
	public String handleFormSubmission(@RequestParam(value = "pincode") int pincode) {

		boolean isValid = pdaoimp.isPincodeValid(pincode);
		if (isValid) {

			return "Valid";
		} else {
			return "Not Valid";
		}
	}

	
	
	@GetMapping("/orderPlacedSuccessfully")
	public String orderPlaced(Model model) {
		
		return "OrderPlaced";
	}
}
