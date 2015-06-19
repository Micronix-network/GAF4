package it.micronixnetwork.gaf.util.kettle;

import it.micronixnetwork.gaf.service.task.GAFTask;

import java.util.HashMap;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.LogLevel;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;

/**
 * Task per l'ntegrzione con i jobs di kettle
 *
 * Permette l'esecuzione di un job specificando il file del job e un insieme di
 * parametri (variabili)
 *
 * @author Andrea Riboldi
 *
 */
public class ExecuteJob extends GAFTask {

    static {
        try {
            KettleEnvironment.init(false);
        } catch (KettleException e) {
            e.printStackTrace();
        }
    }

    private String fileName;
    private HashMap<String, String> vars;

    public ExecuteJob() {
        super();
    }

    @Override
    public void run() {
        try {
            info("Esecuzione TASK");
            debug("Caricamento configurazione kettle");

            debug("Fine caricamento configurazione kettle");
            JobMeta jobMeta = new JobMeta(fileName, null, null);

            // Creazione job
            Job job = new Job(null, jobMeta);
            job.getJobMeta().setInternalKettleVariables(job);

            if (log.isDebugEnabled()) {
                job.setLogLevel(LogLevel.DEBUG);
            } else {
                if (log.isInfoEnabled()) {
                    job.setLogLevel(LogLevel.BASIC);
                }
            }

            job.setName(Thread.currentThread().getName());

            if (vars != null) {
                for (String var : vars.keySet()) {
                    job.setVariable(var, vars.get(var));
                }
            }

            // Esecuzione del Thread kettle
            job.start();
            job.waitUntilFinished();

            if (job.getResult() != null && job.getResult().getNrErrors() != 0) {
                warn("Errore nel Task relativo al Job " + fileName);
            }

            // Mark del job come terminato
            job.setFinished(true);

            // Ripulitura
            jobMeta.eraseParameters();
            job.eraseParameters();

        } catch (Exception e) {
            error("Eccezione nel Task relativo al Job " + fileName, e);
        }

    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public HashMap<String, String> getVars() {
        return vars;
    }

    public void setVars(HashMap<String, String> vars) {
        this.vars = vars;
    }

    public static void main(String[] args) {
        ExecuteJob job = new ExecuteJob();
        job.setFileName("/Users/Andrea Riboldi/migration/incremental_migration.kjb");
        job.run();
    }

}
