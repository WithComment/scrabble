package app;

import javax.swing.*;

import entity.Board;
import interface_adapter.ViewManagerModel;
import interface_adapter.place_letter.PlaceLetterViewModel;
import view.PlayView;
import view.View;
import view.ViewManager;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        View view = new View();

        // Build the main program window, the main panel containing the
        // various cards, and the layout, and stitch them together.

        // The main application window.
        JFrame application = new JFrame("Login Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();

        // The various View objects. Only one view is visible at a time.
        JPanel views = new JPanel(cardLayout);
        application.add(views);

        // This keeps track of and manages which view is currently showing.
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        // The data for the views, such as username and password, are in the ViewModels.
        // This information will be changed by a presenter object that is reporting the
        // results from the use case. The ViewModels are observable, and will
        // be observed by the Views.
        PlaceLetterViewModel placeLetterViewModel = new PlaceLetterViewModel();

//        PlayView playView = new PlayView(placeLetterController, placeLetterViewModel);
//        views.add(playView, loggedInView.viewName);
//
//        viewManagerModel.setActiveView(signupView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }
}
