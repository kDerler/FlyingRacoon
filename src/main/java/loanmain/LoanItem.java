/*
 * This class represents the item of a loan, with its entries, options and results.
 */
package loanmain;

import com.google.common.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the item of a loan, with its entries, options and results.
 *
 * @author jean-blas imbert
 */
public final class LoanItem implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Suivant le type de loan, le calcul n'est pas le même.
     */
    public static enum LoanType implements Serializable {

        /**
         * Détermine la mensualité connaissant les 3 autres
         */
        MENSUALITE,
        /**
         * Détermine le taux connaissant les 3 autres
         */
        TAUX,
        /**
         * Détermine le montant connaissant les 3 autres
         */
        MONTANT,
        /**
         * Détermine la durée connaissant les 3 autres
         */
        DUREE
    }
    /**
     * The EventBus handling changes
     */
    private EventBus changePublisher = null;
    /**
     * The EventBus handling diffs
     */
    private EventBus diffPublisher = null;
    /**
     * The item loan type
     */
    private LoanType type = LoanType.MENSUALITE;
    /**
     * Name of this loan item
     */
    private String name = null;
    //Entries
    /**
     * The amount of the loan, in euros
     */
    private Float amount = 0F;
    /**
     * The rate of the loan, in percents
     */
    private Float taux = 0F;
    /**
     * The amount of one monthly payment, in euros
     */
    private Float mensualite = 0F;
    /**
     * The duration of the loan, in months
     */
    private Float duree = 0F;
    //Options
    /**
     * The bank fees, in euros
     */
    private Float frais = 0F;
    /**
     * The user monthly income, in euros
     */
    private Float salary = 0F;
    /**
     * The insurance of the loan, in percents
     */
    private Float insurance = 0F;

//Methods
    /**
     * Default constructor
     */
    public LoanItem() {
        changePublisher = new EventBus();
        diffPublisher = new EventBus();
    }

    /**
     * Clone this
     *
     * @return the clone of this
     */
    @Override
    public LoanItem clone() {
        LoanItem lClone = new LoanItem();
        lClone.setAmount(getAmount());
        lClone.setDuree(getDuree());
        lClone.setFrais(getFrais());
        lClone.setInsurance(getInsurance());
        lClone.setMensualite(getMensualite());
        lClone.setName(getName());
        lClone.setTaux(getTaux());
        lClone.setSalary(getSalary());
        lClone.setLoanType(getLoanType());
        return lClone;
    }

    /**
     * Return the string value of this in .csv format
     * @return the string value of this in .csv format
     */
    public String toCsv() {
        final String lSep = ";";
        StringBuilder lBuilder = new StringBuilder();
        Double lMensHorsAss = CalcLoanItem.computeMensHorsAss(this);
        Double lMensAss = CalcLoanItem.computeMensAss(this);
        Double lNotFee = CalcLoanItem.computeNotaryFee(this);
        Double lTauxEff = CalcLoanItem.calcTauxEff(this);
        lBuilder.append(this.getName()).append(lSep).append(this.getAmount()).append(lSep).append(this.getTaux())
                .append(lSep).append(this.getMensualite()).append(lSep).append(lMensHorsAss.floatValue())
                .append(lSep).append(lMensAss.floatValue()).append(lSep).append(this.getDuree()).append(lSep)
                .append(this.getFrais()).append(lSep).append(this.getSalary()).append(lSep)
                .append(this.getInsurance()).append(lSep).append(lNotFee.floatValue()).append(lSep)
                .append(this.getLoanType()).append(lSep).append(lTauxEff.floatValue());
        return lBuilder.toString();
    }

    public void addChangeListener(final Object subscriber) {
        changePublisher.register(subscriber);
    }
    public void removeChangeListener(final Object subscriber) {
        changePublisher.unregister(subscriber);
    }
    public void addDiffListener(final Object subscriber) {
        diffPublisher.register(subscriber);
    }
    public void removeDiffListener(final Object subscriber) {
        diffPublisher.unregister(subscriber);
    }
    public void fireItemChanged() {
        changePublisher.post(new ChangeBus(this));
    }
    public void fireItemDiffed(final LoanItem pItem1, final LoanItem pItem2) {
        diffPublisher.post(new DiffBus(pItem1, pItem2));
    }


//getters and setters
    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float pAmount) {
        this.amount = pAmount;
    }

    public Float getTaux() {
        return taux;
    }

    public void setTaux(Float pTaux) {
        this.taux = pTaux;
    }

    public Float getMensualite() {
        return mensualite;
    }

    public void setMensualite(Float pMensualite) {
        this.mensualite = pMensualite;
    }

    public Float getDuree() {
        return duree;
    }

    public void setDuree(Float pDuree) {
        this.duree = pDuree;
    }

    public Float getFrais() {
        return frais;
    }

    public void setFrais(Float pFrais) {
        this.frais = pFrais;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float pSalary) {
        this.salary = pSalary;
    }

    public Float getInsurance() {
        return insurance;
    }

    public void setInsurance(Float pInsurance) {
        this.insurance = pInsurance;
    }

    public void setName(final String pName) {
        name = pName;
    }

    public String getName() {
        return name;
    }

    public LoanType getLoanType() {
        return type;
    }

    public void setLoanType(LoanType pType) {
        this.type = pType;
    }
}
