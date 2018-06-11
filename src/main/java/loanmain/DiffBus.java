package loanmain;

public class DiffBus {
    // TODO : Do we need setters ?
    private LoanItem itemOne, itemTwo;

    public DiffBus(LoanItem item1, LoanItem item2){
        itemOne = item1;
        itemTwo = item2;
    }

    public LoanItem getItemOne() {
        return itemOne;
    }

    public LoanItem getItemTwo() {
        return itemTwo;
    }

}
