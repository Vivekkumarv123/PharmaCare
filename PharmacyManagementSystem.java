//Java Project on Pharmacy Management System
import java.util.*;

public class PharmacyManagementSystem {

    // Customer, Sales, Supplier, Order, InventoryManager, Medicine, Bill, DoctorPrescription classes remain unchanged
    static class Customer {
        String name;
        String phoneNumber;

        public Customer(String name, String phoneNumber) {
            this.name = name;
            this.phoneNumber = phoneNumber;
        }
    }

    public static class Sales {
        Customer customer;
        Medicine medicine;
        int quantity;
        double discount;

        public Sales(Customer customer, Medicine medicine, int quantity, double discount) {
            this.customer = customer;
            this.medicine = medicine;
            this.quantity = quantity;
            this.discount = discount;
        }

        public double calculateTotal() {
            return quantity * (medicine.price - (medicine.price * discount / 100));
        }
    }

    public static class Supplier {
        String name;
        String contact;

        public Supplier(String name, String contact) {
            this.name = name;
            this.contact = contact;
        }
    }

    public static class Order {
        Supplier supplier;
        List<Medicine> medicines;

        public Order(Supplier supplier, List<Medicine> medicines) {
            this.supplier = supplier;
            this.medicines = medicines;
        }
    }

    public static class InventoryManager {
        List<Medicine> inventory;

        public InventoryManager() {
            this.inventory = new ArrayList<>();
        }

        public void addMedicine(Medicine medicine) {
            inventory.add(medicine);
            System.out.println("Medicine added successfully.");
	    System.out.println();
        }

        public void updateMedicine(int index, int newStock, double newPrice) {
            if (index >= 0 && index < inventory.size()) {
                Medicine medicine = inventory.get(index);
                medicine.stock = newStock;
                medicine.price = newPrice;
                System.out.println("Medicine updated successfully.");
	    System.out.println();
            } else {
                System.out.println("Invalid medicine index.");
	    System.out.println();
            }
        }

        public void removeMedicine(int index) {
            if (index >= 0 && index < inventory.size()) {
                Medicine removedMedicine = inventory.remove(index);
                System.out.println("Medicine removed successfully: " + removedMedicine.name);
	    System.out.println();
            } else {
                System.out.println("Invalid medicine index.");
	    System.out.println();
            }
        }

        public void displayMedicineDetails() {
            System.out.println("\t\t\t"+"Medicine Details:");
            System.out.println("\t\t\t"+"+--------------+--------+--------------+-------+--------------+");
            System.out.println("\t\t\t"+"| Medicine     | Stock  | Expiry Date  | Price | Supplier     |");
            System.out.println("\t\t\t"+"+--------------+--------+--------------+-------+--------------+");

            for (Medicine medicine : inventory) {
                System.out.printf("\t\t\t"+"| %-12s | %-6d | %-12s | $%-5.2f | %-12s |\n",
                        medicine.name, medicine.stock, medicine.expiryDate, medicine.price, medicine.supplier.name);
            }

            System.out.println("\t\t\t"+"+--------------+--------+--------------+-------+--------------+");
	    System.out.println();
        }
    }

    public static class Medicine {
        String name;
        int stock;
        String expiryDate;
        double price;
        Supplier supplier;

        public Medicine(String name, int stock, String expiryDate, double price, Supplier supplier) {
            this.name = name;
            this.stock = stock;
            this.expiryDate = expiryDate;
            this.price = price;
            this.supplier = supplier;
        }
    }

    public static class Bill {
        Customer customer;
        DoctorPrescription doctorPrescription;
        List<Sales> salesList;
        double totalAmount;
        double discountedTotal;

        public Bill(Customer customer, DoctorPrescription doctorPrescription, List<Sales> salesList) {
            this.customer = customer;
            this.doctorPrescription = doctorPrescription;
            this.salesList = salesList;
            this.totalAmount = calculateTotalAmount();
            this.discountedTotal = calculateDiscountedTotal();
        }

        public double calculateTotalAmount() {
            return salesList.stream().mapToDouble(Sales::calculateTotal).sum();
        }

        public double calculateDiscountedTotal() {
            return totalAmount * 0.9;  // 10% discount
        }

        public void displayBill() {
            System.out.println("\t\t\t"+"-----------------Pharmacy Management System-----------------");
	    System.out.println();
            System.out.println("\t\t\t"+"------------------------------");
            System.out.println("\t\t\t"+"| Customer: " + customer.name);
            System.out.println("\t\t\t"+"| Doctor: " + doctorPrescription.doctorName);
            System.out.println("\t\t\t"+"| Prescription:");
            for (PrescriptionItem prescriptionItem : doctorPrescription.prescriptionItems) {
                System.out.printf("\t\t\t"+"|   - %-12s | Quantity: %-5d |\n", prescriptionItem.medicineName, prescriptionItem.quantity);
            }
            System.out.println("\t\t\t"+"| Medicines purchased:");
            System.out.println("\t\t\t"+"+--------------+--------+-----------+--------------+");
            System.out.println("\t\t\t"+"| Medicine     | Quantity | Price     | Total        |");
            System.out.println("\t\t\t"+"+--------------+--------+-----------+--------------+");

            for (Sales sales : salesList) {
                System.out.printf("\t\t\t"+"| %-12s | %-8d | $%-7.2f | $%-10.2f |\n",
                        sales.medicine.name, sales.quantity, sales.medicine.price, sales.calculateTotal());
            }

            System.out.println("\t\t\t"+"+--------------+--------+-----------+--------------+");
            System.out.printf("\t\t\t"+"| Total Amount:               | $%-10.2f |", totalAmount);
            System.out.println();
            System.out.printf("\t\t\t"+"| Discounted Total:           | $%-10.2f |", discountedTotal);
	    System.out.println();   
	    System.out.println("\t\t\t"+"+--------------+--------+-----------+--------------+");
	    System.out.println();
        }
    }

    public static class DoctorPrescription {
        String doctorName;
        List<PrescriptionItem> prescriptionItems;

        public DoctorPrescription(String doctorName, List<PrescriptionItem> prescriptionItems) {
            this.doctorName = doctorName;
            this.prescriptionItems = prescriptionItems;
        }
    }

    public static class PrescriptionItem {
        String medicineName;
        int quantity;

        public PrescriptionItem(String medicineName, int quantity) {
            this.medicineName = medicineName;
            this.quantity = quantity;
        }
    }

    private List<Customer> customers;
    private List<Sales> sales;
    private List<Supplier> suppliers;
    private List<Order> orderHistory;
    private InventoryManager inventoryManager;

    public PharmacyManagementSystem() {
        this.customers = new ArrayList<>();
        this.sales = new ArrayList<>();
        this.suppliers = new ArrayList<>();
        this.orderHistory = new ArrayList<>();
        this.inventoryManager = new InventoryManager();
    }

    public void addSupplier(Supplier supplier) {
        suppliers.add(supplier);
        System.out.println("Supplier added successfully.");
        System.out.println();
    }

    public void removeSupplier(int index) {
        if (index >= 0 && index < suppliers.size()) {
            Supplier removedSupplier = suppliers.remove(index);
            System.out.println("Supplier removed successfully: " + removedSupplier.name);
        } else {
            System.out.println("Invalid supplier index.");
        }
    }

    public void displaySuppliers() {
        System.out.println("\t\t\t"+"Suppliers:");
        System.out.println("\t\t\t"+"+--------------+--------------+");
        System.out.println("\t\t\t"+"| Supplier     | Contact      |");
        System.out.println("\t\t\t"+"+--------------+--------------+");

        for (Supplier supplier : suppliers) {
            System.out.printf("\t\t\t"+"| %-12s | %-12s |\n", supplier.name, supplier.contact);
        }

        System.out.println("\t\t\t"+"+--------------+--------------+");
        System.out.println();
    }

    public void addMedicine(String name, int stock, String expiryDate, double price, Supplier supplier) {
        Medicine medicine = new Medicine(name, stock, expiryDate, price, supplier);
        inventoryManager.addMedicine(medicine);
    }

    public void updateMedicine(int index, int newStock, double newPrice) {
        inventoryManager.updateMedicine(index, newStock, newPrice);
    }

    public void removeMedicine(int index) {
        inventoryManager.removeMedicine(index);
    }

    public void displayMedicineDetails() {
        inventoryManager.displayMedicineDetails();
    }

    // Inside the generateBill method, before calling displayBill():

public void generateBill(Customer customer, DoctorPrescription doctorPrescription) {
    List<Sales> prescriptionSales = new ArrayList<>();

    for (PrescriptionItem prescriptionItem : doctorPrescription.prescriptionItems) {
        Medicine medicine = findMedicineByName(prescriptionItem.medicineName);
        if (medicine != null && medicine.stock >= prescriptionItem.quantity) {
            int quantitySold = prescriptionItem.quantity;
            double perMedicinePrice = medicine.price;
            double totalPerMedicinePrice = perMedicinePrice * quantitySold;

            prescriptionSales.add(new Sales(customer, medicine, quantitySold, 0.0));
            medicine.stock -= quantitySold;
        } else {
            System.out.println("Medicine not available in sufficient quantity: " + prescriptionItem.medicineName);
        }
    }

    sales.addAll(prescriptionSales);

    Bill bill = new Bill(customer, doctorPrescription, prescriptionSales);
    orderHistory.add(new Order(suppliers.get(0), inventoryManager.inventory)); // Assuming the first supplier in the list
    bill.displayBill();
}

// Add a helper method to find a medicine by name:

private Medicine findMedicineByName(String medicineName) {
    for (Medicine medicine : inventoryManager.inventory) {
        if (medicine.name.equalsIgnoreCase(medicineName)) {
            return medicine;
        }
    }
    return null;
}


    public void viewPurchaseHistory() {
        System.out.println("\t\t\t"+"Purchase History:");
        System.out.println("\t\t\t"+"+--------------+--------------+--------+--------------+");
        System.out.println("\t\t\t"+"| Supplier     | Medicine     | Stock  | Price        |");
        System.out.println("\t\t\t"+"+--------------+--------------+--------+--------------+");

        for (Order order : orderHistory) {
            for (Medicine medicine : order.medicines) {
                System.out.printf("\t\t\t"+"| %-12s | %-12s | %-6d | $%-5.2f     |\n",
                        order.supplier.name, medicine.name, medicine.stock, medicine.price);
            }
        }

        System.out.println("\t\t\t"+"+--------------+--------------+--------+--------------+");
        System.out.println();
    }

    public static void main(String[] args) {
        PharmacyManagementSystem pms = new PharmacyManagementSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {	 	System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------");
	   System.out.println();
            System.out.println("\t"+"Pharmacy Management System Menu:-");
            System.out.println("\t\t"+"+------------------------------------------+");
            System.out.println("\t\t"+"| 1. Add Supplier                          |");
            System.out.println("\t\t"+"| 2. Remove Supplier                       |");
            System.out.println("\t\t"+"| 3. Display Suppliers                     |");
            System.out.println("\t\t"+"| 4. Add Medicine to Inventory             |");
            System.out.println("\t\t"+"| 5. Update Medicine in Inventory          |");
            System.out.println("\t\t"+"| 6. Remove Medicine from Inventory        |");
            System.out.println("\t\t"+"| 7. Display Medicine Details in Inventory |");
            System.out.println("\t\t"+"| 8. Generate Bill                         |");
            System.out.println("\t\t"+"| 9. View Purchase History                 |");
            System.out.println("\t\t"+"| 10. Exit                                 |");
            System.out.println("\t\t"+"+------------------------------------------+");
            System.out.print("Enter your choice: ");

            int option = scanner.nextInt();
            scanner.nextLine();  // Consume the newline left by nextInt()

            switch (option) {
                case 1:
                    // Add Supplier
                    System.out.println("Enter Supplier Name:");
                    String supplierName = scanner.nextLine();

                    System.out.println("Enter Supplier Contact:");
                    String supplierContact = scanner.nextLine();

                    Supplier newSupplier = new Supplier(supplierName, supplierContact);
                    pms.addSupplier(newSupplier);
                    break;

                case 2:
		     pms.displaySuppliers();
                    // Remove Supplier
                    System.out.println("Enter Supplier Index to Remove: [index starts from 1]");
                    int supplierIndexToRemove = scanner.nextInt();
                    scanner.nextLine();  // Consume the newline left by nextInt()
                    pms.removeSupplier((supplierIndexToRemove-1));
                    break;

                case 3:
                    // Display Suppliers
                    pms.displaySuppliers();
                    break;

                case 4:
                    // Add Medicine to Inventory
                    System.out.println("Enter Medicine Name:");
                    String medicineNameToAdd = scanner.nextLine();

                    System.out.println("Enter Stock:");
                    int stockToAdd = scanner.nextInt();
                    scanner.nextLine();  // Consume the newline left by nextInt()

                    System.out.println("Enter Expiry Date:");
                    String expiryDateToAdd = scanner.nextLine();

                    System.out.println("Enter Price:");
                    double priceToAdd = scanner.nextDouble();
                    scanner.nextLine();  // Consume the newline left by nextDouble()

                    // Display existing suppliers to choose from
                    System.out.println("Available Suppliers:");
                    pms.displaySuppliers();

                    System.out.println("Enter Supplier Index for the Medicine: [index starts from 1]");
                    int supplierIndexForMedicineToAdd = scanner.nextInt();
                    scanner.nextLine();  // Consume the newline left by nextInt()

                    Supplier supplierForMedicineToAdd = pms.suppliers.get((supplierIndexForMedicineToAdd-1));

                    pms.addMedicine(medicineNameToAdd, stockToAdd, expiryDateToAdd, priceToAdd, supplierForMedicineToAdd);
                    break;

                case 5:
		     pms.displayMedicineDetails();
                    // Update Medicine in Inventory
                    System.out.println("Enter Medicine Index to Update:[index starts from 1]");
                    int medicineIndexToUpdate = scanner.nextInt();
                    scanner.nextLine();  // Consume the newline left by nextInt()

                    System.out.println("Enter New Stock:");
                    int newStockToUpdate = scanner.nextInt();
                    scanner.nextLine();  // Consume the newline left by nextInt()

                    System.out.println("Enter New Price:");
                    double newPriceToUpdate = scanner.nextDouble();
                    scanner.nextLine();  // Consume the newline left by nextDouble()

                    pms.updateMedicine((medicineIndexToUpdate-1), newStockToUpdate, newPriceToUpdate);
                    break;

                case 6:
		    pms.displayMedicineDetails();
                    // Remove Medicine from Inventory
                    System.out.println("Enter Medicine Index to Remove: [index starts from 1]");
                    int medicineIndexToRemove = scanner.nextInt();
                    scanner.nextLine();  // Consume the newline left by nextInt()

                    pms.removeMedicine((medicineIndexToRemove-1));
                    break;

                case 7:
                    // Display Medicine Details in Inventory
                    pms.displayMedicineDetails();
                    break;

                case 8:
                    // Generate Bill
                    System.out.println("Enter Customer Name:");
                    String customerName = scanner.nextLine();

                    System.out.println("Enter Customer Phone Number:");
                    String customerPhoneNumber = scanner.nextLine();

                    Customer customer = new Customer(customerName, customerPhoneNumber);

                    System.out.println("Enter Doctor Name:");
                    String doctorName = scanner.nextLine();

		    pms.displayMedicineDetails();

                    List<PrescriptionItem> prescriptionItems = new ArrayList<>();
                    while (true) {
                        System.out.println("Enter Medicine Name for Prescription (or 'exit' to finish):");
                        String medicineName = scanner.nextLine();
                        if (medicineName.equalsIgnoreCase("exit")) {
                            break;
                        }
                        System.out.println("Enter Quantity:");
                        int quantity = scanner.nextInt();
                        scanner.nextLine();  // Consume the newline left by nextInt()
                        prescriptionItems.add(new PrescriptionItem(medicineName, quantity));
                    }

                    DoctorPrescription doctorPrescription = new DoctorPrescription(doctorName, prescriptionItems);

                    pms.generateBill(customer, doctorPrescription);
                    break;

                case 9:
                    // View Purchase History
                    pms.viewPurchaseHistory();
                    break;

                case 10:
                    // Exit
                    System.out.println("Exiting Pharmacy Management System. Goodbye!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option. Please choose a valid option.");
                    break;
            }
        }
    }
}
