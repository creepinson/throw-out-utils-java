package dev.throwouterror.util.worker

import javax.swing.JOptionPane

/**
 * A gui worker that asks for confirmation.
 * You can override makeGui in order to create a custom parent gui for the confirmation dialog.
 */
abstract class ConfirmationGuiWorker(private val confirmationMessage: String) : GuiWorker() {
    private var isConfirmed = false
    override fun processingIsComplete(): Boolean {
        return isConfirmed
    }

    override fun process() {
        val component = this.makeGui()
        component?.isVisible = true
        JOptionPane.showConfirmDialog(component, confirmationMessage, "Confirmation", JOptionPane.QUESTION_MESSAGE)
        isConfirmed = true
    }

    override fun cleanUpResources() {}

}