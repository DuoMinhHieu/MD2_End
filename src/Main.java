import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

class Product {
    String code;
    String name;
    double price;
    int quantity;
    String description;

    public Product(String code, String name, double price, int quantity, String description) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }
}

class ProductManager {
    ArrayList<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public boolean updateProduct(String code, Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).code.equals(code)) {
                products.set(i, updatedProduct);
                return true;
            }
        }
        return false;
    }

    public boolean deleteProduct(String code) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).code.equals(code)) {
                products.remove(i);
                return true;
            }
        }
        return false;
    }

    public void displayProducts() {
        for (Product product : products) {
            System.out.println("Code: " + product.code + ", Name: " + product.name + ", Price: " + product.price + ", Quantity: " + product.quantity + ", Description: " + product.description);
        }
    }

    public void sortProducts() {
        products.sort(Comparator.comparingDouble(product -> product.price));
    }

    public Product getMostExpensiveProduct() {
        if (products.isEmpty()) return null;
        return products.stream().max(Comparator.comparingDouble(product -> product.price)).orElse(null);
    }

    public void saveToCSV(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Code,Name,Price,Quantity,Description");
            for (Product product : products) {
                writer.println(product.code + "," + product.name + "," + product.price + "," + product.quantity + "," + product.description);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromCSV(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                if (data.length == 5) {
                    products.add(new Product(data[0], data[1], Double.parseDouble(data[2]), Integer.parseInt(data[3]), data[4]));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductManager productManager = new ProductManager();

        while (true) {
            System.out.println("\n****Menu:****");
            System.out.println("1. Xem danh sách");
            System.out.println("2. Thêm mới");
            System.out.println("3. Cập nhật");
            System.out.println("4. Xóa");
            System.out.println("5. Sắp xếp");
            System.out.println("6. Tìm kiếm sản phẩm có giá đắt nhất");
            System.out.println("7. Đọc từ file");
            System.out.println("8. Lưu vào file");
            System.out.println("9. Thoát");

            System.out.print("Nhập lựa chọn của bạn: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    productManager.displayProducts();
                    break;
                case "2":
                    System.out.print("Nhập mã sản phẩm: ");
                    String code = scanner.nextLine();
                    System.out.print("Nhập tên sản phẩm: ");
                    String name = scanner.nextLine();
                    System.out.print("Nhập giá sản phẩm: ");
                    double price = Double.parseDouble(scanner.nextLine());
                    System.out.print("Nhập số lượng sản phẩm: ");
                    int quantity = Integer.parseInt(scanner.nextLine());
                    System.out.print("Nhập mô tả sản phẩm: ");
                    String description = scanner.nextLine();
                    productManager.addProduct(new Product(code, name, price, quantity, description));
                    break;
                case "3":
                    System.out.print("Nhập mã sản phẩm cần cập nhật: ");
                    String updateCode = scanner.nextLine();
                    System.out.print("Nhập tên mới: ");
                    String updateName = scanner.nextLine();
                    System.out.print("Nhập giá mới: ");
                    double updatePrice = Double.parseDouble(scanner.nextLine());
                    System.out.print("Nhập số lượng mới: ");
                    int updateQuantity = Integer.parseInt(scanner.nextLine());
                    System.out.print("Nhập mô tả mới: ");
                    String updateDescription = scanner.nextLine();
                    if (!productManager.updateProduct(updateCode, new Product(updateCode, updateName, updatePrice, updateQuantity, updateDescription))) {
                        System.out.println("Sản phẩm không tồn tại.");
                    }
                    break;
                case "4":
                    System.out.print("Nhập mã sản phẩm cần xóa: ");
                    String deleteCode = scanner.nextLine();
                    if (!productManager.deleteProduct(deleteCode)) {
                        System.out.println("Sản phẩm không tồn tại.");
                    } else {
                        System.out.println("Đã xóa sản phẩm.");
                    }
                    break;
                case "5":
                    productManager.sortProducts();
                    System.out.println("Đã sắp xếp danh sách sản phẩm theo giá.");
                    break;
                case "6":
                    Product mostExpensiveProduct = productManager.getMostExpensiveProduct();
                    if (mostExpensiveProduct != null) {
                        System.out.println("Sản phẩm có giá đắt nhất: " + mostExpensiveProduct.name + ", Giá: " + mostExpensiveProduct.price);
                    } else {
                        System.out.println("Không có sản phẩm nào.");
                    }
                    break;
                case "7":
                    System.out.print("Nhập tên file CSV để đọc: ");
                    String readFilename = scanner.nextLine();
                    productManager.readFromCSV(readFilename);
                    break;
                case "8":
                    System.out.print("Nhập tên file CSV để lưu: ");
                    String saveFilename = scanner.nextLine();
                    productManager.saveToCSV(saveFilename);
                    System.out.println("Đã lưu danh sách sản phẩm vào file CSV.");
                    break;
                case "9":
                    System.out.println("Kết thúc chương trình.");
                    System.exit(0);
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }
}