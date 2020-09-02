package edu.tamu.aser.tests.acc;

/**
 * @author Gena
 * @description
 * @date 2020/6/2 0002
 */
public class Acc {
    double checking = 0;
    double saving = 0;
    public Acc(double c, double s){
        setChk(c);
        setSav(s);
    }

    public Acc(Acc acc){
        setEqChk(acc);
        setEqSav(acc);
    }

    public boolean isLegal(){
        return (checking >= 0) && (saving >= 0);
    }

    public synchronized void countInterest(double rate){
        setChk(getChk() * (1.0 + rate));
        setSav(getSav() * (1.0 + rate));
    }

    synchronized void setChk(double c){
        checking = c;
    }

    synchronized double getChk(){
        return checking;
    }

    synchronized void setSav(double s){
        saving = s;
    }

    synchronized double getSav(){
        return saving;

    }

    synchronized void setEqChk(Acc acc){
        setChk(acc.checking);
    }

    synchronized void setEqSav(Acc acc){
        setSav(acc.saving);
    }

}
