/*
 * This component represent the tabbed pane items panel
 */
package loangui;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.google.common.eventbus.Subscribe;
import loanmain.CalcLoanItem;
import loanmain.ChangeBus;
import loanmain.DiffBus;
import loanmain.LoanItem;
import loanutils.FormatterFactory;
import loanutils.FrameUtils;
import static loanutils.MyBundle.translate;

/**
 * This component represent the tabbed pane items panel
 *
 * @author jean-blas imbert
 */
public class TabbedPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    /**
     * Monthly without insurance amount label
     */
    private JLabel menLabel = new JLabel("0");
    /**
     * Monthly insurance only label
     */
    private JLabel assLabel = new JLabel("0");
    /**
     * Monthly total amount label
     */
    private JLabel totLabel = new JLabel("0");
    /**
     * Cost value label
     */
    private JLabel menCostLabel = new JLabel("0");
    /**
     * Cost insurance label
     */
    private JLabel assCostLabel = new JLabel("0");
    /**
     * Cost total amount label
     */
    private JLabel totCostLabel = new JLabel("0");
    /**
     * Effective rate value label
     */
    private JLabel effLabel = new JLabel("0");
    /**
     * Percentage of the salary label
     */
    private JLabel pctLabel = new JLabel("0");
    /**
     * Year total amount label
     */
    private JLabel ytaLabel = new JLabel("0");

    /**
     * Constructor
     */
    public TabbedPanel() {
        setLayout(new GridLayout(1, 3, 5, 5));
        add(buildMonthlyPanel());
        add(buildCostPanel());
        add(buildDiversPanel());
    }

    /**
     * Fill the components with their respective values
     *
     * @param evt the event published by the maincode containing the item that changed.
     */
    @Subscribe
    public void itemChanged(final ChangeBus evt) {
        Double lMensHorsAss = CalcLoanItem.computeMensHorsAss(evt.getLoanItem());
        if (lMensHorsAss == null) {
            lMensHorsAss = 0D;
        }
        menLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lMensHorsAss.floatValue()));
        Double lMensAss = CalcLoanItem.computeMensAss(evt.getLoanItem());
        if (lMensAss == null) {
            lMensAss = 0D;
        }
        assLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lMensAss.floatValue()));
        Double lMens = lMensHorsAss + lMensAss;
        totLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lMens.floatValue()));
        Double lCoutHorsAss = lMensHorsAss * evt.getLoanItem().getDuree() * 12D - evt.getLoanItem().getAmount();
        menCostLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lCoutHorsAss.floatValue()));
        Double lCoutAss = lMensAss * evt.getLoanItem().getDuree() * 12D;
        assCostLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lCoutAss.floatValue()));
        Double lCout = lCoutHorsAss + lCoutAss + (evt.getLoanItem().getFrais() == null? 0D : evt.getLoanItem().getFrais());
        totCostLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lCout.floatValue()));
        Double lTauxEff = CalcLoanItem.calcTauxEff(evt.getLoanItem());
        effLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lTauxEff == null ? 0F : lTauxEff.floatValue()));
        Double lPctSalary = evt.getLoanItem().getSalary().equals(0F) ? 0F : lMens / evt.getLoanItem().getSalary() * 100D;
        pctLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lPctSalary.floatValue()));
        Double lPerYear = lMens * 12D;
        ytaLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lPerYear.floatValue()));
    }

    /**
     * Compute the real difference between two loan items
     *
     * @param evt the event published by the maincode containing the items that are diffed.
     */
    @Subscribe
    public void itemDiffed(final DiffBus evt) {
        Double lMensHorsAss1 = CalcLoanItem.computeMensHorsAss(evt.getItemOne());
        if (lMensHorsAss1 == null) {
            lMensHorsAss1 = 0D;
        }
        Double lMensHorsAss2 = CalcLoanItem.computeMensHorsAss(evt.getItemTwo());
        if (lMensHorsAss2 == null) {
            lMensHorsAss2 = 0D;
        }
        Double lDiffMensHorsAss = lMensHorsAss1 - lMensHorsAss2;
        menLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lDiffMensHorsAss.floatValue()));
        Double lMensAss1 = CalcLoanItem.computeMensAss(evt.getItemOne());
        if (lMensAss1 == null) {
            lMensAss1 = 0D;
        }
        Double lMensAss2 = CalcLoanItem.computeMensAss(evt.getItemTwo());
        if (lMensAss2 == null) {
            lMensAss2 = 0D;
        }
        Double lDiffMensAss = lMensAss1 - lMensAss2;
        assLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lDiffMensAss.floatValue()));
        Double lMens1 = lMensHorsAss1 + lMensAss1;
        Double lMens2 = lMensHorsAss2 + lMensAss2;
        Double lDiffMens = lMens1 - lMens2;
        totLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lDiffMens.floatValue()));
        Double lCoutHorsAss1 = lMensHorsAss1 * evt.getItemOne().getDuree() * 12D - evt.getItemOne().getAmount();
        Double lCoutHorsAss2 = lMensHorsAss2 * evt.getItemTwo().getDuree() * 12D - evt.getItemTwo().getAmount();
        Double lDiffCoutHorsAss = lCoutHorsAss1 - lCoutHorsAss2;
        menCostLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lDiffCoutHorsAss.floatValue()));
        Double lCoutAss1 = lMensAss1 * evt.getItemOne().getDuree() * 12D;
        Double lCoutAss2 = lMensAss2 * evt.getItemTwo().getDuree() * 12D;
        Double lDiffCoutAss = lCoutAss1 - lCoutAss2;
        assCostLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lDiffCoutAss.floatValue()));
        Double lCout1 = lCoutHorsAss1 + lCoutAss1;
        Double lCout2 = lCoutHorsAss2 + lCoutAss2;
        Double lDiffCout = lCout1 - lCout2 + evt.getItemOne().getFrais() - evt.getItemTwo().getFrais();
        totCostLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lDiffCout.floatValue()));
        Double lTauxEff1 = CalcLoanItem.calcTauxEff(evt.getItemOne());
        Double lTauxEff2 = CalcLoanItem.calcTauxEff(evt.getItemTwo());
        Double lDiffTauxEff = (lTauxEff1 == null ? 0D : lTauxEff1) - (lTauxEff2 == null ? 0D : lTauxEff2);
        effLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lDiffTauxEff.floatValue()));
        Double lPctSalary1 = evt.getItemOne().getSalary().equals(0F) ? 0F : lMens1 / evt.getItemOne().getSalary() * 100D;
        Double lPctSalary2 = evt.getItemTwo().getSalary().equals(0F) ? 0F : lMens2 / evt.getItemTwo().getSalary() * 100D;
        Double lDiffPctSalary = lPctSalary1 - lPctSalary2;
        pctLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lDiffPctSalary.floatValue()));
        Double lPerYear1 = lMens1 * 12D;
        Double lPerYear2 = lMens2 * 12D;
        Double lDiffPerYear = lPerYear1 - lPerYear2;
        ytaLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lDiffPerYear.floatValue()));
    }

    /**
     * Construct the panel that contains the monthly result components
     *
     * @return the filled panel
     */
    private JPanel buildMonthlyPanel() {
        JLabel lMenLabel = new JLabel(translate("mensualitesvalue"));
        lMenLabel.setToolTipText(translate("mensualitesvaluetooltip"));
        menLabel.setToolTipText(translate("mensualitesvaluetooltip"));
        JLabel lAssLabel = new JLabel(translate("mensualiteassurance"));
        lAssLabel.setToolTipText(translate("mensualiteassurancetooltip"));
        assLabel.setToolTipText(translate("mensualiteassurancetooltip"));
        JLabel lTotLabel = new JLabel(translate("mensualitetotal"));
        lTotLabel.setToolTipText(translate("mensualitetotaltooltip"));
        totLabel.setToolTipText(translate("mensualitetotaltooltip"));
        JPanel lPanel = new JPanel();
        lPanel.setBorder(BorderFactory.createTitledBorder(translate("mensualites")));
        lPanel.setToolTipText(translate("mensualitestooltip"));
        FrameUtils.buildPanelGroup(lPanel, new JComponent[][]{{lMenLabel, menLabel, new JLabel("\u20ac")},
            {lAssLabel, assLabel, new JLabel("\u20ac")},
            {lTotLabel, totLabel, new JLabel("\u20ac")}});
        return lPanel;
    }

    /**
     * Construct the panel that contains the cost result components
     *
     * @return the filled panel
     */
    private JPanel buildCostPanel() {
        JLabel lMenCostLabel = new JLabel(translate("costvalue"));
        lMenCostLabel.setToolTipText(translate("costvaluetooltip"));
        menCostLabel.setToolTipText(translate("costvaluetooltip"));
        JLabel lAssCostLabel = new JLabel(translate("costassurance"));
        lAssCostLabel.setToolTipText(translate("costassurancetooltip"));
        assCostLabel.setToolTipText(translate("costassurancetooltip"));
        JLabel lTotCostLabel = new JLabel(translate("costtotal"));
        lTotCostLabel.setToolTipText(translate("costtotaltooltip"));
        totCostLabel.setToolTipText(translate("costtotaltooltip"));
        JPanel lPanel = new JPanel();
        lPanel.setBorder(BorderFactory.createTitledBorder(translate("cost")));
        lPanel.setToolTipText(translate("costtooltip"));
        FrameUtils.buildPanelGroup(lPanel, new JComponent[][]{{lMenCostLabel, menCostLabel, new JLabel("\u20ac")},
            {lAssCostLabel, assCostLabel, new JLabel("\u20ac")},
            {lTotCostLabel, totCostLabel, new JLabel("\u20ac")}});
        return lPanel;
    }

    /**
     * Construct the panel that contains the divers result components
     *
     * @return the filled panel
     */
    private JPanel buildDiversPanel() {
        JLabel lEffLabel = new JLabel(translate("effvalue"));
        lEffLabel.setToolTipText(translate("effvaluetooltip"));
        effLabel.setToolTipText(translate("effvaluetooltip"));
        JLabel lPctLabel = new JLabel(translate("pctsalary"));
        lPctLabel.setToolTipText(translate("pctsalarytooltip"));
        pctLabel.setToolTipText(translate("pctsalarytooltip"));
        JLabel lYtaLabel = new JLabel(translate("yta"));
        lYtaLabel.setToolTipText(translate("ytatooltip"));
        ytaLabel.setToolTipText(translate("ytatooltip"));
        JPanel lPanel = new JPanel();
        lPanel.setBorder(BorderFactory.createTitledBorder(translate("divers")));
        lPanel.setToolTipText(translate("diverstooltip"));
        FrameUtils.buildPanelGroup(lPanel, new JComponent[][]{{lEffLabel, effLabel, new JLabel("%")},
            {lPctLabel, pctLabel, new JLabel("%")},
            {lYtaLabel, ytaLabel, new JLabel("\u20ac")}});
        return lPanel;
    }
}
