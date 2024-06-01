module vsb.cs.java1.java1_pav0546_projekt {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.logging;
    requires org.json;
    requires static lombok;
    opens vsb.cs.java1.java1_pav0546_projekt.bdash to javafx.fxml;
    exports vsb.cs.java1.java1_pav0546_projekt.game_api;
    exports vsb.cs.java1.java1_pav0546_projekt.bdash;
    exports vsb.cs.java1.java1_pav0546_projekt.game_assets;
}