package dev.throwouterror.util.threading;

import javax.swing.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Creepinson https:/theoparis.com/about
 **/
public abstract class GuiWorker {
    public final Set<Object> parameters;

    /**
     * Creates the gui for use in a PausingConfirmationWorker
     *
     * @return a swing gui component
     * @see PausingConfirmationWorker
     */
    public abstract JComponent makeGui();

    public GuiWorker(Object... parameters) {
        this.parameters = Collections.unmodifiableSet(Arrays.stream(parameters).collect(Collectors.toSet()));
    }
}
