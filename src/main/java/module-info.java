module com.example.java13_goodspiritswithfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.java13_goodspiritswithfx to javafx.fxml;
    exports com.example.java13_goodspiritswithfx;
    exports com.example.java13_goodspiritswithfx.universe;
    opens com.example.java13_goodspiritswithfx.universe to javafx.fxml;
}