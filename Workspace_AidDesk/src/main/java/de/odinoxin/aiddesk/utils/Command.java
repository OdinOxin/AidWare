package de.odinoxin.aiddesk.utils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

import java.util.UUID;

public class Command {
    private UUID id;
    private Callback execute;
    private BooleanProperty canExecute;
    private ObjectProperty<Node> node;

    public Command(Callback execute, BooleanProperty canExecute, ObjectProperty<Node> node) {
        this.id = UUID.randomUUID();
        this.execute = execute;
        this.canExecute = canExecute;
        this.node = node;
    }

    public UUID getId() {
        return id;
    }

    public void execute() {
        if (this.execute != null)
            execute.call();
    }

    public boolean canExecute() {
        if (this.canExecute != null)
            return this.canExecute.get();
        return true;
    }

    public BooleanProperty canExecuteProperty() {
        return canExecute;
    }

    public Node getNode() {
        if (node != null)
            return node.get();
        return null;
    }

    public ObjectProperty<Node> nodeProperty() {
        return node;
    }
}
