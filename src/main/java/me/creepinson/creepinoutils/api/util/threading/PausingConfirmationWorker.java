package me.creepinson.creepinoutils.api.util.threading;

import javax.swing.*;

/**
 * @author Creepinson https:/theoparis.com/about
 **/
public class PausingConfirmationWorker extends Worker {
    public final GuiWorker gui;
    public final Thread previousThread;
    private boolean isConfirmed;

    public PausingConfirmationWorker(Thread previousThread, GuiWorker gui) {
        this.previousThread = previousThread;
        this.gui = gui;
    }

    @Override
    protected boolean processingIsComplete() {
        return isConfirmed;
    }

    @Override
    public Object getLock() {
        return previousThread;
    }

    @Override
    protected void process() {
        SwingUtilities.invokeLater(() -> {
            JComponent component = this.gui.makeGui();
            component.setVisible(true);
            if (component instanceof JOptionPane) {
                component.addPropertyChangeListener(b -> {
                    // TODO: add confirm button listener
                });
            }
        });
    }

    @Override
    protected void cleanUpResources() {

    }
}
