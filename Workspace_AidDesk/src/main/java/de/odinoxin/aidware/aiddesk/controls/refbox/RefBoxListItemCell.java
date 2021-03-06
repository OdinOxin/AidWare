package de.odinoxin.aidware.aiddesk.controls.refbox;

import de.odinoxin.aidware.aidcloud.provider.TranslatorProvider;
import de.odinoxin.aidware.aiddesk.plugins.RecordItem;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@link ListCell} for {@link RefBoxListItem}.
 *
 * @param <T> The type of the record.
 */
class RefBoxListItemCell<T extends RecordItem> extends ListCell<RefBoxListItem<T>> {

    private GridPane grdItem;
    private int matched;
    private int max;
    private boolean showID;

    public RefBoxListItemCell(int max, boolean showID) {
        this.max = max;
        this.showID = showID;
    }

    /**
     * Updates the content and style.
     *
     * @param item  The new record to represent.
     * @param empty Whether the record is a null item, or empty.
     */
    @Override
    protected void updateItem(RefBoxListItem<T> item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null && this.getIndex() <= this.max) {
            try {
                this.matched = 0;
                this.grdItem = FXMLLoader.load(RefBox.class.getResource("/controls/refboxlistitem.fxml"));

                Node tflID = this.grdItem.lookup("#ID");
                if (this.getIndex() < this.max) {
                    tflID.setVisible(showID);
                    tflID.setManaged(showID);
                    String idText = String.valueOf(item.getRecord().getId());
                    this.markup("ID", idText, item.getHighlight());
                    this.markup("Text", item.getText(), item.getHighlight());
                    this.markup("SubText", item.getSubText(), item.getHighlight());

                    if (item.getHighlight() != null)
                        for (String hightlight : item.getHighlight())
                            if (idText.equals(hightlight)) {
                                item.setMatch(Double.MAX_VALUE);
                                this.matched = -1;
                            }
                    if (this.matched >= 0) {
                        int length = idText.length();
                        if (item.getText() != null)
                            length += item.getText().length();
                        if (item.getSubText() != null)
                            length += item.getSubText().length();
                        item.setMatch((double) this.matched / length);
                    }
                } else {
                    item.setRecord(null);
                    tflID.setVisible(false);
                    tflID.setManaged(false);
                    this.markup("Text", TranslatorProvider.getTranslation("More items avaiable!"), null);
                    this.markup("SubText", TranslatorProvider.getTranslation("Load more items..."), null);
                }
                this.setGraphic(this.grdItem);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            this.setGraphic(null);
            this.setText(null);
        }
    }

    private void markup(String controlId, String value, String[] highlight) {
        if (value == null)
            value = "";

        TextFlow tfl;
        if (!controlId.equals("SubText")) {
            tfl = (TextFlow) this.grdItem.lookup("#" + controlId);
            hightlight(tfl, false, value, highlight);
        } else {
            VBox vbox = (VBox) this.grdItem.lookup("#" + controlId);
            String[] lines = value.split("\\r?\\n");
            for (String line : lines) {
                tfl = new TextFlow();
                hightlight(tfl, true, line, highlight);
                vbox.getChildren().add(tfl);
            }
        }
    }

    private void hightlight(TextFlow tfl, boolean subText, String value, String[] highlight) {
        if (tfl == null)
            return;

        if (highlight != null && highlight.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < highlight.length; i++)
                if (highlight[i] != null && !highlight[i].isEmpty()) {
                    sb.append(highlight[i]);
                    if (i < highlight.length - 1)
                        sb.append('|');
                }
            Pattern pattern = Pattern.compile(sb.toString(), Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(value);

            int prevEnd = 0;
            while (matcher.find()) {
                if (prevEnd < matcher.start())
                    this.addText(tfl, subText, value.substring(prevEnd, matcher.start()));
                Text bold = new Text(value.substring(matcher.start(), matcher.end()));
                if (subText)
                    bold.setStyle("-fx-font-size: 10pt; -fx-fill: darkslategray; -fx-font-weight: bold");
                else
                    bold.setStyle("-fx-font-weight: bold");
                tfl.getChildren().add(bold);
                prevEnd = matcher.end();
                this.matched += matcher.end() - matcher.start();
            }
            if (prevEnd < value.length())
                this.addText(tfl, subText, value.substring(prevEnd));
        } else
            this.addText(tfl, subText, value);
    }

    /**
     * Adds a {@link Text} the the given {@link TextFlow}.
     *
     * @param tfl     The {@link TextFlow} to add the new {@link Text}.
     * @param subText Whether the style for subtexts should be used.
     * @param text    The content.
     */
    private void addText(TextFlow tfl, boolean subText, String text) {
        Text txt = new Text(text);
        if (subText)
            txt.setStyle("-fx-font-size: 10pt; -fx-fill: darkslategray");
        tfl.getChildren().add(txt);
    }
}
