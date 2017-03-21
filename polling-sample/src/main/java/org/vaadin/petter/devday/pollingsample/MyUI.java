package org.vaadin.petter.devday.pollingsample;

import java.util.Optional;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class MyUI extends UI {

    private VerticalLayout layout;

    @Override
    protected void init(VaadinRequest request) {
        layout = new VerticalLayout();
        
        Label label = new Label("Here will be the messages from the backend:");
        label.addStyleName(ValoTheme.LABEL_H1);
		layout.addComponent(label);
        
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);
        addPollListener(event -> pollBackend());
        setPollInterval(1000);
    }

    private void pollBackend() {
        Logger.getLogger(MyUI.class.getName()).info("Polling backend from UI " + this);
        Optional<String> latestMessage = MyBackend.getLatestMessage();
        if (latestMessage.isPresent()) {
            layout.addComponent(new Label(latestMessage.get()));
        }
    }

    @WebServlet(urlPatterns = "/*")
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false, heartbeatInterval = 5)
    public static class Servlet extends VaadinServlet {
    }
}
