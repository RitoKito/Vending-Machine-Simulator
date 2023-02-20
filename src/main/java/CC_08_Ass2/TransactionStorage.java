package CC_08_Ass2;
import java.io.IOException;
import java.io.FileWriter;
// only implemented cancelled transactions at the moment. 

import java.util.ArrayList;

public class TransactionStorage {
    private ArrayList<Transaction> cancelledTransactions;
    private ArrayList<Transaction> successfulTransactions;
    private ArrayList<String> loadCancelled;
    private ArrayList<String> loadSuccessful;
    String productpathA;
    

    public TransactionStorage(){
        this.cancelledTransactions = new ArrayList<Transaction>();
        this.successfulTransactions = new ArrayList<Transaction>();
   
        
    }
    


    public void addTransaction(Transaction transaction){
        if (transaction.getSuccess()){
            this.successfulTransactions.add(transaction);
            productpathA = "./reports/successfulTransactions.csv";
        }
        else {
            this.cancelledTransactions.add(transaction);
            productpathA = "./reports/canceledTransactions.csv";
        }
        this.writeTransactions();
    }

    public void addTransaction(Transaction transaction, boolean cashReport){
        if (transaction.getSuccess()){
            this.successfulTransactions.add(transaction);
            productpathA = "./reports/cashTransactionReport.csv";
        }
        this.writeCashReport();
    }

    public void writeTransactions(){
        ///this.loadFromFile();
		try {
		    FileWriter myWriter = new FileWriter(productpathA,true);
			//for (String lineToWrite : this.loadCancelled){
			///	myWriter.write(lineToWrite + '\n');
			//}
            if(cancelledTransactions.size() > 0) {
                for (Transaction T : this.cancelledTransactions) {
                    myWriter.write(T.CancelledOutput() + '\n');
                }
                this.cancelledTransactions.clear();
            }
            else if(successfulTransactions.size() > 0){
                for (Transaction T : this.successfulTransactions) {
                    myWriter.write(T.successfulOutput() + '\n');
                }
                this.successfulTransactions.clear();
            }
		    myWriter.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
    }

    public void writeCashReport(){
        try {
            FileWriter myWriter = new FileWriter(productpathA,true);
            //for (String lineToWrite : this.loadCancelled){
            ///	myWriter.write(lineToWrite + '\n');
            //}
            for (Transaction T : this.successfulTransactions) {
                myWriter.write(T.cashReportOutput() + '\n');
            }
            this.successfulTransactions.clear();
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
