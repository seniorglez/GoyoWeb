
package hellofx;


import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


public class HelloFX extends Application {
        private WebView webView;
        private WebEngine webEngine;

    public void start(Stage stage) {
        ProgressBar progressBar = new ProgressBar();
        stage.setTitle("JavaFX WebView Example");
        webView = new WebView();
        webEngine = webView.getEngine();
        webEngine.load("https://gregoriofer.com/");
        progressBar.progressProperty().bind(webEngine.getLoadWorker().progressProperty());
        progressBar.visibleProperty().bind(webEngine.getLoadWorker().stateProperty().isEqualTo(Worker.State.RUNNING));
        executeJS();
        VBox vBox = new VBox(webView,progressBar);
        Scene scene = new Scene(vBox, 960, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void executeJS() {
        webEngine.getLoadWorker().stateProperty().addListener((e,o,n) -> {
            if (n == Worker.State.SUCCEEDED) {
                String js = "function navbarRandomizeColor() {\n" +
                        "    document.querySelector('#top-header').style.background = '#' + Math.random().toString(16).slice(-6);\n" +
                        "    document.querySelector('#main-header').style.background = '#' + Math.random().toString(16).slice(-6);\n" +
                        "}\n" +
                        "setInterval(navbarRandomizeColor, 100)";
                webEngine.executeScript(js);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}