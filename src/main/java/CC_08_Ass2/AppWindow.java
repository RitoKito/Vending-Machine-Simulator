 package CC_08_Ass2;

 import javafx.beans.binding.Bindings;
 import javafx.beans.property.DoubleProperty;
 import javafx.beans.property.SimpleDoubleProperty;
 import javafx.event.EventHandler;
 import javafx.scene.Scene;
 import javafx.scene.input.MouseEvent;
 import javafx.scene.paint.Color;


 public class AppWindow {

     enum Window{
         LOGIN,
         STORE,
         CHECKOUT,
         SELLER,
         CASHIER,
         OWNER
     }

     private static Scene scene;
     public Scene getScene() { return scene; }
     SystemManager systemManager;
     WindowManager windowManager;

     private double xSize = 700;

     public double getxSize() {
         return xSize;
     }

     private double ySize = 850;

     public double getySize() {
         return ySize;
     }

     public static DoubleProperty fontSize = new SimpleDoubleProperty(11);

     public AppWindow(SystemManager systemManager) {
         this.systemManager = systemManager;
         this.windowManager = new WindowManager(this);
         windowManager.startTimeline();
         startApp();
     }

     private void startApp() {
         setScene(Window.STORE);
     }

     public void setScene(Window window){

         switch (window) {
             case LOGIN:
                 scene.setRoot(windowManager.getLoginWindow());
                 break;

             case STORE:
                 if(scene == null) {
                     scene = new Scene(windowManager.getStoreWindow(), xSize, ySize, Color.valueOf("GREY"));
                 }
                 else {
                     scene.setRoot(windowManager.getStoreWindow());
                 }
                 break;
             case CHECKOUT:
                 scene.setRoot(windowManager.getCheckoutWindow());
                 break;
             case SELLER:
                 scene.setRoot(windowManager.getSellerMenu());
                 break;
             case CASHIER:
                 scene.setRoot(windowManager.getCashierMenu());
                 break;
             case OWNER:
                 scene.setRoot(windowManager.getOwnerMenu());
                 break;
         }

         scene.setOnMousePressed(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 windowManager.resetTimer();
             }
         });

         scene.getRoot().styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt", fontSize));
     }
 }
