
 package CC_08_Ass2;

 import javafx.animation.KeyFrame;
 import javafx.animation.Timeline;
 import javafx.beans.binding.Bindings;
 import javafx.event.ActionEvent;
 import javafx.event.EventHandler;
 import javafx.geometry.HPos;
 import javafx.geometry.Insets;
 import javafx.geometry.Pos;
 import javafx.geometry.VPos;
 import javafx.scene.Scene;
 import javafx.scene.control.*;
 import javafx.scene.control.cell.PropertyValueFactory;
 import javafx.scene.input.MouseEvent;
 import javafx.scene.layout.GridPane;
 import javafx.scene.layout.Pane;
 import javafx.scene.paint.Color;
 import javafx.scene.text.Font;
 import javafx.stage.Stage;
 import javafx.stage.StageStyle;
 import javafx.util.Duration;

 import java.util.ArrayList;
 import java.util.Optional;

 public class WindowManager {
     private AppWindow appWindow;
     private SystemManager systemManager;
     private Inventory inventory;
     private CashInventory cashInventory;
     private CardReaderManager cardReaderManager;
     private UserManager userManager;
     private User currentUser = new User(0, "Anonymous");
     private TransactionStorage transactionStorage;
     ShoppingCart cart = currentUser.getCart();
     String items;

     private Label userIdentityLabel;
     private Button logoutButton;

     private int elapsedSeconds = 0;
     private double startTime = System.nanoTime();

     private Label lastBoughtItems2;

     public WindowManager(AppWindow appWindow){
         this.appWindow = appWindow;
         this.systemManager = appWindow.systemManager;
         this.inventory = systemManager.getInventory();
         this.cashInventory = systemManager.getCashier();
         this.cardReaderManager = new CardReaderManager();
         this.userManager = systemManager.getUserManager();;
         this.transactionStorage = new TransactionStorage(); // Added
     }


     public Pane getLoginWindow(){
         GridPane gridPane = new GridPane();
         gridPane.setAlignment(Pos.TOP_CENTER);
         gridPane.setHgap(5);
         gridPane.setVgap(5);
         gridPane.setPadding(new Insets(10, 10, 10, 10));

         Label login = new Label();
         login.setText("Login: ");


         Label password = new Label();
         password.setText("Password: ");

         TextField loginEntry = new TextField();
         TextField passwordEntry = new PasswordField();

         Button loginButton = new Button();
         loginButton.setText("Login");
 //        loginButton.setMaxWidth(Double.MAX_VALUE);

         Label loginErrorLabel = new Label("Error: Incorrect Credentials");
         loginErrorLabel.setFont(new Font(15));
         loginErrorLabel.setTextFill(Color.color(1, 0, 0));
         loginErrorLabel.setVisible(false);

         loginButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 resetTimer();
                 User userCheck = UserManager.checkCredentials(loginEntry.getText(), passwordEntry.getText());
                 if(userCheck == null){
                     loginErrorLabel.setText("Error: Incorrect Credentials");
                     loginErrorLabel.setVisible(true);
                 }
                 else{
                     currentUser = userCheck;
                     appWindow.setScene(AppWindow.Window.STORE);
                     appWindow.setScene(AppWindow.Window.STORE);

                     if(currentUser instanceof Seller){
                         inventory.produceInventoryReport();
                         inventory.produceProductSalesReport();
 //                        getSellerMenu().setVisible(true);
                     }
                     else if(currentUser instanceof Cashier){
                         cashInventory.produceCashReport();
                     }else if(currentUser instanceof Owner){
                         Owner.user_report();
                     }
                 }
             }
         });

         Button loginButtonAnonymous = new Button();
         loginButtonAnonymous.setText("Login Anonymous");
 //        loginButtonAnonymous.setMaxWidth(Double.MAX_VALUE);
         loginButtonAnonymous.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 resetTimer();
                 loginErrorLabel.setVisible(false);
                 currentUser = new User(0, "Anonymous");
                 cart = currentUser.getCart();
                 appWindow.setScene(AppWindow.Window.STORE);
                 appWindow.setScene(AppWindow.Window.STORE);
             }
         });

         Button registerButton = new Button("Register");
         registerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 resetTimer();
                 GridPane popupGrid = new GridPane();
                 popupGrid.setVgap(10);
                 popupGrid.setHgap(10);
                 popupGrid.setPadding(new Insets(10, 10, 10, 10));

                 TextField usernameTF = new TextField();
                 TextField passwordTF = new PasswordField();

                 Label usernameLabel = new Label("Username");
                 Label passwordLabel = new Label("Password");

                 Label errorLabel = new Label();
                 errorLabel.setTextFill(Color.color(1, 0, 0));

                 Button confirmButton = new Button("Confirm");
                 Button cancelButton = new Button("Cancel");

                 popupGrid.add(usernameLabel, 0, 0);
                 popupGrid.add(usernameTF, 0, 1);
                 popupGrid.add(passwordLabel, 0, 2);
                 popupGrid.add(passwordTF, 0, 3);

                 GridPane selectingPane = new GridPane();
                 selectingPane.setHgap(10);
                 selectingPane.add(confirmButton, 0, 0);
                 selectingPane.add(cancelButton, 1, 0);
                 popupGrid.add(selectingPane, 0, 4);
                 popupGrid.add(errorLabel, 0, 5);



                 Stage registrationDialogue = new Stage();
                 registrationDialogue.setTitle("Registration");
                 registrationDialogue.initStyle(StageStyle.UTILITY);
                 Scene scene = new Scene(popupGrid, 217, 250, Color.GRAY);
                 scene.getRoot().styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt", AppWindow.fontSize));
                 registrationDialogue.setScene(scene);
                 registrationDialogue.show();

                 confirmButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                     @Override
                     public void handle(MouseEvent event) {
                         resetTimer();
                         if(usernameTF.getText().equals("") || passwordTF.getText().equals(""))
                         {
                             errorLabel.setText("One of the fields is empty");
                             return;
                         }

                         if(userManager.isUsernameAvailable(usernameTF.getText())){

                             currentUser = userManager.registerUser(userManager.getLatestUserID()+1,
                                     usernameTF.getText(),
                                     passwordEntry.getText(),
                                     "User");
                             cart = currentUser.getCart();
                             errorLabel.setVisible(false);
                             appWindow.setScene(AppWindow.Window.STORE);
                             registrationDialogue.hide();
                             appWindow.setScene(AppWindow.Window.STORE);
                         }
                         else{
                             errorLabel.setText("Error: Username is taken");
                             errorLabel.setVisible(true);
                         }
                     }
                 });

                 cancelButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                     @Override
                     public void handle(MouseEvent event) {
                         registrationDialogue.hide();
                         resetTimer();
                     }
                 });
             }
         });

         gridPane.add(login, 0, 0);
         gridPane.add(password, 0, 1);

         gridPane.add(loginEntry, 1, 0);
         GridPane.setColumnSpan(loginEntry, 3);

         gridPane.add(passwordEntry, 1, 1);
         GridPane.setColumnSpan(passwordEntry, 3);

 //        gridPane.add(loginButton, 1, 2);
 //        gridPane.add(loginButtonAnonymous, 2, 2);

         GridPane loginButtonsPane = new GridPane();
         loginButtonsPane.add(loginButton, 0, 0);
         loginButtonsPane.add(loginButtonAnonymous, 1, 0);
         loginButtonsPane.setHgap(5);
         loginButtonsPane.setVgap(5);
         gridPane.add(loginButtonsPane, 1, 2);
         gridPane.add(registerButton, 1, 3);

         gridPane.add(loginErrorLabel, 1, 4);


         return gridPane;
     }

     public Pane getStoreWindow(){
         GridPane gridPane = new GridPane();
         gridPane.setHgap(5);
         gridPane.setVgap(5);
         gridPane.setPadding(new Insets(10, 10, 10, 10));

         TableView productTable = new TableView();
         productTable.setPrefHeight(appWindow.getySize() - 220);
         productTable.setPrefWidth(appWindow.getxSize());
         productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

         TableColumn<Product, String> column0 = new TableColumn<>("Type");
         column0.setCellValueFactory(new PropertyValueFactory<>("productArt"));
         column0.setResizable(true);
         column0.setMinWidth(60);

         TableColumn<Product, String> column1 = new TableColumn<>("Product");
         column1.setCellValueFactory(new PropertyValueFactory<>("name"));
         column1.setMinWidth(50);

         TableColumn<Product, String> column2 = new TableColumn<>("Price $");
         column2.setCellValueFactory(new PropertyValueFactory<>("price"));

         TableColumn<Product, String> column3 = new TableColumn<>("Stock");
         column3.setCellValueFactory(new PropertyValueFactory<>("stock"));

         productTable.getColumns().add(column0);
         productTable.getColumns().add(column1);
         productTable.getColumns().add(column2);
         productTable.getColumns().add(column3);

         for(Product product: inventory.getProducts()){
             productTable.getItems().add(product);
         }

         productTable.getSortOrder().setAll(productTable.getColumns().get(0));

         // Multiple Purchase Button and related event
         Button addToCartBtn = new Button();
         addToCartBtn.setText("Add to Cart");
         addToCartBtn.setMaxWidth(Double.MAX_VALUE);
         addToCartBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) { handleAddToCart(productTable);
             resetTimer();}
         });

         Button checkoutButton = new Button();
         checkoutButton.setText("Checkout");
         checkoutButton.setMaxWidth(Double.MAX_VALUE);
         checkoutButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 if(cart.getCartProducts().isEmpty())
                 {
                     resetTimer();
                     emptyCartDialogue("There are no items in the cart to checkout");
                     return;
                 }

                 appWindow.setScene(AppWindow.Window.CHECKOUT);
             }
         });

         Button login = new Button();
         login.setText("Login");
         login.setMaxWidth(Double.MAX_VALUE);


         login.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 resetTimer();
                 currentUser = new User(0, "Anonymous");
                 cart = currentUser.getCart();
                 appWindow.setScene(AppWindow.Window.LOGIN);
                 Transaction T = new Transaction(); // Added
                 T.setSuccess(false);
                 T.setfailureReason("User logged out");
                 transactionStorage.addTransaction(T);
             }
         });

         Button sellerMenuButton = new Button("Manage Items");
         sellerMenuButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 appWindow.setScene(AppWindow.Window.SELLER);
                 resetTimer();
             }
         });

         if(currentUser instanceof Seller || currentUser instanceof Owner){
             sellerMenuButton.setVisible(true);
         }
         else{
             sellerMenuButton.setVisible(false);
         }

         Button cashierMenuButton = new Button("Manage Cash");
         cashierMenuButton.setVisible(false);
         cashierMenuButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 appWindow.setScene(AppWindow.Window.CASHIER);
                 resetTimer();}
         });

         if(currentUser instanceof Cashier || currentUser instanceof Owner){
             cashierMenuButton.setVisible(true);
         }
         else{
             cashierMenuButton.setVisible(false);
         }

         Button ownerMenuButton = new Button("Manage users");
         ownerMenuButton.setVisible(false);
         ownerMenuButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 resetTimer();
                 appWindow.setScene(AppWindow.Window.OWNER);
             }
         });

         if(currentUser instanceof Owner){
             ownerMenuButton.setVisible(true);
         }
         else{
             ownerMenuButton.setVisible(false);
         }
         userIdentityLabel = new Label();
         userIdentityLabel.setText(String.format("Logged as %s", currentUser.userName));

         Label lastBoughtItems = new Label("Last Bought Items: ");
         lastBoughtItems2 = new Label();
         items = "";
         for(String product: currentUser.getLastBoughtItems()){
             items += product + "\n";
         }

         lastBoughtItems2.setText(items);

         logoutButton = new Button("Logout");
         logoutButton.setVisible(false);
         logoutButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 resetTimer();
                 logoutUser(userIdentityLabel, logoutButton, sellerMenuButton, cashierMenuButton, ownerMenuButton);

                 items = "";
                 for(String product: currentUser.getLastBoughtItems()){
                     items += product + "\n";
                 }

                 lastBoughtItems2.setText(items);
             }
         });

         if(currentUser.getUserID() != 0) { logoutButton.setVisible(true); }


         gridPane.add(productTable, 0, 1);
         GridPane.setColumnSpan(productTable, 4);

         gridPane.add(addToCartBtn,0,2);
         gridPane.add(checkoutButton,1,2);

         GridPane adminMenuPane = new GridPane();
         adminMenuPane.setHgap(5);

         adminMenuPane.add(sellerMenuButton, 0, 2);
         adminMenuPane.add(cashierMenuButton, 1, 2);
         adminMenuPane.add(ownerMenuButton, 2, 2);
         gridPane.add(adminMenuPane, 2, 2);
         GridPane.setRowSpan(adminMenuPane, 2);
         GridPane.setValignment(adminMenuPane, VPos.BOTTOM);
         gridPane.setAlignment(Pos.BOTTOM_CENTER);

 //        GridPane lastItemsPane = new GridPane();
 //        lastItemsPane.add(lastBoughtItems, 0, 0);
 //        lastItemsPane.add(lastBoughtItems2, 1, 0);
 //        gridPane.add(lastItemsPane, 2, 3);
         gridPane.add(lastBoughtItems, 1, 3);
         gridPane.add(lastBoughtItems2, 2, 3);
         GridPane.setValignment(lastBoughtItems, VPos.TOP);



         gridPane.add(login, 0, 3);

 //        gridPane.add(sellerMenuButton, 0, 4);
 //        gridPane.add(ownerMenuButton, 1, 3);
 //        gridPane.add(cashierMenuButton, 1, 4);

         gridPane.add(userIdentityLabel, 0, 5);

         gridPane.add(logoutButton, 1, 5);

         return gridPane;
     }

     private void logoutUser(Label userIdentityLabel, Button logoutButton, Button sellerMenuButton, Button cashierMenuBtton, Button ownerMenuBtton) {
         currentUser = new User(0, "Anonymous");
         cart = currentUser.getCart();
         userIdentityLabel.setText("Logged as: Anonymous");
         logoutButton.setVisible(false);
         sellerMenuButton.setVisible(false);
         cashierMenuBtton.setVisible(false);
         ownerMenuBtton.setVisible(false);
     }

     private static void emptyCartDialogue(String contentText) {
         Dialog emptyCart = new Dialog();

         emptyCart.setContentText(contentText);
         emptyCart.getDialogPane().getButtonTypes().add(ButtonType.OK);
         emptyCart.showAndWait();
         return;
     }

     public Pane getCheckoutWindow(){
         GridPane gridPane = new GridPane();
         gridPane.setHgap(5);
         gridPane.setVgap(5);
         gridPane.setPadding(new Insets(10, 10, 10, 10));

         TableView cartTable = new TableView();
         cartTable.setPrefHeight(appWindow.getySize());
         cartTable.setPrefWidth(appWindow.getxSize());
         cartTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

         TableColumn<Product, String> column0 = new TableColumn<>("Type");
         column0.setCellValueFactory(new PropertyValueFactory<>("productType"));
         column0.setResizable(true);
         column0.setMinWidth(50);

         TableColumn<Product, String> column1 = new TableColumn<>("Product");
         column1.setCellValueFactory(new PropertyValueFactory<>("name"));
         column1.setMinWidth(50);

         TableColumn<Product, String> column2 = new TableColumn<>("Price");
         column2.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

         TableColumn<Product, String> column3 = new TableColumn<>("Quantity");
         column3.setCellValueFactory(new PropertyValueFactory<>("quantity"));

         cartTable.getColumns().add(column0);
         cartTable.getColumns().add(column1);
         cartTable.getColumns().add(column2);
         cartTable.getColumns().add(column3);

         for (CartProduct product: cart.getCartProducts()) {
             cartTable.getItems().add(product);
         }

         Button removeItemButton = new Button();
         removeItemButton.setText("Remove Item");
         removeItemButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 resetTimer();
                 CartProduct selectedProduct = (CartProduct) cartTable.getSelectionModel().getSelectedItem();
                 if(selectedProduct == null) { return; }

                 if(selectedProduct.getQuantity() > 0){
                     selectedProduct.reduceQuantity(1);
                     selectedProduct.calculateTotalPrice();

                     if(selectedProduct.getQuantity() <= 0){
                         cart.removeProduct(selectedProduct);
                         cartTable.getItems().remove(selectedProduct);
                     }

                     cart.updateTotal();
                     cartTable.refresh();
                 }
             }
         });

         Button cancelButton = new Button();
         cancelButton.setText("Cancel Order");
         cancelButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 resetTimer();
                 handleFailedTransactionCash();
                 appWindow.setScene(AppWindow.Window.STORE);
                 Transaction T = new Transaction();
                 T.setSuccess(false);
                 T.setfailureReason("User cancelled order");
                 transactionStorage.addTransaction(T);
                 appWindow.setScene(AppWindow.Window.STORE);
             }
         });

         Button payButton = new Button();
         payButton.setText("Pay");
         payButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 resetTimer();

                 if(cart.getCartProducts().isEmpty())
                 {
                     emptyCartDialogue("There are no items in the cart to pay for");
                     return;
                 }

                 GridPane popupGrid = new GridPane();
                 popupGrid.setVgap(10);
                 popupGrid.setHgap(10);
                 popupGrid.setPadding(new Insets(10, 10, 10, 10));

                 double total = cart.getTotal();
                 Label totalLabel = new Label();
                 totalLabel.setFont(new Font(25));
                 totalLabel.setText("Total to Pay: $" + cart.getTotalToPay());
                 GridPane.setColumnSpan(totalLabel, 3);

                 ComboBox<String> paymentSelection = new ComboBox<>();
                 paymentSelection.setPromptText("Select Payment Method");
                 paymentSelection.getItems().add("Pay by Cash");
                 paymentSelection.getItems().add("Pay by Card");
                 GridPane.setColumnSpan(paymentSelection, 3);

                 ComboBox<String> cashSelection = new ComboBox<>();
                 cashSelection.setPromptText("Select Value");
                 cashSelection.getItems().add("5c");
                 cashSelection.getItems().add("10c");
                 cashSelection.getItems().add("20c");
                 cashSelection.getItems().add("50c");
                 cashSelection.getItems().add("$1");
                 cashSelection.getItems().add("$2");
                 cashSelection.getItems().add("$5");
                 cashSelection.getItems().add("$10");
                 cashSelection.getItems().add("$20");
                 cashSelection.getItems().add("$50");
                 cashSelection.getItems().add("$100");

                 Button insertButton = new Button();
                 insertButton.setText("Insert Cash");

                 Button cashCancelButton = new Button("Return");

                 cashSelection.setVisible(false);
                 insertButton.setVisible(false);
                 cashCancelButton.setVisible(false);


                 Label cardNumberLabel = new Label();
                 cardNumberLabel.setText("Card Number");

                 TextField cardNumberTF = new PasswordField();

                 Label nameOnCardLabel = new Label();
                 nameOnCardLabel.setText("Cardholder Name");

                 TextField nameOnCardTF = new TextField();

                 Button confirmPaymentButton = new Button();
                 confirmPaymentButton.setText("Confirm Payment");

                 Button cardCancelButton = new Button("Return");

                 cardNumberLabel.setVisible(false);
                 cardNumberTF.setVisible(false);
                 nameOnCardLabel.setVisible(false);
                 nameOnCardTF.setVisible(false);
                 confirmPaymentButton.setVisible(false);
                 cardCancelButton.setVisible(false);

                 paymentSelection.setOnAction(new EventHandler<ActionEvent>() {
                     @Override
                     public void handle(ActionEvent event) {
                         if(paymentSelection.getValue().equals("Pay by Cash"))
                         {
                             cashSelection.setVisible(true);
                             insertButton.setVisible(true);
                             cashCancelButton.setVisible(true);

                             cardNumberLabel.setVisible(false);
                             cardNumberTF.setVisible(false);
                             nameOnCardLabel.setVisible(false);
                             nameOnCardTF.setVisible(false);
                             confirmPaymentButton.setVisible(false);
                             cardCancelButton.setVisible(false);
                         }

                         if(paymentSelection.getValue().equals("Pay by Card")){
                             cardNumberLabel.setVisible(true);
                             cardNumberTF.setVisible(true);
                             nameOnCardLabel.setVisible(true);
                             nameOnCardTF.setVisible(true);
                             confirmPaymentButton.setVisible(true);
                             cardCancelButton.setVisible(true);

                             if(userManager.checkIfCustomerHasExistingCard(currentUser.getUserName())){
                                 String[] userCard = userManager.getCustomerExistingCard(currentUser.getUserName());
                                 cardNumberTF.setText(userCard[0]);
                                 nameOnCardTF.setText(userCard[1]);
                             }
                             else{
                                 cardNumberTF.setText("");
                                 nameOnCardTF.setText("");
                             }

                             cashSelection.setVisible(false);
                             insertButton.setVisible(false);
                             cashCancelButton.setVisible(false);
                         }
                     }
                 });


                 popupGrid.add(totalLabel, 0, 0);
                 popupGrid.add(paymentSelection, 0, 1);

                 popupGrid.add(cashSelection, 0, 2);
                 popupGrid.add(insertButton, 0, 3);
                 popupGrid.add(cashCancelButton, 1, 3);

                 popupGrid.add(cardNumberLabel, 0, 2);
                 popupGrid.add(cardNumberTF, 0, 3);
                 popupGrid.add(nameOnCardLabel, 0, 4);
                 popupGrid.add(nameOnCardTF, 0, 5);
                 popupGrid.add(confirmPaymentButton, 0, 6);
                 popupGrid.add(cardCancelButton, 1, 6);

                 Stage payDialogue = new Stage();
                 payDialogue.setTitle("Payment");
                 payDialogue.initStyle(StageStyle.UTILITY);
                 Scene scene = new Scene(popupGrid, 354, 400, Color.GRAY);
                 scene.getRoot().styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt", AppWindow.fontSize));
                 payDialogue.setScene(scene);
                 payDialogue.show();

                 insertButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                     @Override
                     public void handle(MouseEvent event) {
                         resetTimer();
                         try {
                             switch (cashSelection.getValue()) {
                                 case "5c":
                                     cashInventory.acceptInsertedCash(new Currency("0.05c", 1));
                                     cart.subtractFromTotal(0.05);
                                     totalLabel.setText("Total to Pay: $" + cart.getTotalToPay());
                                     if (handleSuccessfulTransactionCash()) {
                                         payDialogue.hide();
                                         appWindow.setScene(AppWindow.Window.STORE);
                                         currentUser = new User(0, "Anonymous");
                                         cart = currentUser.getCart();
                                         userIdentityLabel.setText("Logged as: " + currentUser.getUserName());
                                         logoutButton.setVisible(false);
                                         appWindow.setScene(AppWindow.Window.STORE);
                                     }
                                     break;
                                 case "10c":
                                     cashInventory.acceptInsertedCash(new Currency("0.1c", 1));
                                     cart.subtractFromTotal(0.1);
                                     totalLabel.setText("Total to Pay: $" + cart.getTotalToPay());
                                     if (handleSuccessfulTransactionCash()) {
                                         payDialogue.hide();
                                         appWindow.setScene(AppWindow.Window.STORE);
                                         currentUser = new User(0, "Anonymous");
                                         cart = currentUser.getCart();
                                         userIdentityLabel.setText("Logged as: " + currentUser.getUserName());
                                         logoutButton.setVisible(false);
                                         appWindow.setScene(AppWindow.Window.STORE);
                                     }
                                     break;
                                 case "20c":
                                     cashInventory.acceptInsertedCash(new Currency("0.2c", 1));
                                     cart.subtractFromTotal(0.2);
                                     totalLabel.setText("Total to Pay: $" + cart.getTotalToPay());
                                     if (handleSuccessfulTransactionCash()) {
                                         payDialogue.hide();
                                         appWindow.setScene(AppWindow.Window.STORE);
                                         currentUser = new User(0, "Anonymous");
                                         cart = currentUser.getCart();
                                         userIdentityLabel.setText("Logged as: " + currentUser.getUserName());
                                         logoutButton.setVisible(false);
                                         appWindow.setScene(AppWindow.Window.STORE);
                                     }
                                     break;
                                 case "50c":
                                     cashInventory.acceptInsertedCash(new Currency("0.5c", 1));
                                     cart.subtractFromTotal(0.5);
                                     totalLabel.setText("Total to Pay: $" + cart.getTotalToPay());
                                     if (handleSuccessfulTransactionCash()) {
                                         payDialogue.hide();
                                         appWindow.setScene(AppWindow.Window.STORE);
                                         currentUser = new User(0, "Anonymous");
                                         cart = currentUser.getCart();
                                         userIdentityLabel.setText("Logged as: " + currentUser.getUserName());
                                         logoutButton.setVisible(false);
                                         appWindow.setScene(AppWindow.Window.STORE);
                                     }
                                     break;
                                 case "$1":
                                     cashInventory.acceptInsertedCash(new Currency("$1", 1));
                                     cart.subtractFromTotal(1);
                                     totalLabel.setText("Total to Pay: $" + cart.getTotalToPay());
                                     if (handleSuccessfulTransactionCash()) {
                                         payDialogue.hide();
                                         appWindow.setScene(AppWindow.Window.STORE);
                                         currentUser = new User(0, "Anonymous");
                                         cart = currentUser.getCart();
                                         userIdentityLabel.setText("Logged as: " + currentUser.getUserName());
                                         logoutButton.setVisible(false);
                                         appWindow.setScene(AppWindow.Window.STORE);
                                     }
                                     break;
                                 case "$2":
                                     cashInventory.acceptInsertedCash(new Currency("$2", 1));
                                     cart.subtractFromTotal(2);
                                     totalLabel.setText("Total to Pay: $" + cart.getTotalToPay());
                                     if (handleSuccessfulTransactionCash()) {
                                         payDialogue.hide();
                                         appWindow.setScene(AppWindow.Window.STORE);
                                         currentUser = new User(0, "Anonymous");
                                         cart = currentUser.getCart();
                                         userIdentityLabel.setText("Logged as: " + currentUser.getUserName());
                                         logoutButton.setVisible(false);
                                         appWindow.setScene(AppWindow.Window.STORE);
                                     }
                                     break;
                                 case "$5":
                                     cashInventory.acceptInsertedCash(new Currency("$5", 1));
                                     cart.subtractFromTotal(5);
                                     totalLabel.setText("Total to Pay: $" + cart.getTotalToPay());
                                     if (handleSuccessfulTransactionCash()) {
                                         payDialogue.hide();
                                         appWindow.setScene(AppWindow.Window.STORE);
                                         currentUser = new User(0, "Anonymous");
                                         cart = currentUser.getCart();
                                         userIdentityLabel.setText("Logged as: " + currentUser.getUserName());
                                         logoutButton.setVisible(false);
                                         appWindow.setScene(AppWindow.Window.STORE);
                                     }
                                     break;
                                 case "$10":
                                     cashInventory.acceptInsertedCash(new Currency("$10", 1));
                                     cart.subtractFromTotal(10);
                                     totalLabel.setText("Total to Pay: $" + cart.getTotalToPay());
                                     if (handleSuccessfulTransactionCash()) {
                                         payDialogue.hide();
                                         appWindow.setScene(AppWindow.Window.STORE);
                                         currentUser = new User(0, "Anonymous");
                                         cart = currentUser.getCart();
                                         userIdentityLabel.setText("Logged as: " + currentUser.getUserName());
                                         logoutButton.setVisible(false);
                                         appWindow.setScene(AppWindow.Window.STORE);
                                     }
                                     break;
                                 case "$20":
                                     cashInventory.acceptInsertedCash(new Currency("$20", 1));
                                     cart.subtractFromTotal(20);
                                     totalLabel.setText("Total to Pay: $" + cart.getTotalToPay());
                                     if (handleSuccessfulTransactionCash()) {
                                         payDialogue.hide();
                                         appWindow.setScene(AppWindow.Window.STORE);
                                         currentUser = new User(0, "Anonymous");
                                         cart = currentUser.getCart();
                                         userIdentityLabel.setText("Logged as: " + currentUser.getUserName());
                                         logoutButton.setVisible(false);
                                         appWindow.setScene(AppWindow.Window.STORE);
                                     }
                                     break;
                                 case "$50":
                                     cashInventory.acceptInsertedCash(new Currency("$50", 1));
                                     cart.subtractFromTotal(50);
                                     totalLabel.setText("Total to Pay: $" + cart.getTotalToPay());
                                     if (handleSuccessfulTransactionCash()) {
                                         payDialogue.hide();
                                         appWindow.setScene(AppWindow.Window.STORE);
                                         currentUser = new User(0, "Anonymous");
                                         cart = currentUser.getCart();
                                         userIdentityLabel.setText("Logged as: " + currentUser.getUserName());
                                         logoutButton.setVisible(false);
                                         appWindow.setScene(AppWindow.Window.STORE);
                                     }
                                     ;
                                     break;
                                 case "$100":
                                     cashInventory.acceptInsertedCash(new Currency("$100", 1));
                                     cart.subtractFromTotal(100);
                                     totalLabel.setText("Total to Pay: $" + cart.getTotalToPay());
                                     if (handleSuccessfulTransactionCash()) {
                                         payDialogue.hide();
                                         appWindow.setScene(AppWindow.Window.STORE);
                                         currentUser = new User(0, "Anonymous");
                                         cart = currentUser.getCart();
                                         userIdentityLabel.setText("Logged as: " + currentUser.getUserName());
                                         logoutButton.setVisible(false);
                                         appWindow.setScene(AppWindow.Window.STORE);
                                     }
                                     break;
                             }
                         }
                         catch (UnavailableChangeException e){
                             System.out.println(e);
                             handleFailedTransactionCash();
                             payDialogue.hide();
                             appWindow.setScene(AppWindow.Window.STORE);

                             Dialog changeError  = new Dialog();
                             changeError.setContentText("Error: No change available, please contact administrator");
                             changeError.getDialogPane().getButtonTypes().add(ButtonType.OK);
                             changeError.showAndWait();
                             Transaction T = new Transaction(); // Added
                             T.setSuccess(false);
                             T.setfailureReason("No change Avaiable");
                             transactionStorage.addTransaction(T);

                             appWindow.setScene(AppWindow.Window.STORE);
                         }
                     }
                 });

                 confirmPaymentButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                     @Override
                     public void handle(MouseEvent event) {
                         resetTimer();
                         if(cardReaderManager.checkCard(cardNumberTF.getText(), nameOnCardTF.getText())) {

                             cart.subtractFromTotal(cart.getTotal());
                             totalLabel.setText("Total to Pay: $" + cart.getTotalToPay());
                             handleSuccessfulTransactionCard();

                             payDialogue.hide();



                             if(currentUser.userID != 0 && !userManager.checkExistingCard(currentUser.getUserName(), cardNumberTF.getText(), nameOnCardTF.getText())){
                                 Alert saveCardAlert = new Alert(Alert.AlertType.CONFIRMATION);
                                 saveCardAlert.setTitle("Save Payment Method");
                                 saveCardAlert.setContentText("Would you like to save payment method?");
                                 ButtonType okButton = new ButtonType("Save", ButtonBar.ButtonData.YES);
                                 ButtonType noButton = new ButtonType("Do not save", ButtonBar.ButtonData.NO);
                                 Optional<ButtonType> result = saveCardAlert.showAndWait();
                                 if(result.get() == ButtonType.OK){
                                     userManager.saveUserCard(currentUser.getUserName(), cardNumberTF.getText(), nameOnCardTF.getText());
                                 }
                                 else{}
                             }

                             currentUser = new User(0, "Anonymous");
                             cart = currentUser.getCart();
                             userIdentityLabel.setText(currentUser.getUserName());
                             logoutButton.setVisible(false);

                             items = "";
                             for(String product: currentUser.getLastBoughtItems()){
                                 items += product + "\n";
                             }

                             lastBoughtItems2.setText(items);
                             appWindow.setScene(AppWindow.Window.STORE);
                         }
                         else{
                             Dialog changeError  = new Dialog();
                             changeError.setContentText("Error: Incorrect card information");
                             changeError.getDialogPane().getButtonTypes().add(ButtonType.OK);
                             changeError.showAndWait();
                         }
                     }
                 });

                 cashCancelButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                     @Override
                     public void handle(MouseEvent event) {
                         resetTimer();
                         payDialogue.hide();
                         cashInventory.returnInsertedCash();
                         cart.updateTotal();
                           /// Added


                     }
                 });

                 cardCancelButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                     @Override
                     public void handle(MouseEvent event) {
                         resetTimer();
                         payDialogue.hide();
                                              
                     }
                 });
             }
         });

         Button returnButton = new Button();
         returnButton.setText("Return");
         returnButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {

                 appWindow.setScene(AppWindow.Window.STORE);
                 appWindow.setScene(AppWindow.Window.STORE);
                 resetTimer();
             }
         });

         gridPane.add(cartTable, 0, 1);
         GridPane.setColumnSpan(cartTable, 3);

         gridPane.add(removeItemButton, 0, 2);
         gridPane.add(cancelButton,1, 2);
         gridPane.add(payButton, 2, 2);
         gridPane.add(returnButton, 0, 3);
 //
         return gridPane;
     }

     public GridPane getSellerMenu(){
         GridPane gridPane = new GridPane();
         gridPane.setHgap(5);
         gridPane.setVgap(5);
         gridPane.setPadding(new Insets(10, 10, 10, 10));

         TableView productTable = new TableView();
         productTable.setPrefHeight(appWindow.getySize());
         productTable.setPrefWidth(appWindow.getxSize());
         productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);



         TableColumn<Product, String> column1 = new TableColumn<>("ID");
         column1.setCellValueFactory(new PropertyValueFactory<>("productID"));

         TableColumn<Product, String> column2 = new TableColumn<>("Type");
         column2.setCellValueFactory(new PropertyValueFactory<>("productType"));
         column2.setResizable(true);
         column2.setMinWidth(32);

         TableColumn<Product, String> column3 = new TableColumn<>("Product");
         column3.setCellValueFactory(new PropertyValueFactory<>("name"));
         column3.setMinWidth(60);

         TableColumn<Product, String> column4 = new TableColumn<>("Price");
         column4.setCellValueFactory(new PropertyValueFactory<>("price"));

         TableColumn<Product, String> column5 = new TableColumn<>("Stock");
         column5.setCellValueFactory(new PropertyValueFactory<>("stock"));

 //        TableColumn<Product, String> column6 = new TableColumn<>("Sales");
 //        column5.setCellValueFactory(new PropertyValueFactory<>("totalSales"));

         productTable.getColumns().add(column1);
         productTable.getColumns().add(column2);
         productTable.getColumns().add(column3);
         productTable.getColumns().add(column4);
         productTable.getColumns().add(column5);

         for(Product product: inventory.getProducts()){
             productTable.getItems().add(product);
         }


         Button editProductButton = new Button("Edit Product");
         editProductButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 resetTimer();
                 Product selectedProduct = (Product) productTable.getSelectionModel().getSelectedItem();
                 if(selectedProduct == null) { return; }

                 GridPane popupGrid = new GridPane();
                 popupGrid.setVgap(10);
                 popupGrid.setHgap(10);
                 popupGrid.setPadding(new Insets(10, 10, 10, 10));

                 TextField productIDTF = new TextField(String.valueOf(selectedProduct.getProductID()));
 //                TextField productTypeTF = new TextField(selectedProduct.getProductType());

                 ComboBox<String> productTypeTF = new ComboBox<>();
                 productTypeTF.setPromptText(selectedProduct.getProductType());
                 productTypeTF.getItems().add("Drink");
                 productTypeTF.getItems().add("Chocolate");
                 productTypeTF.getItems().add("Chips");
                 productTypeTF.getItems().add("Candies");

                 TextField productNameTF = new TextField(selectedProduct.getName());
                 TextField productPriceTF = new TextField(String.valueOf(selectedProduct.getPrice()));

                 Button saveButton = new Button("Save");


                 popupGrid.add(productIDTF, 0, 0);
                 popupGrid.add(productTypeTF, 0, 1);
                 popupGrid.add(productNameTF, 0, 2);
                 popupGrid.add(productPriceTF, 0, 3);

                 popupGrid.add(saveButton, 0, 4);

                 Stage editDialogue = new Stage();
                 editDialogue.setTitle("Product Edit");
                 editDialogue.initStyle(StageStyle.UTILITY);
                 Scene scene = new Scene(popupGrid, 300, 265, Color.GRAY);
                 scene.getRoot().styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt", AppWindow.fontSize));
                 editDialogue.setScene(scene);
                 editDialogue.show();

                 saveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                     @Override
                     public void handle(MouseEvent event) {
                         resetTimer();

                         try{
                             if(Integer.valueOf(productIDTF.getText()) <= 0 || Double.valueOf(productPriceTF.getText()) <= 0){
                                 Dialog stockError  = new Dialog();
                                 stockError.setContentText("Error: Product ID or Price cannot be or lower than 0");
                                 stockError.getDialogPane().getButtonTypes().add(ButtonType.OK);
                                 stockError.showAndWait();
                                 productIDTF.setText(String.valueOf(selectedProduct.getProductID()));
                                 productPriceTF.setText(String.valueOf(selectedProduct.getPrice()));
                                 return;
                             }

                             selectedProduct.setProductID(Integer.valueOf(productIDTF.getText()));
                             selectedProduct.setPrice(Double.valueOf(productPriceTF.getText()));
                             selectedProduct.setProductType(productTypeTF.getValue());
                             selectedProduct.setProductName(productNameTF.getText());
                             selectedProduct.setProductArt(selectedProduct.getProductType());

                             productTable.refresh();

                             Inventory.saveToProductFile(inventory.getProducts());
                         }
                         catch (NumberFormatException e){
                             Dialog stockError  = new Dialog();
                             stockError.setContentText("Error: Cannot have non-numeric values for Product ID or Price");
                             stockError.getDialogPane().getButtonTypes().add(ButtonType.OK);
                             stockError.showAndWait();
                             productIDTF.setText(String.valueOf(selectedProduct.getProductID()));
                             productPriceTF.setText(String.valueOf(selectedProduct.getPrice()));
                             return;
                         }


                         editDialogue.hide();
                     }
                 });
             }
         });

         Button restockButton = new Button("Restock");
         restockButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 resetTimer();
                 Product selectedProduct = (Product) productTable.getSelectionModel().getSelectedItem();
                 if(selectedProduct == null) { return; }
                 if(selectedProduct.getStock() >= 15)
                 {
                     Dialog stockError  = new Dialog();
                     stockError.setContentText("Error: Cannot have more than 15 items in stock");
                     stockError.getDialogPane().getButtonTypes().add(ButtonType.OK);
                     stockError.showAndWait();
                     return;
                 }

                 TextInputDialog restockDialogue = new TextInputDialog();
                 restockDialogue.setContentText("Please enter amount to be re-stocked");

                 restockDialogue.showAndWait();
                 try {
                     if(Integer.parseInt(restockDialogue.getResult()) <= 0){
                         Dialog stockError = new Dialog();
                         stockError.setContentText("Error: Quantity must be greater than 0");
                         stockError.getDialogPane().getButtonTypes().add(ButtonType.OK);
                         stockError.showAndWait();
                         return;
                     }

                     if (selectedProduct.getStock() + Integer.parseInt(restockDialogue.getResult()) <= 15) {
                         selectedProduct.restock(Integer.parseInt(restockDialogue.getResult()));
                         productTable.refresh();
                         Inventory.saveToProductFile(inventory.getProducts());
                     }
                     else {
                         Dialog stockError = new Dialog();
                         stockError.setContentText("Error: Cannot have more than 15 items in stock");
                         stockError.getDialogPane().getButtonTypes().add(ButtonType.OK);
                         stockError.showAndWait();
                     }
                 }
                 catch (NumberFormatException e){
                     Dialog stockError  = new Dialog();
                     stockError.setContentText("Error: Quantity must be an integer");
                     stockError.getDialogPane().getButtonTypes().add(ButtonType.OK);
                     stockError.showAndWait();
                     return;
                 }
             }
         });

         Button removeStockButton = new Button("Remove From Stock");
         removeStockButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 resetTimer();
                 Product selectedProduct = (Product) productTable.getSelectionModel().getSelectedItem();
                 if(selectedProduct == null) { return; }
                 if(selectedProduct.getStock() <= 0)
                 {
                     Dialog stockError  = new Dialog();
                     stockError.setContentText("Error: Cannot have less than 0 items in stock");
                     stockError.getDialogPane().getButtonTypes().add(ButtonType.OK);
                     stockError.showAndWait();
                     return;
                 }

                 TextInputDialog restockDialogue = new TextInputDialog();
                 restockDialogue.setContentText("Please enter amount to be removed");

                 restockDialogue.showAndWait();
                 try {
                     if(Integer.parseInt(restockDialogue.getResult()) <= 0){
                         Dialog stockError = new Dialog();
                         stockError.setContentText("Error: Quantity must be greater than 0");
                         stockError.getDialogPane().getButtonTypes().add(ButtonType.OK);
                         stockError.showAndWait();
                         return;
                     }

                     if (selectedProduct.getStock() - Integer.parseInt(restockDialogue.getResult()) > 0) {
                         selectedProduct.reduceStock(Integer.parseInt(restockDialogue.getResult()));
                         productTable.refresh();
                         Inventory.saveToProductFile(inventory.getProducts());
                     }
                     else {
                         Dialog stockError = new Dialog();
                         stockError.setContentText("Error: Cannot have less than 0 items in stock");
                         stockError.getDialogPane().getButtonTypes().add(ButtonType.OK);
                         stockError.showAndWait();
                     }
                 }
                 catch (NumberFormatException e){
                     Dialog stockError  = new Dialog();
                     stockError.setContentText("Error: Quantity must be an integer");
                     stockError.getDialogPane().getButtonTypes().add(ButtonType.OK);
                     stockError.showAndWait();
                     return;
                 }
             }
         });


         Button returnButton = new Button("Return");
         returnButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 resetTimer();
                 appWindow.setScene(AppWindow.Window.STORE);
                 appWindow.setScene(AppWindow.Window.STORE);
             }
         });

         gridPane.add(productTable, 0, 0);
         GridPane.setColumnSpan(productTable, 4);

         gridPane.add(editProductButton, 0, 1);
         gridPane.add(restockButton, 1 , 1);
         gridPane.add(removeStockButton, 2, 1);
         gridPane.add(returnButton, 0, 2);

         return gridPane;
     }

     public GridPane getCashierMenu(){
         GridPane gridPane = new GridPane();
         gridPane.setHgap(5);
         gridPane.setVgap(5);
         gridPane.setPadding(new Insets(10, 10, 10, 10));

         TableView currencyTable = new TableView();
         currencyTable.setPrefHeight(appWindow.getySize());
         currencyTable.setPrefWidth(appWindow.getxSize());
         currencyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


         TableColumn<Product, String> column1 = new TableColumn<>("Cash Type");
         column1.setCellValueFactory(new PropertyValueFactory<>("name"));

         TableColumn<Product, String> column2 = new TableColumn<>("Quantity");
         column2.setCellValueFactory(new PropertyValueFactory<>("quantity"));

         currencyTable.getColumns().add(column1);
         currencyTable.getColumns().add(column2);

         for(Currency c: cashInventory.getCashInventory()){
             currencyTable.getItems().add(c);
         }

         Button editQuantityButton = new Button("Edit Quantity");
         editQuantityButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 resetTimer();
                 Currency selectedCurrency = (Currency) currencyTable.getSelectionModel().getSelectedItem();
                 if(selectedCurrency == null) { return; }

                 TextInputDialog restockDialogue = new TextInputDialog();
                 restockDialogue.setContentText("Please enter amount to be placed/Removed");

                 Optional<String> response = restockDialogue.showAndWait();

                 try {
                     if(response.isPresent()) {
                         if(Integer.parseInt(restockDialogue.getResult()) + selectedCurrency.getQuantity() < 0){
                             Dialog stockError  = new Dialog();
                             stockError.setContentText("Error: You are attempting to remove more quantity than available");
                             stockError.getDialogPane().getButtonTypes().add(ButtonType.OK);
                             stockError.showAndWait();
                             return;
                         }

                         selectedCurrency.increaseQuantity(Integer.valueOf(restockDialogue.getResult()));
                         currencyTable.refresh();
                         cashInventory.saveToCashFile(cashInventory.getCashInventory());
                     }
                     else{
                         return;
                     }
                 }
                 catch (NumberFormatException e){
                     Dialog stockError  = new Dialog();
                     stockError.setContentText("Error: Quantity must be an integer");
                     stockError.getDialogPane().getButtonTypes().add(ButtonType.OK);
                     stockError.showAndWait();
                     return;
                 }
             }
         });


         Button returnButton = new Button("Return");
         returnButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 resetTimer();
                 appWindow.setScene(AppWindow.Window.STORE);
                 appWindow.setScene(AppWindow.Window.STORE);
             }
         });

         gridPane.add(currencyTable, 0, 0);
         GridPane.setColumnSpan(currencyTable, 4);

         gridPane.add(editQuantityButton, 0, 1);
         gridPane.add(returnButton, 0, 2);

         return gridPane;
     }

     public GridPane getOwnerMenu(){
         GridPane gridPane = new GridPane();
         gridPane.setHgap(5);
         gridPane.setVgap(5);
         gridPane.setPadding(new Insets(10, 10, 10, 10));

         TableView userTable = new TableView();
         userTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
         userTable.setPrefHeight(appWindow.getySize());
         userTable.setPrefWidth(appWindow.getxSize());


         TableColumn<Product, String> column11 = new TableColumn<>("UserName");
         column11.setCellValueFactory(new PropertyValueFactory<>("userName"));

         TableColumn<Product, String> column22 = new TableColumn<>("Role");
         column22.setCellValueFactory(new PropertyValueFactory<>("role"));

         userTable.getColumns().add(column11);
         userTable.getColumns().add(column22);

         for(User c: Owner.user_report()){
             userTable.getItems().add(c);
         }

         Button changeRoleButton = new Button("Modify Role");
         changeRoleButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 resetTimer();
                 User selectedUser = (User) userTable.getSelectionModel().getSelectedItem();
                 if (selectedUser != null) {
                     System.out.println("SELECTED USER: " + selectedUser.getUserName());
                     System.out.println("SELECTED USER ROLE: " + selectedUser.getRole());

                     GridPane popupGrid = new GridPane();
                     popupGrid.setVgap(10);
                     popupGrid.setHgap(10);
                     popupGrid.setPadding(new Insets(10, 10, 10, 10));

                     Label usernameLabel = new Label("User: " + selectedUser.getUserName());

                     Label roleLabel = new Label("Role: ");

                     ComboBox<String> roleSelection = new ComboBox<>();

                     if(selectedUser instanceof Seller){
                         roleSelection.setPromptText("Seller");
                     }
                     else if(selectedUser instanceof Cashier)
                     {
                         roleSelection.setPromptText("Cashier");
                     }
                     else if(selectedUser instanceof Owner)
                     {
                         roleSelection.setPromptText("Owner");
                     }
                     else{
                         roleSelection.setPromptText("User");
                     }


                     roleSelection.getItems().add("User");
                     roleSelection.getItems().add("Seller");
                     roleSelection.getItems().add("Cashier");
                     roleSelection.getItems().add("Owner");

                     popupGrid.add(usernameLabel, 0, 0);

                     Button confirmButton = new Button("Confirm");
                     Button cancelButton = new Button("Cancel");


                     GridPane roleGrid = new GridPane();
                     roleGrid.add(roleLabel, 0, 0);
                     roleGrid.add(roleSelection, 1, 0);

                     popupGrid.add(roleGrid, 0, 1);

                     GridPane confirmGrid = new GridPane();
                     confirmGrid.setHgap(10);
                     confirmGrid.add(confirmButton, 0, 0);
                     confirmGrid.add(cancelButton, 1, 0);
                     popupGrid.add(confirmGrid, 0, 2);

                     Stage roleDialogue = new Stage();
                     roleDialogue.setTitle("Role Selection");
                     roleDialogue.initStyle(StageStyle.UTILITY);
                     Scene scene = new Scene(popupGrid, 217, 250, Color.GRAY);
                     scene.getRoot().styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt", AppWindow.fontSize));
                     roleDialogue.setScene(scene);
                     roleDialogue.show();

                     confirmButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                         @Override
                         public void handle(MouseEvent event) {
                             resetTimer();
                             Owner.changeRole(selectedUser.getUserName(), roleSelection.getValue());
                             appWindow.setScene(AppWindow.Window.OWNER);
                             roleDialogue.hide();
                             userTable.refresh();
                         }
                     });

                     cancelButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                         @Override
                         public void handle(MouseEvent event) {
                             resetTimer();
                             roleDialogue.hide();
                         }
                     });
                 }
             }
         });

         Button rmvUserBtn = new Button();
         rmvUserBtn.setText("Remove");
         rmvUserBtn.setMaxWidth(Double.MAX_VALUE);
         rmvUserBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 resetTimer();
                 User selectedUser = (User) userTable.getSelectionModel().getSelectedItem();
                 if(selectedUser == null) { return; }

                 Owner.remove_user(String.valueOf(selectedUser.getUserID()));
                 appWindow.setScene(AppWindow.Window.OWNER);
                 System.out.println("Removed user: " + selectedUser.getUserName());
             }
         });

         Label loginErrorLabel = new Label("Error: Incorrect Credentials");
         loginErrorLabel.setFont(new Font(15));
         loginErrorLabel.setTextFill(Color.color(1, 0, 0));
         loginErrorLabel.setVisible(false);

         Label login = new Label();
         login.setText("Username: ");

         Label password = new Label();
         password.setText("Password: ");

         TextField loginEntry = new TextField();
         TextField passwordEntry = new PasswordField();

         Button addUserBtn = new Button("Add User");
         addUserBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 TextInputDialog nameInput = new TextInputDialog("username");
                 nameInput.setHeaderText("New User");
                 Optional<String> result = nameInput.showAndWait();
                 TextInputDialog passInput = new TextInputDialog("password");
                 nameInput.setHeaderText("Password of new User");
                 Optional<String> res = passInput.showAndWait();
                 if(userManager.isUsernameAvailable(loginEntry.getText())){
                     loginErrorLabel.setVisible(false);
                     if(result.isPresent()) {
                         if(res.isPresent()) {
                             Owner.add_user(userManager.getLatestUserID()+1, nameInput.getResult(), passInput.getResult());
                             appWindow.setScene(AppWindow.Window.OWNER);
                         }
                     }
                 }
                 else{
                     loginErrorLabel.setText("Error: Username is taken");
                     loginErrorLabel.setVisible(true);
                 }
             }
         });


         Button returnButton = new Button("Return");
         returnButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 resetTimer();
                 appWindow.setScene(AppWindow.Window.STORE);
                 appWindow.setScene(AppWindow.Window.STORE);
             }
         });

         gridPane.add(userTable, 0, 0);
         GridPane.setColumnSpan(userTable, 4);

         gridPane.add(changeRoleButton, 3, 2);

         gridPane.add(returnButton, 0, 2);
         gridPane.add(rmvUserBtn, 1, 2);
         gridPane.add(addUserBtn, 2, 2);

         // gridPane.add(loginEntry, 1, 0);
         // GridPane.setColumnSpan(loginEntry, 3);

         // gridPane.add(passwordEntry, 1, 1);
         // GridPane.setColumnSpan(passwordEntry, 3);

         return gridPane;
     }

     private void handleAddToCart(TableView productTable) {
 //
         Product selectedProduct = (Product) productTable.getSelectionModel().getSelectedItem();
         if(selectedProduct == null) { return; }
 //
         TextInputDialog amountInput = new TextInputDialog("Input Desired Amount");
         amountInput.setHeaderText("Purchase Amount");
         amountInput.setTitle("Purchase Amount");
         Optional<String> result = amountInput.showAndWait();
         Integer amount = 0;

         if(result.isPresent()) {
             try {
                 amount = Integer.parseInt(amountInput.getResult());
             } catch (NumberFormatException e) {
                 System.out.println("That is not a valid number");
                 Dialog stockError = new Dialog();
                 stockError.setContentText("Error: Quantity must be an integer");
                 stockError.getDialogPane().getButtonTypes().add(ButtonType.OK);
                 stockError.showAndWait();
                 return;
             }
         }
         else{
             return;
         }

         if(amount < 0) {
             System.out.print("Please enter a valid amount");
             return;
         }

         if(cart.getCartProduct(selectedProduct.getProductID()) != null) {
             if (selectedProduct.getStock() < amount + cart.getCartProduct(selectedProduct.getProductID()).getQuantity()) {
                 emptyCartDialogue("Not enough requested items available in stock");
                 return;
             }
         }
         else{
             if(selectedProduct.getStock() < amount){
                 emptyCartDialogue("Not enough requested items available in stock");
                 return;
             }
         }


         if(cart.getCartProduct(selectedProduct.getProductID()) == null) {

             CartProduct cartProduct = new CartProduct(selectedProduct, amount);
             cart.addProduct(cartProduct);
             System.out.println("Added new " + amount + " " + selectedProduct.getName() + " to cart for $" + selectedProduct.getPrice() * amount);
         }
         else {
             cart.getCartProduct(selectedProduct.getProductID()).increaseQuantity(amount);
             cart.getCartProduct(selectedProduct.getProductID()).calculateTotalPrice();
             System.out.println("Added existing " + amount + " " + selectedProduct.getName() + " to cart for $" + selectedProduct.getPrice() * amount);
         }

         cart.updateTotal();


             productTable.refresh();
     }

     private boolean handleSuccessfulTransactionCash() throws UnavailableChangeException {
         if(cart.getTotalToPay() <= 0) {
             if(!cashInventory.checkAvailableChange(Math.abs(cart.getTotalToPay())))
             {
                 throw new UnavailableChangeException();
             }

             for (CartProduct cartProduct : cart.getCartProducts()) {
                 cartProduct.reduceProductStock(cartProduct.getQuantity());
                 cartProduct.raiseProductSales(cartProduct.getQuantity());
             }

             System.out.println("Remaining change to pay: " + cart.getTotalToPay());
             cashInventory.returnCash(Math.abs(cart.getTotalToPay()), cashInventory.getCashInventory());

             Transaction transaction = new Transaction();
             transaction.setSuccess(true);
             transaction.setPaymentmethod("Cash");
             transaction.setUserName(currentUser.getUserName());
             for(CartProduct product: cart.getCartProducts()){
                 if(!transaction.getProductList().contains(product.getName())) {
                     transaction.addProduct(product.getProductID() + " " + product.getName());
                     transaction.addProductQuant(product.getQuantity());
                     System.out.println(product.getName());
                 }
             }

             for(Integer integer: cashInventory.getChangeReturned()){
                 System.out.println(integer);
                 transaction.addChangeReturned(integer);
             }

             transaction.setAmountPaid(cart.getTotal());

             transactionStorage.addTransaction(transaction);
             transactionStorage.addTransaction(transaction, true);


             Inventory.saveToProductFile(inventory.getProducts());
             cashInventory.saveToCashFile(cashInventory.getCashInventory());

             for(CartProduct product: cart.getCartProducts()){
                 if(!currentUser.getLastBoughtItems().contains(product.getName())){
                     if(currentUser.getLastBoughtItems().size() == 5){
                         currentUser.getLastBoughtItems().remove(0);
                         ArrayList<String> temp = new ArrayList<>();
                         temp.addAll(currentUser.lastBoughtItems);
                         currentUser.getLastBoughtItems().clear();
                         currentUser.getLastBoughtItems().addAll(temp);
                     }
                     currentUser.getLastBoughtItems().add(product.getName());
                 }
             }
             currentUser.saveLastLoadItems();

             cart.clearCart();
             return true;
         }

         return false;
     }

     private void handleSuccessfulTransactionCard() {
         for (CartProduct cartProduct : cart.getCartProducts()) {
             cartProduct.reduceProductStock(cartProduct.getQuantity());
             cartProduct.raiseProductSales(cartProduct.getQuantity());
         }

         Transaction transaction = new Transaction();
         transaction.setSuccess(true);
         transaction.setPaymentmethod("Card");
         transaction.setUserName(currentUser.getUserName());
         for(CartProduct product: cart.getCartProducts()){
             if(!transaction.getProductList().contains(product.getName())) {
                 transaction.addProduct(product.getProductID() + " " + product.getName());
                 transaction.addProductQuant(product.getQuantity());
             }
         }

         for(Integer integer: cashInventory.getChangeReturned()){
             transaction.addChangeReturned(integer);
         }

         transaction.setAmountPaid(cart.getTotal());

         transactionStorage.addTransaction(transaction);
         transactionStorage.addTransaction(transaction, true);

         Inventory.saveToProductFile(inventory.getProducts());

         for(CartProduct product: cart.getCartProducts()){
             if(!currentUser.getLastBoughtItems().contains(product.getName())){
                 if(currentUser.getLastBoughtItems().size() == 5){
                     currentUser.getLastBoughtItems().remove(0);
                     ArrayList<String> temp = new ArrayList<>();
                     temp.addAll(currentUser.lastBoughtItems);
                     currentUser.getLastBoughtItems().clear();
                     currentUser.getLastBoughtItems().addAll(temp);
                 }
                 currentUser.getLastBoughtItems().add(product.getName());
             }
         }
         currentUser.saveLastLoadItems();

         cart.clearCart();
     }

     private void handleFailedTransactionCash(){
         cart.clearCart();
         cashInventory.returnInsertedCash();
     }

     private void handleFailedTransactionCard(){
         cart.clearCart();
     }

     Timeline mainTimeline = null;
     public void startTimeline(){
         mainTimeline = new Timeline(new KeyFrame(Duration.millis(17),
                 t -> this.startTimer()));
         mainTimeline.setCycleCount(Timeline.INDEFINITE);
         mainTimeline.play();
     }

     private void startTimer(){

         double elapsedTime = System.nanoTime() - startTime;
         double elapsedTimeSeconds = (double) elapsedTime / 1000000000;

         if(elapsedTimeSeconds >= 1) { elapsedSeconds++; startTime = System.nanoTime(); }

 //        System.out.println(elapsedSeconds);

         if (elapsedSeconds >= 60) {
             System.out.println("Reset");
             elapsedSeconds = 0;
             startTime = System.nanoTime();

             appWindow.setScene(AppWindow.Window.STORE);
             currentUser = new User(0, "Anonymous");
             cart = currentUser.getCart();

             appWindow.setScene(AppWindow.Window.STORE);
             currentUser = new User(0, "Anonymous");
             cart = currentUser.getCart();
         }
     }

     public void resetTimer(){
         elapsedSeconds = 0;
     }
 }
