package de.odinoxin.aiddesk;

import de.odinoxin.aidcloud.provider.Provider;
import de.odinoxin.aidcloud.provider.TranslatorProvider;
import de.odinoxin.aiddesk.controls.refbox.RefBox;
import de.odinoxin.aiddesk.controls.refbox.RefBoxListItem;
import de.odinoxin.aiddesk.controls.translateable.Button;
import de.odinoxin.aiddesk.dialogs.DecisionDialog;
import de.odinoxin.aiddesk.plugins.Plugin;
import de.odinoxin.aiddesk.plugins.RecordEditor;
import de.odinoxin.aiddesk.plugins.RecordItem;
import de.odinoxin.aiddesk.plugins.addresses.AddressEditor;
import de.odinoxin.aiddesk.plugins.contact.information.ContactInformationEditor;
import de.odinoxin.aiddesk.plugins.contact.types.ContactTypeEditor;
import de.odinoxin.aiddesk.plugins.countries.CountryEditor;
import de.odinoxin.aiddesk.plugins.dietform.DietFormEditor;
import de.odinoxin.aiddesk.plugins.languages.LanguageEditor;
import de.odinoxin.aiddesk.plugins.people.PersonEditor;
import javafx.beans.property.Property;
import javafx.scene.control.ButtonType;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

/**
 * Main menu of AidDesk
 */
public class MainMenu extends Plugin implements Provider<MainMenu.PluginItem> {

    private RefBox<PluginItem> refBoxPlugins;
    private Button btnLogot;
    private Button btnExit;

    /**
     * Avaiable plugins
     */
    private static final PluginItem[] PLUGIN_ITEMS = {
            new PluginItem("PersonEditor", PersonEditor.class, PluginItem.MASTERDATA),
            new PluginItem("AddressEditor", AddressEditor.class, PluginItem.MASTERDATA),
            new PluginItem("CountryEditor", CountryEditor.class, PluginItem.MASTERDATA),
            new PluginItem("LanguageEditor", LanguageEditor.class, PluginItem.MASTERDATA),
            new PluginItem("ContactTypeEditor", ContactTypeEditor.class, PluginItem.MASTERDATA),
            new PluginItem("ContactInformationEditor", ContactInformationEditor.class, PluginItem.MASTERDATA),
            new PluginItem("DietFormEditor", DietFormEditor.class, PluginItem.MASTERDATA),
    };

    public MainMenu() {
        super("/mainmenu.fxml", "Main menu");

        this.refBoxPlugins = (RefBox<PluginItem>) this.root.lookup("#refBoxPlugins");
        this.refBoxPlugins.setProvider(this);
        this.refBoxPlugins.recordProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.launch();
                this.refBoxPlugins.setRecord(null);
            }
        });
        this.btnLogot = (Button) this.root.lookup("#btnLogout");
        this.btnExit = (Button) this.root.lookup("#btnExit");

        this.btnLogot.setOnAction(ev -> {
            DecisionDialog dialog = new DecisionDialog(this, "Log out?", "Log out and close all related windows?");
            Optional<ButtonType> res = dialog.showAndWait();
            if (ButtonType.OK.equals(res.get())) {
                for (Plugin plugin : Plugin.getRunningPlugins())
                    plugin.close();
                this.close();
                new Login();
            }
        });
        Plugin.setButtonEnter(this.btnLogot);
        this.setOnCloseRequest(ev -> {
            DecisionDialog dialog = new DecisionDialog(this, "Exit?", "Exit AidDesk?");
            Optional<ButtonType> res = dialog.showAndWait();
            if (ButtonType.OK.equals(res.get())) {
                for (Plugin plugin : Plugin.getRunningPlugins())
                    plugin.close();
                this.close();
            }
            ev.consume();
        });
        this.btnExit.setOnAction(ev -> this.fireEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSE_REQUEST)));
        Plugin.setButtonEnter(this.btnExit);
        this.show();
        this.sizeToScene();
        this.centerOnScreen();
    }

    @Override
    public PluginItem get(int id) {
        return null;
    }

    @Override
    public PluginItem save(PluginItem item, PluginItem original) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public RefBoxListItem<PluginItem> getRefBoxItem(PluginItem item) {
        if (item == null)
            return null;
        return new RefBoxListItem<>(item, item.getName(), item.getSubText());
    }

    @Override
    public List<RefBoxListItem<PluginItem>> search(List<String> exprs, int max) {
        List<RefBoxListItem<PluginItem>> items = new ArrayList<>();
        for (PluginItem item : PLUGIN_ITEMS) {
            RefBoxListItem<PluginItem> refBoxItem = getRefBoxItem(item);
            if (exprs != null)
                for (String expr : exprs) {
                    expr = expr.toLowerCase();
                    if (refBoxItem.getText().toLowerCase().contains(expr) || refBoxItem.getSubText().toLowerCase().contains(expr)) {
                        items.add(refBoxItem);
                        break;
                    }
                }
            else
                items.add(refBoxItem);
        }
        return items;
    }

    @Override
    public RecordEditor<PluginItem> openEditor(PluginItem entity) {
        return null;
    }

    static class PluginItem extends RecordItem<Object> {
        private static int nextID = 0;
        private String name;
        private String subText;
        private Class<? extends Plugin> pluginClass;
        static final String MASTERDATA = TranslatorProvider.getTranslation("MasterData");

        public PluginItem(String name, Class<? extends Plugin> pluginClass, String subText) {
            this.setId(nextID++);
            this.name = TranslatorProvider.getTranslation(name);
            this.pluginClass = pluginClass;
            this.subText = subText;
        }

        @Override
        protected Object clone() {
            PluginItem item = new PluginItem(this.getName(), this.pluginClass, this.subText);
            item.setId(this.getId());
            return item;
        }

        public String getName() {
            return name;
        }

        public String getSubText() {
            return subText;
        }

        void launch() {
            try {
                if (this.pluginClass != null)
                    this.pluginClass.newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public Object toEntity() {
            return null;
        }

        @Override
        protected Hashtable<String, Property<?>> getProperties() {
            return null;
        }
    }
}
