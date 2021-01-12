package com.main.glory.servicesImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.swing.*;
import javax.websocket.server.ServerEndpoint;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service("restoreDbServiceImp")
public class RestoreDbImpl {



    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.dbname}")
    private String dbname;

    private Runtime runtime;
    private Process process;
    public Boolean restoreDb() throws IOException, InterruptedException {

    //restore db
        try {
            File parent =new File("backup");

            if(!parent.exists())
                parent.mkdir();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            File backupFile = new File(parent+"/"+simpleDateFormat.format(new Date())+".sql");


            if(!backupFile.exists())
                backupFile.createNewFile();


            //String command = "mysql --user="+user+" --password="+password+"  "+dbname+" < " + backupFile;
            //String command = "mysql -u"+user+" -p < " + backupFile;
            //String command="mysqldump --user="+user+" --password="+password+" --databases "+dbname+" -r " + backupFile;

            String[] command = new String[]{"mysql", "--user=" + user, "--password=" + password, "-e", "source " + backupFile};

            //System.out.println(command);
            //runtime = Runtime.getRuntime();
            process = Runtime.getRuntime().exec(command);
            int exitValue = process.waitFor();
            //System.out.println("exit value: " + exitValue);
            if (exitValue == 0)
                return true;
            else
                return false;

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public Boolean backupDb() throws IOException {
        File parent =new File("backup");

        if(!parent.exists())
            parent.mkdir();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        File backupFile = new File(parent+"/"+simpleDateFormat.format(new Date())+".sql");


        if(!backupFile.exists())
            backupFile.createNewFile();
        String cmd="mysqldump --user="+user+" --password="+password+" --databases "+dbname+" -r " + backupFile;


        try {

            //runtime = Runtime.getRuntime();
            process = Runtime.getRuntime().exec(cmd);
            int exitValue = process.waitFor();


            if(exitValue==0)
                return true;
            else return false;


            /*System.out.println("exit value: " + exitValue);
            BufferedReader buf = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = buf.readLine()) != null) {
                System.out.println("exec response: " + line);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }

        return false;

    }
}
