package files.test.files.rest.api.model;


public class FileReadedInformation {
    private long intCounts;
    private int intBytes;
    private int bytes;
    private String extensionName;

    public FileReadedInformation(String extensionName,long intCounts,int intBytes, int bytes){
        this.bytes = bytes;
        this.extensionName  = extensionName;
        this.intBytes = intBytes;
        this.intCounts = intCounts;

    }

    public long getIntCounts() {
        return intCounts;
    }

    public void setIntCounts(long intCounts) {
        this.intCounts = intCounts;
    }

    public int getIntBytes() {
        return intBytes;
    }

    public void setIntBytes(int intBytes) {
        this.intBytes = intBytes;
    }

    public int getBytes() {
        return bytes;
    }

    public void setBytes(int bytes) {
        this.bytes = bytes;
    }

    public String getExtensionName() {
        return extensionName;
    }

    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }
}
