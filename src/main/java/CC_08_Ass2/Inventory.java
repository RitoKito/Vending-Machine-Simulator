
package CC_08_Ass2;
import com.opencsv.CSVWriter;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.util.List;

public class Inventory {
	private static String productpath = "./src/main/resources/Product.csv";
	private ArrayList<Product> products;
	public ArrayList<Product> getProducts() { return products; }

	public Inventory(){
		products = loadProductFile();
	}

    public static ArrayList<Product> loadProductFile(){
        ArrayList<Product> products = new ArrayList<>();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(productpath));
			String line = reader.readLine();
			while (line != null) {
                String[] item = line.split(",");
				products.add(new Product(Integer.parseInt(item[0].replace("\"", "")), item[1].replace("\"", ""), item[2].replace("\"", ""),
						Double.parseDouble(item[3].replace("\"", "")), Integer.parseInt(item[4].replace("\"", "")), Integer.parseInt(item[5].replace("\"", ""))));

				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}// go back

        return products;
    }

    public static void saveToProductFile(ArrayList<Product> products) {
		try {
			CSVWriter writer = new CSVWriter(new FileWriter("./src/main/resources/Product.csv"));
			List<String[]> productDetails = new ArrayList<>();
			for (Product product : products) {
				String[] productDeet = new String[6];
				productDeet[0] = String.valueOf(product.getProductID());
				productDeet[1] = product.getProductType();
				productDeet[2] = product.getName();
				productDeet[3] = String.valueOf(product.getPrice());
				productDeet[4] = String.valueOf(product.getStock());
				productDeet[5] = String.valueOf(product.getTotalSold());
				productDetails.add(productDeet);
			}

			for (String[] d : productDetails) {
				writer.writeNext(d);
			}


			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	public void produceInventoryReport(){
		try {
			CSVWriter writer = new CSVWriter(new FileWriter("./reports/InventoryReport.csv"));
			List<String[]> productDetails = new ArrayList<>();
			for(Product product: products){
				String[] productDeet = new String[5];
				productDeet[0] = String.valueOf(product.getProductID());
				productDeet[1] = product.getProductType();
				productDeet[2] = product.getName();
				productDeet[3] = String.valueOf(product.getPrice());
				productDeet[4] = String.valueOf(product.getStock());
				productDetails.add(productDeet);
			}

			String[] headers = new String[6];
			headers[0] = "Item ID";
			headers[1] = "Product Type";
			headers[2] = "Product Name";
			headers[3] = "Product Price";
			headers[4] = "Stock";

			writer.writeNext(headers);

			for(String[] d: productDetails){
				writer.writeNext(d);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void produceProductSalesReport(){
		try {
			CSVWriter writer = new CSVWriter(new FileWriter("./report/ProductSalesReport.csv"));
			List<String[]> productDetails = new ArrayList<>();
			for(Product product: products){
				String[] productDeet = new String[3];
				productDeet[0] = String.valueOf(product.getProductID());
				productDeet[1] = product.getName();
				productDeet[2] = String.valueOf(product.getTotalSold());
				productDetails.add(productDeet);
			}

			String[] headers = new String[3];
			headers[0] = "Item ID";
			headers[1] = "Product Name";
			headers[2] = "Total Sales";

			writer.writeNext(headers);

			for(String[] d: productDetails){
				writer.writeNext(d);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
