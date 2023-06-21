<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="eStoreProduct.utility.ProductStockPrice" %>
<%@ page import="eStoreProduct.model.custCredModel" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cart</title>
    <script>
    function buynow()
    {
  	  console.log("buy now");
  	 $.ajax({
         url: 'buycartitems',
         method: 'GET',
         success: function(response) {
             console.log("response of updateqty  "+response);
             $('#payment').html(response); // Set the response HTML as the inner HTML of the cart items element
         },
         error: function(xhr, status, error) {
             console.log('AJAX Error: ' + error);
         }
     });

  	  	//window.location.href="buycartitems";  
  	    }
    function updateQuantity(input) {
        var quantity = input.value;
        console.log(quantity+"qnty!!!!!!!!!!!");
        var productId = input.getAttribute('data-product-id');
        console.log("qty in updateqty method "+quantity);
        console.log("product no=" + productId);
        $.ajax({
            url: 'updateQuantity',
            method: 'POST',
            data: { productId: productId, quantity: quantity },
            success: function(response) {
                console.log("response of updateqty  "+response);
                $("#cst").html("Total Cost: " + response);
            },
            error: function(xhr, status, error) {
                console.log('AJAX Error: ' + error);
            }
        });
    }
    function chechValidity(){
    	var pincode=document.getElementById("pincode").value;
    	$.ajax({
            url: 'checkPincodeValidity',
            method: 'POST',
            data: { pincode:pincode },
            success: function(response) {
                console.log("response of updateqty  "+response);
                window.alert(response);
            },
            error: function(xhr, status, error) {
                console.log('AJAX Error: ' + error);
            }
        });
    }
    </script>
</head>
<body>
    <div class="container mt-5">
        <h2>Cart</h2>
        <div class="row mt-4">
            <%-- Iterate over the products and render the HTML content --%>
            <%		custCredModel cust1 = (custCredModel) session.getAttribute("customer");
           			 List<ProductStockPrice> products;
    		if (cust1 != null) {
               products = (List<ProductStockPrice>) request.getAttribute("products");
    		} else {
                products = (List<ProductStockPrice>) request.getAttribute("alist");
			}
				double totalcost=0.0;
                for (ProductStockPrice product : products) {
            %>
            <div class="col-lg-4 col-md-6 mb-4">
                <div class="card h-100">
                    <img class="card-img-top" src="<%= product.getImage_url() %>" alt="<%= product.getProd_title() %>">
                    <div class="card-body">
                        <h5 class="card-title"><%= product.getProd_title() %></h5>
                        <p class="card-text"><%= product.getProd_desc() %></p>
                        <p class="card-text"><%= product.getPrice() %></p>
                        <label>Qty:</label>
                        <input type="number" class="btn btn-primary qtyinp input-width" id="qtyinp" value="<%=product.getQuantity() %>" min="1" onchange="updateQuantity(this)" data-product-id="<%= product.getProd_id() %>">
                        <br><br>
                        <button class="btn btn-primary removeFromCart" data-product-id="<%= product.getProd_id() %>">Remove from Cart</button>
                        <button class="btn btn-secondary addToWishlistButton" data-product-id="<%= product.getProd_id() %>">Add to Wishlist</button>
                    </div>
                </div>
            </div>
            <%
                }
            %>
        </div>
    </div>
    
    <div align="center" container mt-3">
    <div id="checkpincode">
    <input type="number" id="pincode" name="pincode">
    <input type="button" onclick="chechValidity()" value="Check">
    </div>
        <div id="cst">
            <p align="center">Total Cost=<%=request.getAttribute("cartcost") %></p>
        </div>
        <button class="btn btn-primary BuyNow" onclick="buynow()">Place Order</button>
    </div>
</body>
</html>
 