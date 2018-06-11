package loanmain;

public class ChangeBus {
    // TODO : Do we need a setter ?
    private LoanItem loanItem;

    public ChangeBus(LoanItem item){
        loanItem = item;
    }

    public LoanItem getLoanItem() {
        return loanItem;
    }
}
