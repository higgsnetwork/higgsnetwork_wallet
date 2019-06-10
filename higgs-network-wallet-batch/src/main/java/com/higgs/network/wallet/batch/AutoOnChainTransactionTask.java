package com.higgs.network.wallet.batch;

public interface AutoOnChainTransactionTask extends Runnable {
    public static int STATUS_NOTRUN = 0;
    public static int STATUS_RUNNING = 1;
    public static int STATUS_STOPPED = 2;


    public void suspend();

    public void unsuspend();

    public void stop();

}