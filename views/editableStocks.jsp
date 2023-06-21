<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="eStoreProduct.model.stockSummaryModel,java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<table id="tableData" class="table table-bordered table-hover">
    <thead class="thead-dark">
        <tr>
            <th>Product ID</th>
            <th>Product Title</th>
            <th>Product Category ID</th>
            <th>Product GST Category ID</th>
            <th>Product Brand</th>
            <th>Image URL</th>
            <th>Product Description</th>
            <th>Reorder Level</th>
            <th>Product Category Title</th>
            <th>SGST</th>
            <th>IGST</th>
            <th>CGST</th>
            <th>GST</th>
            <th>Product Price</th>
            <th>Product Stock</th>
            <th>Product MRP</th>
            <th>EDIT</th>
        </tr>
    </thead>
    <tbody>
        <% List<stockSummaryModel> stock2=(List<stockSummaryModel>)request.getAttribute("stocks1"); %>
        <% for (stockSummaryModel stock : stock2) { %>\
    
        <tr>
        
            <td name="prod_id"><%= stock.getProd_id() %></td>
            <td><%= stock.getProd_title() %></td>
            <td><%= stock.getProd_prct_id() %></td>
            <td>
                <input type="text" id="prodGstcId-input" name="prod_gstc_id" value="<%= stock.getProd_gstc_id() %>">
            </td>
            <td><%= stock.getProd_brand() %></td>
            <td>
                <input type="text" id="imageUrl-input" name="image_url" value="<%= stock.getImage_url() %>">
            </td>
            <td>
                <input type="text" id="prodDesc-input" name="prod_desc" value="<%= stock.getProd_desc() %>">
            </td>
            <td>
                <input type="text" id="reorderlevel-input" name="reorderlevel" value="<%= stock.getReorderlevel() %>">
            </td>
            <td><%= stock.getPrct_title() %></td>
            <td><%= stock.getSgst() %></td>
            <td><%= stock.getIgst() %></td>
            <td><%= stock.getCgst() %></td>
            <td><%= stock.getGst() %></td>
            <td><%= stock.getProd_price() %></td>
            <td>
                <input type="text" id="prodStock-input" name="prod_stock" value="<%= stock.getProd_stock() %>">
            </td>
            <td>
                <input type="text" id="prodMrp-input"  name="prod_mrp" value="<%= stock.getProd_mrp() %>">
            </td>
            <td>
                <button  id="edit-button" class="btn btn-success" data-gstc-id="<%= stock.getProd_gstc_id() %>"
                    data-imageurl-id="<%= stock.getImage_url() %>" data-prod-id="<%= stock.getProd_id() %>"
                    data-reorderlevel-id="<%= stock.getReorderlevel() %>" data-stock-id="<%= stock.getProd_stock() %>"
                    data-mrp-id="<%= stock.getProd_mrp() %>">UPDATE</button>
            </td>
        </tr>
      
        <% } %>
    </tbody>
</table>
</div>
<script>

</script>
</body>
</html>