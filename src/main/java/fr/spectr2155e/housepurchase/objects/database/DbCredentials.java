package fr.spectr2155e.housepurchase.objects.database;

public class DbCredentials {
    private String host;

    private String user;
    private String pass;
    private String dbName;
    private int port;

    public DbCredentials(String host, String user, String pass, String dbName, int port){
        this.host = host;
        this.user = user;
        this.pass = pass;
        this.dbName = dbName;
        this.port = port;
    }

    public String toURI(){
        final StringBuilder sb = new StringBuilder();

        sb.append("jdbc:mysql://")
                .append(host)
                .append(":")
                .append(port)
                .append("/")
                .append(dbName)
                .append("?allowPublicKeyRetrieval=true&autoReconnect=false&useSSL=false");

        return sb.toString();
    }

    public String getHost() {
        return host;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getDbName() {
        return dbName;
    }

    public int getPort() {
        return port;
    }
}
