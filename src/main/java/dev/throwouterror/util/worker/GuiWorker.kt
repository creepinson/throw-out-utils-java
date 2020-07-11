package dev.throwouterror.util.worker

import javax.swing.JComponent

/**
 * @author Creepinson https:/theoparis.com/about
 */
abstract class GuiWorker : Worker() {
    /**
     * Creates the gui for use in a PausingConfirmationWorker
     *
     * @return a swing gui component
     * @see ConfirmationGuiWorker
     */
    abstract fun makeGui(): JComponent?
}