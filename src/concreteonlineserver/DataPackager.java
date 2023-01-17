package concreteonlineserver;

/**
 *
 * @author Tyler Costa
 */
public class DataPackager {

    String orderNumber, contactName, address, phone, mixDesign, amountConcrete, timeSelect, message, terms, dateStamp, timeStamp, status;

    /*
    Fetches Data From MainDisplay
    Creates a file with all data
    Prepares file to be sent
     */
    public DataPackager(String orderNumber, String contactName, String address, String phone, String mixDesign, String amountConcrete, String timeSelect, String message, String terms, String dateStamp, String timeStamp, String status) {
        this.orderNumber = orderNumber;
        this.contactName = contactName;
        this.address = address;
        this.phone = phone;
        this.mixDesign = mixDesign;
        this.amountConcrete = amountConcrete;
        this.timeSelect = timeSelect;
        this.message = message;
        this.terms = terms;
        this.dateStamp = dateStamp;
        this.timeStamp = timeStamp;
        this.status = status;

        createFile();
    }

    public void createFile() {

        String formattedData;
        String fileName;

        fileName = orderNumber+"-"+contactName;
        formattedData = orderNumber + "|\n" + contactName + "|\n" + address + "|\n" + phone + "|\n" + mixDesign + "|\n" + amountConcrete + "|\n" + timeSelect
                + "|\n" + message + "|\n" + terms + "|\n" + dateStamp + "|\n" + timeStamp + "|\n" + status + "|\n";
        
        
        /*
        formattedData = orderNumber + "|" + contactName + "|" + address + "|" + phone + "|" + mixDesign + "|" + amountConcrete + "|" + timeSelect
                + "|" + message + "|" + terms + "|" + dateStamp + "|" + timeStamp + "|" + status + "|";
        */
        
//INPUT , TARGETFILE, APPEND TRUE/FALSE
        FileCreator wtf = new FileCreator(formattedData, fileName, false);
    }
}
