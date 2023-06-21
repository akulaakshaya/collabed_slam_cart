<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="eStoreProduct.model.stockSummaryModel,java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Stock Summary</title>
</head>
<body>

<div class="container mt-5">
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
            </tr>
        </thead>
        <tbody>
         <% List<stockSummaryModel> stocks=(List<stockSummaryModel>)request.getAttribute("stocks"); %>
            <% for (stockSummaryModel stock : stocks) { %>
            <tr>
                <td><%= stock.getProd_id() %></td>
                <td><%= stock.getProd_title() %></td>
                <td><%= stock.getProd_prct_id() %></td>
                <td><%= stock.getProd_gstc_id() %></td>
                <td><%= stock.getProd_brand() %></td>
                <td><%= stock.getImage_url() %></td>
                <td><%= stock.getProd_desc() %></td>
                <td><%= stock.getReorderlevel() %></td>
                <td><%= stock.getPrct_title() %></td>
                <td><%= stock.getSgst() %></td>
                <td><%= stock.getIgst() %></td>
                <td><%= stock.getCgst() %></td>
                <td><%= stock.getGst() %></td>
                <td><%= stock.getProd_price() %></td>
                <td><%= stock.getProd_stock() %></td>
                <td><%= stock.getProd_mrp() %></td>
            </tr>
            <% } %>
        </tbody>
    </table>
</div>

</body>
</html>
